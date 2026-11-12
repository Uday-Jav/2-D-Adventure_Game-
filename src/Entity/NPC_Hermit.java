package Entity;

import MAIN.GamePannel;

public class NPC_Hermit extends entity {

    public NPC_Hermit(GamePannel gp) {
        super(gp);
        type = 1;
        name = "Hermit Corvus";
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
        up1 = setup("/src/npc/hermit.png");
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }

    public void speak() {
        gp.ui.currentDialogue = "Hermit Corvus:\n'Hehehe... seeking the Ancient Ruins to the north?\nThe stone gate cannot be forced. The goblins in the\nsoutheast glade hoard the sacred Ruins Key in a chest.\nDefeat them, take the key, and proceed north!'";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
