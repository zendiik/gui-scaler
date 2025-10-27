package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.Constants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BoilerplateConfig {

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue ENABLE_BOILERPLATE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        ENABLE_BOILERPLATE = builder
                .comment("Enable the Boilerplate mod")
                .define("enableBoilerplate", true);

        COMMON_CONFIG = builder.build();
    }

}
