package Entity;

import MAIN.GamePannel;

public class PROJ_Arrow extends Projectile {

    public PROJ_Arrow(GamePannel gp) {
        super(gp);
        name = "Arrow";
        speed = 6;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/src/projectile/arrow_up.png");
        up2 = up1;
        down1 = setup("/src/projectile/arrow_down.png");
        down2 = down1;
        left1 = setup("/src/projectile/arrow_left.png");
        left2 = left1;
        right1 = setup("/src/projectile/arrow_right.png");
        right2 = right1;
    }
}
