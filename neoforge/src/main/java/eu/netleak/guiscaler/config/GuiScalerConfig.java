package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.core.ScaleMode;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class GuiScalerConfig {

    public static final ModConfigSpec CLIENT_CONFIG;
    public static final ModConfigSpec.BooleanValue ENABLE_AUTO_SCALE;
    public static final ModConfigSpec.EnumValue<ScaleMode> MODE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_RULES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("GUI Scaler Configuration").push("general");

        ENABLE_AUTO_SCALE = builder
                .comment("Enable automatic GUI scaling")
                .define("enableAutoScale", true);

        MODE = builder
                .comment("Scaling mode: AUTO (intelligent) or CUSTOM (user-defined rules)")
                .defineEnum("mode", ScaleMode.AUTO);

        CUSTOM_RULES = builder
                .comment("Custom scaling rules in format 'width:scale' (e.g., '2560:3' means scale 3 for width >= 2560)")
                .define("customRules",
                    Arrays.asList("2560:3", "1920:2", "1280:1"),
                    o -> o instanceof List && ((List<?>) o).stream().allMatch(
                        item -> item instanceof String && ((String) item).matches("\\d+:\\d+")));

        builder.pop();
        CLIENT_CONFIG = builder.build();
    }

}
