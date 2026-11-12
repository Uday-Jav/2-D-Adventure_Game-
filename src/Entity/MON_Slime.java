package Entity;

import MAIN.GamePannel;
import Object.OBJ_Coin;
import Object.OBJ_Potion_Red;
import java.util.Random;

public class MON_Slime extends entity {

    public MON_Slime(GamePannel gp) {
        super(gp);
        type = 2; // Monster
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 1;
        defense = 0;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/slime_1.png");
        up2 = setup("/src/monster/slime_2.png");
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
        if (actionLockCounter >= 90) {
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
        actionLockCounter = 0;
        directions = gp.player.directions; // Knocked away
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;
        if (i < 50) {
            dropItem(new OBJ_Coin(5));
        } else if (i < 70) {
            dropItem(new OBJ_Potion_Red());
        }
    }
}
