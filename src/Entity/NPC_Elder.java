package Entity;

import MAIN.GamePannel;

public class NPC_Elder extends entity {

    public NPC_Elder(GamePannel gp) {
        super(gp);
        type = 1; // NPC
        name = "Elder Vaelen";
        speed = 1;
        directions = "down";

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/src/npc/elder.png");
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }

    public void speak() {
        if (!gp.questManager.talkedToElder) {
            gp.questManager.talkedToElder = true;
            gp.ui.currentDialogue = "Elder Vaelen:\n'Greetings, brave traveler! A dark corruption\nhas spread from the Lost King's Fortress.\nTake your blade and venture east into the Whispering\nForest. Seek the ancient Sunstone in the Ruins!'";
        } else if (!gp.questManager.ruinsGuardianDefeated) {
            gp.ui.currentDialogue = "Elder Vaelen:\n'The Whispering Forest lies to the east.\nBeware the goblin scouts and seek the Ruins Key\nto enter the Ancient Ruins!'";
        } else if (!gp.questManager.shadowOrbRecovered) {
            gp.ui.currentDialogue = "Elder Vaelen:\n'You recovered the Sunstone Relic! Astounding!\nNow delve into the Crystal Caves and Shadow Dungeon\nto find the second sacred artifact: the Shadow Orb!'";
        } else {
            gp.ui.currentDialogue = "Elder Vaelen:\n'You hold both ancient relics! The seal on the\nLost King's Fortress is broken. Face King Malakor\nand restore light to our kingdom!'";
        }
        gp.gameState = GamePannel.DIALOGUE_STATE;
    }
}
