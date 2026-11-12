package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Sword_Silver extends SuperObject {
    public OBJ_Sword_Silver() {
        name = "Silver Sword";
        image = setup("/src/object/sword_silver.png");
        value = 2; // +2 Attack
        description = "[Silver Sword]\nAn ancient forged blade.\nIncreases attack power by +2.";
        price = 80;
    }

    public void use(GamePannel gp) {
        gp.player.attackPower += value;
        gp.playSE(Sound.SFX_POWERUP);
        gp.ui.currentDialogue = "You equipped the Silver Sword!\nAttack power increased by +2!";
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
