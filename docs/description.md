# GUI Scaler

**Never adjust GUI scale manually again.** GUI Scaler automatically scales your Minecraft GUI based on window size — whether you resize, toggle fullscreen, or switch monitors.

## ✨ Features

*   **Automatic GUI Scaling** — Dynamically adjusts GUI scale when your window size changes
*   **Intelligent Detection** — Uses pixel density detection for optimal readability on any display
*   **Real-time Updates** — Triggers instantly on screen open, fullscreen toggle, or window resize
*   **Two Scaling Modes** — Smart AUTO mode or fully customizable CUSTOM mode
*   **Multiloader** — Works on **Fabric** and **NeoForge** (Forge supported up to 1.21.4)
*   **ModMenu Support** — Fabric users get an in-game config screen via ModMenu

## 🎯 Scaling Modes

### AUTO Mode (Default)

| Resolution | GUI Scale |
| --- | --- |
| 2880x1800+ (HiDPI/Retina) | 3 |
| 1920x1080+ (Full HD) | 2 |
| 1280x720+ (HD) | 1 |
| Otherwise | 0 (auto) |

### CUSTOM Mode

Define your own width → scale rules in the config file.

## ⚙️ Configuration

**Fabric** (`config/guiscaler.json5`):

```
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

**NeoForge** (`config/guiscaler-client.toml`):

```
[general]
  enableAutoScale = true
  mode = "AUTO"
  customRules = ["2560:3", "1920:2", "1280:1"]
```

*   `enableAutoScale` — Toggle automatic scaling on or off
*   `mode` — `AUTO` (intelligent) or `CUSTOM` (user-defined rules)
*   `customRules` — Custom width:scale mappings for CUSTOM mode

## 📦 Supported Platforms

| Loader | Required Dependencies |
| --- | --- |
| Fabric | Fabric API, Cloth Config |
| NeoForge | — |
| Forge | — *(up to 1.21.4 only)* |

## 🔗 Links

*   [GitHub](https://github.com/zendiik/gui-scaler)
*   [Issue Tracker](https://github.com/zendiik/gui-scaler/issues)
