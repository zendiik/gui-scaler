package eu.netleak.guiscaler.platform;

import eu.netleak.guiscaler.platform.services.IPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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