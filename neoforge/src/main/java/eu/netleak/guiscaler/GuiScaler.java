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
                // Default custom rules (NeoForge doesn't support complex config maps easily)
                Map<Integer, Integer> rules = new HashMap<>();
                rules.put(2560, 3);
                rules.put(1920, 2);
                rules.put(1280, 1);
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