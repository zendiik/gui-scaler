package eu.netleak.guiscaler;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.netleak.guiscaler.config.GuiScalerConfigModel;
import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import java.util.Map;

public class GuiScaler implements ModInitializer, ModMenuApi {

    public static GuiScalerConfigModel CONFIG;

    @Override
    public void onInitialize() {
        // Register and load config
        AutoConfig.register(GuiScalerConfigModel.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(GuiScalerConfigModel.class).getConfig();

        CommonClass.init();

        // Set config provider for CommonClass
        CommonClass.setConfigProvider(new CommonClass.ConfigProvider() {
            @Override
            public boolean isAutoScaleEnabled() {
                return CONFIG.enableAutoScale;
            }

            @Override
            public ScaleMode getScaleMode() {
                return CONFIG.mode;
            }

            @Override
            public Map<Integer, Integer> getCustomRules() {
                return CONFIG.customRules;
            }
        });

        // Register screen init callback
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            CommonClass.onScreenInit();
        });
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(GuiScalerConfigModel.class, parent).get();
    }
}
