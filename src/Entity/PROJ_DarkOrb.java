package Entity;

import MAIN.GamePannel;

public class PROJ_DarkOrb extends Projectile {

    public PROJ_DarkOrb(GamePannel gp) {
        super(gp);
        name = "Dark Orb";
        speed = 5;
        maxLife = 90;
        life = maxLife;
        attack = 3;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/src/projectile/dark_orb_1.png");
        up2 = setup("/src/projectile/dark_orb_2.png");
        down1 = up1;
        down2 = up2;
        left1 = up1;
        left2 = up2;
        right1 = up1;
        right2 = up2;
    }
}
