package MAIN;

import Object.*;
import Entity.entity;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class UI {

    private final GamePannel gp;
    private Graphics2D g2;

    public BufferedImage titleBg;
    public BufferedImage heart_full, heart_half, heart_blank;
    public BufferedImage coin_icon, key_icon;

    public int commandNum = 0;
    public int titleScreenState = 0; // 0=Main, 1=Controls/Guide, 2=Settings
    public String currentDialogue = "";

    // Inventory slot cursor
    public int slotCol = 0;
    public int slotRow = 0;

    // Game stats
    public int enemiesDefeated = 0;
    public int treasuresFound = 0;
    public double playTime = 0;

    public UI(GamePannel gp) {
        this.gp = gp;
        loadImages();
    }

    private void loadImages() {
        try {
            titleBg = loadImg("/src/title/title_bg.jpg");
            heart_full = loadImg("/src/ui/heart_full.png");
            heart_half = loadImg("/src/ui/heart_half.png");
            heart_blank = loadImg("/src/ui/heart_blank.png");
            coin_icon = loadImg("/src/ui/coin_icon.png");
            key_icon = loadImg("/src/ui/key_icon.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage loadImg(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) is = getClass().getResourceAsStream(path.replace("/src", ""));
            if (is != null) return ImageIO.read(is);
        } catch (Exception e) {}
        return null;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (gp.gameState == GamePannel.TITLE_STATE) {
            drawTitleScreen();
        } else if (gp.gameState == GamePannel.PLAY_STATE) {
            drawPlayScreen();
        } else if (gp.gameState == GamePannel.PAUSE_STATE) {
            drawPlayScreen();
            drawPauseScreen();
        } else if (gp.gameState == GamePannel.DIALOGUE_STATE) {
            drawPlayScreen();
            drawDialogueScreen();
        } else if (gp.gameState == GamePannel.INVENTORY_STATE) {
            drawPlayScreen();
            drawInventoryScreen();
        } else if (gp.gameState == GamePannel.SHOP_STATE) {
            drawPlayScreen();
            drawShopScreen();
        } else if (gp.gameState == GamePannel.GAME_OVER_STATE) {
            drawGameOverScreen();
        } else if (gp.gameState == GamePannel.VICTORY_STATE) {
            drawVictoryScreen();
        }
    }

    // ==========================================
    // TITLE SCREEN
    // ==========================================
    private void drawTitleScreen() {
        if (titleBg != null) {
            g2.drawImage(titleBg, 0, 0, gp.screenwidth, gp.screenHeight, null);
        } else {
            g2.setColor(new Color(20, 15, 30));
            g2.fillRect(0, 0, gp.screenwidth, gp.screenHeight);
        }

        if (titleScreenState == 0) { // Main Menu
            // Dark gradient overlay for menu readability
            g2.setColor(new Color(10, 8, 20, 170));
            g2.fillRoundRect(gp.screenwidth / 2 - 220, 360, 440, 310, 25, 25);
            g2.setColor(new Color(215, 175, 55));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(gp.screenwidth / 2 - 220, 360, 440, 310, 25, 25);

            // Subtitle
            g2.setFont(new Font("Georgia", Font.BOLD, 22));
            String sub = "ANCIENT CORRUPTION AWAITS";
            int x = getXForCenteredText(sub);
            g2.setColor(new Color(255, 220, 120));
            g2.drawString(sub, x, 400);

            // Menu options
            String[] options = {"NEW GAME", "CONTINUE GAME", "CONTROLS & GUIDE", "SETTINGS", "QUIT"};
            g2.setFont(new Font("Arial", Font.BOLD, 20));

            for (int i = 0; i < options.length; i++) {
                int optY = 445 + i * 44;
                int optX = getXForCenteredText(options[i]);

                if (commandNum == i) {
                    // Selection highlight bar
                    g2.setColor(new Color(255, 215, 60, 60));
                    g2.fillRoundRect(gp.screenwidth / 2 - 180, optY - 26, 360, 36, 12, 12);
                    g2.setColor(new Color(255, 230, 90));
                    g2.drawString(">  " + options[i] + "  <", optX - 24, optY);
                } else {
                    g2.setColor(new Color(210, 210, 220));
                    g2.drawString(options[i], optX, optY);
                }
            }

        } else if (titleScreenState == 1) { // Controls & Guide
            drawSubWindow(100, 80, 760, 560);
            g2.setFont(new Font("Georgia", Font.BOLD, 28));
            g2.setColor(new Color(255, 215, 60));
            g2.drawString("CONTROLS & ADVENTURE GUIDE", 240, 130);

            g2.setFont(new Font("Arial", Font.PLAIN, 18));
            g2.setColor(Color.WHITE);
            int gy = 180;
            g2.drawString("Movement:         W, A, S, D  or  Arrow Keys", 140, gy); gy += 38;
            g2.drawString("Attack / Slash:   SPACE  or  J", 140, gy); gy += 38;
            g2.drawString("Interact / Talk:  E  (Chests, Doors, Signs, Shrines, NPCs)", 140, gy); gy += 38;
            g2.drawString("Inventory:        I  or  TAB  (Use items, equip boots & swords)", 140, gy); gy += 38;
            g2.drawString("Pause Game:       P  or  ESCAPE", 140, gy); gy += 38;
            g2.drawString("Mute Audio:       M  (In Settings)", 140, gy); gy += 45;

            g2.setColor(new Color(255, 220, 100));
            g2.drawString("Adventure Mission:", 140, gy); gy += 30;
            g2.setFont(new Font("Arial", Font.ITALIC, 16));
            g2.setColor(new Color(220, 220, 230));
            g2.drawString("- Speak to Elder Vaelen in the starting village to begin your journey.", 140, gy); gy += 26;
            g2.drawString("- Search the Whispering Forest for the Ruins Key to enter the Ancient Ruins.", 140, gy); gy += 26;
            g2.drawString("- Slay the Ruins Guardian to reclaim the radiant Sunstone Relic.", 140, gy); gy += 26;
            g2.drawString("- Solve the 3-Crystal puzzle in Crystal Caves and claim the Shadow Orb.", 140, gy); gy += 26;
            g2.drawString("- Slay King Malakor in the Lost King's Fortress to restore peace!", 140, gy); gy += 40;

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(new Color(255, 215, 60));
            g2.drawString("Press ESCAPE or ENTER to return", 320, gy);

        } else if (titleScreenState == 2) { // Settings
            drawSubWindow(200, 180, 560, 360);
            g2.setFont(new Font("Georgia", Font.BOLD, 28));
            g2.setColor(new Color(255, 215, 60));
            g2.drawString("GAME SETTINGS", 340, 235);

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            String soundStatus = gp.music.isMuted() ? "[ MUTED ]" : "[ ENABLED ]";
            g2.drawString("Sound & Music:     " + soundStatus, 260, 310);
            g2.drawString("Resolution:        960 x 720 (60 FPS)", 260, 360);
            g2.drawString("Press 'M' to Toggle Audio On/Off", 260, 420);

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(new Color(255, 215, 60));
            g2.drawString("Press ESCAPE or ENTER to return", 300, 480);
        }
    }

    // ==========================================
    // PLAY SCREEN HUD
    // ==========================================
    private void drawPlayScreen() {
        playTime += 1.0 / 60.0;

        // 1. Draw Player Hearts (Top-Left)
        int x = 20;
        int y = 20;
        int i = 0;
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, 32, 32, null);
            i++;
            x += 36;
        }

        // Draw current HP
        x = 20;
        y = 20;
        i = 0;
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, 32, 32, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, 32, 32, null);
            }
            i++;
            x += 36;
        }

        // 2. Draw Coins & Keys (Below hearts)
        y = 62;
        if (coin_icon != null) g2.drawImage(coin_icon, 20, y - 4, 28, 28, null);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(255, 215, 50));
        g2.drawString("Gold: " + gp.player.coin, 54, y + 17);

        // Keys counter
        int keyCount = 0;
        for (SuperObject item : gp.player.inventory) {
            if (item != null && item.name.contains("Key")) keyCount++;
        }
        if (key_icon != null) g2.drawImage(key_icon, 160, y - 4, 28, 28, null);
        g2.setColor(new Color(220, 230, 255));
        g2.drawString("Keys: " + keyCount, 194, y + 17);

        // 3. Draw Quest Tracker Banner (Top-Right)
        String obj = gp.questManager.getCurrentObjective();
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        int strW = g2.getFontMetrics().stringWidth(obj);
        int qx = gp.screenwidth - strW - 40;
        int qy = 20;

        g2.setColor(new Color(15, 12, 24, 200));
        g2.fillRoundRect(qx, qy, strW + 24, 34, 12, 12);
        g2.setColor(new Color(255, 215, 60));
        g2.drawRoundRect(qx, qy, strW + 24, 34, 12, 12);
        g2.drawString(obj, qx + 12, qy + 22);

        // 4. Area Name Banner
        String areaName = getAreaName(gp.currentMap);
        g2.setColor(new Color(200, 210, 230));
        g2.setFont(new Font("Georgia", Font.ITALIC, 14));
        g2.drawString("Area: " + areaName, 20, gp.screenHeight - 20);

        // 5. Boss Health Bar (if fighting Guardian or Corrupted King)
        drawBossHealthBar();

        // 6. Contextual Interaction Prompt
        drawContextualPrompt();
    }

    private String getAreaName(int map) {
        switch (map) {
            case 0: return "The Forgotten Village";
            case 1: return "Whispering Forest";
            case 2: return "Ancient Ruins";
            case 3: return "Crystal Caves";
            case 4: return "Shadow Dungeon";
            case 5: return "Lost King's Fortress";
            default: return "Unknown Land";
        }
    }

    private void drawBossHealthBar() {
        entity boss = null;
        for (entity m : gp.monster) {
            if (m != null && m.alive && m.type == 4) {
                boss = m;
                break;
            }
        }
        if (boss != null) {
            int barW = 400;
            int barH = 20;
            int bx = gp.screenwidth / 2 - barW / 2;
            int by = 60;

            // Frame
            g2.setColor(new Color(20, 10, 25, 220));
            g2.fillRoundRect(bx - 4, by - 24, barW + 8, barH + 32, 10, 10);
            g2.setColor(new Color(225, 175, 40));
            g2.drawRoundRect(bx - 4, by - 24, barW + 8, barH + 32, 10, 10);

            // Name
            g2.setFont(new Font("Georgia", Font.BOLD, 15));
            g2.setColor(new Color(255, 225, 120));
            g2.drawString(boss.name, bx + 10, by - 6);

            // Health fill
            g2.setColor(new Color(60, 20, 20));
            g2.fillRect(bx, by, barW, barH);
            double scale = (double) barW / boss.maxLife;
            int fill = (int) (scale * boss.life);
            g2.setColor(new Color(230, 30, 45));
            g2.fillRect(bx, by, fill, barH);
        }
    }

    private void drawContextualPrompt() {
        int cx = gp.player.worldX;
        int cy = gp.player.worldY;
        // check nearby interactable
        boolean canInteract = false;
        String prompt = "[E] Interact";

        for (SuperObject o : gp.obj) {
            if (o != null) {
                int dist = Math.abs(o.worldX - cx) + Math.abs(o.worldY - cy);
                if (dist < gp.tileSize * 1.5) {
                    canInteract = true;
                    if (o instanceof OBJ_Chest) prompt = o.opened ? "[E] Empty Chest" : "[E] Open Chest";
                    else if (o instanceof OBJ_Door) prompt = "[E] Unlock Door";
                    else if (o instanceof OBJ_Sign) prompt = "[E] Read Sign";
                    else if (o instanceof OBJ_Statue) prompt = "[E] Pray & Save";
                    else if (o instanceof OBJ_Switch) prompt = "[E] Step on Switch";
                    break;
                }
            }
        }
        if (!canInteract) {
            for (entity n : gp.npc) {
                if (n != null) {
                    int dist = Math.abs(n.worldX - cx) + Math.abs(n.worldY - cy);
                    if (dist < gp.tileSize * 1.5) {
                        canInteract = true;
                        prompt = "[E] Speak with " + n.name;
                        break;
                    }
                }
            }
        }

        if (canInteract) {
            g2.setFont(new Font("Arial", Font.BOLD, 15));
            int strW = g2.getFontMetrics().stringWidth(prompt);
            int px = gp.screenwidth / 2 - strW / 2 - 14;
            int py = gp.screenHeight - 70;

            g2.setColor(new Color(15, 12, 24, 210));
            g2.fillRoundRect(px, py, strW + 28, 36, 12, 12);
            g2.setColor(new Color(255, 215, 60));
            g2.drawRoundRect(px, py, strW + 28, 36, 12, 12);
            g2.drawString(prompt, px + 14, py + 24);
        }
    }

    // ==========================================
    // DIALOGUE SCREEN
    // ==========================================
    private void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.screenwidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(new Font("Georgia", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);

        int textY = y + 36;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x + 30, textY);
            textY += 28;
        }

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(new Color(255, 215, 60));
        g2.drawString("[ENTER / E to continue]", x + width - 180, y + height - 16);
    }

    // ==========================================
    // INVENTORY SCREEN
    // ==========================================
    private void drawInventoryScreen() {
        // Frame
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 8;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Title
        g2.setFont(new Font("Georgia", Font.BOLD, 22));
        g2.setColor(new Color(255, 215, 60));
        g2.drawString("INVENTORY (" + gp.player.inventory.size() + "/20)", frameX + 24, frameY + 36);

        // Slots
        final int slotXstart = frameX + 24;
        final int slotYstart = frameY + 54;
        int slotSize = gp.tileSize;

        // Draw items in slots (5 cols x 4 rows)
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            SuperObject item = gp.player.inventory.get(i);
            int col = i % 5;
            int row = i / 5;
            int slotX = slotXstart + (col * (slotSize + 8));
            int slotY = slotYstart + (row * (slotSize + 8));

            // Slot background
            g2.setColor(new Color(45, 40, 55, 180));
            g2.fillRoundRect(slotX, slotY, slotSize, slotSize, 8, 8);
            g2.setColor(new Color(80, 75, 95));
            g2.drawRoundRect(slotX, slotY, slotSize, slotSize, 8, 8);

            if (item != null && item.image != null) {
                g2.drawImage(item.image, slotX + 2, slotY + 2, slotSize - 4, slotSize - 4, null);
            }
        }

        // Draw Cursor
        int cursorX = slotXstart + (slotCol * (slotSize + 8));
        int cursorY = slotYstart + (slotRow * (slotSize + 8));
        g2.setColor(new Color(255, 220, 60));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, slotSize, slotSize, 8, 8);

        // Description Window below
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + 10;
        int dFrameW = frameWidth;
        int dFrameH = gp.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameW, dFrameH);

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.size()) {
            SuperObject selected = gp.player.inventory.get(itemIndex);
            g2.setFont(new Font("Arial", Font.BOLD, 17));
            g2.setColor(new Color(255, 215, 60));
            g2.drawString(selected.name, dFrameX + 20, dFrameY + 30);

            g2.setFont(new Font("Arial", Font.PLAIN, 15));
            g2.setColor(Color.WHITE);
            int dy = dFrameY + 56;
            for (String line : selected.description.split("\n")) {
                g2.drawString(line, dFrameX + 20, dy);
                dy += 22;
            }

            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.setColor(new Color(130, 225, 255));
            g2.drawString("[ENTER] Use / Equip      [I / ESC] Close", dFrameX + 20, dFrameY + dFrameH - 14);
        }
    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 5);
    }

    // ==========================================
    // SHOP SCREEN
    // ==========================================
    private void drawShopScreen() {
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize * 2;
        int frameWidth = gp.tileSize * 12;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setFont(new Font("Georgia", Font.BOLD, 24));
        g2.setColor(new Color(255, 215, 60));
        g2.drawString("BORIS'S PROVISIONS (Gold: " + gp.player.coin + ")", frameX + 30, frameY + 45);

        String[] shopItems = {
            "Health Potion        [ 20 Gold ] - Restores 4 Life points",
            "Speed Elixir         [ 40 Gold ] - Permanent +1 Speed boost",
            "Hermes Boots         [ 60 Gold ] - Winged boots of swiftness",
            "Silver Sword         [ 80 Gold ] - +2 Attack power",
            "Knight Shield        [ 70 Gold ] - +1 Defense reduction",
            "Dungeon Key          [ 25 Gold ] - Standard iron key"
        };

        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        for (int i = 0; i < shopItems.length; i++) {
            int iy = frameY + 95 + i * 46;
            if (commandNum == i) {
                g2.setColor(new Color(255, 215, 60, 60));
                g2.fillRoundRect(frameX + 20, iy - 26, frameWidth - 40, 36, 8, 8);
                g2.setColor(new Color(255, 230, 90));
                g2.drawString("> " + shopItems[i], frameX + 30, iy);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString(shopItems[i], frameX + 30, iy);
            }
        }

        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(new Color(255, 215, 60));
        g2.drawString("[ENTER] Buy Item       [ESCAPE] Leave Shop", frameX + 30, frameY + frameHeight - 20);
    }

    public void buyShopItem(int index) {
        int[] prices = {20, 40, 60, 80, 70, 25};
        SuperObject[] items = {
            new OBJ_Potion_Red(),
            new OBJ_Potion_Blue(),
            new OBJ_Boots(),
            new OBJ_Sword_Silver(),
            new OBJ_Shield(),
            new Obj_Key()
        };

        if (index >= 0 && index < prices.length) {
            int cost = prices[index];
            if (gp.player.coin >= cost) {
                gp.player.coin -= cost;
                gp.player.inventory.add(items[index]);
                gp.playSE(Sound.SFX_COIN);
                currentDialogue = "Boris: 'Pleasure doing business with you! Purchased: " + items[index].name + "!'";
                gp.gameState = GamePannel.DIALOGUE_STATE;
            } else {
                currentDialogue = "Boris: 'Sorry friend, you do not have enough gold for that!'";
                gp.gameState = GamePannel.DIALOGUE_STATE;
            }
        }
    }

    // ==========================================
    // PAUSE SCREEN
    // ==========================================
    private void drawPauseScreen() {
        // Dark translucent veil
        g2.setColor(new Color(10, 8, 20, 200));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenHeight);

        g2.setFont(new Font("Georgia", Font.BOLD, 42));
        g2.setColor(new Color(255, 215, 60));
        String title = "GAME PAUSED";
        int tx = getXForCenteredText(title);
        g2.drawString(title, tx, 220);

        String[] options = {"RESUME GAME", "SAVE GAME", "AUDIO: " + (gp.music.isMuted() ? "OFF" : "ON"), "RETURN TO TITLE"};
        g2.setFont(new Font("Arial", Font.BOLD, 22));

        for (int i = 0; i < options.length; i++) {
            int oy = 300 + i * 50;
            int ox = getXForCenteredText(options[i]);

            if (commandNum == i) {
                g2.setColor(new Color(255, 230, 90));
                g2.drawString(">  " + options[i] + "  <", ox - 24, oy);
            } else {
                g2.setColor(new Color(200, 200, 215));
                g2.drawString(options[i], ox, oy);
            }
        }
    }

    // ==========================================
    // GAME OVER SCREEN
    // ==========================================
    private void drawGameOverScreen() {
        g2.setColor(new Color(25, 5, 10, 230));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenHeight);

        g2.setFont(new Font("Georgia", Font.BOLD, 46));
        g2.setColor(new Color(230, 40, 50));
        String text = "YOU FELL IN BATTLE";
        int tx = getXForCenteredText(text);
        g2.drawString(text, tx, 260);

        String[] options = {"RESPAWN AT GODDESS SHRINE", "RETURN TO TITLE SCREEN"};
        g2.setFont(new Font("Arial", Font.BOLD, 22));

        for (int i = 0; i < options.length; i++) {
            int oy = 360 + i * 60;
            int ox = getXForCenteredText(options[i]);

            if (commandNum == i) {
                g2.setColor(new Color(255, 215, 60));
                g2.drawString(">  " + options[i] + "  <", ox - 24, oy);
            } else {
                g2.setColor(new Color(210, 210, 220));
                g2.drawString(options[i], ox, oy);
            }
        }
    }

    // ==========================================
    // VICTORY SCREEN
    // ==========================================
    private void drawVictoryScreen() {
        g2.setColor(new Color(15, 25, 40));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenHeight);

        if (titleBg != null) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            g2.drawImage(titleBg, 0, 0, gp.screenwidth, gp.screenHeight, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        g2.setFont(new Font("Georgia", Font.BOLD, 40));
        g2.setColor(new Color(255, 215, 60));
        String text = "THE KINGDOM IS RESTORED!";
        int tx = getXForCenteredText(text);
        g2.drawString(text, tx, 150);

        drawSubWindow(150, 190, 660, 440);

        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        int vy = 240;
        g2.drawString("With the defeat of King Malakor, the dark corruption dissolves.", 180, vy); vy += 32;
        g2.drawString("Lush green leaves return to the Whispering Forest, and golden", 180, vy); vy += 28;
        g2.drawString("sunlight bathes the Forgotten Village once more.", 180, vy); vy += 28;
        g2.drawString("The ancient relics rest safely in their sacred sanctums.", 180, vy); vy += 40;

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(new Color(255, 220, 100));
        g2.drawString("HERO'S CHRONICLE:", 180, vy); vy += 34;
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        g2.drawString("Time Elapsed:           " + (int) playTime + " seconds", 180, vy); vy += 30;
        g2.drawString("Gold Amassed:           " + gp.player.coin + " coins", 180, vy); vy += 30;
        g2.drawString("Relics Recovered:       2 / 2 (Sunstone & Shadow Orb)", 180, vy); vy += 50;

        g2.setFont(new Font("Georgia", Font.BOLD, 20));
        g2.setColor(new Color(255, 215, 60));
        g2.drawString("PRESS ENTER TO RETURN TO TITLE", 310, vy);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        // Dark translucent glass panel
        g2.setColor(new Color(18, 14, 28, 220));
        g2.fillRoundRect(x, y, width, height, 20, 20);
        // Golden border
        g2.setColor(new Color(215, 175, 55));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 20, 20);
    }

    private int getXForCenteredText(String text) {
        int len = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenwidth / 2 - len / 2;
    }
}
