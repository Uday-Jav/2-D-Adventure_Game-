package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Boots extends SuperObject {
    public OBJ_Boots() {
        name = "Hermes Boots";
        image = setup("/src/object/boots.png");
        description = "[Hermes Boots]\nWinged boots that grant\npermanent +1 movement speed.";
        price = 60;
    }

    public void use(GamePannel gp) {
        gp.player.speed += 1;
        gp.playSE(Sound.SFX_POWERUP);
        gp.ui.currentDialogue = "You equipped Hermes Boots!\nMovement speed increased by 1!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
