package eu.netleak.guiscaler.platform;

import eu.netleak.guiscaler.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public int getCurrentGuiScale() {
        return Minecraft.getInstance().options.guiScale().get();
    }

    @Override
    public void setGuiScale(int scale) {
        Minecraft.getInstance().options.guiScale().set(scale);
    }

    @Override
    public int getWindowWidth() {
        return Minecraft.getInstance().getWindow().getWidth();
    }

    @Override
    public int getWindowHeight() {
        return Minecraft.getInstance().getWindow().getHeight();
    }
}
