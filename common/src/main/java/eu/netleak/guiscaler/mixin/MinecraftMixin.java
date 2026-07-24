package eu.netleak.guiscaler.mixin;

import eu.netleak.guiscaler.CommonClass;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies the automatic GUI scale at the very start of {@link Minecraft#resizeDisplay()}. That
 * method reads the guiScale option right after this injection and then performs a single relayout,
 * so by only updating the option here we let vanilla do exactly one resize with our scale — without
 * ever calling resizeDisplay() ourselves. This avoids re-initializing the active screen mid dispatch,
 * which previously duplicated widgets added by other mods (#4). resizeDisplay() is invoked on every
 * window / framebuffer resize, on fullscreen toggle, and once during client startup, so this covers
 * all cases.
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "resizeDisplay", at = @At("HEAD"))
    private void guiscaler$applyAutoScale(CallbackInfo ci) {
        CommonClass.applyAutoScale();
    }
}
