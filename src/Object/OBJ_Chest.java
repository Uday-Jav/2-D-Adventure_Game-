package Object;

import MAIN.GamePannel;
import MAIN.Sound;

public class OBJ_Chest extends SuperObject {

    public SuperObject loot;
    public int goldReward = 0;
    public String chestId = "";

    public OBJ_Chest(String id, SuperObject loot, int gold) {
        this.chestId = id;
        this.loot = loot;
        this.goldReward = gold;
        name = "Chest";
        image = setup("/src/object/chest.png");
        image2 = setup("/src/object/chest_open.png");
        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public OBJ_Chest() {
        this("chest_default", new Obj_Key(), 20);
    }

    @Override
    public void interact(GamePannel gp) {
        if (!opened) {
            opened = true;
            gp.playSE(Sound.SFX_CHEST);
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest!\n");
            if (loot != null) {
                gp.player.inventory.add(loot);
                sb.append("Obtained: ").append(loot.name).append("!\n");
            }
            if (goldReward > 0) {
                gp.player.coin += goldReward;
                sb.append("Obtained: ").append(goldReward).append(" Gold!");
            }
            gp.ui.currentDialogue = sb.toString();
            gp.gameState = GamePannel.DIALOGUE_STATE;
            gp.saveManager.openedChests.add(chestId);
        } else {
            gp.ui.currentDialogue = "The chest is already empty.";
            gp.gameState = GamePannel.DIALOGUE_STATE;
        }
    }
}
