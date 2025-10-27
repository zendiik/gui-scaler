package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.GuiScalerConfig;
import eu.netleak.guiscaler.core.ScaleMode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import java.util.Map;

public class GuiScaler implements ModInitializer {

    public static final GuiScalerConfig CONFIG = GuiScalerConfig.createAndLoad();

    @Override
    public void onInitialize() {
        CommonClass.init();

        // Set config provider for CommonClass
        CommonClass.setConfigProvider(new CommonClass.ConfigProvider() {
            @Override
            public boolean isAutoScaleEnabled() {
                return CONFIG.enableAutoScale();
            }

            @Override
            public ScaleMode getScaleMode() {
                return CONFIG.mode();
            }

            @Override
            public Map<Integer, Integer> getCustomRules() {
                return CONFIG.customRules();
            }
        });

        // Register screen init callback
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            CommonClass.onScreenInit();
        });
    }
}
