package MAIN;

import Entity.*;
import Object.*;

public class AssetSetter {

    private final GamePannel gp;

    public AssetSetter(GamePannel gp) {
        this.gp = gp;
    }

    public void setAll() {
        setObjectsForMap(gp.currentMap);
        setNPCsForMap(gp.currentMap);
        setMonstersForMap(gp.currentMap);
    }

    public void setObjectsForMap(int map) {
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        int i = 0;
        if (map == 0) { // Village
            gp.obj[i] = new OBJ_Statue();
            gp.obj[i].worldX = 20 * gp.tileSize;
            gp.obj[i].worldY = 22 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Sign("Welcome to the Forgotten Village!\nNorth: Elder Vaelen's Lodge\nSouth: Merchant Boris's Shop\nEast: Whispering Forest");
            gp.obj[i].worldX = 24 * gp.tileSize;
            gp.obj[i].worldY = 22 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Sign("Elder's Council House\nSeek wisdom within.");
            gp.obj[i].worldX = 22 * gp.tileSize;
            gp.obj[i].worldY = 14 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Sign("Boris's Provisions\nPotions, equipment and keys for sale!");
            gp.obj[i].worldX = 22 * gp.tileSize;
            gp.obj[i].worldY = 34 * gp.tileSize;
            i++;

            OBJ_Chest villageChest = new OBJ_Chest("village_chest", new OBJ_Potion_Red(), 30);
            if (gp.saveManager.openedChests.contains("village_chest")) {
                villageChest.opened = true;
            }
            gp.obj[i] = villageChest;
            gp.obj[i].worldX = 6 * gp.tileSize;
            gp.obj[i].worldY = 28 * gp.tileSize;
            i++;

        } else if (map == 1) { // Forest
            OBJ_Chest forestChest = new OBJ_Chest("forest_key_chest", new OBJ_Key_Ruins(), 50);
            if (gp.saveManager.openedChests.contains("forest_key_chest")) {
                forestChest.opened = true;
            }
            gp.obj[i] = forestChest;
            gp.obj[i].worldX = 43 * gp.tileSize;
            gp.obj[i].worldY = 42 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Sign("Whispering Forest Crossroads\nNorth: Ancient Ruins Gate\nEast: Goblin Camp\nWest: Forgotten Village");
            gp.obj[i].worldX = 27 * gp.tileSize;
            gp.obj[i].worldY = 23 * gp.tileSize;
            i++;

        } else if (map == 2) { // Ruins
            OBJ_Door ruinsGate = new OBJ_Door("ruins_gate", "Ruins Key");
            if (gp.saveManager.unlockedDoors.contains("ruins_gate")) {
                ruinsGate.opened = true;
                ruinsGate.collision = false;
            }
            gp.obj[i] = ruinsGate;
            gp.obj[i].worldX = 24 * gp.tileSize;
            gp.obj[i].worldY = 22 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Statue();
            gp.obj[i].worldX = 14 * gp.tileSize;
            gp.obj[i].worldY = 36 * gp.tileSize;
            i++;

        } else if (map == 3) { // Caves
            OBJ_Chest caveChest = new OBJ_Chest("cave_boots_chest", new OBJ_Boots(), 40);
            if (gp.saveManager.openedChests.contains("cave_boots_chest")) {
                caveChest.opened = true;
            }
            gp.obj[i] = caveChest;
            gp.obj[i].worldX = 10 * gp.tileSize;
            gp.obj[i].worldY = 10 * gp.tileSize;
            i++;

            // 3 Ancient Crystal Switches
            gp.obj[i] = new OBJ_Switch("crystal_cyan", 0);
            gp.obj[i].worldX = 14 * gp.tileSize;
            gp.obj[i].worldY = 18 * gp.tileSize;
            if (gp.questManager.crystalsActivated[0]) gp.obj[i].opened = true;
            i++;

            gp.obj[i] = new OBJ_Switch("crystal_violet", 1);
            gp.obj[i].worldX = 34 * gp.tileSize;
            gp.obj[i].worldY = 18 * gp.tileSize;
            if (gp.questManager.crystalsActivated[1]) gp.obj[i].opened = true;
            i++;

            gp.obj[i] = new OBJ_Switch("crystal_emerald", 2);
            gp.obj[i].worldX = 24 * gp.tileSize;
            gp.obj[i].worldY = 34 * gp.tileSize;
            if (gp.questManager.crystalsActivated[2]) gp.obj[i].opened = true;
            i++;

        } else if (map == 4) { // Dungeon
            OBJ_Door dungeonDoor = new OBJ_Door("dungeon_door", "Dungeon Key");
            if (gp.saveManager.unlockedDoors.contains("dungeon_door")) {
                dungeonDoor.opened = true;
                dungeonDoor.collision = false;
            }
            gp.obj[i] = dungeonDoor;
            gp.obj[i].worldX = 24 * gp.tileSize;
            gp.obj[i].worldY = 21 * gp.tileSize;
            i++;

            OBJ_Chest keyChest = new OBJ_Chest("dungeon_key_chest", new OBJ_Key_Dungeon(), 30);
            if (gp.saveManager.openedChests.contains("dungeon_key_chest")) {
                keyChest.opened = true;
            }
            gp.obj[i] = keyChest;
            gp.obj[i].worldX = 6 * gp.tileSize;
            gp.obj[i].worldY = 26 * gp.tileSize;
            i++;

            // Shadow Orb Relic Chest in sanctum
            OBJ_Chest relicChest = new OBJ_Chest("relic_shadow_chest", new OBJ_Relic_ShadowOrb(), 100);
            if (gp.saveManager.openedChests.contains("relic_shadow_chest")) {
                relicChest.opened = true;
            }
            gp.obj[i] = relicChest;
            gp.obj[i].worldX = 24 * gp.tileSize;
            gp.obj[i].worldY = 10 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Switch("dungeon_switch", 0);
            gp.obj[i].worldX = 8 * gp.tileSize;
            gp.obj[i].worldY = 28 * gp.tileSize;
            if (gp.questManager.shadowDungeonSwitchPressed) gp.obj[i].opened = true;
            i++;

        } else if (map == 5) { // Fortress
            gp.obj[i] = new OBJ_Statue();
            gp.obj[i].worldX = 13 * gp.tileSize;
            gp.obj[i].worldY = 29 * gp.tileSize;
            i++;

            gp.obj[i] = new OBJ_Sign("The Throne Room of King Malakor\nEnter only with the Sunstone and Shadow Orb.");
            gp.obj[i].worldX = 22 * gp.tileSize;
            gp.obj[i].worldY = 21 * gp.tileSize;
            i++;
        }
    }

    public void setNPCsForMap(int map) {
        for (int i = 0; i < gp.npc.length; i++) {
            gp.npc[i] = null;
        }

        if (map == 0) { // Village
            gp.npc[0] = new NPC_Elder(gp);
            gp.npc[0].worldX = 25 * gp.tileSize;
            gp.npc[0].worldY = 15 * gp.tileSize;

            gp.npc[1] = new NPC_Merchant(gp);
            gp.npc[1].worldX = 25 * gp.tileSize;
            gp.npc[1].worldY = 34 * gp.tileSize;

            gp.npc[2] = new NPC_Guard(gp);
            gp.npc[2].worldX = 44 * gp.tileSize;
            gp.npc[2].worldY = 24 * gp.tileSize;

        } else if (map == 1) { // Forest
            gp.npc[0] = new NPC_Hermit(gp);
            gp.npc[0].worldX = 10 * gp.tileSize;
            gp.npc[0].worldY = 9 * gp.tileSize;
        }
    }

    public void setMonstersForMap(int map) {
        for (int i = 0; i < gp.monster.length; i++) {
            gp.monster[i] = null;
        }

        int i = 0;
        if (map == 1) { // Forest
            gp.monster[i] = new MON_Slime(gp);
            gp.monster[i].worldX = 14 * gp.tileSize;
            gp.monster[i].worldY = 22 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Slime(gp);
            gp.monster[i].worldX = 20 * gp.tileSize;
            gp.monster[i].worldY = 16 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Slime(gp);
            gp.monster[i].worldX = 19 * gp.tileSize;
            gp.monster[i].worldY = 34 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Goblin(gp);
            gp.monster[i].worldX = 38 * gp.tileSize;
            gp.monster[i].worldY = 38 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Goblin(gp);
            gp.monster[i].worldX = 44 * gp.tileSize;
            gp.monster[i].worldY = 40 * gp.tileSize;
            i++;

        } else if (map == 2) { // Ruins
            gp.monster[i] = new MON_Skeleton(gp);
            gp.monster[i].worldX = 16 * gp.tileSize;
            gp.monster[i].worldY = 26 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Skeleton(gp);
            gp.monster[i].worldX = 32 * gp.tileSize;
            gp.monster[i].worldY = 26 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Skeleton(gp);
            gp.monster[i].worldX = 24 * gp.tileSize;
            gp.monster[i].worldY = 32 * gp.tileSize;
            i++;

            if (!gp.questManager.ruinsGuardianDefeated) {
                gp.monster[i] = new BOSS_RuinsGuardian(gp);
                gp.monster[i].worldX = 24 * gp.tileSize;
                gp.monster[i].worldY = 12 * gp.tileSize;
                i++;
            }

        } else if (map == 3) { // Caves
            gp.monster[i] = new MON_Slime(gp);
            gp.monster[i].worldX = 14 * gp.tileSize;
            gp.monster[i].worldY = 24 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Slime(gp);
            gp.monster[i].worldX = 34 * gp.tileSize;
            gp.monster[i].worldY = 24 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Golem(gp);
            gp.monster[i].worldX = 20 * gp.tileSize;
            gp.monster[i].worldY = 26 * gp.tileSize;
            i++;

        } else if (map == 4) { // Dungeon
            gp.monster[i] = new MON_Cultist(gp);
            gp.monster[i].worldX = 20 * gp.tileSize;
            gp.monster[i].worldY = 30 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Cultist(gp);
            gp.monster[i].worldX = 28 * gp.tileSize;
            gp.monster[i].worldY = 30 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Golem(gp);
            gp.monster[i].worldX = 24 * gp.tileSize;
            gp.monster[i].worldY = 34 * gp.tileSize;
            i++;

        } else if (map == 5) { // Fortress
            gp.monster[i] = new MON_Golem(gp);
            gp.monster[i].worldX = 22 * gp.tileSize;
            gp.monster[i].worldY = 32 * gp.tileSize;
            i++;

            gp.monster[i] = new MON_Golem(gp);
            gp.monster[i].worldX = 26 * gp.tileSize;
            gp.monster[i].worldY = 32 * gp.tileSize;
            i++;

            if (!gp.questManager.corruptedKingDefeated) {
                gp.monster[i] = new BOSS_CorruptedKing(gp);
                gp.monster[i].worldX = 24 * gp.tileSize;
                gp.monster[i].worldY = 10 * gp.tileSize;
                i++;
            }
        }
    }
}
