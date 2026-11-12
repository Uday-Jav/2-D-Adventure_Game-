package Entity;

import MAIN.GamePannel;
import MAIN.KeyHandler;
import MAIN.Sound;
import Object.SuperObject;
import Object.Obj_Key;
import Object.OBJ_Sword_Silver;
import Object.OBJ_Boots;
import Object.OBJ_Potion_Red;
import Object.OBJ_Potion_Blue;
import Object.OBJ_Shield;
import Object.OBJ_Coin;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends entity {

    public KeyHandler key;
    public final int screenX;
    public final int screenY;

    public int coin = 20;
    public int attackPower = 2;
    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePannel gp, KeyHandler key) {
        super(gp);
        this.key = key;

        screenX = gp.screenwidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        soildArea = solidArea;

        attackArea = new Rectangle(0, 0, 36, 36);

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        directions = "down";
        maxLife = 6;
        life = maxLife;
        attack = 2;
        defense = 0;
        attackPower = 2;
        alive = true;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(new Obj_Key());
        inventory.add(new OBJ_Potion_Red());
    }

    public void getPlayerImage() {
        up1 = setup("/src/player/up_walk_1.png");
        up2 = setup("/src/player/up_walk_2.png");
        down1 = setup("/src/player/down_walk_1.png");
        down2 = setup("/src/player/down_walk_2.png");
        left1 = setup("/src/player/left_walk_1.png");
        left2 = setup("/src/player/left_walk_2.png");
        right1 = setup("/src/player/right_walk_1.png");
        right2 = setup("/src/player/right_walk_2.png");
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/src/player/attack_up_1.png");
        attackUp2 = setup("/src/player/attack_up_2.png");
        attackDown1 = setup("/src/player/attack_down_1.png");
        attackDown2 = setup("/src/player/attack_down_2.png");
        attackLeft1 = setup("/src/player/attack_left_1.png");
        attackLeft2 = setup("/src/player/attack_left_2.png");
        attackRight1 = setup("/src/player/attack_right_1.png");
        attackRight2 = setup("/src/player/attack_right_2.png");
    }

    public int searchItemInInventory(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if (itemIndex < inventory.size()) {
            SuperObject selected = inventory.get(itemIndex);
            if (selected instanceof OBJ_Potion_Red) {
                ((OBJ_Potion_Red) selected).use(gp);
                inventory.remove(itemIndex);
            } else if (selected instanceof OBJ_Potion_Blue) {
                ((OBJ_Potion_Blue) selected).use(gp);
                inventory.remove(itemIndex);
            } else if (selected instanceof OBJ_Boots) {
                ((OBJ_Boots) selected).use(gp);
                inventory.remove(itemIndex);
            } else if (selected instanceof OBJ_Sword_Silver) {
                ((OBJ_Sword_Silver) selected).use(gp);
                inventory.remove(itemIndex);
            } else if (selected instanceof OBJ_Shield) {
                ((OBJ_Shield) selected).use(gp);
                inventory.remove(itemIndex);
            }
        }
    }

    @Override
    public void update() {
        if (!alive || life <= 0) {
            alive = false;
            gp.stopMusic();
            gp.playSE(Sound.SFX_GAMEOVER);
            gp.gameState = GamePannel.GAME_OVER_STATE;
            return;
        }

        // Failsafe: if player ever spawns inside or gets trapped in a solid obstacle or
        // water, rescue to safe checkpoint
        if (gp.checker != null && gp.checker.isPlayerInSolid()) {
            gp.respawnPlayer();
            return;
        }

        if (attacking) {
            attackingLogic();
            return;
        }

        // Check for attack input
        if (key.attackPressed) {
            attacking = true;
            gp.playSE(Sound.SFX_SLASH);
            return;
        }

        // Check for interaction input [E]
        if (key.interactPressed) {
            key.interactPressed = false;
            interactWithNearby();
        }

        // Movement
        if (key.upPressed || key.downPressed || key.leftPressed || key.rightPressed) {
            if (key.upPressed)
                directions = "up";
            else if (key.downPressed)
                directions = "down";
            else if (key.leftPressed)
                directions = "left";
            else if (key.rightPressed)
                directions = "right";

            collisionOn = false;
            gp.checker.checkTile(this);
            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.checker.checkEntity(this, gp.npc);
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Move if collision is false
            if (!collisionOn) {
                switch (directions) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // Check map transition event
            gp.eventHandler.checkEvent();

            spritecounter++;
            if (spritecounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spritecounter = 0;
            }
        }

        // Invincibility counter
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void attackingLogic() {
        spritecounter++;
        if (spritecounter <= 5) {
            spriteNum = 1;
        } else if (spritecounter <= 20) {
            spriteNum = 2;

            // Save current position and calculate attack hitbox
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (directions) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attackPower);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            spriteNum = 1;
            spritecounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999 && gp.obj[i] != null) {
            SuperObject o = gp.obj[i];
            if (o instanceof OBJ_Coin) {
                coin += o.value;
                gp.playSE(Sound.SFX_COIN);
                gp.obj[i] = null;
            }
        }
    }

    public void interactWithNearby() {
        // Check objects directly in front
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        switch (directions) {
            case "up":
                worldY -= gp.tileSize / 2;
                break;
            case "down":
                worldY += gp.tileSize / 2;
                break;
            case "left":
                worldX -= gp.tileSize / 2;
                break;
            case "right":
                worldX += gp.tileSize / 2;
                break;
        }
        int objIndex = gp.checker.checkObject(this, true);
        int npcIndex = gp.checker.checkEntity(this, gp.npc);
        worldX = currentWorldX;
        worldY = currentWorldY;

        if (objIndex != 999 && gp.obj[objIndex] != null) {
            gp.obj[objIndex].interact(gp);
        } else if (npcIndex != 999 && gp.npc[npcIndex] != null) {
            entity n = gp.npc[npcIndex];
            if (n instanceof NPC_Elder)
                ((NPC_Elder) n).speak();
            else if (n instanceof NPC_Merchant)
                ((NPC_Merchant) n).speak();
            else if (n instanceof NPC_Guard)
                ((NPC_Guard) n).speak();
            else if (n instanceof NPC_Hermit)
                ((NPC_Hermit) n).speak();
        }
    }

    public void contactMonster(int i) {
        if (i != 999 && gp.monster[i] != null && !invincible && gp.monster[i].alive && !gp.monster[i].dying) {
            gp.playSE(Sound.SFX_HIT_PLAYER);
            int damage = Math.max(1, gp.monster[i].attack - defense);
            life -= damage;
            invincible = true;
        }
    }

    public void damageMonster(int i, int attackDamage) {
        if (i != 999 && gp.monster[i] != null && gp.monster[i].alive && !gp.monster[i].invincible) {
            gp.playSE(Sound.SFX_HIT_MONSTER);
            gp.monster[i].life -= attackDamage;
            gp.monster[i].invincible = true;
            gp.monster[i].damageReaction();

            if (gp.monster[i].life <= 0) {
                gp.monster[i].dying = true;
                gp.monster[i].checkDrop();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        if (!attacking) {
            switch (directions) {
                case "up":
                    img = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    img = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    img = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    img = (spriteNum == 1) ? right1 : right2;
                    break;
            }
        } else {
            switch (directions) {
                case "up":
                    img = (spriteNum == 1) ? attackUp1 : attackUp2;
                    break;
                case "down":
                    img = (spriteNum == 1) ? attackDown1 : attackDown2;
                    break;
                case "left":
                    img = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                    break;
                case "right":
                    img = (spriteNum == 1) ? attackRight1 : attackRight2;
                    break;
            }
        }
        if (img == null)
            img = down1;

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        int drawX = worldX - gp.getCameraX();
        int drawY = worldY - gp.getCameraY();

        g2.drawImage(img, drawX, drawY, gp.tileSize, gp.tileSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
