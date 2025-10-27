package eu.netleak.guiscaler.core;

import java.util.Map;

public class GUIScaleCalculator {

    public static int calculateOptimalScale(int width, int height, ScaleMode mode, Map<Integer, Integer> customRules) {
        if (mode == ScaleMode.CUSTOM) {
            return calculateCustomScale(width, customRules);
        }
        return calculateAutoScale(width, height);
    }

    private static int calculateAutoScale(int width, int height) {
        int totalPixels = width * height;

        // HiDPI/4K territory (2560+ width, or 4.5M+ pixels)
        if (width >= 2560 || totalPixels > 4_500_000) {
            return 3;
        }
        // Full HD territory (1920+ width, or 2M+ pixels)
        else if (width >= 1920 || totalPixels > 2_000_000) {
            return 2;
        }
        // HD territory (1280+ width)
        else if (width >= 1280) {
            return 1;
        }
        // Let Minecraft decide
        else {
            return 0; // auto
        }
    }

    private static int calculateCustomScale(int width, Map<Integer, Integer> rules) {
        return rules.entrySet().stream()
            .filter(e -> width >= e.getKey())
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(0);
    }
}
