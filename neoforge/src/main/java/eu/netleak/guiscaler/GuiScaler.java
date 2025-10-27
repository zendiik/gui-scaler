package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.GuiScalerConfig;
import eu.netleak.guiscaler.core.ScaleMode;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.Map;

@Mod(Constants.MOD_ID)
public class GuiScaler {

    public GuiScaler(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, GuiScalerConfig.CLIENT_CONFIG);

        CommonClass.init();

        // Set config provider
        CommonClass.setConfigProvider(new CommonClass.ConfigProvider() {
            @Override
            public boolean isAutoScaleEnabled() {
                return GuiScalerConfig.ENABLE_AUTO_SCALE.get();
            }

            @Override
            public ScaleMode getScaleMode() {
                return GuiScalerConfig.MODE.get();
            }

            @Override
            public Map<Integer, Integer> getCustomRules() {
                Map<Integer, Integer> rules = new HashMap<>();
                for (String rule : GuiScalerConfig.CUSTOM_RULES.get()) {
                    try {
                        String[] parts = rule.split(":");
                        if (parts.length == 2) {
                            int width = Integer.parseInt(parts[0]);
                            int scale = Integer.parseInt(parts[1]);
                            rules.put(width, scale);
                        }
                    } catch (NumberFormatException e) {
                        Constants.LOG.error("Invalid custom rule format: {}", rule);
                    }
                }
                return rules;
            }
        });

        NeoForge.EVENT_BUS.register(new ScreenEventHandler());
    }

    public static class ScreenEventHandler {

        @SubscribeEvent
        public void onScreenInit(ScreenEvent.Init.Post event) {
            CommonClass.onScreenInit();
        }
    }
}