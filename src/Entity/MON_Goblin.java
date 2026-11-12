package Entity;

import MAIN.GamePannel;
import Object.OBJ_Coin;
import java.util.Random;

public class MON_Goblin extends entity {

    public MON_Goblin(GamePannel gp) {
        super(gp);
        type = 2;
        name = "Goblin Scout";
        speed = 2;
        maxLife = 6;
        life = maxLife;
        attack = 2;
        defense = 0;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/goblin_1.png");
        up2 = setup("/src/monster/goblin_2.png");
        down1 = up1;
        down2 = up2;
        left1 = up1;
        left2 = up2;
        right1 = up1;
        right2 = up2;
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter >= 60) {
            // Aggro if close to player (within 5 tiles)
            int dx = Math.abs(worldX - gp.player.worldX);
            int dy = Math.abs(worldY - gp.player.worldY);
            if (dx + dy < gp.tileSize * 6) {
                if (dx > dy) {
                    directions = (worldX > gp.player.worldX) ? "left" : "right";
                } else {
                    directions = (worldY > gp.player.worldY) ? "up" : "down";
                }
            } else {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25) directions = "up";
                else if (i <= 50) directions = "down";
                else if (i <= 75) directions = "left";
                else directions = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        directions = gp.player.directions;
    }

    @Override
    public void checkDrop() {
        dropItem(new OBJ_Coin(15));
    }
}
