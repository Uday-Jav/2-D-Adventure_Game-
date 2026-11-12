package Entity;

import MAIN.GamePannel;
import Object.OBJ_Coin;
import Object.Obj_Key;
import java.util.Random;

public class MON_Skeleton extends entity {

    public MON_Skeleton(GamePannel gp) {
        super(gp);
        type = 2;
        name = "Skeleton Archer";
        speed = 1;
        maxLife = 8;
        life = maxLife;
        attack = 2;
        defense = 1;

        solidArea.x = 8;
        solidArea.y = 14;
        solidArea.width = 32;
        solidArea.height = 34;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        projectile = new PROJ_Arrow(gp);

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/skeleton_1.png");
        up2 = setup("/src/monster/skeleton_2.png");
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
        if (actionLockCounter >= 80) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) directions = "up";
            else if (i <= 50) directions = "down";
            else if (i <= 75) directions = "left";
            else directions = "right";
            actionLockCounter = 0;
        }

        // Fire arrow towards player if aligned or within range
        int dist = Math.abs(worldX - gp.player.worldX) + Math.abs(worldY - gp.player.worldY);
        if (dist < gp.tileSize * 8 && shotAvailableCounter >= 60 && !projectile.alive) {
            String shootDir = directions;
            if (Math.abs(worldX - gp.player.worldX) > Math.abs(worldY - gp.player.worldY)) {
                shootDir = (worldX > gp.player.worldX) ? "left" : "right";
            } else {
                shootDir = (worldY > gp.player.worldY) ? "up" : "down";
            }
            directions = shootDir;
            projectile.set(worldX, worldY, shootDir, true, this);
            for (int k = 0; k < gp.projectileList.size(); k++) {
                if (!gp.projectileList.get(k).alive) {
                    gp.projectileList.remove(k);
                    break;
                }
            }
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
        if (i < 40) {
            dropItem(new OBJ_Coin(20));
        } else if (i < 60) {
            dropItem(new Obj_Key());
        }
    }
}
