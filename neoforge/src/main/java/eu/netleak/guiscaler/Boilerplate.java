package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.BoilerplateConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class Boilerplate {
    
    public Boilerplate(IEventBus modEventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, BoilerplateConfig.CONFIG_SPEC);

        CommonClass.init();
    }
}