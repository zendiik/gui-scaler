package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.GuiScalerConfigModel;
import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import java.util.HashMap;
import java.util.Map;

public class GuiScaler implements ModInitializer {

    private static GuiScalerConfigModel CONFIG;

    @Override
    public void onInitialize() {
        AutoConfig.register(GuiScalerConfigModel.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(GuiScalerConfigModel.class).getConfig();

        CommonClass.init();

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
                Map<Integer, Integer> rules = new HashMap<>();
                GuiScalerConfigModel.CustomRules cr = CONFIG.customRules;
                if (cr.rule1Width > 0) rules.put(cr.rule1Width, cr.rule1Scale);
                if (cr.rule2Width > 0) rules.put(cr.rule2Width, cr.rule2Scale);
                if (cr.rule3Width > 0) rules.put(cr.rule3Width, cr.rule3Scale);
                return rules;
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            CommonClass.onScreenInit();
        });
    }
}
