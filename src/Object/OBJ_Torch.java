package Object;

public class OBJ_Torch extends SuperObject {
    public OBJ_Torch() {
        name = "Torch";
        image = setup("/src/object/torch_1.png");
        image2 = setup("/src/object/torch_2.png");
        collision = true;
        solidArea.x = 16;
        solidArea.y = 20;
        solidArea.width = 16;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
