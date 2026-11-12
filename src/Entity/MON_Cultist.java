package Entity;

import MAIN.GamePannel;
import Object.OBJ_Coin;
import Object.OBJ_Potion_Red;
import java.util.Random;

public class MON_Cultist extends entity {

    public MON_Cultist(GamePannel gp) {
        super(gp);
        type = 2;
        name = "Dark Cultist";
        speed = 2;
        maxLife = 10;
        life = maxLife;
        attack = 3;
        defense = 1;

        solidArea.x = 8;
        solidArea.y = 12;
        solidArea.width = 32;
        solidArea.height = 36;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        projectile = new PROJ_DarkOrb(gp);

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/cultist_1.png");
        up2 = setup("/src/monster/cultist_2.png");
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
        if (actionLockCounter >= 70) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) directions = "up";
            else if (i <= 50) directions = "down";
            else if (i <= 75) directions = "left";
            else directions = "right";
            actionLockCounter = 0;
        }

        // Cast dark magic projectile
        int dist = Math.abs(worldX - gp.player.worldX) + Math.abs(worldY - gp.player.worldY);
        if (dist < gp.tileSize * 9 && shotAvailableCounter >= 55 && !projectile.alive) {
            String shootDir = directions;
            if (Math.abs(worldX - gp.player.worldX) > Math.abs(worldY - gp.player.worldY)) {
                shootDir = (worldX > gp.player.worldX) ? "left" : "right";
            } else {
                shootDir = (worldY > gp.player.worldY) ? "up" : "down";
            }
            directions = shootDir;
            projectile.set(worldX, worldY, shootDir, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        directions = gp.player.directions;
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100);
        if (i < 50) {
            dropItem(new OBJ_Coin(25));
        } else {
            dropItem(new OBJ_Potion_Red());
        }
    }
}
