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

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ScaleMode mode = ScaleMode.AUTO;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.CollapsibleObject
    public CustomRules customRules = new CustomRules();

    public static class CustomRules {
        public int rule1Width = 2560;
        public int rule1Scale = 3;
        public int rule2Width = 1920;
        public int rule2Scale = 2;
        public int rule3Width = 1280;
        public int rule3Scale = 1;
    }
}
