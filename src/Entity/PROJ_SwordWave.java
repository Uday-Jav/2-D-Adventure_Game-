package Entity;

import MAIN.GamePannel;
import java.awt.image.BufferedImage;

public class PROJ_SwordWave extends Projectile {

    public PROJ_SwordWave(GamePannel gp) {
        super(gp);
        name = "Sword Wave";
        speed = 7;
        maxLife = 60;
        life = maxLife;
        attack = 3;
        alive = false;
        getImage();
    }

    public void getImage() {
        BufferedImage wave = setup("/src/projectile/sword_wave.png");
        up1 = wave;
        up2 = wave;
        down1 = wave;
        down2 = wave;
        left1 = wave;
        left2 = wave;
        right1 = wave;
        right2 = wave;
    }
}
