# Changelog

All notable changes to GUI Scaler will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.1.0] - 2026-03-14

### Changed
- Updated to Minecraft 1.21.4
- Updated Fabric API to 0.110.5+1.21.4
- Updated Forge to 54.1.0
- Updated NeoForge to 21.4.147
- Updated Cloth Config to 17.0.144
- Updated Parchment mappings to 2024.12.29
- Updated pack format to 46
- Added Jade as optional test dependency
- Removed JEI (Fabric/Forge) and EMI (NeoForge) - no 1.21.4 builds available
- Updated CI deploy to target MC 1.21.4

## [1.0.3] - 2026-03-14

### Fixed
- GUI scale not applying visually when window is maximized before menu loads (added `resizeDisplay()` call)
- Fabric dev launcher crash caused by duplicate ASM classes on classpath
- Downgraded Fabric Loom from unstable 1.10-SNAPSHOT to stable 1.9.2

## [1.0.2] - 2025-11-02

### Added
- Fabric: missing dependencies

## [1.0.1] - 2025-10-28

### Fixed
- Client only

## [1.0.0] - 2025-10-27

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
