package MAIN;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public final GamePannel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed, attackPressed, interactPressed;

    public KeyHandler(GamePannel gp) {
        this.gp = gp;
    }

    public KeyHandler() {
        this.gp = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp == null) {
            handleBasicMovement(code, true);
            return;
        }

        // Title State
        if (gp.gameState == GamePannel.TITLE_STATE) {
            handleTitleInput(code);
        }
        // Play State
        else if (gp.gameState == GamePannel.PLAY_STATE) {
            handlePlayInput(code);
        }
        // Pause State
        else if (gp.gameState == GamePannel.PAUSE_STATE) {
            handlePauseInput(code);
        }
        // Dialogue State
        else if (gp.gameState == GamePannel.DIALOGUE_STATE) {
            handleDialogueInput(code);
        }
        // Inventory State
        else if (gp.gameState == GamePannel.INVENTORY_STATE) {
            handleInventoryInput(code);
        }
        // Shop State
        else if (gp.gameState == GamePannel.SHOP_STATE) {
            handleShopInput(code);
        }
        // Game Over State
        else if (gp.gameState == GamePannel.GAME_OVER_STATE) {
            handleGameOverInput(code);
        }
        // Victory State
        else if (gp.gameState == GamePannel.VICTORY_STATE) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.gameState = GamePannel.TITLE_STATE;
                gp.stopMusic();
            }
        }
    }

    private void handleBasicMovement(int code, boolean pressed) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = pressed;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = pressed;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = pressed;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = pressed;
    }

    private void handleTitleInput(int code) {
        if (gp.ui.titleScreenState == 0) {
            // Main menu
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 4;
                gp.playSE(Sound.SFX_SLASH);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 4) gp.ui.commandNum = 0;
                gp.playSE(Sound.SFX_SLASH);
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.playSE(Sound.SFX_COIN);
                if (gp.ui.commandNum == 0) { // New Game
                    gp.resetGame();
                    gp.gameState = GamePannel.PLAY_STATE;
                    gp.playMusic(Sound.BGM_VILLAGE);
                } else if (gp.ui.commandNum == 1) { // Continue Game
                    if (gp.saveManager.hasSaveData()) {
                        gp.saveManager.loadGame();
                        gp.gameState = GamePannel.PLAY_STATE;
                    } else {
                        gp.ui.currentDialogue = "No saved game found! Please start a New Game.";
                        gp.gameState = GamePannel.DIALOGUE_STATE;
                    }
                } else if (gp.ui.commandNum == 2) { // Guide / Controls
                    gp.ui.titleScreenState = 1;
                } else if (gp.ui.commandNum == 3) { // Settings
                    gp.ui.titleScreenState = 2;
                } else if (gp.ui.commandNum == 4) { // Exit
                    System.exit(0);
                }
            }
        } else {
            // Sub-screens (Guide or Settings)
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_ENTER) {
                gp.ui.titleScreenState = 0;
            }
            if (gp.ui.titleScreenState == 2) {
                // Settings audio toggle
                if (code == KeyEvent.VK_M) {
                    gp.music.setMuted(!gp.music.isMuted());
                    gp.se.setMuted(!gp.se.isMuted());
                    if (gp.music.isMuted()) gp.stopMusic();
                }
            }
        }
    }

    private void handlePlayInput(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;

        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_J) {
            attackPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            interactPressed = true;
        }
        if (code == KeyEvent.VK_I || code == KeyEvent.VK_TAB) {
            gp.gameState = GamePannel.INVENTORY_STATE;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GamePannel.PAUSE_STATE;
        }
    }

    private void handlePauseInput(int code) {
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GamePannel.PLAY_STATE;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 3;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 3) gp.ui.commandNum = 0;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) { // Resume
                gp.gameState = GamePannel.PLAY_STATE;
            } else if (gp.ui.commandNum == 1) { // Save Game
                gp.saveManager.saveGame();
                gp.ui.currentDialogue = "Game progress has been successfully saved!";
                gp.gameState = GamePannel.DIALOGUE_STATE;
            } else if (gp.ui.commandNum == 2) { // Audio Toggle
                boolean muted = !gp.music.isMuted();
                gp.music.setMuted(muted);
                gp.se.setMuted(muted);
                if (muted) gp.stopMusic();
                else gp.playMusic(Sound.BGM_VILLAGE);
            } else if (gp.ui.commandNum == 3) { // Return to Title
                gp.stopMusic();
                gp.gameState = GamePannel.TITLE_STATE;
            }
        }
    }

    private void handleDialogueInput(int code) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE) {
            gp.gameState = GamePannel.PLAY_STATE;
        }
    }

    private void handleInventoryInput(int code) {
        if (code == KeyEvent.VK_I || code == KeyEvent.VK_TAB || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GamePannel.PLAY_STATE;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.slotRow != 0) gp.ui.slotRow--;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.slotRow != 3) gp.ui.slotRow++;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.slotCol != 0) gp.ui.slotCol--;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.slotCol != 4) gp.ui.slotCol++;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    private void handleShopInput(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GamePannel.PLAY_STATE;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 5;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 5) gp.ui.commandNum = 0;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.ui.buyShopItem(gp.ui.commandNum);
        }
    }

    private void handleGameOverInput(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum = (gp.ui.commandNum == 0) ? 1 : 0;
            gp.playSE(Sound.SFX_SLASH);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) { // Respawn at checkpoint
                gp.respawnPlayer();
                gp.gameState = GamePannel.PLAY_STATE;
            } else { // Return to Title
                gp.stopMusic();
                gp.gameState = GamePannel.TITLE_STATE;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_J) attackPressed = false;
        if (code == KeyEvent.VK_E) interactPressed = false;
    }
}
