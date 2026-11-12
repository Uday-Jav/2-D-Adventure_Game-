package Object;

import MAIN.GamePannel;

public class OBJ_Sign extends SuperObject {

    public String message;

    public OBJ_Sign(String message) {
        this.message = message;
        name = "Sign";
        image = setup("/src/object/sign.png");
        collision = true;
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact(GamePannel gp) {
        gp.ui.currentDialogue = message;
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
