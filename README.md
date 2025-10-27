# GUI Scaler

Automatically scales Minecraft GUI based on window resolution.

## Features

- **Automatic GUI scaling** - dynamically adjusts GUI scale when window size changes
- **Intelligent scaling** - uses pixel density detection for optimal readability
- **Multiloader support** - works on Fabric, Forge, and NeoForge
- **Configurable** - choose between AUTO (intelligent) or CUSTOM (user-defined) modes
- **Real-time** - triggers on every screen open (fullscreen toggle, window resize)

## Scaling Logic

### AUTO Mode (Default)
```
2880x1800+ (HiDPI/Retina) → GUI scale 3
1920x1080+ (Full HD)      → GUI scale 2
1280x720+  (HD)           → GUI scale 1
Otherwise                 → GUI scale 0 (auto)
```

Uses combined width and pixel count for accurate detection.

### CUSTOM Mode
Define your own scaling rules in the config file.

## Installation

1. Download the mod for your platform (Fabric, Forge, or NeoForge)
2. Place the JAR file in your `mods/` folder
3. Launch Minecraft

## Configuration

Config file location: `config/guiscaler-client.toml` (or `.json5` for Fabric)

### Fabric (owo-lib)
```json5
{
	"enableAutoScale": true,
	"mode": "AUTO",
	"customRules": {
		"2560": 3,
		"1920": 2,
		"1280": 1
	}
}
```

### Forge/NeoForge
```toml
#GUI Scaler Configuration
[general]
	#Enable automatic GUI scaling
	enableAutoScale = true
	#Scaling mode: AUTO (intelligent) or CUSTOM (user-defined rules)
	#Allowed Values: AUTO, CUSTOM
	mode = "AUTO"
	#Custom scaling rules in format 'width:scale' (e.g., '2560:3' means scale 3 for width >= 2560)
	customRules = ["2560:3", "1920:2", "1280:1"]
```

## Building

**Important**: Build must be run in Windows cmd, not WSL.

```cmd
cd C:\_dev\minecraft\mods\GUI Scaler
gradlew.bat build
```

Output JARs will be in:
- `fabric/build/libs/`
- `forge/build/libs/`
- `neoforge/build/libs/`

## Requirements

- Minecraft 1.21.1
- Java 21
- Fabric Loader 0.16.10+ (Fabric)
- Forge 52.0.28+ (Forge)
- NeoForge 21.1.80+ (NeoForge)

## License

GNU LGPL 3.0

## Links

- Repository: https://github.com/zendiik/gui-scaler
- Issues: https://github.com/zendiik/gui-scaler/issues