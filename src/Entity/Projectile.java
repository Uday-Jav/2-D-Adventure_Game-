package Entity;

import MAIN.GamePannel;
import MAIN.Sound;

public class Projectile extends entity {

    public entity user;

    public Projectile(GamePannel gp) {
        super(gp);
        type = 3;
    }

    public void set(int worldX, int worldY, String directions, boolean alive, entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.directions = directions;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    @Override
    public void update() {
        if (user == gp.player) {
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        } else {
            boolean contactPlayer = gp.checker.checkPlayer(this);
            if (!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                alive = false;
            }
        }

        switch (directions) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spritecounter++;
        if (spritecounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spritecounter = 0;
        }
    }
}
