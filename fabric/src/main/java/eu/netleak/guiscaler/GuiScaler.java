package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.GuiScalerConfigModel;
import eu.netleak.guiscaler.core.ScaleMode;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import java.util.Map;

public class GuiScaler implements ModInitializer {

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

        // GUI scale is applied via MinecraftMixin (common) hooking Minecraft#resizeGui.
    }
}
