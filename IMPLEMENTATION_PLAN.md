# Plán implementace: GUI Scaler Mod

**Datum**: 2025-10-27
**Projekt**: Transformace Boilerplate → GUI Scaler
**Platforma**: Multiloader (Fabric + Forge + NeoForge)
**Minecraft**: 1.21.1
**Java**: 21

---

## 🎯 Cíl projektu

Automatické škálování GUI v Minecraftu podle velikosti okna s inteligentní logikou a konfigurovatelností.

### Požadované chování:
- **2880x1800** (HiDPI/Retina) → GUI scale **3**
- **1920x1080** (Full HD) → GUI scale **2**
- **Dynamické**: Reagovat na změny velikosti okna za běhu
- **Konfigurovatelné**: Výchozí "auto" režim + možnost custom pravidel

---

## 📐 Rozhodnutí o implementaci

### 1. **Kdy kontrolovat rozlišení?**
✅ **Screen Init Event** - při každém otevření obrazovky
- Zachytí změny fullscreen ↔ windowed
- Reaguje na resize okna
- Dynamické za běhu

### 2. **Kde implementovat logiku?**
✅ **Common modul** - sdílená logika pro všechny platformy
- DRY princip
- Snadná údržba
- Platform abstrakce přes Services/IPlatformHelper

### 3. **Jak získat/nastavit GUI scale?**
✅ **Minecraft.getInstance().options.guiScale()**
- Standardní MC API
- Window size (ne monitor resolution)
- Client-side operace

### 4. **Config systém**
✅ **Střední úroveň** - Auto režim + Custom pravidla

**Fabric (owo-lib)**:
```java
@Config(name = "guiscaler", wrapperName = "GuiScalerConfig")
public class GuiScalerConfigModel {
    public boolean enableAutoScale = true;
    public ScaleMode mode = ScaleMode.AUTO;
    public Map<Integer, Integer> customRules = new HashMap<>();
}
```

**Forge/NeoForge (ForgeConfigSpec)**:
```java
public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_SCALE;
public static final ForgeConfigSpec.EnumValue<ScaleMode> MODE;
public static final ForgeConfigSpec.ConfigValue<Map<?, ?>> CUSTOM_RULES;
```

---

## 🧮 Škálovací logika

### Auto režim (výchozí):
```java
int width = window.getWidth();
int height = window.getHeight();
int totalPixels = width * height;

// HiDPI/4K territory (2560+, nebo 4.5M+ pixelů)
if (width >= 2560 || totalPixels > 4_500_000) {
    return 3;
}
// Full HD territory (1920+, nebo 2M+ pixelů)
else if (width >= 1920 || totalPixels > 2_000_000) {
    return 2;
}
// HD territory (1280+)
else if (width >= 1280) {
    return 1;
}
// Nechej Minecraft rozhodnout
else {
    return 0; // auto
}
```

### Custom režim:
Pravidla z configu: `width_threshold → scale_value`
```
2560 → 3
1920 → 2
1280 → 1
```

---

## 🏗️ Struktura implementace

### **1. Přejmenování a refactoring** (10 kroků)

#### 1.1 Aktualizace gradle.properties
```properties
mod_id=guiscaler
mod_name=GUI Scaler
description=Automatically scales GUI based on window resolution
```

#### 1.2 Refactoring package
- `eu.netleak.boilerplate` → `eu.netleak.guiscaler`
- Všechny Java soubory
- Import statements

#### 1.3 Aktualizace Constants.java
```java
public static final String MOD_ID = "guiscaler";
public static final String MOD_NAME = "GUI Scaler";
```

#### 1.4 Přejmenování mixin config souborů
- `boilerplate.mixins.json` → `guiscaler.mixins.json`
- `boilerplate.fabric.mixins.json` → `guiscaler.fabric.mixins.json`
- `boilerplate.forge.mixins.json` → `guiscaler.forge.mixins.json`
- `boilerplate.neoforge.mixins.json` → `guiscaler.neoforge.mixins.json`

#### 1.5 Aktualizace fabric.mod.json
```json
{
  "id": "guiscaler",
  "name": "GUI Scaler",
  "description": "Automatically scales GUI based on window resolution"
}
```

#### 1.6 Aktualizace Forge mods.toml
```toml
modId="guiscaler"
displayName="GUI Scaler"
description="Automatically scales GUI based on window resolution"
```

#### 1.7 Aktualizace NeoForge mods.toml
(Stejné jako Forge)

#### 1.8 Přejmenování resource souborů
- `assets/boilerplate/` → `assets/guiscaler/`
- Lang soubory, textury (pokud existují)

#### 1.9 Aktualizace build.gradle souborů
- Odkazy na mod_id v jar manifest
- Mixin configs

#### 1.10 Vyčištění nepotřebných souborů
- Odstranit MixinTitleScreen (nepoužíváme)
- Odstranit ukázkový kód z boilerplate

---

### **2. Common modul** (5 kroků)

#### 2.1 GUIScaleCalculator.java
```java
package eu.netleak.guiscaler.core;

public class GUIScaleCalculator {

    public static int calculateOptimalScale(int width, int height, ScaleMode mode, Map<Integer, Integer> customRules) {
        if (mode == ScaleMode.CUSTOM) {
            return calculateCustomScale(width, customRules);
        }
        return calculateAutoScale(width, height);
    }

    private static int calculateAutoScale(int width, int height) {
        int totalPixels = width * height;

        if (width >= 2560 || totalPixels > 4_500_000) {
            return 3;
        } else if (width >= 1920 || totalPixels > 2_000_000) {
            return 2;
        } else if (width >= 1280) {
            return 1;
        } else {
            return 0; // auto
        }
    }

    private static int calculateCustomScale(int width, Map<Integer, Integer> rules) {
        return rules.entrySet().stream()
            .filter(e -> width >= e.getKey())
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(0);
    }
}
```

#### 2.2 ScaleMode enum
```java
package eu.netleak.guiscaler.core;

public enum ScaleMode {
    AUTO,
    CUSTOM
}
```

#### 2.3 Rozšíření IPlatformHelper
```java
public interface IPlatformHelper {
    String getPlatformName();
    boolean isModLoaded(String modId);

    // Nové metody pro GUI scale
    int getCurrentGuiScale();
    void setGuiScale(int scale);
    int getWindowWidth();
    int getWindowHeight();
}
```

#### 2.4 Aktualizace CommonClass.java
```java
package eu.netleak.guiscaler;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("GUI Scaler initialized for {}", Services.PLATFORM.getPlatformName());
    }

    public static void onScreenInit() {
        // Volá se z platform-specific kódu
        if (!isAutoScaleEnabled()) {
            return;
        }

        int width = Services.PLATFORM.getWindowWidth();
        int height = Services.PLATFORM.getWindowHeight();
        int currentScale = Services.PLATFORM.getCurrentGuiScale();

        ScaleMode mode = getConfigMode();
        Map<Integer, Integer> customRules = getCustomRules();

        int newScale = GUIScaleCalculator.calculateOptimalScale(width, height, mode, customRules);

        if (newScale != currentScale) {
            Services.PLATFORM.setGuiScale(newScale);
            Constants.LOG.info("GUI scale changed: {} → {} ({}x{})", currentScale, newScale, width, height);
        }
    }

    // Platform-specific config gettery (implementovány v každé platformě)
    private static boolean isAutoScaleEnabled() { ... }
    private static ScaleMode getConfigMode() { ... }
    private static Map<Integer, Integer> getCustomRules() { ... }
}
```

#### 2.5 Odstranění mixinů (pokud nejsou potřeba)
- MixinMinecraft můžeme nechat pro debug
- Nebo použít čisté eventy

---

### **3. Fabric implementace** (4 kroky)

#### 3.1 Config (owo-lib)
```java
package eu.netleak.guiscaler.fabric.config;

@Config(name = "guiscaler", wrapperName = "GuiScalerConfig")
public class GuiScalerConfigModel {

    @Comment("Enable automatic GUI scaling")
    public boolean enableAutoScale = true;

    @Comment("Scaling mode: AUTO (intelligent) or CUSTOM (user-defined rules)")
    public ScaleMode mode = ScaleMode.AUTO;

    @Comment("Custom scaling rules (width → scale). Only used when mode is CUSTOM.")
    public Map<Integer, Integer> customRules = Map.of(
        2560, 3,
        1920, 2,
        1280, 1
    );
}
```

#### 3.2 FabricPlatformHelper implementation
```java
package eu.netleak.guiscaler.fabric.platform;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public int getCurrentGuiScale() {
        return Minecraft.getInstance().options.guiScale().get();
    }

    @Override
    public void setGuiScale(int scale) {
        Minecraft.getInstance().options.guiScale().set(scale);
    }

    @Override
    public int getWindowWidth() {
        return Minecraft.getInstance().getWindow().getWidth();
    }

    @Override
    public int getWindowHeight() {
        return Minecraft.getInstance().getWindow().getHeight();
    }
}
```

#### 3.3 Screen Init callback
```java
package eu.netleak.guiscaler.fabric;

public class GuiScaler implements ModInitializer {

    public static final GuiScalerConfig CONFIG = GuiScalerConfig.createAndLoad();

    @Override
    public void onInitialize() {
        CommonClass.init();

        // Registrace screen init callbacku
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            CommonClass.onScreenInit();
        });
    }
}
```

#### 3.4 Config gettery pro CommonClass
```java
// V GuiScaler.java nebo helper třídě
public static boolean isAutoScaleEnabled() {
    return GuiScaler.CONFIG.enableAutoScale();
}

public static ScaleMode getConfigMode() {
    return GuiScaler.CONFIG.mode();
}

public static Map<Integer, Integer> getCustomRules() {
    return GuiScaler.CONFIG.customRules();
}
```

---

### **4. Forge implementace** (4 kroky)

#### 4.1 Config (ForgeConfigSpec)
```java
package eu.netleak.guiscaler.forge.config;

public class GuiScalerConfig {

    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_SCALE;
    public static final ForgeConfigSpec.EnumValue<ScaleMode> MODE;
    // Pro custom rules použijeme ConfigValue<List<String>> a parsujeme

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("GUI Scaler Configuration").push("general");

        ENABLE_AUTO_SCALE = builder
            .comment("Enable automatic GUI scaling")
            .define("enableAutoScale", true);

        MODE = builder
            .comment("Scaling mode: AUTO or CUSTOM")
            .defineEnum("mode", ScaleMode.AUTO);

        builder.pop();
        CLIENT_CONFIG = builder.build();
    }
}
```

#### 4.2 ForgePlatformHelper implementation
```java
package eu.netleak.guiscaler.forge.platform;

public class ForgePlatformHelper implements IPlatformHelper {
    // Stejná implementace jako Fabric
    @Override
    public int getCurrentGuiScale() { ... }
    @Override
    public void setGuiScale(int scale) { ... }
    @Override
    public int getWindowWidth() { ... }
    @Override
    public int getWindowHeight() { ... }
}
```

#### 4.3 Event subscriber
```java
package eu.netleak.guiscaler.forge;

@Mod(Constants.MOD_ID)
public class GuiScaler {

    public GuiScaler() {
        // Registrace config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GuiScalerConfig.CLIENT_CONFIG);

        // Common init
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        // Forge event bus pro game eventy
        MinecraftForge.EVENT_BUS.register(new ScreenEventHandler());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CommonClass.init();
    }
}

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ScreenEventHandler {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        CommonClass.onScreenInit();
    }
}
```

#### 4.4 Config gettery
```java
public static boolean isAutoScaleEnabled() {
    return GuiScalerConfig.ENABLE_AUTO_SCALE.get();
}
// atd.
```

---

### **5. NeoForge implementace** (4 kroky)

Velmi podobné Forge, ale s NeoForge API:

#### 5.1 Config (NeoForge config systém)
```java
// Podobné jako Forge, ale možná lehké API rozdíly
```

#### 5.2 NeoForgePlatformHelper
```java
// Stejná implementace jako ostatní platformy
```

#### 5.3 Event handling
```java
@Mod(Constants.MOD_ID)
public class GuiScaler {
    // Podobné jako Forge, ale NeoForge event bus
}
```

#### 5.4 Config gettery
```java
// Stejné jako Forge
```

---

## 📝 Aktualizace dokumentace (3 kroky)

### 6.1 CLAUDE.md
- Přidat sekci o funkcionalitě GUI scaleru
- Vysvětlit config možnosti
- Přidat příklady použití

### 6.2 README.md
- Popis modu
- Instalace
- Konfigurace
- Screenshots (volitelné)

### 6.3 CHANGELOG.md
```markdown
## [1.0.1] - 2025-10-27
### Changed
- Transformed from boilerplate to GUI Scaler mod
- Added automatic GUI scaling based on window resolution
- Implemented auto and custom scaling modes
```

---

## ✅ Checklist implementace

### Fáze 1: Přejmenování (10 úkolů)
- [ ] 1.1 gradle.properties
- [ ] 1.2 Refactoring package
- [ ] 1.3 Constants.java
- [ ] 1.4 Mixin config soubory
- [ ] 1.5 fabric.mod.json
- [ ] 1.6 Forge mods.toml
- [ ] 1.7 NeoForge mods.toml
- [ ] 1.8 Resource soubory
- [ ] 1.9 build.gradle
- [ ] 1.10 Vyčištění

### Fáze 2: Common (5 úkolů)
- [ ] 2.1 GUIScaleCalculator.java
- [ ] 2.2 ScaleMode enum
- [ ] 2.3 IPlatformHelper rozšíření
- [ ] 2.4 CommonClass.java
- [ ] 2.5 Cleanup mixinů

### Fáze 3: Fabric (4 úkoly)
- [ ] 3.1 Config (owo-lib)
- [ ] 3.2 FabricPlatformHelper
- [ ] 3.3 Screen callback
- [ ] 3.4 Config gettery

### Fáze 4: Forge (4 úkoly)
- [ ] 4.1 Config (ForgeConfigSpec)
- [ ] 4.2 ForgePlatformHelper
- [ ] 4.3 Event subscriber
- [ ] 4.4 Config gettery

### Fáze 5: NeoForge (4 úkoly)
- [ ] 5.1 Config
- [ ] 5.2 NeoForgePlatformHelper
- [ ] 5.3 Event handling
- [ ] 5.4 Config gettery

### Fáze 6: Dokumentace (3 úkoly)
- [ ] 6.1 CLAUDE.md
- [ ] 6.2 README.md
- [ ] 6.3 CHANGELOG.md

### Fáze 7: Testování (3 úkoly)
- [ ] 7.1 Build test všech platform
- [ ] 7.2 Runtime test - změna rozlišení
- [ ] 7.3 Config test - auto vs custom

---

## 🎮 Testovací scénáře

### Test 1: Auto režim
1. Spustit na 2880x1800 → očekáváno scale 3
2. Změnit na 1920x1080 → očekáváno scale 2
3. Změnit na 1280x720 → očekáváno scale 1

### Test 2: Custom režim
1. Nastavit custom pravidla v configu
2. Změnit rozlišení
3. Ověřit, že se použijí custom hodnoty

### Test 3: Za běhu
1. Spustit fullscreen
2. Přepnout na windowed
3. Změnit velikost okna
4. Ověřit, že scale se mění dynamicky

### Test 4: Config změny
1. Vypnout enableAutoScale
2. Ověřit, že mod nic nedělá
3. Zapnout zpět
4. Ověřit, že funguje

---

## 📦 Build a deploy

```bash
# Build všech platform
./gradlew build

# Výstupy:
fabric/build/libs/GUI-Scaler-fabric-1.21.1-1.0.1.jar
forge/build/libs/GUI-Scaler-forge-1.21.1-1.0.1.jar
neoforge/build/libs/GUI-Scaler-neoforge-1.21.1-1.0.1.jar
```

---

## 🔧 Potenciální problémy a řešení

### Problem 1: Mixin conflicts
**Řešení**: Použít pouze eventy, minimalizovat mixiny

### Problem 2: Config loading timing
**Řešení**: Config load v mod init, před prvním screen eventem

### Problem 3: Thread safety
**Řešení**: Všechny operace na client threadu (už tak je)

### Problem 4: Ultra-wide monitory
**Řešení**: Kombinace width + pixel count dobře pokrývá edge cases

---

## 🚀 Priorita implementace

1. **Vysoká**: Fáze 1, 2 (přejmenování + common logika)
2. **Vysoká**: Fáze 3 (Fabric) - testování na jedné platformě
3. **Střední**: Fáze 4, 5 (Forge, NeoForge) - port na ostatní platformy
4. **Nízká**: Fáze 6, 7 (dokumentace, testing)

---

**Celkem úkolů**: 30
**Odhadovaný čas**: 3-4 hodiny
**Komplexita**: Střední
