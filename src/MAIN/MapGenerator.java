package MAIN;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handcrafted 50x50 map generator for all 6 game areas in Echoes of the Lost Kingdom.
 */
public class MapGenerator {

    public static final int COLS = 50;
    public static final int ROWS = 50;

    // Tile indices matching TileManager
    public static final int T_GRASS = 0;
    public static final int T_DIRT = 1;
    public static final int T_WATER = 2;
    public static final int T_SAND = 3;
    public static final int T_COBBLE = 4;
    public static final int T_BRIDGE = 5;
    public static final int T_TREE = 6;
    public static final int T_BUSH = 7;
    public static final int T_RUINS_WALL = 8;
    public static final int T_RUINS_FLOOR = 9;
    public static final int T_CAVE_FLOOR = 10;
    public static final int T_CAVE_WALL = 11;
    public static final int T_DUNGEON_FLOOR = 12;
    public static final int T_DUNGEON_WALL = 13;
    public static final int T_FORTRESS_FLOOR = 14;
    public static final int T_FORTRESS_WALL = 15;
    public static final int T_LAVA = 16;
    public static final int T_HOUSE_ROOF = 17;
    public static final int T_HOUSE_WALL = 18;
    public static final int T_SWITCH = 19;
    public static final int T_CRYSTAL = 20;
    public static final int T_SPIKES = 21;
    public static final int T_SHALLOW_WATER = 22;

    public static void main(String[] args) {
        System.out.println("Generating handcrafted level maps for all 6 areas...");
        new File("res/src/map").mkdirs();
        generateVillage();
        generateForest();
        generateRuins();
        generateCaves();
        generateDungeon();
        generateFortress();
        System.out.println("All 6 maps successfully generated!");
    }

    private static void saveMap(int[][] map, String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (int r = 0; r < ROWS; r++) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < COLS; c++) {
                    sb.append(map[c][r]);
                    if (c < COLS - 1) sb.append(" ");
                }
                pw.println(sb.toString());
            }
            System.out.println("Saved map: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // MAP 0: THE FORGOTTEN VILLAGE
    // ==========================================
    private static void generateVillage() {
        int[][] map = new int[COLS][ROWS];
        // Default lush grass
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_GRASS;
            }
        }
        // Outer boundary trees
        for (int c = 0; c < COLS; c++) {
            map[c][0] = T_TREE;
            map[c][ROWS - 1] = T_TREE;
            map[c][1] = T_TREE;
            map[c][ROWS - 2] = T_TREE;
        }
        for (int r = 0; r < ROWS; r++) {
            map[0][r] = T_TREE;
            map[COLS - 1][r] = T_TREE;
            map[1][r] = T_TREE;
            map[COLS - 2][r] = T_TREE;
        }

        // River crossing vertically on west side (cols 12-14)
        for (int r = 2; r < ROWS - 2; r++) {
            map[12][r] = T_SAND;
            map[13][r] = T_WATER;
            map[14][r] = T_WATER;
            map[15][r] = T_SAND;
        }

        // Bridges across river
        for (int c = 12; c <= 15; c++) {
            map[c][25] = T_BRIDGE;
            map[c][26] = T_BRIDGE;
            map[c][10] = T_BRIDGE;
        }

        // Central Cobblestone plaza (cols 20-32, rows 20-30)
        for (int c = 20; c <= 32; c++) {
            for (int r = 20; r <= 30; r++) {
                map[c][r] = T_COBBLE;
            }
        }

        // Cobblestone roads
        // Road to west bridge
        for (int c = 16; c < 20; c++) {
            map[c][25] = T_COBBLE;
            map[c][26] = T_COBBLE;
        }
        // Road to east exit (leading to Forest)
        for (int c = 33; c < COLS - 2; c++) {
            map[c][25] = T_DIRT;
            map[c][26] = T_DIRT;
        }
        // East forest exit opening
        map[COLS - 1][25] = T_DIRT;
        map[COLS - 1][26] = T_DIRT;
        map[COLS - 2][25] = T_DIRT;
        map[COLS - 2][26] = T_DIRT;

        // Elder's House (north side: cols 22-29, rows 8-14)
        buildHouse(map, 22, 8, 8, 6);

        // Merchant's Shop (south side: cols 22-29, rows 35-41)
        buildHouse(map, 22, 35, 8, 6);

        // Guard Barracks (east side: cols 36-42, rows 18-23)
        buildHouse(map, 36, 18, 7, 5);

        // Player starting cottage (west side past river: cols 4-10, rows 22-27)
        buildHouse(map, 4, 22, 7, 5);

        // Decorative bushes and trees
        int[][] bushes = {
            {18, 20}, {18, 21}, {34, 20}, {34, 21},
            {20, 18}, {32, 18}, {20, 32}, {32, 32},
            {16, 28}, {16, 29}, {38, 28}, {38, 29}
        };
        for (int[] b : bushes) {
            map[b[0]][b[1]] = T_BUSH;
        }

        // Village pond in south-west
        for (int c = 4; c <= 9; c++) {
            for (int r = 34; r <= 39; r++) {
                map[c][r] = T_WATER;
            }
        }

        saveMap(map, "res/src/map/map00_village.txt");
    }

    private static void buildHouse(int[][] map, int startX, int startY, int w, int h) {
        // roof (top 2 rows)
        for (int x = startX; x < startX + w; x++) {
            for (int y = startY; y < startY + 2; y++) {
                map[x][y] = T_HOUSE_ROOF;
            }
        }
        // walls
        for (int x = startX; x < startX + w; x++) {
            for (int y = startY + 2; y < startY + h; y++) {
                map[x][y] = T_HOUSE_WALL;
            }
        }
        // Doorway
        map[startX + w / 2][startY + h - 1] = T_COBBLE;
    }

    // ==========================================
    // MAP 1: WHISPERING FOREST
    // ==========================================
    private static void generateForest() {
        int[][] map = new int[COLS][ROWS];
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_GRASS;
            }
        }
        // Boundaries
        for (int c = 0; c < COLS; c++) {
            map[c][0] = T_TREE;
            map[c][ROWS - 1] = T_TREE;
            map[c][1] = T_TREE;
            map[c][ROWS - 2] = T_TREE;
        }
        for (int r = 0; r < ROWS; r++) {
            map[0][r] = T_TREE;
            map[COLS - 1][r] = T_TREE;
            map[1][r] = T_TREE;
            map[COLS - 2][r] = T_TREE;
        }

        // West entrance from village (rows 24-26)
        map[0][24] = T_DIRT; map[0][25] = T_DIRT; map[0][26] = T_DIRT;
        map[1][24] = T_DIRT; map[1][25] = T_DIRT; map[1][26] = T_DIRT;

        // Winding dirt path across forest
        for (int c = 1; c <= 25; c++) {
            map[c][25] = T_DIRT;
            map[c][26] = T_DIRT;
        }
        // North path leading to Ancient Ruins
        for (int r = 2; r <= 25; r++) {
            map[25][r] = T_DIRT;
            map[26][r] = T_DIRT;
        }
        // North exit to Ruins
        map[25][0] = T_COBBLE; map[26][0] = T_COBBLE;
        map[25][1] = T_COBBLE; map[26][1] = T_COBBLE;

        // East path leading to Hidden Glade with Ruins Key
        for (int c = 26; c <= 42; c++) {
            map[c][25] = T_DIRT;
            map[c][26] = T_DIRT;
        }
        for (int r = 26; r <= 42; r++) {
            map[42][r] = T_DIRT;
            map[43][r] = T_DIRT;
        }

        // River crossing middle forest diagonally
        for (int i = 0; i < 30; i++) {
            int cx = 35 - i / 2;
            int ry = 5 + i;
            if (cx >= 2 && cx < COLS - 2 && ry >= 2 && ry < ROWS - 2) {
                map[cx][ry] = T_WATER;
                map[cx + 1][ry] = T_WATER;
            }
        }
        // Broad stone crossing at the crossroads. Place it after the river so
        // water cannot overwrite the only safe route between the west, north,
        // and goblin-camp paths.
        for (int c = 24; c <= 27; c++) {
            for (int r = 24; r <= 26; r++) {
                map[c][r] = T_BRIDGE;
            }
        }

        // Tree clusters / groves
        for (int c = 4; c < 20; c += 2) {
            for (int r = 4; r < 20; r += 2) {
                if ((c * 7 + r * 13) % 3 != 0) {
                    map[c][r] = T_TREE;
                }
            }
        }
        for (int c = 6; c < 22; c += 2) {
            for (int r = 30; r < 45; r += 2) {
                if ((c * 11 + r * 5) % 3 != 0) {
                    map[c][r] = T_TREE;
                }
            }
        }

        // Goblin encampment in south-east (cols 36-46, rows 36-46)
        for (int c = 36; c <= 46; c++) {
            for (int r = 36; r <= 46; r++) {
                map[c][r] = T_DIRT;
            }
        }
        // surrounding trees around goblin camp except north entry
        for (int c = 35; c <= 47; c++) {
            map[c][35] = T_TREE;
            map[c][47] = T_TREE;
        }
        for (int r = 35; r <= 47; r++) {
            map[35][r] = T_TREE;
            map[47][r] = T_TREE;
        }
        map[42][35] = T_DIRT; // gate into camp
        map[43][35] = T_DIRT;

        // Hermit's glade in north-west (cols 6-14, rows 6-12)
        for (int c = 6; c <= 14; c++) {
            for (int r = 6; r <= 12; r++) {
                map[c][r] = T_GRASS;
            }
        }
        map[10][9] = T_COBBLE;

        saveMap(map, "res/src/map/map01_forest.txt");
    }

    // ==========================================
    // MAP 2: ANCIENT RUINS
    // ==========================================
    private static void generateRuins() {
        int[][] map = new int[COLS][ROWS];
        // Base mossy stone floor
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_RUINS_FLOOR;
            }
        }
        // Outer stone walls
        for (int c = 0; c < COLS; c++) {
            map[c][0] = T_RUINS_WALL;
            map[c][ROWS - 1] = T_RUINS_WALL;
            map[c][1] = T_RUINS_WALL;
            map[c][ROWS - 2] = T_RUINS_WALL;
        }
        for (int r = 0; r < ROWS; r++) {
            map[0][r] = T_RUINS_WALL;
            map[COLS - 1][r] = T_RUINS_WALL;
            map[1][r] = T_RUINS_WALL;
            map[COLS - 2][r] = T_RUINS_WALL;
        }

        // South entrance from Whispering Forest
        map[24][ROWS - 1] = T_COBBLE; map[25][ROWS - 1] = T_COBBLE;
        map[24][ROWS - 2] = T_COBBLE; map[25][ROWS - 2] = T_COBBLE;

        // Grand Entry Corridor (cols 23-26, rows 32-48)
        for (int r = 32; r < ROWS - 2; r++) {
            map[22][r] = T_RUINS_WALL;
            map[27][r] = T_RUINS_WALL;
        }

        // Spikes trap corridor (rows 35-37)
        for (int c = 23; c <= 26; c++) {
            map[c][36] = T_SPIKES;
        }

        // Outer courtyard (rows 22-31)
        // Locked Great Gate dividing courtyard from Mini-Boss Hall
        for (int c = 2; c < COLS - 2; c++) {
            map[c][22] = T_RUINS_WALL;
        }
        // Gate passage in center
        map[24][22] = T_RUINS_FLOOR;
        map[25][22] = T_RUINS_FLOOR;

        // Mini-Boss Arena: Guardian's Sanctum (cols 12-38, rows 6-21)
        for (int c = 12; c <= 38; c++) {
            map[c][5] = T_RUINS_WALL;
        }
        for (int r = 5; r <= 21; r++) {
            map[11][r] = T_RUINS_WALL;
            map[39][r] = T_RUINS_WALL;
        }
        // Guardian Arena columns
        int[][] pillars = {
            {16, 9}, {16, 17}, {34, 9}, {34, 17},
            {20, 13}, {30, 13}
        };
        for (int[] p : pillars) {
            map[p[0]][p[1]] = T_RUINS_WALL;
        }

        // East passage to Crystal Caves (cols 40-48, rows 12-14)
        for (int c = 39; c < COLS - 1; c++) {
            map[c][11] = T_RUINS_WALL;
            map[c][12] = T_CAVE_FLOOR;
            map[c][13] = T_CAVE_FLOOR;
            map[c][14] = T_RUINS_WALL;
        }
        // East exit opening
        map[COLS - 1][12] = T_CAVE_FLOOR;
        map[COLS - 1][13] = T_CAVE_FLOOR;

        saveMap(map, "res/src/map/map02_ruins.txt");
    }

    // ==========================================
    // MAP 3: CRYSTAL CAVES
    // ==========================================
    private static void generateCaves() {
        int[][] map = new int[COLS][ROWS];
        // Default solid dark rock
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_CAVE_WALL;
            }
        }

        // West entrance from Ruins (cols 0-4, row 12-13)
        for (int c = 0; c < 8; c++) {
            map[c][12] = T_CAVE_FLOOR;
            map[c][13] = T_CAVE_FLOOR;
        }

        // Main cavern hall (cols 8-42, rows 8-40)
        for (int c = 8; c <= 42; c++) {
            for (int r = 8; r <= 40; r++) {
                map[c][r] = T_CAVE_FLOOR;
            }
        }

        // Underground chasm with water (cols 22-26, rows 10-38)
        for (int c = 22; c <= 26; c++) {
            for (int r = 10; r <= 38; r++) {
                map[c][r] = T_WATER;
            }
        }
        // Stone bridges over chasm
        for (int c = 22; c <= 26; c++) {
            map[c][16] = T_BRIDGE;
            map[c][24] = T_BRIDGE;
            map[c][25] = T_BRIDGE;
            map[c][30] = T_BRIDGE;
        }

        // 3 Ancient Crystal Altars (for progression puzzle)
        // Cyan Altar (west side)
        map[14][16] = T_CRYSTAL;
        map[14][18] = T_SWITCH; // Cyan pressure switch

        // Violet Altar (east side)
        map[34][16] = T_CRYSTAL;
        map[34][18] = T_SWITCH; // Violet pressure switch

        // Emerald Altar (south side)
        map[24][36] = T_CRYSTAL;
        map[24][34] = T_SWITCH; // Emerald pressure switch

        // North staircase down to Shadow Dungeon (cols 23-25, rows 4-7)
        for (int c = 23; c <= 25; c++) {
            for (int r = 2; r <= 7; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        map[24][2] = T_COBBLE; // portal / entrance
        map[24][1] = T_COBBLE;
        map[24][0] = T_COBBLE;

        saveMap(map, "res/src/map/map03_caves.txt");
    }

    // ==========================================
    // MAP 4: SHADOW DUNGEON
    // ==========================================
    private static void generateDungeon() {
        int[][] map = new int[COLS][ROWS];
        // Solid dungeon brick
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_DUNGEON_WALL;
            }
        }

        // South entrance from Caves (cols 23-26, rows 44-49)
        for (int c = 23; c <= 26; c++) {
            for (int r = 44; r < 50; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        // Connect the south entrance to the central chamber; the former
        // three-tile gap trapped the player in the entrance alcove.
        for (int c = 23; c <= 26; c++) {
            for (int r = 40; r <= 44; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }

        // Central Grand Chamber (cols 16-33, rows 24-40)
        for (int c = 16; c <= 33; c++) {
            for (int r = 24; r <= 40; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }

        // West puzzle room (cols 4-13, rows 24-34)
        for (int c = 4; c <= 13; c++) {
            for (int r = 24; r <= 34; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        // corridor connecting West room
        for (int c = 13; c <= 16; c++) {
            map[c][28] = T_DUNGEON_FLOOR;
            map[c][29] = T_DUNGEON_FLOOR;
        }
        map[8][28] = T_SWITCH; // Dungeon switch to lower iron gate

        // East treasure room (cols 36-45, rows 24-34)
        for (int c = 36; c <= 45; c++) {
            for (int r = 24; r <= 34; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        for (int c = 33; c <= 36; c++) {
            map[c][28] = T_DUNGEON_FLOOR;
            map[c][29] = T_DUNGEON_FLOOR;
        }

        // North Relic Sanctum (cols 16-33, rows 6-18)
        for (int c = 16; c <= 33; c++) {
            for (int r = 6; r <= 18; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        // Corridor between Central and North Sanctum
        for (int c = 23; c <= 26; c++) {
            for (int r = 18; r <= 24; r++) {
                map[c][r] = T_DUNGEON_FLOOR;
            }
        }
        // Iron gate position (row 21)
        map[23][21] = T_SPIKES;
        map[24][21] = T_SPIKES;
        map[25][21] = T_SPIKES;
        map[26][21] = T_SPIKES;

        // North grand exit stairs leading up to King's Fortress
        for (int c = 23; c <= 26; c++) {
            for (int r = 0; r < 6; r++) {
                map[c][r] = T_FORTRESS_FLOOR;
            }
        }

        saveMap(map, "res/src/map/map04_dungeon.txt");
    }

    // ==========================================
    // MAP 5: LOST KING'S FORTRESS
    // ==========================================
    private static void generateFortress() {
        int[][] map = new int[COLS][ROWS];
        // Corrupted royal walls
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                map[c][r] = T_FORTRESS_WALL;
            }
        }

        // South entrance from Dungeon (cols 23-26, rows 42-49)
        for (int c = 23; c <= 26; c++) {
            for (int r = 42; r < 50; r++) {
                map[c][r] = T_FORTRESS_FLOOR;
            }
        }

        // Grand Royal Hallway (cols 18-31, rows 22-42)
        for (int c = 18; c <= 31; c++) {
            for (int r = 22; r <= 42; r++) {
                map[c][r] = T_FORTRESS_FLOOR;
            }
        }
        // Red velvet carpet in center
        for (int c = 23; c <= 26; c++) {
            for (int r = 22; r <= 42; r++) {
                map[c][r] = T_COBBLE;
            }
        }
        // Royal pillars along hallway
        for (int r = 26; r <= 38; r += 4) {
            map[20][r] = T_FORTRESS_WALL;
            map[29][r] = T_FORTRESS_WALL;
        }

        // Checkpoint sanctuary with Goddess Statue (cols 10-16, rows 26-32)
        for (int c = 10; c <= 16; c++) {
            for (int r = 26; r <= 32; r++) {
                map[c][r] = T_FORTRESS_FLOOR;
            }
        }
        for (int c = 16; c <= 18; c++) {
            map[c][29] = T_FORTRESS_FLOOR;
        }

        // Grand Corrupted Throne Room (cols 8-41, rows 4-20)
        for (int c = 8; c <= 41; c++) {
            for (int r = 4; r <= 20; r++) {
                map[c][r] = T_FORTRESS_FLOOR;
            }
        }

        // Throne room decorative void fire / lava pits at corners
        int[][] lavaPits = {
            {10, 6}, {11, 6}, {10, 7}, {11, 7},
            {38, 6}, {39, 6}, {38, 7}, {39, 7},
            {10, 18}, {11, 18}, {10, 19}, {11, 19},
            {38, 18}, {39, 18}, {38, 19}, {39, 19}
        };
        for (int[] p : lavaPits) {
            map[p[0]][p[1]] = T_LAVA;
        }

        // Royal dais / throne in center-north (cols 22-27, rows 6-8)
        for (int c = 22; c <= 27; c++) {
            for (int r = 6; r <= 8; r++) {
                map[c][r] = T_COBBLE;
            }
        }

        saveMap(map, "res/src/map/map05_fortress.txt");
    }
}
