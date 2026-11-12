package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Potion_Blue extends SuperObject {
    public OBJ_Potion_Blue() {
        name = "Speed Elixir";
        image = setup("/src/object/potion_blue.png");
        description = "[Speed Elixir]\nA glowing blue potion.\nGrants permanent +1 speed.";
        price = 40;
    }

    public void use(GamePannel gp) {
        gp.player.speed += 1;
        gp.playSE(Sound.SFX_POWERUP);
        gp.ui.currentDialogue = "You drank the Speed Elixir!\nYour reflexes and speed improved!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
