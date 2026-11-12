package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Switch extends SuperObject {

    public String switchId = "";
    public int sequenceIndex = 0; // For sequence-based puzzles

    public OBJ_Switch(String switchId, int sequenceIndex) {
        this.switchId = switchId;
        this.sequenceIndex = sequenceIndex;
        name = "Switch";
        image = setup("/src/tile/switch_off.png");
        image2 = setup("/src/tile/switch_on.png");
        collision = false;
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact(GamePannel gp) {
        if (!opened) {
            opened = true;
            gp.playSE(Sound.SFX_PUZZLE);
            gp.questManager.triggerSwitch(switchId, sequenceIndex);
        }
    }
}
