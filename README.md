# GUI Scaler

[![CurseForge Downloads](https://cf.way2muchnoise.eu/full_1373686_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/gui-scaler)
[![CurseForge Versions](https://cf.way2muchnoise.eu/versions/1373686.svg)](https://www.curseforge.com/minecraft/mc-mods/gui-scaler)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/auto-gui-scaler?logo=modrinth&label=downloads)](https://modrinth.com/mod/auto-gui-scaler)
[![Modrinth Version](https://img.shields.io/modrinth/v/auto-gui-scaler?logo=modrinth&label=version)](https://modrinth.com/mod/auto-gui-scaler)

Automatically scales Minecraft GUI based on window resolution.

## Features

- **Automatic GUI scaling** - dynamically adjusts GUI scale when window size changes
- **Intelligent scaling** - uses pixel density detection for optimal readability
- **Multiloader support** - works on Fabric and Forge
- **Configurable** - choose between AUTO (intelligent) or CUSTOM (user-defined) modes
- **Real-time** - re-applies the scale whenever the window size changes (resize, fullscreen toggle)

## Scaling Logic

### AUTO Mode (Default)
```
2560+ wide, or over 4.5M pixels → GUI scale 3
1280+ wide, or over 2M pixels   → GUI scale 2
Otherwise                       → GUI scale 0 (let Minecraft decide)
```

Uses combined width and pixel count for accurate detection. Widths are measured in
framebuffer pixels, so on a HiDPI/Retina display a window that looks like 1280x720
reports 2560x1440 and gets scale 3.

### CUSTOM Mode
Define your own scaling rules in the config file. Rules match as "width >= threshold";
the highest matching rule wins. If no rule matches, Minecraft decides (scale 0).

## Installation

1. Download the mod for your platform (Fabric or Forge)
2. Place the JAR file in your `mods/` folder
3. Launch Minecraft

## Configuration

### Fabric (Cloth Config)
Config file: `config/guiscaler.json`

```json
{
	"enableAutoScale": true,
	"mode": "AUTO",
	"customRules": {
		"rule1Width": 2560,
		"rule1Scale": 3,
		"rule2Width": 1920,
		"rule2Scale": 2,
		"rule3Width": 1280,
		"rule3Scale": 1
	}
}
```

Config screen available via **ModMenu** → GUI Scaler → Config button.

### Forge
Config file: `config/guiscaler-client.toml`

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

## Requirements

- Minecraft 1.20.1
- Java 17
- Fabric Loader 0.16.9+ (Fabric)
- Forge 47.2.30+ (Forge)

## License

GNU LGPL 3.0

## Links

- Repository: https://github.com/zendiik/gui-scaler
- Issues: https://github.com/zendiik/gui-scaler/issues
- ☕ Support / Donate: https://ko-fi.com/netleak
