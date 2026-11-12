package MAIN;

public class EventHandler {

    private final GamePannel gp;
    private int eventCooldown = 0;

    public EventHandler(GamePannel gp) {
        this.gp = gp;
    }

    public void checkEvent() {
        if (eventCooldown > 0) {
            eventCooldown--;
            return;
        }

        int col = gp.player.worldX / gp.tileSize;
        int row = gp.player.worldY / gp.tileSize;

        // MAP 0 (Village) -> MAP 1 (Forest)
        if (gp.currentMap == 0 && col >= 48 && (row >= 24 && row <= 27)) {
            if (gp.questManager.talkedToElder) {
                teleport(1, 2, 25);
                gp.playMusic(Sound.BGM_FOREST);
            } else {
                gp.ui.currentDialogue = "Captain Roderick stops you:\n'Speak to Elder Vaelen before heading into the forest!'";
                gp.gameState = GamePannel.DIALOGUE_STATE;
                gp.player.worldX -= gp.tileSize;
            }
        }
        // MAP 1 (Forest) -> MAP 0 (Village)
        else if (gp.currentMap == 1 && col <= 1 && (row >= 24 && row <= 27)) {
            teleport(0, 47, 25);
            gp.playMusic(Sound.BGM_VILLAGE);
        }
        // MAP 1 (Forest) -> MAP 2 (Ruins)
        else if (gp.currentMap == 1 && row <= 1 && (col >= 24 && col <= 27)) {
            teleport(2, 24, 47);
            gp.playMusic(Sound.BGM_DUNGEON);
        }
        // MAP 2 (Ruins) -> MAP 1 (Forest)
        else if (gp.currentMap == 2 && row >= 48 && (col >= 23 && col <= 26)) {
            teleport(1, 25, 2);
            gp.playMusic(Sound.BGM_FOREST);
        }
        // MAP 2 (Ruins) -> MAP 3 (Caves)
        else if (gp.currentMap == 2 && col >= 48 && (row >= 11 && row <= 14)) {
            if (gp.questManager.ruinsGuardianDefeated) {
                teleport(3, 2, 12);
                gp.playMusic(Sound.BGM_DUNGEON);
            } else {
                gp.ui.currentDialogue = "The cavern tunnel is blocked by ancient fallen rubble.\nDefeat the Ruins Guardian to clear the path!";
                gp.gameState = GamePannel.DIALOGUE_STATE;
                gp.player.worldX -= gp.tileSize;
            }
        }
        // MAP 3 (Caves) -> MAP 2 (Ruins)
        else if (gp.currentMap == 3 && col <= 1 && (row >= 11 && row <= 14)) {
            teleport(2, 47, 12);
            gp.playMusic(Sound.BGM_DUNGEON);
        }
        // MAP 3 (Caves) -> MAP 4 (Dungeon)
        else if (gp.currentMap == 3 && row <= 2 && (col >= 22 && col <= 26)) {
            if (gp.questManager.cavesPuzzleSolved) {
                teleport(4, 24, 47);
                gp.playMusic(Sound.BGM_DUNGEON);
            } else {
                gp.ui.currentDialogue = "A heavy stone gate bars the stairs downward.\nActivate all 3 glowing Crystal Altars to unseal it!";
                gp.gameState = GamePannel.DIALOGUE_STATE;
                gp.player.worldY += gp.tileSize;
            }
        }
        // MAP 4 (Dungeon) -> MAP 3 (Caves)
        else if (gp.currentMap == 4 && row >= 48 && (col >= 22 && col <= 26)) {
            teleport(3, 24, 4);
            gp.playMusic(Sound.BGM_DUNGEON);
        }
        // MAP 4 (Dungeon) -> MAP 5 (Fortress)
        else if (gp.currentMap == 4 && row <= 2 && (col >= 22 && col <= 26)) {
            if (gp.questManager.ruinsGuardianDefeated && gp.questManager.shadowOrbRecovered) {
                teleport(5, 24, 47);
                gp.playMusic(Sound.BGM_BOSS);
            } else {
                gp.ui.currentDialogue = "The Grand Seal of King Malakor bars the way!\nYou must possess BOTH the Sunstone Relic and the Shadow Orb\nto break this dark barrier!";
                gp.gameState = GamePannel.DIALOGUE_STATE;
                gp.player.worldY += gp.tileSize;
            }
        }
        // MAP 5 (Fortress) -> MAP 4 (Dungeon)
        else if (gp.currentMap == 5 && row >= 48 && (col >= 22 && col <= 26)) {
            teleport(4, 24, 4);
            gp.playMusic(Sound.BGM_DUNGEON);
        }
        // Lava Hazard in Fortress
        else if (gp.currentMap == 5 && gp.tileM.mapTileNum[5][col][row] == MapGenerator.T_LAVA) {
            if (!gp.player.invincible) {
                gp.playSE(Sound.SFX_HIT_PLAYER);
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
    }

    public void teleport(int targetMap, int col, int row) {
        gp.currentMap = targetMap;
        gp.player.worldX = col * gp.tileSize;
        gp.player.worldY = row * gp.tileSize;
        gp.aSetter.setObjectsForMap(targetMap);
        gp.aSetter.setNPCsForMap(targetMap);
        gp.aSetter.setMonstersForMap(targetMap);
        eventCooldown = 30; // 0.5s cooldown
    }
}
