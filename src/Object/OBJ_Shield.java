package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Shield extends SuperObject {
    public OBJ_Shield() {
        name = "Knight Shield";
        image = setup("/src/object/shield.png");
        value = 1; // +1 Defense
        description = "[Knight Shield]\nRoyal crested shield.\nReduces incoming damage by 1.";
        price = 70;
    }

    public void use(GamePannel gp) {
        gp.player.defense += value;
        gp.playSE(Sound.SFX_POWERUP);
        gp.ui.currentDialogue = "You equipped the Knight Shield!\nDefense increased by +1!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
