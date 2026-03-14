package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.HashMap;
import java.util.Map;

@Config(name = "guiscaler")
public class GuiScalerConfigModel implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean enableAutoScale = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Tooltip
    public ScaleMode mode = ScaleMode.AUTO;

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public Map<Integer, Integer> customRules = new HashMap<>() {{
        put(2560, 3);
        put(1920, 2);
        put(1280, 1);
    }};
}
