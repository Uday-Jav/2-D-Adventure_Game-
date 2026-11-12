package Entity;

import MAIN.GamePannel;

public class NPC_Merchant extends entity {

    public NPC_Merchant(GamePannel gp) {
        super(gp);
        type = 1;
        name = "Merchant Boris";
        speed = 0;
        directions = "down";

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/npc/merchant.png");
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }

    public void speak() {
        gp.ui.currentDialogue = "Merchant Boris:\n'Welcome to Boris's Provisions, adventurer!\nTake a look at my wares!'";
        gp.gameState = GamePannel.SHOP_STATE;
    }
}
