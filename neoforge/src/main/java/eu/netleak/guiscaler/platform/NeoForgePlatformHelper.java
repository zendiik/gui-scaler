package eu.netleak.guiscaler.platform;

import eu.netleak.guiscaler.platform.services.IPlatformHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
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