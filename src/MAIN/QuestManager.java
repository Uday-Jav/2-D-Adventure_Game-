package MAIN;

public class QuestManager {

    private final GamePannel gp;

    // Quest progression flags
    public boolean talkedToElder = false;
    public boolean ruinsKeyFound = false;
    public boolean ruinsUnlocked = false;
    public boolean ruinsGuardianDefeated = false;
    public boolean[] crystalsActivated = new boolean[3]; // Cyan, Violet, Emerald
    public boolean cavesPuzzleSolved = false;
    public boolean shadowDungeonSwitchPressed = false;
    public boolean shadowOrbRecovered = false;
    public boolean fortressUnlocked = false;
    public boolean corruptedKingDefeated = false;

    public QuestManager(GamePannel gp) {
        this.gp = gp;
    }

    public void reset() {
        talkedToElder = false;
        ruinsKeyFound = false;
        ruinsUnlocked = false;
        ruinsGuardianDefeated = false;
        crystalsActivated[0] = false;
        crystalsActivated[1] = false;
        crystalsActivated[2] = false;
        cavesPuzzleSolved = false;
        shadowDungeonSwitchPressed = false;
        shadowOrbRecovered = false;
        fortressUnlocked = false;
        corruptedKingDefeated = false;
        updateDungeonGate();
    }

    public void updateDungeonGate() {
        if (gp.tileM == null) return;
        int gateTile = shadowDungeonSwitchPressed
                ? MapGenerator.T_DUNGEON_FLOOR
                : MapGenerator.T_SPIKES;
        for (int col = 23; col <= 26; col++) {
            gp.tileM.mapTileNum[4][col][21] = gateTile;
        }
    }

    public String getCurrentObjective() {
        if (!talkedToElder) {
            return "Quest: Speak to Elder Vaelen in the Village";
        }
        if (!ruinsKeyFound) {
            return "Quest: Find the Ruins Key in the Whispering Forest";
        }
        if (!ruinsGuardianDefeated) {
            return "Quest: Defeat the Guardian in Ancient Ruins for the Sunstone";
        }
        if (!cavesPuzzleSolved) {
            return "Quest: Activate the 3 Ancient Crystals in Crystal Caves";
        }
        if (!shadowOrbRecovered) {
            return "Quest: Recover the Shadow Orb from the Shadow Dungeon";
        }
        if (!corruptedKingDefeated) {
            return "Final Quest: Defeat King Malakor in the Royal Fortress!";
        }
        return "Victory: The Kingdom is Saved!";
    }

    public void triggerSwitch(String switchId, int sequenceIndex) {
        if (switchId.startsWith("crystal_")) {
            if (sequenceIndex >= 0 && sequenceIndex < crystalsActivated.length) {
                crystalsActivated[sequenceIndex] = true;
                gp.playSE(Sound.SFX_PUZZLE);

                // Check if all 3 crystals are lit
                if (crystalsActivated[0] && crystalsActivated[1] && crystalsActivated[2]) {
                    cavesPuzzleSolved = true;
                    gp.playSE(Sound.SFX_POWERUP);
                    gp.ui.currentDialogue = "All 3 Ancient Crystal Altars are resonant!\nThe deep staircase to the Shadow Dungeon is unlocked!";
                    gp.gameState = GamePannel.DIALOGUE_STATE;
                } else {
                    gp.ui.currentDialogue = "The Ancient Crystal glows brightly!\n(" + getActiveCrystalCount() + "/3 Altars Activated)";
                    gp.gameState = GamePannel.DIALOGUE_STATE;
                }
            }
        } else if ("dungeon_switch".equals(switchId)) {
            shadowDungeonSwitchPressed = true;
            updateDungeonGate();
            gp.playSE(Sound.SFX_PUZZLE);
            gp.ui.currentDialogue = "You stepped on the heavy pressure plate!\nA loud clanking sound echoes... The iron spike gate has lowered!";
            gp.gameState = GamePannel.DIALOGUE_STATE;
        }
    }

    public int getActiveCrystalCount() {
        int count = 0;
        for (boolean b : crystalsActivated) if (b) count++;
        return count;
    }
}
