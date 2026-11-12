package MAIN;

import Object.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SaveManager {

    private final GamePannel gp;
    private final File saveFile = new File("save_data.dat");

    public Set<String> openedChests = new HashSet<>();
    public Set<String> unlockedDoors = new HashSet<>();

    public SaveManager(GamePannel gp) {
        this.gp = gp;
    }

    public boolean hasSaveData() {
        return saveFile.exists() && saveFile.length() > 0;
    }

    public void saveGame() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(saveFile))) {
            pw.println("MAP=" + gp.currentMap);
            pw.println("X=" + gp.player.worldX);
            pw.println("Y=" + gp.player.worldY);
            pw.println("LIFE=" + gp.player.life);
            pw.println("MAXLIFE=" + gp.player.maxLife);
            pw.println("SPEED=" + gp.player.speed);
            pw.println("ATTACK=" + gp.player.attackPower);
            pw.println("DEFENSE=" + gp.player.defense);
            pw.println("COIN=" + gp.player.coin);

            // Quests
            pw.println("Q_ELDER=" + gp.questManager.talkedToElder);
            pw.println("Q_KEY=" + gp.questManager.ruinsKeyFound);
            pw.println("Q_RUINS_UNLOCKED=" + gp.questManager.ruinsUnlocked);
            pw.println("Q_GUARDIAN=" + gp.questManager.ruinsGuardianDefeated);
            pw.println("Q_CRYSTAL_0=" + gp.questManager.crystalsActivated[0]);
            pw.println("Q_CRYSTAL_1=" + gp.questManager.crystalsActivated[1]);
            pw.println("Q_CRYSTAL_2=" + gp.questManager.crystalsActivated[2]);
            pw.println("Q_CAVES=" + gp.questManager.cavesPuzzleSolved);
            pw.println("Q_DUNGEON_SWITCH=" + gp.questManager.shadowDungeonSwitchPressed);
            pw.println("Q_SHADOW_ORB=" + gp.questManager.shadowOrbRecovered);
            pw.println("Q_FORTRESS=" + gp.questManager.fortressUnlocked);
            pw.println("Q_KING=" + gp.questManager.corruptedKingDefeated);

            // Opened chests
            StringBuilder chests = new StringBuilder("CHESTS=");
            for (String cid : openedChests) chests.append(cid).append(",");
            pw.println(chests.toString());

            // Unlocked doors
            StringBuilder doors = new StringBuilder("DOORS=");
            for (String did : unlockedDoors) doors.append(did).append(",");
            pw.println(doors.toString());

            // Inventory
            StringBuilder inv = new StringBuilder("INVENTORY=");
            for (SuperObject item : gp.player.inventory) {
                if (item != null) inv.append(item.name).append(",");
            }
            pw.println(inv.toString());

            System.out.println("Saved game successfully to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        if (!hasSaveData()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line;
            openedChests.clear();
            unlockedDoors.clear();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String val = parts[1].trim();

                switch (key) {
                    case "MAP": gp.currentMap = Integer.parseInt(val); break;
                    case "X": gp.player.worldX = Integer.parseInt(val); break;
                    case "Y": gp.player.worldY = Integer.parseInt(val); break;
                    case "LIFE": gp.player.life = Integer.parseInt(val); break;
                    case "MAXLIFE": gp.player.maxLife = Integer.parseInt(val); break;
                    case "SPEED": gp.player.speed = Integer.parseInt(val); break;
                    case "ATTACK": gp.player.attackPower = Integer.parseInt(val); break;
                    case "DEFENSE": gp.player.defense = Integer.parseInt(val); break;
                    case "COIN": gp.player.coin = Integer.parseInt(val); break;

                    case "Q_ELDER": gp.questManager.talkedToElder = Boolean.parseBoolean(val); break;
                    case "Q_KEY": gp.questManager.ruinsKeyFound = Boolean.parseBoolean(val); break;
                    case "Q_RUINS_UNLOCKED": gp.questManager.ruinsUnlocked = Boolean.parseBoolean(val); break;
                    case "Q_GUARDIAN": gp.questManager.ruinsGuardianDefeated = Boolean.parseBoolean(val); break;
                    case "Q_CRYSTAL_0": gp.questManager.crystalsActivated[0] = Boolean.parseBoolean(val); break;
                    case "Q_CRYSTAL_1": gp.questManager.crystalsActivated[1] = Boolean.parseBoolean(val); break;
                    case "Q_CRYSTAL_2": gp.questManager.crystalsActivated[2] = Boolean.parseBoolean(val); break;
                    case "Q_CAVES": gp.questManager.cavesPuzzleSolved = Boolean.parseBoolean(val); break;
                    case "Q_DUNGEON_SWITCH": gp.questManager.shadowDungeonSwitchPressed = Boolean.parseBoolean(val); break;
                    case "Q_SHADOW_ORB": gp.questManager.shadowOrbRecovered = Boolean.parseBoolean(val); break;
                    case "Q_FORTRESS": gp.questManager.fortressUnlocked = Boolean.parseBoolean(val); break;
                    case "Q_KING": gp.questManager.corruptedKingDefeated = Boolean.parseBoolean(val); break;

                    case "CHESTS":
                        for (String c : val.split(",")) {
                            if (!c.isEmpty()) openedChests.add(c);
                        }
                        break;
                    case "DOORS":
                        for (String d : val.split(",")) {
                            if (!d.isEmpty()) unlockedDoors.add(d);
                        }
                        break;
                    case "INVENTORY":
                        gp.player.inventory.clear();
                        for (String item : val.split(",")) {
                            if (item.isEmpty()) continue;
                            if (item.equalsIgnoreCase("Key")) gp.player.inventory.add(new Obj_Key());
                            else if (item.equalsIgnoreCase("Ruins Key")) gp.player.inventory.add(new OBJ_Key_Ruins());
                            else if (item.equalsIgnoreCase("Dungeon Key")) gp.player.inventory.add(new OBJ_Key_Dungeon());
                            else if (item.equalsIgnoreCase("Health Potion")) gp.player.inventory.add(new OBJ_Potion_Red());
                            else if (item.equalsIgnoreCase("Speed Elixir")) gp.player.inventory.add(new OBJ_Potion_Blue());
                            else if (item.equalsIgnoreCase("Hermes Boots")) gp.player.inventory.add(new OBJ_Boots());
                            else if (item.equalsIgnoreCase("Silver Sword")) gp.player.inventory.add(new OBJ_Sword_Silver());
                            else if (item.equalsIgnoreCase("Knight Shield")) gp.player.inventory.add(new OBJ_Shield());
                            else if (item.equalsIgnoreCase("Sunstone Relic")) gp.player.inventory.add(new OBJ_Relic_Sunstone());
                            else if (item.equalsIgnoreCase("Shadow Orb")) gp.player.inventory.add(new OBJ_Relic_ShadowOrb());
                        }
                        break;
                }
            }

            // Safety check: ensure loaded position is not inside solid tile or water
            int loadCol = gp.player.worldX / gp.tileSize;
            int loadRow = gp.player.worldY / gp.tileSize;
            if (loadCol < 0 || loadCol >= gp.maxWorldCol || loadRow < 0 || loadRow >= gp.maxWorldRow ||
                gp.checker.isSolid(gp.tileM.mapTileNum[gp.currentMap][loadCol][loadRow])) {
                switch (gp.currentMap) {
                    case 0: gp.player.worldX = 20 * gp.tileSize; gp.player.worldY = 23 * gp.tileSize; break;
                    case 1: gp.player.worldX = 4 * gp.tileSize; gp.player.worldY = 25 * gp.tileSize; break;
                    case 2: gp.player.worldX = 14 * gp.tileSize; gp.player.worldY = 37 * gp.tileSize; break;
                    case 3: gp.player.worldX = 4 * gp.tileSize; gp.player.worldY = 12 * gp.tileSize; break;
                    case 4: gp.player.worldX = 24 * gp.tileSize; gp.player.worldY = 45 * gp.tileSize; break;
                    case 5: gp.player.worldX = 13 * gp.tileSize; gp.player.worldY = 30 * gp.tileSize; break;
                    default: gp.player.worldX = 20 * gp.tileSize; gp.player.worldY = 23 * gp.tileSize; break;
                }
            }

            gp.questManager.updateDungeonGate();
            gp.aSetter.setObjectsForMap(gp.currentMap);
            gp.aSetter.setNPCsForMap(gp.currentMap);
            gp.aSetter.setMonstersForMap(gp.currentMap);

            if (gp.currentMap == 0) gp.playMusic(Sound.BGM_VILLAGE);
            else if (gp.currentMap == 1) gp.playMusic(Sound.BGM_FOREST);
            else if (gp.currentMap == 5) gp.playMusic(Sound.BGM_BOSS);
            else gp.playMusic(Sound.BGM_DUNGEON);

            System.out.println("Loaded game successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
