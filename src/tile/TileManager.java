package tile;

import MAIN.GamePannel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private final GamePannel gp;
    public Tile[] tiles;
    public int[][][] mapTileNum; // [map][col][row]
    public final int maxMap = 6;

    public TileManager(GamePannel gp) {
        this.gp = gp;
        tiles = new Tile[30];
        mapTileNum = new int[maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/src/map/map00_village.txt", 0);
        loadMap("/src/map/map01_forest.txt", 1);
        loadMap("/src/map/map02_ruins.txt", 2);
       loadMap("/src/map/map03_caves.txt", 3);
       loadMap("/src/map/map04_dungeon.txt", 4);
        loadMap("/src/map/map05_fortress.txt", 5);
    }

    private void setupTile(int index, String imageName, boolean collision) {
        try {
            tiles[index] = new Tile();
            InputStream is = getClass().getResourceAsStream("/src/tile/" + imageName);
            if (is == null) {
                is = getClass().getResourceAsStream("/tile/" + imageName);
            }
            if (is != null) {
                tiles[index].image = ImageIO.read(is);
            } else {
                // Fallback procedural placeholder so it never crashes
                BufferedImage fallback = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = fallback.createGraphics();
                g.setColor(collision ? Color.GRAY : Color.GREEN);
                g.fillRect(0, 0, gp.tileSize, gp.tileSize);
                g.dispose();
                tiles[index].image = fallback;
            }
            tiles[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        setupTile(0, "grass.png", false);
        setupTile(1, "earth.png", false);
        setupTile(2, "water.png", true);
        setupTile(3, "sand.png", false);
        setupTile(4, "cobblestone.png", false);
        setupTile(5, "bridge.png", false);
        setupTile(6, "tree.png", true);
        setupTile(7, "bush.png", true);
        setupTile(8, "ruins_wall.png", true);
        setupTile(9, "ruins_floor.png", false);
        setupTile(10, "cave_floor.png", false);
        setupTile(11, "cave_wall.png", true);
        setupTile(12, "dungeon_floor.png", false);
        setupTile(13, "dungeon_wall.png", true);
        setupTile(14, "fortress_floor.png", false);
        setupTile(15, "fortress_wall.png", true);
        setupTile(16, "hazard_lava.png", false);
        setupTile(17, "house_roof.png", true);
        setupTile(18, "house_wall.png", true);
        setupTile(19, "switch_off.png", false);
        setupTile(20, "crystal_cyan.png", true);
        setupTile(21, "spikes_retracted.png", false);
        setupTile(22, "shallow_water.png", false);
        setupTile(23, "wall.png", true);
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                is = getClass().getResourceAsStream(filePath.replace("/src", ""));
            }
            if (is == null) {
                System.err.println("Map file not found: " + filePath);
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.trim().split("\\s+");
                for (int c = 0; c < numbers.length && c < gp.maxWorldCol; c++) {
                    int num = Integer.parseInt(numbers[c]);
                    mapTileNum[map][c][row] = num;
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int currentMap = gp.currentMap;
        int camX = gp.getCameraX();
        int camY = gp.getCameraY();

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[currentMap][worldCol][worldRow];
            if (tileNum < 0 || tileNum >= tiles.length || tiles[tileNum] == null) {
                tileNum = 0;
            }

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - camX;
            int screenY = worldY - camY;

            // Frustum culling: render if within screen bounds
            if (screenX + gp.tileSize > 0 && screenX < gp.screenwidth &&
                screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
