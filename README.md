This README describes the folder layout and purpose of the main packages and files in our Arkanoid game project.

```
src/
 └─ com/
    └─ breakout/
       ├─ config/              # Game configuration and constants
       │   ├─ Defs.java
       │   └─ GameConfig.java
       │
       ├─ core/                # Core abstract/base game objects
       │   ├─ GameObject.java
       │   └─ MovableObject.java
       │
       ├─ entities/            # Game entities (Ball, Paddle, Bricks, Items)
       │   ├─ bricks/
       │   │   ├─ Brick.java
       │   │   ├─ ExplosiveBrick.java
       │   │   ├─ FallingBrick.java
       │   │   ├─ InvisibleBallBrick.java
       │   │   ├─ ItemBrick.java
       │   │   ├─ NormalBrick.java
       │   │   └─ UnbreakableBrick.java
       │   │
       │   ├─ items/
       │   │   ├─ Ball.java
       │   │   ├─ Item.java
       │   │   └─ Paddle.java
       │
       ├─ gui/                 # GUI panels and screens
       │   ├─ GUIPanel.java
       │   ├─ GameModesPanel.java
       │   ├─ GameOverPanel.java
       │   ├─ GameplayPanel.java
       │   ├─ MenuPanel.java
       │   ├─ SettingPanel.java
       │   └─ WinPanel.java
       │
       ├─ interfaces/          # Interfaces used across the game
       │   ├─ Controllable.java
       │   └─ Destructible.java
       │
       ├─ listeners/           # Event listeners
       │   └─ GameKeyListener.java
       │
       ├─ managers/            # Game and system management
       │   ├─ GUIManager.java
       │   ├─ GameManager.java
       │   ├─ Level.java
       │   ├─ LevelData.java
       │   ├─ SaveManager.java
       │   └─ SoundManager.java
       │
       ├─ saves/               # Save data handling
       │   ├─ BallSave.java
       │   ├─ BrickSave.java
       │   ├─ GameSave.java
       │   └─ PaddleSave.java
       │
       ├─ utils/               # Utility classes
       │   ├─ Game.java
       │   └─ Main.java
       │
       ├─ README.md
       └─ class_diagram.jpg

```

## File highlights

- **Main.java** — The entry point for launching the game (contains `public static void main(String[] args)`).
- **Ball.java** — Handles ball physics and collisions.
- **Paddle.java** — Player paddle control and rendering.
- **Brick.java / ExplosiveBrick.java / UnbreakableBrick.java** — Different brick behaviors and hit logic.
- **Item classes (ExpandPaddle, MultiBall, Laser)** — Power-ups dropped by bricks; each class implements its effect.
- **Destructible.java** — Interface for objects that can be destroyed (bricks, certain items).
- **GameManager.java / Level.java** — Game lifecycle, level loading, score, lives, spawning items.
- **Item.java** — Base class for power-ups dropped by bricks.
- **Controllable.java / Destructible.java** — Interfaces for controllable or destructible objects.
- **GameManager.java / Level.java / LevelData.java** — Control game flow, level loading, and progression.
- **SaveManager.java / GameSave.java / BallSave.java** / PaddleSave.java / BrickSave.java — Manage saving and loading of game data.
- **GUIManager.java / GUIPanel.java / MenuPanel.java / GameOverPanel.java / SettingPanel.java / WinPanel.java** — Manage user interface panels and transitions.
- **GameKeyListener.java** — Handles keyboard input for player controls.
- **Defs.java / GameConfig.java** — Contain global constants and configuration settings.
- **SoundManager.java** — Controls sound effects and background music.
- **class_diagram.jpg** — UML diagram showing the overall class structure.
