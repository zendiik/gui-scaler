package eu.netleak.guiscaler;

import eu.netleak.guiscaler.core.GUIScaleCalculator;
import eu.netleak.guiscaler.core.ScaleMode;
import eu.netleak.guiscaler.platform.Services;

import java.util.Map;

public class CommonClass {

    private static ConfigProvider configProvider;

    public static void init() {
        Constants.LOG.info("{} initialized on {}", Constants.MOD_NAME, Services.PLATFORM.getPlatformName());
    }

    public static void setConfigProvider(ConfigProvider provider) {
        configProvider = provider;
    }

    /**
     * Applies the automatic GUI scale for the current window size. This is called from a mixin at
     * the HEAD of {@code Minecraft#resizeGui}, so we only update the guiScale option and let
     * vanilla's own resize pass (which reads the option right after) do a single relayout. We never
     * call resizeGui() ourselves, which is what previously re-initialized the active screen mid
     * event dispatch and duplicated widgets added by other mods (#4).
     */
    public static void applyAutoScale() {
        if (configProvider == null || !configProvider.isAutoScaleEnabled()) {
            return;
        }

        int width = Services.PLATFORM.getWindowWidth();
        int height = Services.PLATFORM.getWindowHeight();
        ScaleMode mode = configProvider.getScaleMode();
        Map<Integer, Integer> customRules = configProvider.getCustomRules();

        int desired = GUIScaleCalculator.calculateOptimalScale(width, height, mode, customRules);
        if (desired != Services.PLATFORM.getCurrentGuiScale()) {
            Services.PLATFORM.setGuiScaleOption(desired);
            Constants.LOG.info("GUI scale set to {} ({}x{})", desired, width, height);
        }
    }

    public interface ConfigProvider {
        boolean isAutoScaleEnabled();
        ScaleMode getScaleMode();
        Map<Integer, Integer> getCustomRules();
    }
}
