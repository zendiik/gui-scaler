package eu.netleak.guiscaler;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.netleak.guiscaler.config.GuiScalerConfigModel;
import me.shedaniel.autoconfig.AutoConfig;

public class GuiScalerModMenuIntegration implements ModMenuApi {
    @Override
    @SuppressWarnings("removal")
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(GuiScalerConfigModel.class, parent).get();
    }
}
