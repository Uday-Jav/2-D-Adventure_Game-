package Entity;

import MAIN.GamePannel;
import MAIN.Sound;
import java.awt.*;
import java.util.Random;

public class BOSS_CorruptedKing extends entity {

    public boolean phase2 = false;
    private int attackCooldown = 0;
    private int teleportCooldown = 0;

    public BOSS_CorruptedKing(GamePannel gp) {
        super(gp);
        type = 4; // Boss type
        name = "Malakor, The Corrupted King";
        speed = 2;
        maxLife = 80;
        life = maxLife;
        attack = 4;
        defense = 2;

        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        projectile = new PROJ_DarkOrb(gp);

        getImage();
    }

    public void getImage() {
        if (!phase2) {
            up1 = setup("/src/monster/king_1.png");
            up2 = setup("/src/monster/king_2.png");
            down1 = up1;
            down2 = up2;
            left1 = up1;
            left2 = up2;
            right1 = up1;
            right2 = up2;
        } else {
            up1 = setup("/src/monster/king_phase2_1.png");
            up2 = setup("/src/monster/king_phase2_2.png");
            down1 = up1;
            down2 = up2;
            left1 = up1;
            left2 = up2;
            right1 = up1;
            right2 = up2;
        }
    }

    @Override
    public void setAction() {
        // Check phase 2 transition
        if (!phase2 && life <= maxLife / 2) {
            phase2 = true;
            speed = 3;
            attack = 5;
            defense = 3;
            getImage();
            gp.playSE(Sound.SFX_POWERUP);
        }

        actionLockCounter++;
        if (actionLockCounter >= 40) {
            int dx = Math.abs(worldX - gp.player.worldX);
            int dy = Math.abs(worldY - gp.player.worldY);
            if (dx > dy) {
                directions = (worldX > gp.player.worldX) ? "left" : "right";
            } else {
                directions = (worldY > gp.player.worldY) ? "up" : "down";
            }
            actionLockCounter = 0;
        }

        // Teleport logic (every ~6 seconds)
        teleportCooldown++;
        if (teleportCooldown > 360) {
            teleportCooldown = 0;
            Random r = new Random();
            int newCol = 14 + r.nextInt(22);
            int newRow = 8 + r.nextInt(10);
            worldX = newCol * gp.tileSize;
            worldY = newRow * gp.tileSize;
            gp.playSE(Sound.SFX_SLASH);
        }

        // Attack wave logic
        attackCooldown++;
        int threshold = phase2 ? 50 : 80;
        if (attackCooldown >= threshold) {
            attackCooldown = 0;
            // Shoot dark orb or sword wave towards player
            String shootDir = directions;
            if (Math.abs(worldX - gp.player.worldX) > Math.abs(worldY - gp.player.worldY)) {
                shootDir = (worldX > gp.player.worldX) ? "left" : "right";
            } else {
                shootDir = (worldY > gp.player.worldY) ? "up" : "down";
            }
            directions = shootDir;

            Projectile proj = phase2 ? new PROJ_SwordWave(gp) : new PROJ_DarkOrb(gp);
            proj.set(worldX, worldY, shootDir, true, this);
            gp.projectileList.add(proj);

            if (phase2) {
                // In phase 2, fire secondary orthogonal projectile
                Projectile proj2 = new PROJ_DarkOrb(gp);
                String altDir = shootDir.equals("up") || shootDir.equals("down") ? "left" : "up";
                proj2.set(worldX, worldY, altDir, true, this);
                gp.projectileList.add(proj2);
            }
        }
    }

    @Override
    public void damageReaction() {
        // Boss resists knockback
    }

    @Override
    public void checkDrop() {
        gp.questManager.corruptedKingDefeated = true;
        gp.stopMusic();
        gp.playSE(Sound.SFX_VICTORY);
        // Trigger glorious victory ending screen!
        gp.gameState = GamePannel.VICTORY_STATE;
    }
}
