package MAIN;

import Entity.entity;

public class CollisionChecker {

    private final GamePannel gp;

    public CollisionChecker(GamePannel gp) {
        this.gp = gp;
    }

    public void checkTile(entity ent) {
        int leftWorldX = ent.worldX + ent.solidArea.x;
        int rightWorldX = ent.worldX + ent.solidArea.x + ent.solidArea.width;
        int topWorldY = ent.worldY + ent.solidArea.y;
        int bottomWorldY = ent.worldY + ent.solidArea.y + ent.solidArea.height;

        int leftCol = leftWorldX / gp.tileSize;
        int rightCol = rightWorldX / gp.tileSize;
        int topRow = topWorldY / gp.tileSize;
        int bottomRow = bottomWorldY / gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;

        switch (ent.directions) {
            case "up":
                topRow = (topWorldY - ent.speed) / gp.tileSize;
                if (topRow < 0 || leftCol < 0 || rightCol >= gp.maxWorldCol) {
                    ent.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][rightCol][topRow];
                if (isSolid(tileNum1) || isSolid(tileNum2)) {
                    ent.collisionOn = true;
                }
                break;
            case "down":
                bottomRow = (bottomWorldY + ent.speed) / gp.tileSize;
                if (bottomRow >= gp.maxWorldRow || leftCol < 0 || rightCol >= gp.maxWorldCol) {
                    ent.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][leftCol][bottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][rightCol][bottomRow];
                if (isSolid(tileNum1) || isSolid(tileNum2)) {
                    ent.collisionOn = true;
                }
                break;
            case "left":
                leftCol = (leftWorldX - ent.speed) / gp.tileSize;
                if (leftCol < 0 || topRow < 0 || bottomRow >= gp.maxWorldRow) {
                    ent.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][leftCol][bottomRow];
                if (isSolid(tileNum1) || isSolid(tileNum2)) {
                    ent.collisionOn = true;
                }
                break;
            case "right":
                rightCol = (rightWorldX + ent.speed) / gp.tileSize;
                if (rightCol >= gp.maxWorldCol || topRow < 0 || bottomRow >= gp.maxWorldRow) {
                    ent.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][rightCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][rightCol][bottomRow];
                if (isSolid(tileNum1) || isSolid(tileNum2)) {
                    ent.collisionOn = true;
                }
                break;
        }
    }

    public boolean isSolid(int tileNum) {
        if (tileNum < 0 || tileNum >= gp.tileM.tiles.length || gp.tileM.tiles[tileNum] == null) {
            return false;
        }
        return gp.tileM.tiles[tileNum].collision;
    }

    public boolean isPlayerInSolid() {
        if (gp.tileM == null || gp.tileM.mapTileNum == null || gp.player == null) return false;
        int leftCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int rightCol = (gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width - 1) / gp.tileSize;
        int topRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
        int bottomRow = (gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height - 1) / gp.tileSize;

        if (leftCol < 0 || rightCol >= gp.maxWorldCol || topRow < 0 || bottomRow >= gp.maxWorldRow) return true;

        return isSolid(gp.tileM.mapTileNum[gp.currentMap][leftCol][topRow]) ||
               isSolid(gp.tileM.mapTileNum[gp.currentMap][rightCol][topRow]) ||
               isSolid(gp.tileM.mapTileNum[gp.currentMap][leftCol][bottomRow]) ||
               isSolid(gp.tileM.mapTileNum[gp.currentMap][rightCol][bottomRow]);
    }

    public int checkObject(entity ent, boolean isPlayer) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                ent.solidArea.x = ent.worldX + ent.solidAreaDefaultX;
                ent.solidArea.y = ent.worldY + ent.solidAreaDefaultY;

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

                offsetHitbox(ent);

                if (ent.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        ent.collisionOn = true;
                    }
                    if (isPlayer) {
                        index = i;
                    }
                }

                ent.solidArea.x = ent.solidAreaDefaultX;
                ent.solidArea.y = ent.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkEntity(entity ent, entity[] target) {
        int index = 999;
        if (target == null) return index;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null && target[i] != ent && target[i].alive) {
                ent.solidArea.x = ent.worldX + ent.solidAreaDefaultX;
                ent.solidArea.y = ent.worldY + ent.solidAreaDefaultY;

                target[i].solidArea.x = target[i].worldX + target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].worldY + target[i].solidAreaDefaultY;

                offsetHitbox(ent);

                if (ent.solidArea.intersects(target[i].solidArea)) {
                    ent.collisionOn = true;
                    index = i;
                }

                ent.solidArea.x = ent.solidAreaDefaultX;
                ent.solidArea.y = ent.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(entity ent) {
        boolean contactPlayer = false;

        ent.solidArea.x = ent.worldX + ent.solidAreaDefaultX;
        ent.solidArea.y = ent.worldY + ent.solidAreaDefaultY;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidAreaDefaultY;

        offsetHitbox(ent);

        if (ent.solidArea.intersects(gp.player.solidArea)) {
            ent.collisionOn = true;
            contactPlayer = true;
        }

        ent.solidArea.x = ent.solidAreaDefaultX;
        ent.solidArea.y = ent.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }

    private void offsetHitbox(entity ent) {
        switch (ent.directions) {
            case "up": ent.solidArea.y -= ent.speed; break;
            case "down": ent.solidArea.y += ent.speed; break;
            case "left": ent.solidArea.x -= ent.speed; break;
            case "right": ent.solidArea.x += ent.speed; break;
        }
    }
}
