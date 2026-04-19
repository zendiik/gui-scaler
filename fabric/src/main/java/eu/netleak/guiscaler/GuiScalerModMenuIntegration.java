package eu.netleak.guiscaler;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.netleak.guiscaler.config.GuiScalerConfigModel;
import me.shedaniel.autoconfig.AutoConfigClient;

public class GuiScalerModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfigClient.getConfigScreen(GuiScalerConfigModel.class, parent).get();
    }
}
