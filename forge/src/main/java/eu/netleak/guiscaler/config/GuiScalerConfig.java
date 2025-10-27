package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.Constants;
import eu.netleak.guiscaler.core.ScaleMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuiScalerConfig {

    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_SCALE;
    public static final ForgeConfigSpec.EnumValue<ScaleMode> MODE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("GUI Scaler Configuration").push("general");

        ENABLE_AUTO_SCALE = builder
                .comment("Enable automatic GUI scaling")
                .define("enableAutoScale", true);

        MODE = builder
                .comment("Scaling mode: AUTO (intelligent) or CUSTOM (user-defined rules)")
                .defineEnum("mode", ScaleMode.AUTO);

        builder.pop();
        CLIENT_CONFIG = builder.build();
    }

}
