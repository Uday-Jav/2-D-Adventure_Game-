package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Potion_Red extends SuperObject {
    public OBJ_Potion_Red() {
        name = "Health Potion";
        image = setup("/src/object/potion_red.png");
        value = 4; // Restores 4 HP (2 full hearts)
        description = "[Health Potion]\nA ruby elixir restoring\nup to 4 Life points.";
        price = 20;
    }

    public void use(GamePannel gp) {
        if (gp.player.life < gp.player.maxLife) {
            gp.player.life = Math.min(gp.player.maxLife, gp.player.life + value);
            gp.playSE(Sound.SFX_POWERUP);
            gp.ui.currentDialogue = "You drank a Health Potion!\nRestored 4 Life points.";
        } else {
            gp.ui.currentDialogue = "Your Life is already full!";
        }
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
