package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "guiscaler")
public class GuiScalerConfigModel implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean enableAutoScale = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Tooltip
    public ScaleMode mode = ScaleMode.AUTO;

    @ConfigEntry.Gui.Tooltip
    public List<String> customRules = new ArrayList<>(List.of("2560:3", "1920:2", "1280:1"));
}
