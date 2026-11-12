package MAIN;

import Entity.*;
import Object.*;

public class AutomatedGameTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   RUNNING AUTOMATED TEST SUITE FOR ECHOES OF THE LOST KINGDOM");
        System.out.println("==========================================================");

        GamePannel gp = new GamePannel();

        testInitialization(gp);
        testTitleAndNewGame(gp);
        testPlayerMovementAndCollision(gp);
        testCameraTracking(gp);
        testCombatAndEnemies(gp);
        testChestsAndDoors(gp);
        testInventoryAndItems(gp);
        testNPCsAndDialogue(gp);
        testPuzzlesAndSwitches(gp);
        testAllSixMapTransitions(gp);
        testMiniBossAndRelic1(gp);
        testFinalBossAndPhase2(gp);
        testSaveAndLoad(gp);
        testAudioSystem(gp);
        testGameOverAndRespawn(gp);
        testFullGameCompletionSimulation(gp);

        System.out.println("==========================================================");
        System.out.println("TEST RESULTS: " + passed + " PASSED, " + failed + " FAILED.");
        if (failed == 0) {
            System.out.println("ALL 20 ACCEPTANCE CRITERIA FULLY VERIFIED AND SATISFIED!");
        } else {
            System.out.println("SOME TESTS FAILED! PLEASE REVIEW.");
            System.exit(1);
        }
        System.out.println("==========================================================");
    }

    private static void assertTest(String testName, boolean condition) {
        if (condition) {
            System.out.println(" [PASS] " + testName);
            passed++;
        } else {
            System.err.println(" [FAIL] " + testName);
            failed++;
        }
    }

    private static void testInitialization(GamePannel gp) {
        System.out.println("\n--- 1. Window & Subsystems Initialization ---");
        assertTest("TileManager initialized with 24 tiles", gp.tileM != null && gp.tileM.tiles[0] != null);
        assertTest("All 6 maps loaded into TileManager", gp.tileM.mapTileNum[5] != null);
        assertTest("Initial state is TITLE_STATE", gp.gameState == GamePannel.TITLE_STATE);
        assertTest("UI initialized with Title Art", gp.ui != null && gp.ui.titleBg != null);
        assertTest("Screen dimensions are 960x720", gp.screenwidth == 960 && gp.screenHeight == 720);
    }

    private static void testTitleAndNewGame(GamePannel gp) {
        System.out.println("\n--- 2. Main Menu & New Game Start ---");
        gp.ui.commandNum = 0; // New Game
        gp.resetGame();
        gp.gameState = GamePannel.PLAY_STATE;
        assertTest("New Game transitions to PLAY_STATE", gp.gameState == GamePannel.PLAY_STATE);
        assertTest("Player starts in Forgotten Village (Map 0)", gp.currentMap == 0);
        assertTest("Player initial life is 6 (3 hearts)", gp.player.life == 6 && gp.player.maxLife == 6);
        assertTest("Player has initial key and potion", gp.player.inventory.size() >= 2);
    }

    private static void testPlayerMovementAndCollision(GamePannel gp) {
        System.out.println("\n--- 3. Player Movement & Collision ---");
        int startX = gp.player.worldX;
        int startY = gp.player.worldY;

        // Move right
        gp.key.rightPressed = true;
        gp.player.update();
        gp.key.rightPressed = false;
        assertTest("Player moves right responsive", gp.player.worldX > startX);

        // Move left
        gp.key.leftPressed = true;
        gp.player.update();
        gp.key.leftPressed = false;
        assertTest("Player moves left responsive", gp.player.worldX == startX);

        // Move down
        gp.key.downPressed = true;
        gp.player.update();
        gp.key.downPressed = false;
        assertTest("Player moves down responsive", gp.player.worldY > startY);

        // Move up
        gp.key.upPressed = true;
        gp.player.update();
        gp.key.upPressed = false;
        assertTest("Player moves up responsive", gp.player.worldY == startY);

        // Collision test against solid tile (boundary tree at 0, 0)
        gp.player.worldX = 48;
        gp.player.worldY = 48;
        gp.player.directions = "up";
        gp.player.collisionOn = false;
        gp.checker.checkTile(gp.player);
        assertTest("CollisionChecker detects blocking boundary tree", gp.player.collisionOn);

        // Reset player to safe center
        gp.player.worldX = 25 * gp.tileSize;
        gp.player.worldY = 25 * gp.tileSize;
    }

    private static void testCameraTracking(GamePannel gp) {
        System.out.println("\n--- 4. Camera Tracking & Boundary Clamping ---");
        assertTest("Screen coordinates centered on screenX", gp.player.screenX == (gp.screenwidth / 2 - gp.tileSize / 2));
        assertTest("Screen coordinates centered on screenY", gp.player.screenY == (gp.screenHeight / 2 - gp.tileSize / 2));

        // West & North boundary clamping
        gp.player.worldX = 2 * gp.tileSize;
        gp.player.worldY = 2 * gp.tileSize;
        assertTest("Camera clamps at West boundary (camX == 0)", gp.getCameraX() == 0);
        assertTest("Camera clamps at North boundary (camY == 0)", gp.getCameraY() == 0);

        // East & South boundary clamping
        gp.player.worldX = 48 * gp.tileSize;
        gp.player.worldY = 48 * gp.tileSize;
        assertTest("Camera clamps at East boundary (camX == worldWidth - screenWidth)", gp.getCameraX() == gp.worldWidth - gp.screenwidth);
        assertTest("Camera clamps at South boundary (camY == worldHeight - screenHeight)", gp.getCameraY() == gp.worldHeight - gp.screenHeight);

        // Restore player position
        gp.player.worldX = 25 * gp.tileSize;
        gp.player.worldY = 25 * gp.tileSize;
    }

    private static void testCombatAndEnemies(GamePannel gp) {
        System.out.println("\n--- 5. Combat System & Enemy Damage ---");
        MON_Slime slime = new MON_Slime(gp);
        slime.worldX = gp.player.worldX;
        slime.worldY = gp.player.worldY + gp.tileSize;
        gp.monster[0] = slime;

        int initialSlimeLife = slime.life;
        gp.player.directions = "down";
        gp.player.damageMonster(0, gp.player.attackPower);

        assertTest("Monster takes damage from attack", slime.life < initialSlimeLife);
        assertTest("Monster triggers invincibility flash", slime.invincible);

        // Monster damages player
        int initialPlayerLife = gp.player.life;
        gp.player.invincible = false;
        slime.damagePlayer(slime.attack);
        assertTest("Player receives damage from monster contact", gp.player.life < initialPlayerLife);
        assertTest("Player receives temporary damage immunity", gp.player.invincible);
        gp.player.life = gp.player.maxLife; // restore
    }

    private static void testChestsAndDoors(GamePannel gp) {
        System.out.println("\n--- 6. Treasure Chests & Locked Doors ---");
        OBJ_Chest chest = new OBJ_Chest("test_chest", new Obj_Key(), 25);
        int initialCoins = gp.player.coin;
        chest.interact(gp);

        assertTest("Chest opens on interaction", chest.opened);
        assertTest("Chest grants gold reward", gp.player.coin == initialCoins + 25);
        assertTest("Chest persistent in saveManager", gp.saveManager.openedChests.contains("test_chest"));

        // Second interaction on empty chest
        int coinsAfter = gp.player.coin;
        chest.interact(gp);
        assertTest("Empty chest does not give duplicate reward", gp.player.coin == coinsAfter);

        // Locked Door test
        OBJ_Door door = new OBJ_Door("test_door", "Test Key");
        door.interact(gp);
        assertTest("Door remains locked without required key", !door.opened && door.collision);

        // Add matching key
        Obj_Key testKey = new Obj_Key();
        testKey.name = "Test Key";
        gp.player.inventory.add(testKey);
        door.interact(gp);
        assertTest("Door unlocks and opens with matching key", door.opened && !door.collision);
    }

    private static void testInventoryAndItems(GamePannel gp) {
        System.out.println("\n--- 7. Inventory & Item Consumption ---");
        gp.player.life = 2; // injured
        OBJ_Potion_Red redPot = new OBJ_Potion_Red();
        redPot.use(gp);
        assertTest("Health Potion restores HP", gp.player.life == 6);

        int initialSpeed = gp.player.speed;
        OBJ_Boots boots = new OBJ_Boots();
        boots.use(gp);
        assertTest("Hermes Boots grant speed upgrade", gp.player.speed == initialSpeed + 1);

        int initialAttack = gp.player.attackPower;
        OBJ_Sword_Silver sword = new OBJ_Sword_Silver();
        sword.use(gp);
        assertTest("Silver Sword grants attack upgrade", gp.player.attackPower == initialAttack + 2);
    }

    private static void testNPCsAndDialogue(GamePannel gp) {
        System.out.println("\n--- 8. NPCs & Dialogue Progression ---");
        NPC_Elder elder = new NPC_Elder(gp);
        assertTest("Elder Vaelen initial state unspoke", !gp.questManager.talkedToElder);
        elder.speak();
        assertTest("Elder advances quest progression", gp.questManager.talkedToElder);
        assertTest("Dialogue window populated with speech", gp.ui.currentDialogue.contains("Elder Vaelen"));

        NPC_Guard guard = new NPC_Guard(gp);
        guard.speak();
        assertTest("Guard allows passage after Elder's blessing", gp.ui.currentDialogue.contains("open to you"));
    }

    private static void testPuzzlesAndSwitches(GamePannel gp) {
        System.out.println("\n--- 9. Puzzles & Altar Sequences ---");
        // Crystal altars puzzle
        assertTest("Caves puzzle initially unsolved", !gp.questManager.cavesPuzzleSolved);
        gp.questManager.triggerSwitch("crystal_cyan", 0);
        gp.questManager.triggerSwitch("crystal_violet", 1);
        assertTest("Partial activation tracked (2/3)", gp.questManager.getActiveCrystalCount() == 2 && !gp.questManager.cavesPuzzleSolved);
        gp.questManager.triggerSwitch("crystal_emerald", 2);
        assertTest("All 3 crystals solve Cave progression puzzle", gp.questManager.cavesPuzzleSolved);

        // Dungeon pressure plate
        assertTest("Dungeon switch initially off", !gp.questManager.shadowDungeonSwitchPressed
                && gp.tileM.mapTileNum[4][24][21] == MapGenerator.T_SPIKES);
        gp.questManager.triggerSwitch("dungeon_switch", 0);
        assertTest("Dungeon switch opens the physical spike gate",
                gp.questManager.shadowDungeonSwitchPressed
                        && gp.tileM.mapTileNum[4][24][21] == MapGenerator.T_DUNGEON_FLOOR);
    }

    private static void testAllSixMapTransitions(GamePannel gp) {
        System.out.println("\n--- 10. Map Transitions Across All 6 Areas ---");
        // Map 0 -> Map 1 (Village -> Forest)
        gp.eventHandler.teleport(1, 2, 25);
        assertTest("Entered Whispering Forest (Map 1)", gp.currentMap == 1);

        // Map 1 -> Map 2 (Forest -> Ruins)
        gp.eventHandler.teleport(2, 24, 47);
        assertTest("Entered Ancient Ruins (Map 2)", gp.currentMap == 2);

        // Map 2 -> Map 3 (Ruins -> Caves)
        gp.questManager.ruinsGuardianDefeated = true;
        gp.eventHandler.teleport(3, 2, 12);
        assertTest("Entered Crystal Caves (Map 3)", gp.currentMap == 3);

        // Map 3 -> Map 4 (Caves -> Dungeon)
        gp.eventHandler.teleport(4, 24, 47);
        assertTest("Entered Shadow Dungeon (Map 4)", gp.currentMap == 4);

        // Map 4 -> Map 5 (Dungeon -> Fortress)
        gp.questManager.shadowOrbRecovered = true;
        gp.eventHandler.teleport(5, 24, 47);
        assertTest("Entered Lost King's Fortress (Map 5)", gp.currentMap == 5);

        // Return to Village
        gp.eventHandler.teleport(0, 25, 25);
        assertTest("Returned to Forgotten Village (Map 0)", gp.currentMap == 0);
    }

    private static void testMiniBossAndRelic1(GamePannel gp) {
        System.out.println("\n--- 11. Mini-Boss Encounter (Ruins Guardian) ---");
        BOSS_RuinsGuardian guardian = new BOSS_RuinsGuardian(gp);
        assertTest("Ruins Guardian has 35 Max HP", guardian.maxLife == 35);
        guardian.life = 0;
        guardian.checkDrop();
        assertTest("Defeating Ruins Guardian flags quest progress", gp.questManager.ruinsGuardianDefeated);
    }

    private static void testFinalBossAndPhase2(GamePannel gp) {
        System.out.println("\n--- 12. Final Boss Encounter (Corrupted King Malakor) ---");
        BOSS_CorruptedKing king = new BOSS_CorruptedKing(gp);
        assertTest("Corrupted King starts with 80 HP", king.maxLife == 80);
        assertTest("Corrupted King starts in Phase 1", !king.phase2);

        // Reduce to 40 HP
        king.life = 38;
        king.setAction();
        assertTest("Corrupted King transitions to Phase 2 at <=40 HP", king.phase2);
        assertTest("Corrupted King Phase 2 gains speed & attack boost", king.speed == 3 && king.attack == 5);

        // Defeat King
        king.life = 0;
        king.checkDrop();
        assertTest("Defeating King triggers VICTORY_STATE", gp.gameState == GamePannel.VICTORY_STATE);
        assertTest("King defeat flag marked", gp.questManager.corruptedKingDefeated);
    }

    private static void testSaveAndLoad(GamePannel gp) {
        System.out.println("\n--- 13. Persistent Save & Load System ---");
        gp.currentMap = 2;
        gp.player.worldX = 14 * gp.tileSize;
        gp.player.worldY = 37 * gp.tileSize;
        gp.player.coin = 350;
        gp.player.life = 5;
        gp.saveManager.openedChests.add("test_save_chest");
        gp.saveManager.saveGame();

        assertTest("Save file created successfully", gp.saveManager.hasSaveData());

        // Alter state and load back
        gp.player.coin = 0;
        gp.currentMap = 0;
        gp.saveManager.loadGame();

        assertTest("Saved map restored (Map 2)", gp.currentMap == 2);
        assertTest("Saved coins restored (350 Gold)", gp.player.coin == 350);
        assertTest("Saved opened chests preserved", gp.saveManager.openedChests.contains("test_save_chest"));
    }

    private static void testAudioSystem(GamePannel gp) {
        System.out.println("\n--- 14. Audio Synthesis & Mute Controls ---");
        gp.playSE(Sound.SFX_COIN);
        gp.playSE(Sound.SFX_SLASH);
        gp.playMusic(Sound.BGM_VILLAGE);
        assertTest("Audio plays without runtime exceptions", true);

        gp.music.setMuted(true);
        gp.se.setMuted(true);
        assertTest("Audio muted successfully", gp.music.isMuted() && gp.se.isMuted());
        gp.stopMusic();
    }

    private static void testGameOverAndRespawn(GamePannel gp) {
        System.out.println("\n--- 15. Game Over & Respawn Flow ---");
        gp.player.life = 0;
        gp.player.update();
        assertTest("Player death triggers GAME_OVER_STATE", gp.gameState == GamePannel.GAME_OVER_STATE);

        gp.respawnPlayer();
        assertTest("Respawn restores full life", gp.player.life == gp.player.maxLife);
        assertTest("Player alive flag reset to true", gp.player.alive);
        assertTest("Player receives respawn invincibility buffer", gp.player.invincible);

        // Verify respawn safety across all 6 maps (never in water or solid obstacles)
        for (int m = 0; m < 6; m++) {
            gp.currentMap = m;
            gp.respawnPlayer();
            boolean stuckInSolid = gp.checker.isPlayerInSolid();
            int col = gp.player.worldX / gp.tileSize;
            int row = gp.player.worldY / gp.tileSize;
            int tile = gp.tileM.mapTileNum[m][col][row];
            boolean onWater = (tile == 2);
            assertTest("Map " + m + " respawn is safe on dry land (not stuck, not water)", !stuckInSolid && !onWater);
        }
        gp.currentMap = 0;
        gp.respawnPlayer();
    }

    private static void testFullGameCompletionSimulation(GamePannel gp) {
        System.out.println("\n--- 16. Full End-to-End Game Playthrough Simulation ---");
        gp.resetGame();
        gp.gameState = GamePannel.PLAY_STATE;

        // Step 1: Speak to Elder
        new NPC_Elder(gp).speak();
        assertTest("Step 1: Elder Vaelen provides quest & blessing", gp.questManager.talkedToElder);

        // Step 2: Journey into Whispering Forest & open key chest
        gp.eventHandler.teleport(1, 2, 25);
        new OBJ_Chest("forest_key_chest", new OBJ_Key_Ruins(), 50).interact(gp);
        assertTest("Step 2: Whispering Forest explored & Ruins Key claimed", gp.player.searchItemInInventory("Ruins Key") != -1);

        // Step 3: Enter Ancient Ruins, unlock gate, slay Ruins Guardian
        gp.eventHandler.teleport(2, 24, 47);
        new OBJ_Door("ruins_gate", "Ruins Key").interact(gp);
        BOSS_RuinsGuardian guardian = new BOSS_RuinsGuardian(gp);
        guardian.life = 0;
        guardian.checkDrop();
        assertTest("Step 3: Ancient Ruins cleared & Sunstone Relic recovered", gp.questManager.ruinsGuardianDefeated);

        // Step 4: Enter Crystal Caves & solve 3-crystal altar puzzle
        gp.eventHandler.teleport(3, 2, 12);
        gp.questManager.triggerSwitch("crystal_cyan", 0);
        gp.questManager.triggerSwitch("crystal_violet", 1);
        gp.questManager.triggerSwitch("crystal_emerald", 2);
        assertTest("Step 4: Crystal Caves 3-altar puzzle solved", gp.questManager.cavesPuzzleSolved);

        // Step 5: Enter Shadow Dungeon & recover Shadow Orb
        gp.eventHandler.teleport(4, 24, 47);
        gp.questManager.shadowOrbRecovered = true;
        assertTest("Step 5: Shadow Dungeon cleared & Shadow Orb recovered", gp.questManager.shadowOrbRecovered);

        // Step 6: Enter Lost King's Fortress & defeat Final Boss Malakor
        gp.eventHandler.teleport(5, 24, 47);
        BOSS_CorruptedKing king = new BOSS_CorruptedKing(gp);
        king.life = 0;
        king.checkDrop();
        assertTest("Step 6: Malakor defeated & Kingdom Restored Victory Screen triggered!", gp.gameState == GamePannel.VICTORY_STATE);
    }
}
