package eu.netleak.guiscaler.config;

import eu.netleak.guiscaler.core.ScaleMode;
import io.wispforest.owo.config.annotation.Config;

import java.util.HashMap;
import java.util.Map;

@Config(name = "guiscaler", wrapperName = "GuiScalerConfig")
public class GuiScalerConfigModel {

    public boolean enableAutoScale = true;
    public ScaleMode mode = ScaleMode.AUTO;

    public Map<Integer, Integer> customRules = new HashMap<>() {{
        put(2560, 3);
        put(1920, 2);
        put(1280, 1);
    }};
}
