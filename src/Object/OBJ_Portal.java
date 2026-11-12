package Object;

import MAIN.GamePannel;

public class OBJ_Portal extends SuperObject {

    public int targetMap;
    public int targetCol;
    public int targetRow;

    public OBJ_Portal(int targetMap, int targetCol, int targetRow) {
        this.targetMap = targetMap;
        this.targetCol = targetCol;
        this.targetRow = targetRow;
        name = "Portal";
        image = setup("/src/object/portal_1.png");
        image2 = setup("/src/object/portal_2.png");
        collision = false;
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact(GamePannel gp) {
        gp.eventHandler.teleport(targetMap, targetCol, targetRow);
    }
}
