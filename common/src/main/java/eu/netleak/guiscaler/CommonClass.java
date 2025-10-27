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

    public static void onScreenInit() {
        if (configProvider == null || !configProvider.isAutoScaleEnabled()) {
            return;
        }

        int width = Services.PLATFORM.getWindowWidth();
        int height = Services.PLATFORM.getWindowHeight();
        int currentScale = Services.PLATFORM.getCurrentGuiScale();

        ScaleMode mode = configProvider.getScaleMode();
        Map<Integer, Integer> customRules = configProvider.getCustomRules();

        int newScale = GUIScaleCalculator.calculateOptimalScale(width, height, mode, customRules);

        if (newScale != currentScale) {
            Services.PLATFORM.setGuiScale(newScale);
            Constants.LOG.info("GUI scale changed: {} → {} ({}x{})", currentScale, newScale, width, height);
        }
    }

    public interface ConfigProvider {
        boolean isAutoScaleEnabled();
        ScaleMode getScaleMode();
        Map<Integer, Integer> getCustomRules();
    }
}