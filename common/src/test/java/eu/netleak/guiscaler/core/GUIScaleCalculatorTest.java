package eu.netleak.guiscaler.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GUIScaleCalculatorTest {

    // AUTO mode tests

    @Test
    void autoMode_4kWidth_returnsScale3() {
        assertEquals(3, GUIScaleCalculator.calculateOptimalScale(2560, 1440, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_4kWidthLarge_returnsScale3() {
        assertEquals(3, GUIScaleCalculator.calculateOptimalScale(3840, 2160, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_fullHdWidth_returnsScale2() {
        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(1920, 1080, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_hdWidth_returnsScale1() {
        assertEquals(1, GUIScaleCalculator.calculateOptimalScale(1280, 720, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_smallWindow_returnsScale0() {
        assertEquals(0, GUIScaleCalculator.calculateOptimalScale(800, 600, ScaleMode.AUTO, Map.of()));
    }

    // Pixel density tests

    @Test
    void autoMode_highPixelDensityNarrow_returnsScale3() {
        // 1800 * 2600 = 4_680_000 > 4.5M → scale 3
        assertEquals(3, GUIScaleCalculator.calculateOptimalScale(1800, 2600, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_mediumPixelDensity_returnsScale2() {
        // 1500 * 1400 = 2_100_000 > 2M → scale 2
        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(1500, 1400, ScaleMode.AUTO, Map.of()));
    }

    // Boundary tests

    @Test
    void autoMode_exactlyAt2560_returnsScale3() {
        assertEquals(3, GUIScaleCalculator.calculateOptimalScale(2560, 1, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_justBelow2560_returnsScale2() {
        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(2559, 1, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_exactlyAt1920_returnsScale2() {
        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(1920, 1, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_justBelow1920_returnsScale1() {
        assertEquals(1, GUIScaleCalculator.calculateOptimalScale(1919, 1, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_exactlyAt1280_returnsScale1() {
        assertEquals(1, GUIScaleCalculator.calculateOptimalScale(1280, 1, ScaleMode.AUTO, Map.of()));
    }

    @Test
    void autoMode_justBelow1280_returnsScale0() {
        assertEquals(0, GUIScaleCalculator.calculateOptimalScale(1279, 1, ScaleMode.AUTO, Map.of()));
    }

    // CUSTOM mode tests

    @Test
    void customMode_matchesHighestRule() {
        Map<Integer, Integer> rules = new HashMap<>();
        rules.put(2560, 3);
        rules.put(1920, 2);
        rules.put(1280, 1);

        assertEquals(3, GUIScaleCalculator.calculateOptimalScale(2560, 1440, ScaleMode.CUSTOM, rules));
    }

    @Test
    void customMode_matchesMiddleRule() {
        Map<Integer, Integer> rules = new HashMap<>();
        rules.put(2560, 3);
        rules.put(1920, 2);
        rules.put(1280, 1);

        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(1920, 1080, ScaleMode.CUSTOM, rules));
    }

    @Test
    void customMode_matchesBetweenRules() {
        Map<Integer, Integer> rules = new HashMap<>();
        rules.put(2560, 3);
        rules.put(1920, 2);
        rules.put(1280, 1);

        assertEquals(2, GUIScaleCalculator.calculateOptimalScale(2000, 1200, ScaleMode.CUSTOM, rules));
    }

    @Test
    void customMode_noMatchingRule_returnsScale0() {
        Map<Integer, Integer> rules = new HashMap<>();
        rules.put(2560, 3);
        rules.put(1920, 2);

        assertEquals(0, GUIScaleCalculator.calculateOptimalScale(800, 600, ScaleMode.CUSTOM, rules));
    }

    @Test
    void customMode_emptyRules_returnsScale0() {
        assertEquals(0, GUIScaleCalculator.calculateOptimalScale(1920, 1080, ScaleMode.CUSTOM, Map.of()));
    }
}
