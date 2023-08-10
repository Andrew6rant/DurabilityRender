package io.github.andrew6rant.durabilityrender.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipBackgroundRenderer.class)
public class TooltipBackgroundRendererMixin {
    /*@Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;IIIII)V", at = @At(value = "TAIL"))
    private static void render(DrawContext context, int x, int y, int width, int height, int z, CallbackInfo ci) {
        if (stack.isItemBarVisible()) {
            int i = stack.getItemBarStep();
            int j = stack.getItemBarColor();
            int k = x + 2;
            int l = y + 13;
            this.fill(RenderLayer.getGuiOverlay(), k, l, k + 13, l + 2, -16777216);
            this.fill(RenderLayer.getGuiOverlay(), k, l, k + i, l + 1, j | 0xFF000000);
        }
    }*/
}
