package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Override
    public void validatePostLoad() {
        // Jankson appends the loaded values after the default-initialized list, so a saved list
        // ends up as [default entries..., user entries...]. Deduplicate by width and keep the
        // last occurrence, so the user's saved value wins over the appended default.
        LinkedHashMap<Integer, String> byWidth = new LinkedHashMap<>();
        for (String rule : customRules) {
            String[] parts = rule.split(":");
            if (parts.length == 2) {
                try {
                    byWidth.put(Integer.parseInt(parts[0].trim()), rule.trim());
                } catch (NumberFormatException _) {
                    // drop malformed entries; the parser would ignore them anyway
                }
            }
        }
        customRules = new ArrayList<>(byWidth.values());
    }
}
