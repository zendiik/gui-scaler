package eu.netleak.guiscaler.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BoilerplateConfig {

    public static final BoilerplateConfig CONFIG;

    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Boolean> enableBoilerplate;

    private BoilerplateConfig(ModConfigSpec.Builder builder) {
        enableBoilerplate = builder
                .comment("Enable the Boilerplate mod")
                .define("enableBoilerplate", true);
    }

    static {
        Pair<BoilerplateConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(BoilerplateConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

}
