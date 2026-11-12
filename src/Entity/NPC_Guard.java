package Entity;

import MAIN.GamePannel;

public class NPC_Guard extends entity {

    public NPC_Guard(GamePannel gp) {
        super(gp);
        type = 1;
        name = "Captain Roderick";
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
        up1 = setup("/src/npc/guard.png");
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }

    public void speak() {
        if (!gp.questManager.talkedToElder) {
            gp.ui.currentDialogue = "Captain Roderick:\n'Halt! The Whispering Forest is far too dangerous!\nSpeak to Elder Vaelen in the northern lodge first\nbefore I can let you pass through this gate.'";
        } else {
            gp.ui.currentDialogue = "Captain Roderick:\n'The Elder has given his blessing. The east gate\nis open to you, traveler. May the sacred light\nprotect your blade!'";
        }
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
