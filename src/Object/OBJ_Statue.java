package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Statue extends SuperObject {

    public OBJ_Statue() {
        name = "Goddess Shrine";
        image = setup("/src/object/statue_goddess.png");
        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact(GamePannel gp) {
        gp.player.life = gp.player.maxLife;
        gp.saveManager.saveGame();
        gp.playSE(Sound.SFX_POWERUP);
        gp.ui.currentDialogue = "You prayed at the Goddess Shrine.\nYour Life has been fully restored!\nProgress saved safely!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
