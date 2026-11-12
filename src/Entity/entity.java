package Entity;

import MAIN.GamePannel;
import Object.SuperObject;
import Object.OBJ_Coin;
import Object.OBJ_Potion_Red;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

public class entity {

    public GamePannel gp;
    public int worldX, worldY;
    public int speed = 3;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String directions = "down";
    public int spritecounter = 0;
    public int spriteNum = 1;

    // Hitbox: both solidArea and legacy spelling soildArea
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    public Rectangle soildArea = solidArea;
    public int solidAreaDefaultX = 8;
    public int solidAreaDefaultY = 16;
    public boolean collisionOn = false;

    // Combat & Life
    public int maxLife = 6;
    public int life = 6;
    public int attack = 1;
    public int defense = 0;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 36, 36);

    // AI & Entity Type
    public int actionLockCounter = 0;
    public int type = 0; // 0=player, 1=npc, 2=monster, 3=projectile, 4=boss
    public String name = "";
    public Projectile projectile;
    public int shotAvailableCounter = 0;
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    public entity(GamePannel gp) {
        this.gp = gp;
        this.soildArea = this.solidArea;
    }

    public entity() {
        this.soildArea = this.solidArea;
    }

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

    public void setAction() {
        // Overridden by monster and NPC AI
    }

    public void damageReaction() {
        // Overridden by monsters
    }

    public void checkDrop() {
        // Overridden by monsters to drop coins or items
    }

    public void dropItem(SuperObject droppedItem) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        if (gp.checker != null) {
            gp.checker.checkTile(this);
            gp.checker.checkObject(this, false);
            gp.checker.checkEntity(this, gp.npc);
            gp.checker.checkEntity(this, gp.monster);
            boolean contactPlayer = gp.checker.checkPlayer(this);

            if (this.type == 2 && contactPlayer) { // Monster damages player
                damagePlayer(attack);
            }
        }

        // Move if collision is false
        if (!collisionOn) {
            switch (directions) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // Animation counter
        spritecounter++;
        if (spritecounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spritecounter = 0;
        }

        // Invincibility counter
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Projectile cooldown
        if (shotAvailableCounter < 60) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
            gp.playSE(MAIN.Sound.SFX_HIT_PLAYER);
            int damage = Math.max(1, attack - gp.player.defense);
            gp.player.life -= damage;
            gp.player.invincible = true;
            if (gp.player.life <= 0) {
                gp.player.life = 0;
                gp.player.alive = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int camX = gp.getCameraX();
        int camY = gp.getCameraY();
        int screenX = worldX - camX;
        int screenY = worldY - camY;

        if (screenX + gp.tileSize * 2 > 0 && screenX - gp.tileSize < gp.screenwidth &&
            screenY + gp.tileSize * 2 > 0 && screenY - gp.tileSize < gp.screenHeight) {

            BufferedImage img = null;
            switch (directions) {
                case "up": img = (spriteNum == 1) ? up1 : up2; break;
                case "down": img = (spriteNum == 1) ? down1 : down2; break;
                case "left": img = (spriteNum == 1) ? left1 : left2; break;
                case "right": img = (spriteNum == 1) ? right1 : right2; break;
            }
            if (img == null) img = down1;

            // Monster floating HP bar
            if (type == 2 && hpBarOn) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 11, gp.tileSize + 2, 8);
                g2.setColor(new Color(255, 30, 40));
                g2.fillRect(screenX, screenY - 10, (int) hpBarValue, 6);
                hpBarCounter++;
                if (hpBarCounter > 400) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            // Invincibility flashing
            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            // Dying blink effect
            if (dying) {
                dyingAnimation(g2);
            }

            if (img != null) {
                g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            // Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int interval = 5;
        if (dyingCounter % (interval * 2) < interval) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        } else {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        }
        if (dyingCounter > 40) {
            alive = false;
        }
    }
}
