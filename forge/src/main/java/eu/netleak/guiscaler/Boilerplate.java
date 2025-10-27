package eu.netleak.guiscaler;

import eu.netleak.guiscaler.config.BoilerplateConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class Boilerplate {
    
    public Boilerplate(FMLJavaModLoadingContext modLoadingContext) {
        IEventBus modEventBus = modLoadingContext.getModEventBus();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, BoilerplateConfig.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);

        CommonClass.init();
    }
}