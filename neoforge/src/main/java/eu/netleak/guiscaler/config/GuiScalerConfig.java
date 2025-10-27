package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.Constants;
import eu.netleak.guiscaler.core.ScaleMode;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuiScalerConfig {

    public static final ModConfigSpec CLIENT_CONFIG;
    public static final ModConfigSpec.BooleanValue ENABLE_AUTO_SCALE;
    public static final ModConfigSpec.EnumValue<ScaleMode> MODE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

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
