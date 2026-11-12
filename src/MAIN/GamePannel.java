package MAIN;

import Entity.Player;
import Entity.entity;
import Object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.concurrent.locks.LockSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePannel extends JPanel implements Runnable {

    // Screen Settings
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenwidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 720 pixels

    // World Map Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public int currentMap = 0; // 0=Village, 1=Forest, 2=Ruins, 3=Caves, 4=Dungeon, 5=Fortress

    // FPS & Thread
    public final int fps = 60;
    public Thread gameThread;

    // Game States
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;
    public static final int INVENTORY_STATE = 4;
    public static final int SHOP_STATE = 5;
    public static final int GAME_OVER_STATE = 6;
    public static final int VICTORY_STATE = 7;
    public int gameState = TITLE_STATE;

    // Subsystems
    public KeyHandler key;
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker checker;
    public AssetSetter aSetter;
    public UI ui;
    public EventHandler eventHandler;
    public QuestManager questManager;
    public SaveManager saveManager;
    public TileManager tileM;

    // Entities & Objects
    public Player player;
    public SuperObject[] obj = new SuperObject[40];
    public entity[] npc = new entity[15];
    public entity[] monster = new entity[25];
    public ArrayList<entity> projectileList = new ArrayList<>();
    public ArrayList<entity> entityList = new ArrayList<>();

    public GamePannel() {
        this.key = new KeyHandler(this);
        this.checker = new CollisionChecker(this);
        this.questManager = new QuestManager(this);
        this.saveManager = new SaveManager(this);
        this.eventHandler = new EventHandler(this);
        this.player = new Player(this, key);
        this.tileM = new TileManager(this);
        this.aSetter = new AssetSetter(this);
        this.ui = new UI(this);

        this.setPreferredSize(new Dimension(screenwidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);

        setupGame();
    }

    public void setupGame() {
        aSetter.setAll();
        gameState = TITLE_STATE;
    }

    public void resetGame() {
        currentMap = 0;
        player.setDefaultValues();
        player.setItems();
        questManager.reset();
        saveManager.openedChests.clear();
        saveManager.unlockedDoors.clear();
        aSetter.setAll();
    }

    public void respawnPlayer() {
        player.life = player.maxLife;
        player.alive = true;
        player.invincible = true;
        player.invincibleCounter = 0;
        player.collisionOn = false;

        switch (currentMap) {
            case 0: // The Forgotten Village (safe cobblestone in front of Goddess Shrine)
                player.worldX = 20 * tileSize;
                player.worldY = 23 * tileSize;
                playMusic(Sound.BGM_VILLAGE);
                break;
            case 1: // Whispering Forest (safe wide main road near village exit)
                player.worldX = 4 * tileSize;
                player.worldY = 25 * tileSize;
                playMusic(Sound.BGM_FOREST);
                break;
            case 2: // Ancient Ruins (safe stone floor in front of ruins shrine)
                player.worldX = 14 * tileSize;
                player.worldY = 37 * tileSize;
                playMusic(Sound.BGM_DUNGEON);
                break;
            case 3: // Crystal Caves (safe dry cavern floor at ruins entrance tunnel)
                player.worldX = 4 * tileSize;
                player.worldY = 12 * tileSize;
                playMusic(Sound.BGM_DUNGEON);
                break;
            case 4: // Shadow Dungeon (safe corridor floor at entrance stairs)
                player.worldX = 24 * tileSize;
                player.worldY = 45 * tileSize;
                playMusic(Sound.BGM_DUNGEON);
                break;
            case 5: // Lost King's Fortress (safe royal floor in front of final shrine)
                player.worldX = 13 * tileSize;
                player.worldY = 30 * tileSize;
                playMusic(Sound.BGM_BOSS);
                break;
            default:
                currentMap = 0;
                player.worldX = 20 * tileSize;
                player.worldY = 23 * tileSize;
                playMusic(Sound.BGM_VILLAGE);
                break;
        }

        player.directions = "down";
        if (key != null) {
            key.upPressed = false;
            key.downPressed = false;
            key.leftPressed = false;
            key.rightPressed = false;
            key.attackPressed = false;
            key.interactPressed = false;
        }
        aSetter.setMonstersForMap(currentMap);
    }

    public void StartGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final long drawInterval = 1000000000L / fps;
        long nextDrawTime = System.nanoTime();

        while (gameThread != null) {
            update();
            repaint();

            nextDrawTime += drawInterval;
            long remainingTime = nextDrawTime - System.nanoTime();
            if (remainingTime > 0) {
                LockSupport.parkNanos(remainingTime);
            } else if (remainingTime < -drawInterval) {
                // Drop excess catch-up frames after a stall to avoid a busy
                // loop that makes the game feel choppy and consumes a core.
                nextDrawTime = System.nanoTime();
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            // 1. Update Player
            player.update();

            // 2. Update NPCs
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            // 3. Update Monsters
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if (!monster[i].alive) {
                        monster[i] = null;
                    }
                }
            }

            // 4. Update Projectiles
            for (int i = 0; i < projectileList.size(); i++) {
                entity proj = projectileList.get(i);
                if (proj != null) {
                    if (proj.alive) {
                        proj.update();
                    } else {
                        projectileList.remove(i);
                        i--;
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == TITLE_STATE) {
            ui.draw(g2);
        } else {
            // 1. Draw Map Tiles
            tileM.draw(g2);

            // 2. Draw Interactive Objects
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // 3. Collect and Sort Entities by Y for correct isometric depth
            entityList.clear();
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) entityList.add(npc[i]);
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) entityList.add(monster[i]);
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) entityList.add(projectileList.get(i));
            }

            Collections.sort(entityList, new Comparator<entity>() {
                @Override
                public int compare(entity e1, entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // 4. Draw Sorted Entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // 5. Draw UI Overlay
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public int getCameraX() {
        if (player == null) return 0;
        int maxCamX = Math.max(0, worldWidth - screenwidth);
        int camX = player.worldX - (screenwidth / 2 - tileSize / 2);
        return Math.max(0, Math.min(camX, maxCamX));
    }

    public int getCameraY() {
        if (player == null) return 0;
        int maxCamY = Math.max(0, worldHeight - screenHeight);
        int camY = player.worldY - (screenHeight / 2 - tileSize / 2);
        return Math.max(0, Math.min(camY, maxCamY));
    }
}
