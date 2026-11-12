# Echoes of the Lost Kingdom ⚔️🏰

A complete, polished, fully playable 2D top-down retro fantasy adventure game built from scratch in pure Java (AWT/Swing).

---

## 📖 Story & Game Concept
In a realm forgotten by time, an ancient kingdom has fallen beneath a shroud of dark purple magic. You play as an intrepid adventurer who awakens in **The Forgotten Village**. Guided by the village elders, you must venture through enchanted woodlands, crumbling ruins, underground caverns, and shadowy dungeons to recover the sacred relics of light, break the ancient seals, and confront **Malakor, The Corrupted King** in his royal fortress.

---

## 🎮 Controls Guide

| Action | Keybinding |
| :--- | :--- |
| **Move Up / Down / Left / Right** | `W`, `A`, `S`, `D` or `Arrow Keys` |
| **Melee Attack / Sword Slash** | `SPACE` or `J` |
| **Interact / Talk / Open / Examine** | `E` (Talk to NPCs, open chests, unlock doors, read signs, pray at shrines) |
| **Open / Close Inventory** | `I` or `TAB` |
| **Inventory Navigation** | `W`, `A`, `S`, `D` or `Arrow Keys` |
| **Use / Equip Selected Item** | `ENTER` |
| **Pause Menu** | `P` or `ESCAPE` |
| **Menu Select / Advance Dialogue** | `ENTER` or `E` |
| **Mute / Unmute Audio** | `M` (In Settings / Pause Menu) |

---

## 🗺️ World Areas (6 Connected Levels)

1. **The Forgotten Village (Map 0)**
   - Peaceful starting sanctuary.
   - Speak to **Elder Vaelen** to receive your mission and blessing.
   - Visit **Merchant Boris** to buy Health Potions, Speed Elixirs, Hermes Boots, Silver Swords, and Knight Shields.
   - Consult **Captain Roderick** at the eastern gate once you have received the Elder's blessing.
   - Pray at the **Goddess Checkpoint Shrine** to fully restore life and save your progress.

2. **Whispering Forest (Map 1)**
   - Winding dirt paths, rivers, bridges, and tree clusters.
   - Encounter **Green Slimes** and agile **Goblin Scouts**.
   - Speak with **Hermit Corvus** in his secluded glade for guidance.
   - Infiltrate the southeast goblin camp to discover a treasure chest holding the **Ruins Key**.

3. **Ancient Ruins (Map 2)**
   - Moss-covered temple walls, cracked stone tiles, and spike floor traps.
   - Guarded by ranged **Skeleton Archers** firing high-velocity arrows.
   - Mini-Boss Arena: Confront the **Ruins Guardian** (35 HP). Slaying him yields the radiant **Sunstone Relic** and unseals the eastern cavern tunnel!

4. **Crystal Caves (Map 3)**
   - Deep subterranean caverns with glowing cyan and violet crystals and underground chasms.
   - Face heavy **Stone Golems** and cave creatures.
   - **3-Altar Progression Puzzle**: Activate the Cyan, Violet, and Emerald crystal switches to trigger the resonance that unseals the staircase to the Shadow Dungeon.

5. **Shadow Dungeon (Map 4)**
   - Dark iron-barred labyrinth with locked doors and spellcasting **Dark Cultists**.
   - Solve the pressure-plate switch puzzle in the western chamber to lower the spike barrier.
   - Locate the dungeon key and unlock the north relic sanctum to reclaim the **Shadow Orb Relic**.

6. **Lost King's Fortress (Map 5)**
   - Royal corridors adorned with velvet red carpet, marble checkered floors, and void flame hazards.
   - Requires both the **Sunstone Relic** and **Shadow Orb** to shatter the King's seal.
   - Save at the final goddess shrine before entering the grand throne room.
   - **Final Boss: Malakor, The Corrupted King (80 HP)**:
     - **Phase 1**: Teleports across the throne room, unleashes directional dark sword waves and charging slashes.
     - **Phase 2 (HP ≤ 40)**: Enters Corrupted Frenzy, gaining heightened speed, multi-orb projectile barrage, and void strikes.
     - Slaying King Malakor triggers the glorious **Victory Screen** with player statistics and kingdom epilogue!

---

## 🚀 How to Compile and Run

### Option 1: Command Line (PowerShell / Command Prompt)

1. Open a terminal in the project directory:
   ```powershell
   cd c:\Users\udaym\IdeaProjects\game
   ```

2. Compile all source files into the `bin` directory:
   ```powershell
   javac -d bin -cp "src;res" (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
   ```

3. Run the game:
   ```powershell
   java -cp "bin;res" MAIN.Main
   ```

4. Run the automated test suite (verifying all 20 acceptance criteria):
   ```powershell
   java -cp "bin;res" MAIN.AutomatedGameTest
   ```

### Option 2: IntelliJ IDEA
- Open the project in IntelliJ IDEA.
- The project structure uses the pre-configured modules (`game.iml` and `res.iml`).
- Open `src/MAIN/Main.java` and click the green **Run** ▶ button.

---

## 🛠️ Architecture & Systems

- **Graphics & Rendering (`GamePannel`, `TileManager`)**:
  - Logical tile size: 48 × 48 pixels.
  - Native display resolution: 960 × 720 pixels (20 × 15 tiles).
  - Target 60 FPS update loop with delta timing.
  - Frustum culling ensures only on-screen tiles are rendered.
  - Y-depth sorting guarantees that player, monsters, NPCs, and projectiles overlap correctly.

- **Combat System (`entity`, `Player`, `Projectile`)**:
  - Directional attack hitbox detection with sword swing animations.
  - Knockback, floating enemy health bars, and invincibility blinking buffers.
  - Enemy projectile physics (Skeleton arrows, Cultist void orbs, King sword waves).

- **Save & Load System (`SaveManager`)**:
  - Persistent game data saved to `save_data.dat`.
  - Preserves current map, player coordinates, life, max life, speed, attack, defense, coins, full inventory, unlocked doors, opened chests, and quest flags.
  - Auto-saves at Goddess Shrines and supports manual saving via the Pause menu.

- **Audio System (`Sound`)**:
  - 14 synthesized retro 16-bit sound effects and atmospheric background themes:
    - SFX: Sword slash, monster hit, player hurt, coin pickup, chest open, door unlock, powerup chime, puzzle chime, game over, victory fanfare.
    - BGM: Village Theme, Forest Theme, Dungeon Theme, Boss Battle Theme.
  - Full volume scaling and mute toggle support (`M` key in Settings/Pause menu).

---

## ✅ Acceptance Test Checklist

| # | Acceptance Test Criteria | Status |
| :---: | :--- | :---: |
| 1 | Application compiles and launches | **PASS** |
| 2 | Main menu works with responsive keyboard navigation | **PASS** |
| 3 | New Game starts a playable game in The Forgotten Village | **PASS** |
| 4 | Player moves in all four directions (WASD / Arrows) | **PASS** |
| 5 | Player collides correctly with blocking objects, trees, and water | **PASS** |
| 6 | Camera follows the player smoothly throughout the world | **PASS** |
| 7 | All 6 areas can be reached through actual game progression | **PASS** |
| 8 | Doors and keys work correctly with inventory consumption | **PASS** |
| 9 | Chests open and grant rewards only once persistently | **PASS** |
| 10 | Inventory opens, displays item grid, and supports consumption/equipment | **PASS** |
| 11 | Enemies move, follow/shoot, receive damage, and drop loot | **PASS** |
| 12 | Player can attack and receive damage with invincibility frames | **PASS** |
| 13 | NPC dialogue works with progressive story states | **PASS** |
| 14 | Main quests progress correctly from Elder to King | **PASS** |
| 15 | All required puzzles (3-crystal altars, pressure plates) are solvable | **PASS** |
| 16 | Mini-boss (Ruins Guardian) and Final Boss (Malakor) are functional | **PASS** |
| 17 | Defeating final boss triggers victory epilogue and statistics screen | **PASS** |
| 18 | Saving and loading preserve full progression to disk | **PASS** |
| 19 | No missing required assets or fatal runtime exceptions | **PASS** |
| 20 | Game can be completed from fresh start to finish without intervention | **PASS** |
