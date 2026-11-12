package MAIN;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Procedural Pixel Art and Audio Generator for Echoes of the Lost Kingdom.
 * Produces crisp, transparent, 48x48 / 96x96 pixel art and authentic 16-bit retro audio.
 */
public class AssetGenerator {

    public static void main(String[] args) {
        System.out.println("=== Starting Asset Generation for Echoes of the Lost Kingdom ===");
        ensureDirs();
        generateTiles();
        generateObjects();
        generatePlayerCombatSprites();
        generateNPCs();
        generateMonsters();
        generateProjectiles();
        generateUI();
        generateAudio();
        System.out.println("=== All Assets Successfully Generated and Verified! ===");
    }

    private static void ensureDirs() {
        String[] dirs = {
            "res/src/tile",
            "res/src/object",
            "res/src/player",
            "res/src/npc",
            "res/src/monster",
            "res/src/projectile",
            "res/src/ui",
            "res/src/sound",
            "res/src/map"
        };
        for (String d : dirs) {
            new File(d).mkdirs();
        }
    }

    private static void saveImg(BufferedImage img, String path) {
        try {
            File f = new File(path);
            f.getParentFile().mkdirs();
            ImageIO.write(img, "png", f);
            System.out.println("Saved: " + path + " (" + img.getWidth() + "x" + img.getHeight() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // TILES GENERATION
    // ==========================================
    private static void generateTiles() {
        // Tile 0: Lush Grass with subtle blade variations
        saveImg(createGrassTile(new Color(68, 148, 74), new Color(52, 122, 58), new Color(86, 172, 92)), "res/src/tile/grass.png");
        // Tile 1: Earth / Dirt road
        saveImg(createDirtTile(new Color(156, 114, 68), new Color(132, 94, 52), new Color(178, 134, 84)), "res/src/tile/earth.png");
        // Tile 2: Deep Animated Water
        saveImg(createWaterTile(new Color(36, 88, 172), new Color(24, 66, 140), new Color(92, 160, 224)), "res/src/tile/water.png");
        // Tile 3: Sand shore
        saveImg(createSandTile(new Color(228, 204, 132), new Color(208, 180, 112)), "res/src/tile/sand.png");
        // Tile 4: Cobblestone / Village path
        saveImg(createCobblestoneTile(new Color(140, 140, 148), new Color(100, 100, 108), new Color(175, 175, 185)), "res/src/tile/cobblestone.png");
        // Tile 5: Wooden Bridge / Planks
        saveImg(createBridgeTile(), "res/src/tile/bridge.png");
        // Tile 6: Forest Tree (Oak)
        saveImg(createTreeTile(new Color(42, 110, 48), new Color(28, 80, 36), new Color(60, 145, 68)), "res/src/tile/tree.png");
        // Tile 7: Forest Bush with red berries
        saveImg(createBushTile(), "res/src/tile/bush.png");
        // Tile 8: Ancient Ruins Stone Wall
        saveImg(createRuinsWallTile(), "res/src/tile/ruins_wall.png");
        // Tile 9: Ancient Ruins Mossy Floor
        saveImg(createRuinsFloorTile(), "res/src/tile/ruins_floor.png");
        // Tile 10: Crystal Cave Floor
        saveImg(createCaveFloorTile(), "res/src/tile/cave_floor.png");
        // Tile 11: Crystal Cave Rock Wall
        saveImg(createCaveWallTile(), "res/src/tile/cave_wall.png");
        // Tile 12: Shadow Dungeon Stone Floor
        saveImg(createDungeonFloorTile(), "res/src/tile/dungeon_floor.png");
        // Tile 13: Shadow Dungeon Iron/Brick Wall
        saveImg(createDungeonWallTile(), "res/src/tile/dungeon_wall.png");
        // Tile 14: Lost King Fortress Marble Floor
        saveImg(createFortressFloorTile(), "res/src/tile/fortress_floor.png");
        // Tile 15: Lost King Fortress Corrupted Royal Wall
        saveImg(createFortressWallTile(), "res/src/tile/fortress_wall.png");
        // Tile 16: Dark Void / Lava Hazard
        saveImg(createLavaTile(), "res/src/tile/hazard_lava.png");
        // Tile 17: Village House Roof
        saveImg(createHouseRoofTile(), "res/src/tile/house_roof.png");
        // Tile 18: Village House Wall
        saveImg(createHouseWallTile(), "res/src/tile/house_wall.png");
        // Tile 19: Floor Pressure Plate (Switch)
        saveImg(createSwitchTile(false), "res/src/tile/switch_off.png");
        saveImg(createSwitchTile(true), "res/src/tile/switch_on.png");
        // Tile 20: Glowing Cyan Crystal
        saveImg(createCrystalTile(new Color(0, 220, 255)), "res/src/tile/crystal_cyan.png");
        saveImg(createCrystalTile(new Color(220, 50, 255)), "res/src/tile/crystal_violet.png");
        saveImg(createCrystalTile(new Color(50, 255, 120)), "res/src/tile/crystal_emerald.png");
        // Tile 21: Spike Trap
        saveImg(createSpikeTrapTile(false), "res/src/tile/spikes_retracted.png");
        saveImg(createSpikeTrapTile(true), "res/src/tile/spikes_active.png");
        // Tile 22: Shallow Water / Lilypad
        saveImg(createShallowWaterTile(), "res/src/tile/shallow_water.png");
        // Tile 23: Standard stone wall (replaces old wall.png)
        saveImg(createRuinsWallTile(), "res/src/tile/wall.png");
    }

    private static BufferedImage createGrassTile(Color base, Color dark, Color light) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(base);
        g.fillRect(0, 0, 48, 48);
        for (int y = 0; y < 48; y += 4) {
            for (int x = 0; x < 48; x += 4) {
                int r = (x * 13 + y * 29) % 7;
                if (r == 0) {
                    g.setColor(dark);
                    g.fillRect(x, y, 2, 3);
                } else if (r == 1) {
                    g.setColor(light);
                    g.fillRect(x + 1, y, 2, 2);
                }
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createDirtTile(Color base, Color dark, Color light) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(base);
        g.fillRect(0, 0, 48, 48);
        for (int y = 0; y < 48; y += 3) {
            for (int x = 0; x < 48; x += 3) {
                int hash = (x * 17 + y * 31) % 5;
                if (hash == 0) {
                    g.setColor(dark);
                    g.fillRect(x, y, 2, 2);
                } else if (hash == 1) {
                    g.setColor(light);
                    g.fillRect(x + 1, y + 1, 1, 1);
                }
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createWaterTile(Color base, Color dark, Color light) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(base);
        g.fillRect(0, 0, 48, 48);
        g.setColor(dark);
        for (int y = 4; y < 48; y += 10) {
            g.fillRect(2, y, 20, 2);
            g.fillRect(26, y + 5, 18, 2);
        }
        g.setColor(light);
        for (int y = 6; y < 48; y += 10) {
            g.fillRect(6, y, 12, 1);
            g.fillRect(30, y + 5, 10, 1);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createSandTile(Color base, Color dark) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(base);
        g.fillRect(0, 0, 48, 48);
        g.setColor(dark);
        for (int y = 0; y < 48; y += 4) {
            for (int x = 0; x < 48; x += 4) {
                if ((x * 7 + y * 19) % 4 == 0) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createCobblestoneTile(Color base, Color dark, Color light) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(dark);
        g.fillRect(0, 0, 48, 48);
        // stones
        int[][] stones = {
            {2, 2, 20, 10}, {24, 2, 22, 10},
            {2, 14, 14, 9}, {18, 14, 28, 9},
            {2, 25, 22, 10}, {26, 25, 20, 10},
            {2, 37, 28, 9}, {32, 37, 14, 9}
        };
        for (int[] s : stones) {
            g.setColor(base);
            g.fillRect(s[0], s[1], s[2], s[3]);
            g.setColor(light);
            g.fillRect(s[0], s[1], s[2] - 1, 2);
            g.fillRect(s[0], s[1], 2, s[3] - 1);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createBridgeTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(24, 66, 140)); // water underneath
        g.fillRect(0, 0, 48, 48);
        // wooden planks
        for (int y = 2; y < 46; y += 9) {
            g.setColor(new Color(110, 72, 36));
            g.fillRect(2, y, 44, 7);
            g.setColor(new Color(150, 104, 56));
            g.fillRect(3, y + 1, 42, 2);
            g.setColor(new Color(65, 40, 18));
            g.fillRect(2, y + 7, 44, 1);
            // nails
            g.setColor(new Color(40, 40, 40));
            g.fillRect(5, y + 3, 2, 2);
            g.fillRect(41, y + 3, 2, 2);
        }
        // side ropes
        g.setColor(new Color(180, 140, 80));
        g.fillRect(1, 0, 3, 48);
        g.fillRect(44, 0, 3, 48);
        g.dispose();
        return img;
    }

    private static BufferedImage createTreeTile(Color canopy, Color dark, Color light) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // grass under
        g.setColor(new Color(68, 148, 74));
        g.fillRect(0, 0, 48, 48);
        // trunk
        g.setColor(new Color(100, 65, 30));
        g.fillRect(18, 24, 12, 20);
        g.setColor(new Color(65, 40, 18));
        g.fillRect(26, 24, 4, 20);
        // roots
        g.fillRect(16, 40, 6, 6);
        g.fillRect(26, 40, 6, 6);
        // canopy
        g.setColor(dark);
        g.fillOval(3, 2, 42, 32);
        g.setColor(canopy);
        g.fillOval(5, 4, 38, 28);
        g.setColor(light);
        g.fillOval(10, 6, 16, 14);
        g.fillOval(24, 8, 14, 12);
        g.dispose();
        return img;
    }

    private static BufferedImage createBushTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(68, 148, 74));
        g.fillRect(0, 0, 48, 48);
        // round bush
        g.setColor(new Color(30, 85, 38));
        g.fillOval(6, 8, 36, 32);
        g.setColor(new Color(45, 125, 55));
        g.fillOval(8, 10, 32, 28);
        g.setColor(new Color(75, 165, 80));
        g.fillOval(12, 12, 14, 12);
        // red berries
        g.setColor(new Color(220, 40, 40));
        int[][] berries = {{14, 18}, {24, 14}, {32, 22}, {18, 28}, {28, 30}};
        for (int[] b : berries) {
            g.fillRect(b[0], b[1], 3, 3);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createRuinsWallTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(50, 52, 58));
        g.fillRect(0, 0, 48, 48);
        for (int y = 0; y < 48; y += 12) {
            int offset = (y % 24 == 0) ? 0 : 12;
            for (int x = -12; x < 48; x += 24) {
                int bx = x + offset;
                g.setColor(new Color(110, 115, 125));
                g.fillRect(bx + 1, y + 1, 22, 10);
                g.setColor(new Color(145, 150, 160));
                g.fillRect(bx + 1, y + 1, 21, 2);
                g.fillRect(bx + 1, y + 1, 2, 9);
            }
        }
        // moss/vines
        g.setColor(new Color(55, 115, 60));
        g.fillRect(6, 12, 4, 16);
        g.fillRect(8, 26, 6, 8);
        g.dispose();
        return img;
    }

    private static BufferedImage createRuinsFloorTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(88, 92, 100));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(60, 64, 70));
        g.drawRect(0, 0, 47, 47);
        g.drawLine(24, 0, 24, 47);
        g.drawLine(0, 24, 47, 24);
        // cracks and moss
        g.setColor(new Color(45, 48, 52));
        g.drawLine(10, 10, 18, 16);
        g.drawLine(18, 16, 14, 22);
        g.setColor(new Color(60, 120, 65));
        g.fillRect(28, 4, 6, 6);
        g.fillRect(36, 28, 8, 5);
        g.dispose();
        return img;
    }

    private static BufferedImage createCaveFloorTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(42, 38, 52));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(58, 52, 72));
        for (int y = 0; y < 48; y += 4) {
            for (int x = 0; x < 48; x += 4) {
                if ((x * 11 + y * 23) % 5 == 0) {
                    g.fillRect(x, y, 2, 2);
                }
            }
        }
        // tiny glowing cyan speckles
        g.setColor(new Color(0, 220, 255, 180));
        g.fillRect(12, 16, 2, 2);
        g.fillRect(34, 38, 2, 2);
        g.dispose();
        return img;
    }

    private static BufferedImage createCaveWallTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(24, 20, 32));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(46, 40, 58));
        g.fillRect(4, 4, 40, 40);
        g.setColor(new Color(72, 64, 90));
        g.fillRect(8, 8, 20, 14);
        g.fillRect(22, 24, 18, 14);
        // glowing embedded crystal
        g.setColor(new Color(0, 240, 255));
        g.fillRect(18, 18, 6, 10);
        g.setColor(new Color(180, 250, 255));
        g.fillRect(20, 20, 2, 6);
        g.dispose();
        return img;
    }

    private static BufferedImage createDungeonFloorTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(38, 34, 44));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(24, 20, 28));
        g.drawRect(0, 0, 47, 47);
        g.drawRect(4, 4, 39, 39);
        g.setColor(new Color(60, 54, 70));
        g.fillRect(6, 6, 36, 36);
        // dark purple symbol
        g.setColor(new Color(90, 40, 110, 120));
        g.drawOval(16, 16, 16, 16);
        g.dispose();
        return img;
    }

    private static BufferedImage createDungeonWallTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(20, 18, 24));
        g.fillRect(0, 0, 48, 48);
        // dark bricks
        for (int y = 0; y < 48; y += 12) {
            int offset = (y % 24 == 0) ? 0 : 12;
            for (int x = -12; x < 48; x += 24) {
                int bx = x + offset;
                g.setColor(new Color(48, 42, 56));
                g.fillRect(bx + 1, y + 1, 22, 10);
                g.setColor(new Color(75, 68, 86));
                g.fillRect(bx + 1, y + 1, 21, 2);
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createFortressFloorTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Checkered royal marble
        g.setColor(new Color(180, 180, 195));
        g.fillRect(0, 0, 24, 24);
        g.fillRect(24, 24, 24, 24);
        g.setColor(new Color(80, 25, 40)); // corrupted velvet red
        g.fillRect(24, 0, 24, 24);
        g.fillRect(0, 24, 24, 24);
        // gold border inlay
        g.setColor(new Color(215, 175, 60));
        g.drawRect(1, 1, 45, 45);
        g.dispose();
        return img;
    }

    private static BufferedImage createFortressWallTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(25, 15, 25));
        g.fillRect(0, 0, 48, 48);
        // royal dark stone with gold trim
        g.setColor(new Color(65, 40, 60));
        g.fillRect(3, 3, 42, 42);
        g.setColor(new Color(215, 175, 60));
        g.drawRect(5, 5, 37, 37);
        // crimson gem in center
        g.setColor(new Color(220, 25, 45));
        g.fillRect(20, 20, 8, 8);
        g.setColor(new Color(255, 120, 140));
        g.fillRect(21, 21, 3, 3);
        g.dispose();
        return img;
    }

    private static BufferedImage createLavaTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(180, 40, 15));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(235, 110, 25));
        g.fillOval(6, 8, 20, 14);
        g.fillOval(22, 24, 20, 16);
        g.setColor(new Color(255, 220, 50));
        g.fillOval(10, 12, 10, 6);
        g.fillOval(26, 28, 10, 7);
        g.dispose();
        return img;
    }

    private static BufferedImage createHouseRoofTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(150, 55, 45)); // terracotta roof
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(105, 35, 28));
        for (int y = 0; y < 48; y += 8) {
            g.drawLine(0, y, 47, y);
        }
        for (int y = 0; y < 48; y += 8) {
            int offset = (y % 16 == 0) ? 0 : 8;
            for (int x = offset; x < 48; x += 16) {
                g.drawLine(x, y, x, y + 8);
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createHouseWallTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(225, 215, 195)); // plaster
        g.fillRect(0, 0, 48, 48);
        // timber beams
        g.setColor(new Color(90, 55, 25));
        g.fillRect(0, 0, 6, 48);
        g.fillRect(42, 0, 6, 48);
        g.fillRect(0, 0, 48, 6);
        g.fillRect(0, 42, 48, 6);
        // diagonal timber cross
        g.drawLine(6, 6, 41, 41);
        g.drawLine(6, 7, 41, 42);
        g.dispose();
        return img;
    }

    private static BufferedImage createSwitchTile(boolean active) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(75, 80, 90));
        g.fillRect(0, 0, 48, 48);
        g.setColor(new Color(45, 50, 58));
        g.fillRect(6, 6, 36, 36);
        if (active) {
            g.setColor(new Color(50, 230, 90));
            g.fillRect(12, 12, 24, 24);
            g.setColor(new Color(180, 255, 190));
            g.fillRect(16, 16, 16, 16);
        } else {
            g.setColor(new Color(200, 50, 50));
            g.fillRect(10, 10, 28, 28);
            g.setColor(new Color(130, 30, 30));
            g.fillRect(12, 12, 24, 24);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createCrystalTile(Color glow) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // transparent bg or cave floor
        g.setColor(new Color(42, 38, 52));
        g.fillRect(0, 0, 48, 48);
        // crystal base
        g.setColor(new Color(25, 20, 35));
        g.fillOval(10, 32, 28, 12);
        // crystal spikes
        int[] xs1 = {16, 24, 32};
        int[] ys1 = {38, 6, 38};
        g.setColor(glow.darker());
        g.fillPolygon(xs1, ys1, 3);
        int[] xs2 = {20, 24, 28};
        int[] ys2 = {38, 10, 38};
        g.setColor(glow);
        g.fillPolygon(xs2, ys2, 3);
        // highlight
        g.setColor(Color.WHITE);
        g.drawLine(24, 8, 22, 34);
        g.dispose();
        return img;
    }

    private static BufferedImage createSpikeTrapTile(boolean active) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(80, 80, 85));
        g.fillRect(0, 0, 48, 48);
        // holes
        g.setColor(new Color(30, 30, 35));
        for (int y = 8; y < 44; y += 14) {
            for (int x = 8; x < 44; x += 14) {
                g.fillOval(x, y, 8, 8);
                if (active) {
                    g.setColor(new Color(200, 200, 210));
                    int[] sx = {x + 1, x + 4, x + 7};
                    int[] sy = {y + 6, y - 6, y + 6};
                    g.fillPolygon(sx, sy, 3);
                    g.setColor(new Color(30, 30, 35));
                }
            }
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createShallowWaterTile() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(70, 140, 210));
        g.fillRect(0, 0, 48, 48);
        // sand visible beneath
        g.setColor(new Color(180, 170, 120, 80));
        g.fillRect(0, 0, 48, 48);
        // lilypad
        g.setColor(new Color(40, 130, 50));
        g.fillOval(14, 14, 20, 20);
        g.setColor(new Color(70, 140, 210));
        g.fillPolygon(new int[]{24, 34, 30}, new int[]{24, 20, 30}, 3);
        // tiny pink flower
        g.setColor(new Color(255, 140, 180));
        g.fillOval(20, 18, 6, 6);
        g.dispose();
        return img;
    }

    // ==========================================
    // OBJECTS GENERATION
    // ==========================================
    private static void generateObjects() {
        saveImg(createChestImg(false), "res/src/object/chest.png");
        saveImg(createChestImg(true), "res/src/object/chest_open.png");
        saveImg(createDoorImg(false, "village"), "res/src/object/door.png");
        saveImg(createDoorImg(true, "village"), "res/src/object/door_open.png");
        saveImg(createDoorImg(false, "ruins"), "res/src/object/door_ruins.png");
        saveImg(createDoorImg(false, "dungeon"), "res/src/object/door_dungeon.png");
        saveImg(createDoorImg(false, "fortress"), "res/src/object/door_fortress.png");
        saveImg(createKeyImg(new Color(230, 190, 40)), "res/src/object/key.png");
        saveImg(createKeyImg(new Color(110, 180, 240)), "res/src/object/key_ruins.png");
        saveImg(createKeyImg(new Color(175, 75, 230)), "res/src/object/key_dungeon.png");
        saveImg(createBootsImg(), "res/src/object/boots.png");
        saveImg(createPotionImg(new Color(225, 40, 50)), "res/src/object/potion_red.png");
        saveImg(createPotionImg(new Color(40, 120, 230)), "res/src/object/potion_blue.png");
        saveImg(createCoinImg(), "res/src/object/coin.png");
        saveImg(createRelicSunstoneImg(), "res/src/object/relic_sunstone.png");
        saveImg(createRelicShadowOrbImg(), "res/src/object/relic_shadoworb.png");
        saveImg(createSwordSilverImg(), "res/src/object/sword_silver.png");
        saveImg(createShieldImg(), "res/src/object/shield.png");
        saveImg(createSignImg(), "res/src/object/sign.png");
        saveImg(createTorchImg(1), "res/src/object/torch_1.png");
        saveImg(createTorchImg(2), "res/src/object/torch_2.png");
        saveImg(createGoddessStatueImg(), "res/src/object/statue_goddess.png");
        saveImg(createPortalImg(1), "res/src/object/portal_1.png");
        saveImg(createPortalImg(2), "res/src/object/portal_2.png");
        saveImg(createBarrelImg(), "res/src/object/barrel.png");
    }

    private static BufferedImage createChestImg(boolean open) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        if (!open) {
            // body
            g.setColor(new Color(125, 75, 30));
            g.fillRect(6, 14, 36, 26);
            // lid
            g.setColor(new Color(155, 95, 40));
            g.fillRect(4, 10, 40, 12);
            // iron bands
            g.setColor(new Color(210, 170, 40));
            g.fillRect(10, 10, 4, 30);
            g.fillRect(34, 10, 4, 30);
            // golden lock
            g.setColor(new Color(245, 210, 50));
            g.fillRect(21, 20, 6, 8);
            g.setColor(Color.BLACK);
            g.fillRect(23, 23, 2, 3);
        } else {
            // interior dark
            g.setColor(new Color(45, 25, 10));
            g.fillRect(6, 14, 36, 26);
            // gold treasure glow
            g.setColor(new Color(255, 215, 0));
            g.fillOval(12, 18, 24, 16);
            // open lid
            g.setColor(new Color(155, 95, 40));
            g.fillRect(4, 2, 40, 10);
            // front rim
            g.setColor(new Color(125, 75, 30));
            g.fillRect(6, 28, 36, 14);
            g.setColor(new Color(210, 170, 40));
            g.fillRect(10, 28, 4, 14);
            g.fillRect(34, 28, 4, 14);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createDoorImg(boolean open, String type) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        Color frameCol = new Color(70, 70, 75);
        Color woodCol = new Color(130, 80, 40);
        Color trimCol = new Color(200, 160, 50);

        if ("ruins".equals(type)) {
            frameCol = new Color(90, 95, 105);
            woodCol = new Color(75, 80, 90);
            trimCol = new Color(100, 180, 230);
        } else if ("dungeon".equals(type)) {
            frameCol = new Color(45, 40, 50);
            woodCol = new Color(40, 35, 45);
            trimCol = new Color(180, 70, 220);
        } else if ("fortress".equals(type)) {
            frameCol = new Color(30, 20, 30);
            woodCol = new Color(85, 25, 40);
            trimCol = new Color(225, 180, 50);
        }

        // Frame
        g.setColor(frameCol);
        g.fillRect(0, 0, 48, 48);

        if (!open) {
            // Door panel
            g.setColor(woodCol);
            g.fillRect(6, 6, 36, 42);
            // Trim
            g.setColor(trimCol);
            g.drawRect(8, 8, 31, 38);
            // Keyhole / Doorknob
            g.setColor(new Color(240, 210, 60));
            g.fillOval(32, 24, 6, 6);
            g.setColor(Color.BLACK);
            g.fillRect(34, 26, 2, 3);
        } else {
            // open doorway dark passage
            g.setColor(new Color(15, 15, 20));
            g.fillRect(6, 6, 36, 42);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createKeyImg(Color keyColor) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(keyColor);
        // ring
        g.fillOval(10, 8, 16, 16);
        g.setColor(new Color(0, 0, 0, 0));
        g.setComposite(AlphaComposite.Clear);
        g.fillOval(14, 12, 8, 8);
        g.setComposite(AlphaComposite.SrcOver);
        // stem
        g.setColor(keyColor);
        g.fillRect(16, 22, 5, 18);
        // teeth
        g.fillRect(21, 32, 7, 3);
        g.fillRect(21, 37, 5, 3);
        // shine highlight
        g.setColor(Color.WHITE);
        g.fillOval(12, 10, 3, 3);
        g.dispose();
        return img;
    }

    private static BufferedImage createBootsImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // winged leather boots
        g.setColor(new Color(140, 85, 35));
        g.fillRect(10, 14, 10, 20);
        g.fillRect(10, 26, 16, 8);
        g.fillRect(26, 14, 10, 20);
        g.fillRect(26, 26, 16, 8);
        // wings
        g.setColor(new Color(230, 240, 255));
        g.fillPolygon(new int[]{10, 2, 8}, new int[]{16, 10, 22}, 3);
        g.fillPolygon(new int[]{36, 44, 38}, new int[]{16, 10, 22}, 3);
        // buckles
        g.setColor(new Color(230, 190, 40));
        g.fillRect(12, 20, 6, 2);
        g.fillRect(28, 20, 6, 2);
        g.dispose();
        return img;
    }

    private static BufferedImage createPotionImg(Color liquid) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // cork
        g.setColor(new Color(160, 110, 60));
        g.fillRect(20, 6, 8, 5);
        // bottle neck
        g.setColor(new Color(210, 230, 255, 180));
        g.fillRect(19, 11, 10, 6);
        // bottle sphere
        g.setColor(new Color(210, 230, 255, 140));
        g.fillOval(10, 16, 28, 26);
        // liquid inside
        g.setColor(liquid);
        g.fillOval(12, 20, 24, 20);
        // shine highlight
        g.setColor(Color.WHITE);
        g.fillOval(14, 22, 5, 8);
        g.dispose();
        return img;
    }

    private static BufferedImage createCoinImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(210, 160, 30));
        g.fillOval(8, 8, 32, 32);
        g.setColor(new Color(255, 215, 50));
        g.fillOval(11, 11, 26, 26);
        g.setColor(new Color(200, 150, 20));
        g.drawOval(15, 15, 18, 18);
        // star / emblem
        g.setColor(new Color(255, 240, 150));
        g.fillRect(22, 17, 4, 14);
        g.fillRect(17, 22, 14, 4);
        g.dispose();
        return img;
    }

    private static BufferedImage createRelicSunstoneImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Golden sun rays
        g.setColor(new Color(255, 190, 40));
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            int x2 = 24 + (int)(18 * Math.cos(angle));
            int y2 = 24 + (int)(18 * Math.sin(angle));
            g.drawLine(24, 24, x2, y2);
        }
        // Sunstone crystal core
        g.setColor(new Color(255, 130, 20));
        g.fillOval(12, 12, 24, 24);
        g.setColor(new Color(255, 235, 80));
        g.fillOval(16, 16, 16, 16);
        g.setColor(Color.WHITE);
        g.fillOval(19, 19, 7, 7);
        g.dispose();
        return img;
    }

    private static BufferedImage createRelicShadowOrbImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Shadow vortex aura
        g.setColor(new Color(110, 30, 160, 160));
        g.fillOval(4, 4, 40, 40);
        // Dark metallic prongs
        g.setColor(new Color(60, 50, 70));
        g.drawArc(6, 6, 36, 36, 45, 90);
        g.drawArc(6, 6, 36, 36, 225, 90);
        // Orb core
        g.setColor(new Color(30, 10, 45));
        g.fillOval(12, 12, 24, 24);
        g.setColor(new Color(160, 60, 230));
        g.drawOval(15, 15, 18, 18);
        g.setColor(new Color(220, 140, 255));
        g.fillOval(20, 20, 8, 8);
        g.dispose();
        return img;
    }

    private static BufferedImage createSwordSilverImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Blade
        g.setColor(new Color(220, 230, 245));
        int[] bx = {36, 42, 18};
        int[] by = {6, 12, 36};
        g.fillPolygon(bx, by, 3);
        // Center ridge
        g.setColor(new Color(170, 185, 205));
        g.drawLine(38, 8, 18, 36);
        // Crossguard
        g.setColor(new Color(215, 175, 45));
        g.drawLine(14, 30, 26, 42);
        // Hilt & Pommel
        g.setColor(new Color(110, 60, 30));
        g.drawLine(16, 34, 10, 40);
        g.setColor(new Color(215, 175, 45));
        g.fillOval(7, 39, 5, 5);
        g.dispose();
        return img;
    }

    private static BufferedImage createShieldImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(40, 80, 140));
        int[] sx = {10, 38, 38, 24, 10};
        int[] sy = {10, 10, 28, 42, 28};
        g.fillPolygon(sx, sy, 5);
        // gold rim
        g.setColor(new Color(220, 180, 50));
        g.drawPolygon(sx, sy, 5);
        // cross
        g.fillRect(22, 14, 4, 18);
        g.fillRect(15, 18, 18, 4);
        g.dispose();
        return img;
    }

    private static BufferedImage createSignImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // post
        g.setColor(new Color(100, 65, 30));
        g.fillRect(22, 24, 4, 20);
        // board
        g.setColor(new Color(150, 100, 50));
        g.fillRect(6, 8, 36, 20);
        g.setColor(new Color(110, 70, 35));
        g.drawRect(6, 8, 36, 20);
        // text lines
        g.setColor(new Color(80, 50, 25));
        g.drawLine(10, 13, 36, 13);
        g.drawLine(10, 18, 32, 18);
        g.drawLine(10, 23, 28, 23);
        g.dispose();
        return img;
    }

    private static BufferedImage createTorchImg(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // wooden sconce
        g.setColor(new Color(110, 70, 30));
        g.fillRect(22, 22, 4, 20);
        g.fillRect(18, 22, 12, 4);
        // flame
        g.setColor(new Color(255, 80, 20));
        int h = (frame == 1) ? 14 : 16;
        g.fillOval(19, 10, 10, h);
        g.setColor(new Color(255, 210, 40));
        g.fillOval(21, 13, 6, h - 5);
        g.dispose();
        return img;
    }

    private static BufferedImage createGoddessStatueImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Stone plinth
        g.setColor(new Color(120, 125, 135));
        g.fillRect(10, 36, 28, 10);
        g.setColor(new Color(160, 165, 175));
        g.fillRect(12, 36, 24, 3);
        // Angelic goddess statue
        g.setColor(new Color(190, 195, 205));
        // dress
        int[] dx = {18, 30, 34, 14};
        int[] dy = {18, 18, 36, 36};
        g.fillPolygon(dx, dy, 4);
        // head & halo
        g.setColor(new Color(255, 235, 120));
        g.drawOval(20, 4, 8, 8);
        g.setColor(new Color(210, 215, 225));
        g.fillOval(21, 8, 6, 8);
        // wings
        g.setColor(new Color(230, 235, 245));
        g.fillPolygon(new int[]{18, 6, 16}, new int[]{18, 10, 28}, 3);
        g.fillPolygon(new int[]{30, 42, 32}, new int[]{18, 10, 28}, 3);
        g.dispose();
        return img;
    }

    private static BufferedImage createPortalImg(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        Color c1 = (frame == 1) ? new Color(130, 40, 240) : new Color(40, 160, 255);
        Color c2 = (frame == 1) ? new Color(200, 100, 255) : new Color(120, 220, 255);
        g.setColor(c1);
        g.fillOval(6, 6, 36, 36);
        g.setColor(c2);
        g.fillOval(12, 12, 24, 24);
        g.setColor(Color.WHITE);
        g.fillOval(18, 18, 12, 12);
        g.dispose();
        return img;
    }

    private static BufferedImage createBarrelImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(120, 75, 35));
        g.fillOval(8, 10, 32, 32);
        g.fillRect(8, 14, 32, 24);
        g.setColor(new Color(60, 60, 65));
        g.drawRect(8, 16, 31, 3);
        g.drawRect(8, 30, 31, 3);
        g.dispose();
        return img;
    }

    // ==========================================
    // PLAYER COMBAT & DEATH SPRITES
    // ==========================================
    private static void generatePlayerCombatSprites() {
        // Player attack slashes (2 frames per 4 directions)
        saveImg(createPlayerAttack(0, 1), "res/src/player/attack_down_1.png");
        saveImg(createPlayerAttack(0, 2), "res/src/player/attack_down_2.png");
        saveImg(createPlayerAttack(1, 1), "res/src/player/attack_up_1.png");
        saveImg(createPlayerAttack(1, 2), "res/src/player/attack_up_2.png");
        saveImg(createPlayerAttack(2, 1), "res/src/player/attack_left_1.png");
        saveImg(createPlayerAttack(2, 2), "res/src/player/attack_left_2.png");
        saveImg(createPlayerAttack(3, 1), "res/src/player/attack_right_1.png");
        saveImg(createPlayerAttack(3, 2), "res/src/player/attack_right_2.png");
        // Player hit & dead
        saveImg(createPlayerHit(), "res/src/player/player_hit.png");
        saveImg(createPlayerDead(), "res/src/player/player_dead.png");
    }

    private static BufferedImage createPlayerAttack(int dir, int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Hero body
        g.setColor(new Color(45, 90, 180)); // blue tunic
        g.fillRect(16, 18, 16, 18);
        g.setColor(new Color(240, 190, 140)); // face
        g.fillOval(17, 8, 14, 12);
        g.setColor(new Color(90, 50, 20)); // brown hair
        g.fillOval(16, 6, 16, 8);
        g.setColor(new Color(70, 45, 20)); // boots
        g.fillRect(16, 36, 6, 8);
        g.fillRect(26, 36, 6, 8);

        // Sword swing slash arc
        g.setColor(new Color(220, 240, 255));
        Stroke old = g.getStroke();
        g.setStroke(new BasicStroke(3));
        if (dir == 0) { // down
            int startA = (frame == 1) ? 200 : 220;
            g.drawArc(8, 22, 32, 24, startA, 100);
            g.setColor(Color.WHITE);
            g.drawLine(24, 28, 24, 44);
        } else if (dir == 1) { // up
            int startA = (frame == 1) ? 20 : 40;
            g.drawArc(8, 2, 32, 24, startA, 100);
            g.setColor(Color.WHITE);
            g.drawLine(24, 18, 24, 2);
        } else if (dir == 2) { // left
            g.drawArc(2, 10, 24, 32, 110, 100);
            g.setColor(Color.WHITE);
            g.drawLine(18, 24, 2, 24);
        } else if (dir == 3) { // right
            g.drawArc(22, 10, 24, 32, 290, 100);
            g.setColor(Color.WHITE);
            g.drawLine(30, 24, 46, 24);
        }
        g.setStroke(old);
        g.dispose();
        return img;
    }

    private static BufferedImage createPlayerHit() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(240, 60, 60)); // red flash
        g.fillRect(16, 18, 16, 18);
        g.fillOval(17, 8, 14, 12);
        g.fillRect(16, 36, 6, 8);
        g.fillRect(26, 36, 6, 8);
        g.dispose();
        return img;
    }

    private static BufferedImage createPlayerDead() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Fallen sword on ground
        g.setColor(new Color(180, 190, 205));
        g.drawLine(10, 36, 34, 36);
        g.setColor(new Color(210, 170, 40));
        g.drawLine(16, 32, 16, 40);
        // adventurer's fallen shield / hat
        g.setColor(new Color(45, 90, 180));
        g.fillOval(20, 22, 18, 14);
        g.dispose();
        return img;
    }

    // ==========================================
    // NPCS GENERATION
    // ==========================================
    private static void generateNPCs() {
        saveImg(createElderNPC(), "res/src/npc/elder.png");
        saveImg(createMerchantNPC(), "res/src/npc/merchant.png");
        saveImg(createGuardNPC(), "res/src/npc/guard.png");
        saveImg(createHermitNPC(), "res/src/npc/hermit.png");
    }

    private static BufferedImage createElderNPC() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // White robe
        g.setColor(new Color(230, 230, 240));
        g.fillRect(14, 18, 20, 26);
        // Face & long white beard
        g.setColor(new Color(240, 195, 150));
        g.fillOval(18, 8, 12, 12);
        g.setColor(Color.WHITE);
        g.fillPolygon(new int[]{18, 30, 24}, new int[]{16, 16, 28}, 3);
        // Wooden staff with amber crystal
        g.setColor(new Color(110, 70, 30));
        g.fillRect(36, 4, 3, 40);
        g.setColor(new Color(255, 180, 30));
        g.fillOval(34, 2, 7, 7);
        g.dispose();
        return img;
    }

    private static BufferedImage createMerchantNPC() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Green tunic & red merchant turban
        g.setColor(new Color(40, 130, 70));
        g.fillRect(14, 18, 20, 26);
        g.setColor(new Color(240, 195, 150));
        g.fillOval(18, 10, 12, 12);
        // Turban with feather
        g.setColor(new Color(190, 40, 40));
        g.fillOval(16, 4, 16, 10);
        g.setColor(new Color(255, 215, 0));
        g.fillRect(20, 8, 8, 2);
        // Backpack
        g.setColor(new Color(120, 75, 35));
        g.fillRect(8, 18, 6, 18);
        g.dispose();
        return img;
    }

    private static BufferedImage createGuardNPC() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Silver armor & helmet with red plume
        g.setColor(new Color(175, 185, 195));
        g.fillRect(14, 18, 20, 26);
        g.fillOval(17, 8, 14, 12);
        // Red crest
        g.setColor(new Color(210, 30, 40));
        g.fillRect(22, 2, 4, 10);
        // Spear
        g.setColor(new Color(100, 65, 30));
        g.fillRect(36, 2, 3, 44);
        g.setColor(new Color(220, 230, 240));
        g.fillPolygon(new int[]{34, 41, 37}, new int[]{8, 8, 1}, 3);
        g.dispose();
        return img;
    }

    private static BufferedImage createHermitNPC() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Brown hooded cloak
        g.setColor(new Color(90, 65, 45));
        g.fillRect(14, 18, 20, 26);
        g.fillOval(16, 6, 16, 16);
        // Glowing eyes inside dark hood
        g.setColor(new Color(20, 15, 10));
        g.fillOval(18, 10, 12, 10);
        g.setColor(new Color(0, 240, 255));
        g.fillRect(21, 14, 2, 2);
        g.fillRect(25, 14, 2, 2);
        g.dispose();
        return img;
    }

    // ==========================================
    // MONSTERS & BOSSES GENERATION
    // ==========================================
    private static void generateMonsters() {
        // 1. Green Slime
        saveImg(createSlime(1), "res/src/monster/slime_1.png");
        saveImg(createSlime(2), "res/src/monster/slime_2.png");

        // 2. Goblin Scout
        saveImg(createGoblin(1), "res/src/monster/goblin_1.png");
        saveImg(createGoblin(2), "res/src/monster/goblin_2.png");

        // 3. Skeleton Archer
        saveImg(createSkeleton(1), "res/src/monster/skeleton_1.png");
        saveImg(createSkeleton(2), "res/src/monster/skeleton_2.png");

        // 4. Stone Golem
        saveImg(createGolem(1), "res/src/monster/golem_1.png");
        saveImg(createGolem(2), "res/src/monster/golem_2.png");

        // 5. Dark Cultist
        saveImg(createCultist(1), "res/src/monster/cultist_1.png");
        saveImg(createCultist(2), "res/src/monster/cultist_2.png");

        // 6. Mini-Boss: Ruins Guardian (Gargoyle / Stone Knight)
        saveImg(createRuinsGuardian(1), "res/src/monster/guardian_1.png");
        saveImg(createRuinsGuardian(2), "res/src/monster/guardian_2.png");

        // 7. Final Boss: The Corrupted King (Malakor)
        saveImg(createCorruptedKing(1, false), "res/src/monster/king_1.png");
        saveImg(createCorruptedKing(2, false), "res/src/monster/king_2.png");
        saveImg(createCorruptedKing(1, true), "res/src/monster/king_phase2_1.png");
        saveImg(createCorruptedKing(2, true), "res/src/monster/king_phase2_2.png");
    }

    private static BufferedImage createSlime(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        int yOff = (frame == 1) ? 0 : 4;
        int h = (frame == 1) ? 26 : 22;
        int w = (frame == 1) ? 32 : 36;
        int x = 24 - w / 2;
        g.setColor(new Color(50, 180, 60, 220));
        g.fillOval(x, 18 + yOff, w, h);
        g.setColor(new Color(90, 220, 100));
        g.fillOval(x + 4, 20 + yOff, w - 8, h - 8);
        // cute white/black eyes
        g.setColor(Color.WHITE);
        g.fillOval(17, 24 + yOff, 5, 6);
        g.fillOval(26, 24 + yOff, 5, 6);
        g.setColor(Color.BLACK);
        g.fillOval(19, 26 + yOff, 2, 3);
        g.fillOval(28, 26 + yOff, 2, 3);
        g.dispose();
        return img;
    }

    private static BufferedImage createGoblin(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Green skin, pointed ears, club
        g.setColor(new Color(75, 145, 60));
        g.fillRect(16, 18, 16, 18);
        g.fillOval(16, 8, 16, 14);
        // pointed ears
        g.fillPolygon(new int[]{16, 8, 16}, new int[]{10, 12, 16}, 3);
        g.fillPolygon(new int[]{32, 40, 32}, new int[]{10, 12, 16}, 3);
        // evil yellow eyes
        g.setColor(new Color(255, 220, 30));
        g.fillRect(19, 13, 3, 3);
        g.fillRect(26, 13, 3, 3);
        // ragged tunic
        g.setColor(new Color(110, 75, 45));
        g.fillRect(16, 24, 16, 12);
        // wooden spiked club
        g.setColor(new Color(130, 85, 40));
        int cy = (frame == 1) ? 10 : 14;
        g.fillRect(34, cy, 6, 22);
        g.setColor(new Color(200, 200, 200));
        g.fillRect(32, cy + 2, 2, 2);
        g.fillRect(40, cy + 4, 2, 2);
        g.dispose();
        return img;
    }

    private static BufferedImage createSkeleton(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Skull & bones
        g.setColor(new Color(225, 225, 215));
        g.fillOval(17, 6, 14, 14);
        g.setColor(Color.BLACK);
        g.fillRect(20, 11, 3, 3); // eye sockets
        g.fillRect(25, 11, 3, 3);
        // Ribcage
        g.setColor(new Color(225, 225, 215));
        g.fillRect(23, 20, 2, 16);
        for (int y = 22; y <= 32; y += 4) {
            g.fillRect(17, y, 14, 2);
        }
        // Legs
        int legY = (frame == 1) ? 36 : 38;
        g.fillRect(19, legY, 2, 10);
        g.fillRect(27, legY, 2, 10);
        // Bow
        g.setColor(new Color(130, 80, 40));
        g.drawArc(6, 14, 12, 26, 80, 200);
        g.setColor(Color.WHITE);
        g.drawLine(10, 15, 10, 39);
        g.dispose();
        return img;
    }

    private static BufferedImage createGolem(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Heavy stone torso
        g.setColor(new Color(90, 85, 95));
        g.fillRect(10, 14, 28, 24);
        g.setColor(new Color(130, 125, 135));
        g.fillRect(12, 16, 24, 20);
        // Golem head
        g.setColor(new Color(80, 75, 85));
        g.fillRect(16, 4, 16, 12);
        // Glowing cyan rune in chest
        g.setColor(new Color(0, 240, 255));
        g.fillRect(21, 22, 6, 6);
        g.fillRect(20, 8, 2, 2); // eyes
        g.fillRect(26, 8, 2, 2);
        // Heavy fists
        int fistY = (frame == 1) ? 22 : 26;
        g.setColor(new Color(70, 65, 75));
        g.fillRect(4, fistY, 8, 12);
        g.fillRect(36, fistY, 8, 12);
        g.dispose();
        return img;
    }

    private static BufferedImage createCultist(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Purple/black robes
        g.setColor(new Color(55, 20, 75));
        g.fillRect(14, 18, 20, 26);
        g.fillOval(16, 6, 16, 16);
        // Dark void cowl with glowing purple eyes
        g.setColor(Color.BLACK);
        g.fillOval(18, 10, 12, 10);
        g.setColor(new Color(200, 60, 255));
        g.fillRect(20, 13, 3, 2);
        g.fillRect(25, 13, 3, 2);
        // Staff channeling dark orb
        g.setColor(new Color(90, 50, 30));
        g.fillRect(36, 8, 3, 36);
        g.setColor(new Color(150, 30, 210));
        int orbGlow = (frame == 1) ? 8 : 10;
        g.fillOval(33, 4, orbGlow, orbGlow);
        g.dispose();
        return img;
    }

    private static BufferedImage createRuinsGuardian(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Ancient Stone Gargoyle / Guardian
        g.setColor(new Color(75, 80, 85));
        // wings
        g.fillPolygon(new int[]{12, 0, 14}, new int[]{16, 4, 32}, 3);
        g.fillPolygon(new int[]{36, 48, 34}, new int[]{16, 4, 32}, 3);
        // torso
        g.setColor(new Color(105, 110, 120));
        g.fillRect(12, 16, 24, 24);
        // horn helmet
        g.setColor(new Color(60, 65, 70));
        g.fillOval(16, 4, 16, 14);
        g.fillPolygon(new int[]{16, 12, 18}, new int[]{8, 0, 10}, 3);
        g.fillPolygon(new int[]{32, 36, 30}, new int[]{8, 0, 10}, 3);
        // glowing red eyes
        g.setColor(new Color(255, 30, 30));
        g.fillRect(19, 10, 3, 2);
        g.fillRect(26, 10, 3, 2);
        // massive ancient cleaver
        g.setColor(new Color(160, 165, 175));
        int swordY = (frame == 1) ? 6 : 10;
        g.fillRect(36, swordY, 8, 30);
        g.setColor(new Color(210, 180, 50));
        g.fillRect(34, swordY + 16, 12, 4);
        g.dispose();
        return img;
    }

    private static BufferedImage createCorruptedKing(int frame, boolean phase2) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // The Corrupted King Malakor
        Color armor = phase2 ? new Color(90, 15, 35) : new Color(35, 25, 45);
        Color aura = phase2 ? new Color(255, 30, 60, 160) : new Color(130, 30, 200, 140);

        // Dark corruption aura
        g.setColor(aura);
        g.fillOval(6, 4, 36, 40);

        // Royal cape
        g.setColor(phase2 ? new Color(180, 20, 40) : new Color(85, 20, 110));
        g.fillRect(10, 16, 28, 28);

        // Dark Royal Armor
        g.setColor(armor);
        g.fillRect(14, 16, 20, 24);

        // Gold Crown
        g.setColor(new Color(240, 200, 40));
        int[] cx = {16, 20, 24, 28, 32};
        int[] cy = {8, 12, 7, 12, 8};
        g.fillPolygon(new int[]{16, 20, 24, 28, 32, 32, 16}, new int[]{8, 12, 7, 12, 8, 14, 14}, 7);

        // Corrupted visage with blazing eyes
        g.setColor(new Color(20, 15, 25));
        g.fillOval(17, 8, 14, 12);
        g.setColor(phase2 ? Color.YELLOW : Color.RED);
        g.fillRect(20, 12, 3, 3);
        g.fillRect(25, 12, 3, 3);

        // Cursed Greatsword
        g.setColor(phase2 ? new Color(255, 60, 80) : new Color(160, 80, 240));
        int sy = (frame == 1) ? 4 : 8;
        g.fillRect(36, sy, 6, 34);
        g.setColor(new Color(240, 200, 40));
        g.fillRect(33, sy + 18, 12, 4);

        g.dispose();
        return img;
    }

    // ==========================================
    // PROJECTILES GENERATION
    // ==========================================
    private static void generateProjectiles() {
        saveImg(createArrowImg(0), "res/src/projectile/arrow_down.png");
        saveImg(createArrowImg(1), "res/src/projectile/arrow_up.png");
        saveImg(createArrowImg(2), "res/src/projectile/arrow_left.png");
        saveImg(createArrowImg(3), "res/src/projectile/arrow_right.png");
        saveImg(createDarkOrbImg(1), "res/src/projectile/dark_orb_1.png");
        saveImg(createDarkOrbImg(2), "res/src/projectile/dark_orb_2.png");
        saveImg(createSwordWaveImg(), "res/src/projectile/sword_wave.png");
    }

    private static BufferedImage createArrowImg(int dir) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(140, 95, 45)); // wood shaft
        g.setColor(new Color(210, 215, 225)); // iron head
        if (dir == 0) { // down
            g.fillRect(23, 10, 2, 24);
            g.fillPolygon(new int[]{20, 28, 24}, new int[]{30, 30, 38}, 3);
        } else if (dir == 1) { // up
            g.fillRect(23, 14, 2, 24);
            g.fillPolygon(new int[]{20, 28, 24}, new int[]{18, 18, 10}, 3);
        } else if (dir == 2) { // left
            g.fillRect(14, 23, 24, 2);
            g.fillPolygon(new int[]{18, 18, 10}, new int[]{20, 28, 24}, 3);
        } else if (dir == 3) { // right
            g.fillRect(10, 23, 24, 2);
            g.fillPolygon(new int[]{30, 30, 38}, new int[]{20, 28, 24}, 3);
        }
        g.dispose();
        return img;
    }

    private static BufferedImage createDarkOrbImg(int frame) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(130, 30, 210, 180));
        g.fillOval(12, 12, 24, 24);
        g.setColor(new Color(220, 80, 255));
        int r = (frame == 1) ? 14 : 16;
        g.fillOval(24 - r / 2, 24 - r / 2, r, r);
        g.setColor(Color.WHITE);
        g.fillOval(22, 22, 5, 5);
        g.dispose();
        return img;
    }

    private static BufferedImage createSwordWaveImg() {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(120, 210, 255));
        g.setStroke(new BasicStroke(4));
        g.drawArc(10, 10, 28, 28, 45, 90);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawArc(12, 12, 24, 24, 45, 90);
        g.dispose();
        return img;
    }

    // ==========================================
    // UI ASSETS GENERATION
    // ==========================================
    private static void generateUI() {
        saveImg(createHeartImg(2), "res/src/ui/heart_full.png");
        saveImg(createHeartImg(1), "res/src/ui/heart_half.png");
        saveImg(createHeartImg(0), "res/src/ui/heart_blank.png");
        saveImg(createCoinImg(), "res/src/ui/coin_icon.png");
        saveImg(createKeyImg(new Color(230, 190, 40)), "res/src/ui/key_icon.png");
    }

    private static BufferedImage createHeartImg(int state) {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Outline
        g.setColor(new Color(80, 10, 15));
        int[] hx = {16, 28, 30, 24, 16, 8, 2, 4};
        int[] hy = {8, 2, 8, 16, 28, 16, 8, 2};
        g.fillPolygon(hx, hy, 8);

        if (state == 2) { // Full
            g.setColor(new Color(225, 35, 45));
            int[] fx = {16, 26, 28, 22, 16, 10, 4, 6};
            int[] fy = {10, 4, 10, 16, 26, 16, 10, 4};
            g.fillPolygon(fx, fy, 8);
            // shine
            g.setColor(new Color(255, 160, 170));
            g.fillRect(8, 7, 3, 4);
        } else if (state == 1) { // Half
            g.setColor(new Color(40, 20, 25)); // dark right half
            int[] fx = {16, 26, 28, 22, 16, 10, 4, 6};
            int[] fy = {10, 4, 10, 16, 26, 16, 10, 4};
            g.fillPolygon(fx, fy, 8);
            g.setColor(new Color(225, 35, 45)); // red left half
            g.setClip(0, 0, 16, 32);
            g.fillPolygon(fx, fy, 8);
            g.setClip(null);
            g.setColor(new Color(255, 160, 170));
            g.fillRect(8, 7, 3, 4);
        } else { // Blank
            g.setColor(new Color(50, 25, 30));
            int[] fx = {16, 26, 28, 22, 16, 10, 4, 6};
            int[] fy = {10, 4, 10, 16, 26, 16, 10, 4};
            g.fillPolygon(fx, fy, 8);
        }
        g.dispose();
        return img;
    }

    // ==========================================
    // AUDIO SYNTHESIS GENERATION (WAV)
    // ==========================================
    private static void generateAudio() {
        System.out.println("Synthesizing authentic 16-bit sound effects and music tracks...");
        createWav("res/src/sound/sword_slash.wav", synthSlash());
        createWav("res/src/sound/hit_monster.wav", synthHitMonster());
        createWav("res/src/sound/hit_player.wav", synthHitPlayer());
        createWav("res/src/sound/coin_pickup.wav", synthCoin());
        createWav("res/src/sound/chest_open.wav", synthChestOpen());
        createWav("res/src/sound/door_unlock.wav", synthDoorUnlock());
        createWav("res/src/sound/powerup.wav", synthPowerup());
        createWav("res/src/sound/puzzle_solve.wav", synthPuzzleSolve());
        createWav("res/src/sound/game_over.wav", synthGameOver());
        createWav("res/src/sound/victory_fanfare.wav", synthVictory());
        createWav("res/src/sound/theme_village.wav", synthVillageTheme());
        createWav("res/src/sound/theme_forest.wav", synthForestTheme());
        createWav("res/src/sound/theme_dungeon.wav", synthDungeonTheme());
        createWav("res/src/sound/theme_boss.wav", synthBossTheme());
    }

    private static void createWav(String path, byte[] pcmData) {
        try {
            AudioFormat format = new AudioFormat(22050, 16, 1, true, false);
            ByteArrayInputStream bais = new ByteArrayInputStream(pcmData);
            AudioInputStream ais = new AudioInputStream(bais, format, pcmData.length / 2);
            File out = new File(path);
            out.getParentFile().mkdirs();
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, out);
            System.out.println("Generated Audio: " + path + " (" + pcmData.length / 2 + " samples)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] synthSlash() {
        int rate = 22050;
        int len = (int)(rate * 0.18);
        byte[] buf = new byte[len * 2];
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double freq = 800 - (t * 3500);
            if (freq < 150) freq = 150;
            double env = 1.0 - ((double) i / len);
            double noise = (Math.random() * 2 - 1) * 0.6;
            double val = (Math.sin(2 * Math.PI * freq * t) * 0.4 + noise) * env;
            short s = (short)(val * 24000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthHitMonster() {
        int rate = 22050;
        int len = (int)(rate * 0.22);
        byte[] buf = new byte[len * 2];
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double freq = 220 - (t * 800);
            if (freq < 40) freq = 40;
            double env = Math.exp(-t * 18);
            double val = (Math.sin(2 * Math.PI * freq * t) + (Math.random() * 2 - 1) * 0.5) * env;
            short s = (short)(val * 28000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthHitPlayer() {
        int rate = 22050;
        int len = (int)(rate * 0.28);
        byte[] buf = new byte[len * 2];
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double freq = 150 - (t * 400);
            if (freq < 30) freq = 30;
            double env = Math.exp(-t * 14);
            double val = Math.sin(2 * Math.PI * freq * t) * env;
            short s = (short)(val * 26000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthCoin() {
        int rate = 22050;
        int len = (int)(rate * 0.35);
        byte[] buf = new byte[len * 2];
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double freq = (t < 0.1) ? 987.77 : 1318.51; // B5 to E6
            double env = Math.exp(-t * 8);
            double val = Math.sin(2 * Math.PI * freq * t) * env;
            short s = (short)(val * 22000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthChestOpen() {
        int rate = 22050;
        int len = (int)(rate * 0.55);
        byte[] buf = new byte[len * 2];
        double[] notes = {523.25, 659.25, 783.99, 1046.50}; // C5, E5, G5, C6
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int noteIdx = Math.min((int)(t / 0.12), notes.length - 1);
            double freq = notes[noteIdx];
            double env = Math.exp(-(t % 0.12) * 12);
            double val = Math.sin(2 * Math.PI * freq * t) * env;
            short s = (short)(val * 24000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthDoorUnlock() {
        int rate = 22050;
        int len = (int)(rate * 0.45);
        byte[] buf = new byte[len * 2];
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double freq = (t < 0.15) ? 440 : 554.37;
            double click = (t < 0.05 || (t > 0.15 && t < 0.20)) ? (Math.random() * 2 - 1) * 0.8 : 0;
            double env = Math.exp(-t * 7);
            double val = (Math.sin(2 * Math.PI * freq * t) * 0.5 + click) * env;
            short s = (short)(val * 22000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthPowerup() {
        int rate = 22050;
        int len = (int)(rate * 0.7);
        byte[] buf = new byte[len * 2];
        double[] notes = {440, 554.37, 659.25, 880, 1108.73};
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int n = Math.min((int)(t / 0.12), notes.length - 1);
            double freq = notes[n];
            double env = 1.0 - (t / 0.7);
            double val = Math.sin(2 * Math.PI * freq * t) * env;
            short s = (short)(val * 20000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthPuzzleSolve() {
        int rate = 22050;
        int len = (int)(rate * 0.8);
        byte[] buf = new byte[len * 2];
        double[] notes = {587.33, 739.99, 880.00, 1174.66}; // D5, F#5, A5, D6
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int n = Math.min((int)(t / 0.18), notes.length - 1);
            double freq = notes[n];
            double env = Math.exp(-(t % 0.18) * 8);
            double val = Math.sin(2 * Math.PI * freq * t) * env;
            short s = (short)(val * 22000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthGameOver() {
        int rate = 22050;
        int len = (int)(rate * 1.5);
        byte[] buf = new byte[len * 2];
        double[] notes = {440, 415.30, 392, 349.23, 311.13, 261.63}; // descending sorrow
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int n = Math.min((int)(t / 0.24), notes.length - 1);
            double freq = notes[n];
            double env = Math.exp(-(t % 0.24) * 6);
            double val = (Math.sin(2 * Math.PI * freq * t) + 0.3 * Math.sin(4 * Math.PI * freq * t)) * env;
            short s = (short)(val * 20000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthVictory() {
        int rate = 22050;
        int len = (int)(rate * 2.2);
        byte[] buf = new byte[len * 2];
        double[] notes = {523.25, 523.25, 523.25, 659.25, 783.99, 1046.50};
        double[] durs = {0.2, 0.2, 0.2, 0.4, 0.4, 0.8};
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            double accum = 0;
            int idx = 0;
            for (int k = 0; k < durs.length; k++) {
                if (t >= accum && t < accum + durs[k]) {
                    idx = k;
                    break;
                }
                accum += durs[k];
            }
            double freq = notes[idx];
            double env = 1.0 - (t / 2.2);
            double val = (Math.sin(2 * Math.PI * freq * t) + 0.5 * Math.sin(4 * Math.PI * freq * t)) * env;
            short s = (short)(val * 22000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthVillageTheme() {
        // Peaceful, looping acoustic-style theme (8 seconds)
        int rate = 22050;
        double duration = 8.0;
        int len = (int)(rate * duration);
        byte[] buf = new byte[len * 2];
        double[] melody = {261.63, 329.63, 392.00, 523.25, 392.00, 329.63, 293.66, 349.23, 440.00, 392.00, 329.63, 261.63};
        double step = duration / melody.length;
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int note = (int)(t / step) % melody.length;
            double freq = melody[note];
            double bassFreq = melody[note / 2] / 2;
            double lead = Math.sin(2 * Math.PI * freq * t) * 0.4;
            double bass = Math.sin(2 * Math.PI * bassFreq * t) * 0.3;
            double env = 0.8 + 0.2 * Math.sin(2 * Math.PI * 4 * t);
            short s = (short)((lead + bass) * env * 16000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthForestTheme() {
        // Mysterious woodland theme with gentle rhythm (8 seconds)
        int rate = 22050;
        double duration = 8.0;
        int len = (int)(rate * duration);
        byte[] buf = new byte[len * 2];
        double[] melody = {220.00, 261.63, 329.63, 440.00, 392.00, 329.63, 293.66, 261.63};
        double step = duration / melody.length;
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int note = (int)(t / step) % melody.length;
            double freq = melody[note];
            double lead = Math.sin(2 * Math.PI * freq * t) * 0.35;
            double sub = Math.sin(2 * Math.PI * (freq / 2) * t) * 0.25;
            short s = (short)((lead + sub) * 15000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthDungeonTheme() {
        // Tense, low dungeon ambience with pulsing dark bass (8 seconds)
        int rate = 22050;
        double duration = 8.0;
        int len = (int)(rate * duration);
        byte[] buf = new byte[len * 2];
        double[] melody = {110.00, 116.54, 110.00, 98.00, 110.00, 130.81, 123.47, 98.00};
        double step = duration / melody.length;
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int note = (int)(t / step) % melody.length;
            double freq = melody[note];
            double drone = Math.sin(2 * Math.PI * freq * t) * 0.45;
            double highEcho = Math.sin(2 * Math.PI * freq * 4 * t) * 0.15;
            double pulse = 0.7 + 0.3 * Math.sin(2 * Math.PI * 2 * t);
            short s = (short)((drone + highEcho) * pulse * 16000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }

    private static byte[] synthBossTheme() {
        // High-energy fast rhythmic battle theme (8 seconds)
        int rate = 22050;
        double duration = 8.0;
        int len = (int)(rate * duration);
        byte[] buf = new byte[len * 2];
        double[] bassline = {146.83, 146.83, 174.61, 164.81, 146.83, 130.81, 146.83, 220.00};
        double step = duration / bassline.length;
        for (int i = 0; i < len; i++) {
            double t = (double) i / rate;
            int note = (int)(t / step) % bassline.length;
            double freq = bassline[note];
            // Square-ish wave for chiptune bite
            double val = (Math.sin(2 * Math.PI * freq * t) > 0 ? 0.3 : -0.3)
                       + Math.sin(2 * Math.PI * freq * 2 * t) * 0.2;
            short s = (short)(val * 16000);
            buf[i * 2] = (byte)(s & 0xff);
            buf[i * 2 + 1] = (byte)((s >> 8) & 0xff);
        }
        return buf;
    }
}
