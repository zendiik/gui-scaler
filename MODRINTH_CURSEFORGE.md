# GUI Scaler - Mod Description for CurseForge & Modrinth

## Short Description (160 characters max)
Automatically adjusts Minecraft GUI scale based on window resolution. Smart scaling for optimal readability on any screen size.

## Full Description

### Overview
GUI Scaler is a lightweight utility mod that automatically adjusts your Minecraft GUI scale when you resize your game window or toggle fullscreen. No more squinting at tiny menus on large displays or oversized UI on small windows!

### Features
- **Automatic GUI Scaling** - Dynamically adjusts GUI scale when window size changes
- **Intelligent Detection** - Uses pixel density detection for optimal readability
- **Real-time Updates** - Triggers instantly on screen open, fullscreen toggle, or window resize
- **Two Scaling Modes**:
  - **AUTO Mode** (Default) - Smart scaling based on resolution:
    - 2880x1800+ (HiDPI/Retina) → GUI scale 3
    - 1920x1080+ (Full HD) → GUI scale 2
    - 1280x720+ (HD) → GUI scale 1
    - Otherwise → GUI scale 0 (auto)
  - **CUSTOM Mode** - Define your own scaling rules in the config
- **Multiloader Support** - Works seamlessly on Fabric, Forge, and NeoForge
- **Configurable** - Easy-to-use config files for each platform

### Why Use This Mod?
Perfect for players who:
- Switch between windowed and fullscreen modes frequently
- Play on high-resolution displays (1440p, 4K, or Retina)
- Use multiple monitors with different resolutions
- Want consistent UI readability without manual adjustments
- Stream or record at various resolutions

### How It Works
The mod monitors screen initialization events and automatically calculates the optimal GUI scale based on your current window dimensions. The scaling algorithm considers both width and total pixel count to ensure the best visual experience across all display types.

### Configuration

#### Fabric (owo-lib)
Config file: `config/guiscaler.json5`

```toml
enableAutoScale = true
mode = "AUTO"  # or "CUSTOM"

[customRules]
2560 = 3
1920 = 2
1280 = 1
```

#### Forge/NeoForge
Config file: `config/guiscaler-client.toml`

```toml
[general]
enableAutoScale = true
mode = "AUTO"  # or "CUSTOM"
```

### Installation
1. Download the correct version for your mod loader (Fabric, Forge, or NeoForge)
2. Place the JAR file in your `mods/` folder
3. Launch Minecraft - the mod works automatically!

### Requirements
- **Minecraft:** 1.21.1
- **Java:** 21
- **Fabric Loader:** 0.16.10+ (for Fabric version)
- **Forge:** 52.0.28+ (for Forge version)
- **NeoForge:** 21.1.80+ (for NeoForge version)

### Compatibility
GUI Scaler is designed to be lightweight and compatible with most mods. It uses:
- Screen initialization events (no mixins into critical game code)
- Platform-specific config systems
- Standard Minecraft options for GUI scale

No known incompatibilities with other mods.

### Support & Issues
Found a bug or have a suggestion? Please report it on our [issue tracker](https://gitea.zendovo.eu/minecraft/gui-scaler-mod/issues).

### License
This mod is licensed under GNU LGPL 3.0.

---

## Changelog (for 1.0.0)

### Added
- Automatic GUI scaling based on window resolution
- Intelligent AUTO mode with pixel density detection
  - 2880x1800+ (HiDPI/Retina) → scale 3
  - 1920x1080+ (Full HD) → scale 2
  - 1280x720+ (HD) → scale 1
- CUSTOM mode for user-defined scaling rules
- Multiloader support (Fabric, Forge, NeoForge)
- Configuration system for each platform
  - Fabric: owo-lib with full custom rules support
  - Forge: ForgeConfigSpec with default rules
  - NeoForge: ModConfigSpec with default rules
- Dynamic scaling on screen open events
- Platform abstraction via IPlatformHelper service loader

### Technical Details
- **Common module**: GUIScaleCalculator, ScaleMode enum, ConfigProvider interface
- **Fabric**: ScreenEvents.AFTER_INIT callback
- **Forge**: ScreenEvent.Init.Post event with MinecraftForge.EVENT_BUS
- **NeoForge**: ScreenEvent.Init.Post event with NeoForge.EVENT_BUS
- All platforms implement IPlatformHelper for GUI scale operations

---

## Tags/Categories (for upload platforms)

### CurseForge Categories
- Utility
- Client-side
- Quality of Life

### Modrinth Categories
- utility
- client-side
- quality-of-life

### Keywords/Tags
- GUI
- scaling
- resolution
- HiDPI
- Retina
- automatic
- UI
- interface
- display
- window
- fullscreen
- multiloader

---

## Gallery/Screenshot Suggestions

1. **Before/After Comparison**: Show the same menu at different window sizes with appropriate GUI scaling
2. **Config Screenshot**: Show the simple config file with customization options
3. **Multiple Resolutions**: Demonstrate scaling behavior at 1080p, 1440p, and 4K
4. **Mod Menu Integration**: Show the mod listed in Fabric's Mod Menu or Forge's mod list
