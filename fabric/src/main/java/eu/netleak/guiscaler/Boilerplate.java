package eu.netleak.guiscaler;

import net.fabricmc.api.ModInitializer;

public class Boilerplate implements ModInitializer {

    public static final BoilerplateConfig CONFIG = BoilerplateConfig.createAndLoad();
    
    @Override
    public void onInitialize() {
        CommonClass.init();
    }
}
