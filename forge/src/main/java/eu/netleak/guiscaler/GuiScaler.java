package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.GuiScalerConfig;
import eu.netleak.guiscaler.core.ScaleMode;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod(Constants.MOD_ID)
public class GuiScaler {

    public GuiScaler(FMLJavaModLoadingContext modLoadingContext) {
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, GuiScalerConfig.CLIENT_CONFIG);

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
                // Default custom rules (Forge doesn't support complex config maps easily)
                Map<Integer, Integer> rules = new HashMap<>();
                rules.put(2560, 3);
                rules.put(1920, 2);
                rules.put(1280, 1);
                return rules;
            }
        });

        MinecraftForge.EVENT_BUS.register(new ScreenEventHandler());
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    public static class ScreenEventHandler {

        @SubscribeEvent
        public static void onScreenInit(ScreenEvent.Init.Post event) {
            CommonClass.onScreenInit();
        }
    }
}