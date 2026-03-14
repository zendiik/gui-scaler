# Changelog

All notable changes to GUI Scaler will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.20.1-1.0.0] - 2026-03-14

### Added
- Backport to Minecraft 1.20.1 (Fabric + Forge)
- Cloth Config integration for Fabric (replaces owo-lib)
- ModMenu config screen support
- Czech translation (cs_cz)

### Changed
- Java 21 to Java 17
- Build system switched to ModDevGradle legacyforge
- NeoForge excluded (not available for 1.20.1)

### Fixed
- GUI scale not applying after change (missing resizeDisplay call)

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
