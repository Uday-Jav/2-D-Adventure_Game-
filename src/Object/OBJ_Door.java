package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Door extends SuperObject {

    public String doorId = "";
    public String requiredKey = "Key";

    public OBJ_Door(String id, String requiredKey) {
        this.doorId = id;
        this.requiredKey = requiredKey;
        name = "Door";
        image = setup("/src/object/door.png");
        image2 = setup("/src/object/door_open.png");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public OBJ_Door() {
        this("door_default", "Key");
    }

    @Override
    public void interact(GamePannel gp) {
        if (opened) return;

        // Check player inventory for matching key
        int keyIndex = gp.player.searchItemInInventory(requiredKey);
        if (keyIndex != -1) {
            opened = true;
            collision = false;
            gp.player.inventory.remove(keyIndex);
            gp.playSE(Sound.SFX_DOOR);
            gp.saveManager.unlockedDoors.add(doorId);
            gp.ui.currentDialogue = "You unlocked the door using the " + requiredKey + "!";
            gp.gameState = GamePannel.DIALOGUE_STATE;
        } else {
            gp.ui.currentDialogue = "The door is locked!\nYou need a " + requiredKey + " to open it.";
            gp.gameState = GamePannel.DIALOGUE_STATE;
        }
    }
}
