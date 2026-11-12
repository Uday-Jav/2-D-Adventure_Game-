package Entity;

import MAIN.GamePannel;
import Object.OBJ_Coin;
import Object.OBJ_Potion_Blue;
import java.util.Random;

public class MON_Golem extends entity {

    public MON_Golem(GamePannel gp) {
        super(gp);
        type = 2;
        name = "Stone Golem";
        speed = 1;
        maxLife = 14;
        life = maxLife;
        attack = 3;
        defense = 2; // High armor

        solidArea.x = 4;
        solidArea.y = 8;
        solidArea.width = 40;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/golem_1.png");
        up2 = setup("/src/monster/golem_2.png");
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
        if (actionLockCounter >= 100) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) directions = "up";
            else if (i <= 50) directions = "down";
            else if (i <= 75) directions = "left";
            else directions = "right";
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        // Golem barely flinches
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100);
        if (i < 50) {
            dropItem(new OBJ_Coin(30));
        } else {
            dropItem(new OBJ_Potion_Blue());
        }
    }
}
