package Object;

import MAIN.GamePannel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SuperObject {

    public BufferedImage image;
    public BufferedImage image2;
    public String name = "";
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public boolean opened = false;
    public String description = "";
    public int price = 0;
    public int value = 0; // Heal amount, coin value, or stat bonus

    public BufferedImage setup(String imagePath) {
        BufferedImage img = null;
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                is = getClass().getResourceAsStream(imagePath.replace("/src", ""));
            }
            if (is != null) {
                img = ImageIO.read(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public void interact(GamePannel gp) {
        // Base interact method - overridden by specific objects
    }

    public void draw(Graphics2D g2, GamePannel gp) {
        int camX = gp.getCameraX();
        int camY = gp.getCameraY();
        int screenX = worldX - camX;
        int screenY = worldY - camY;

        if (screenX + gp.tileSize * 2 > 0 && screenX - gp.tileSize < gp.screenwidth &&
            screenY + gp.tileSize * 2 > 0 && screenY - gp.tileSize < gp.screenHeight) {

            BufferedImage current = (opened && image2 != null) ? image2 : image;
            if (current != null) {
                g2.drawImage(current, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
