package Entity;

import MAIN.GamePannel;
import Object.OBJ_Relic_Sunstone;
import java.util.Random;

public class BOSS_RuinsGuardian extends entity {

    public BOSS_RuinsGuardian(GamePannel gp) {
        super(gp);
        type = 4; // Boss type
        name = "Ruins Guardian";
        speed = 2;
        maxLife = 35;
        life = maxLife;
        attack = 3;
        defense = 2;

        solidArea.x = 6;
        solidArea.y = 8;
        solidArea.width = 36;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/monster/guardian_1.png");
        up2 = setup("/src/monster/guardian_2.png");
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
        if (actionLockCounter >= 50) {
            // Aggressive tracking
            int dx = Math.abs(worldX - gp.player.worldX);
            int dy = Math.abs(worldY - gp.player.worldY);

            if (dx > dy) {
                directions = (worldX > gp.player.worldX) ? "left" : "right";
            } else {
                directions = (worldY > gp.player.worldY) ? "up" : "down";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        // Boss doesn't get knocked back
    }

    @Override
    public void checkDrop() {
        gp.questManager.ruinsGuardianDefeated = true;
        dropItem(new OBJ_Relic_Sunstone());
        gp.playSE(MAIN.Sound.SFX_VICTORY);
        gp.ui.currentDialogue = "The Ruins Guardian has fallen!\nThe ancient Sunstone Relic has been recovered!\nThe eastern cavern passage is now unsealed!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
