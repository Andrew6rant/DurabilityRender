package io.github.andrew6rant.durabilityrender.mixin.client;

import io.github.andrew6rant.durabilityrender.DrawUtil;
import io.github.andrew6rant.durabilityrender.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static io.github.andrew6rant.durabilityrender.Util.getDurabilityColor;
import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow @Final private MinecraftClient client;

    @Unique private static ItemStack savedFocusedStack;
    @Unique private static ItemStack savedSlotStack;

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V", at = @At(value = "HEAD"))
    private void durabilityrender$saveFocusedStack(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        Screen currentScreen = client.currentScreen;
        if (currentScreen != null && currentScreen instanceof HandledScreen<?> handledScreen) {
            Slot focusedSlot = ((HandledScreenAccessor)handledScreen).getFocusedSlot();
            if (focusedSlot != null) {
                savedFocusedStack = focusedSlot.getStack();
            }
        }
    }

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V", at = @At(value = "TAIL"))
    private void durabilityrender$cullFocusedStack(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        savedFocusedStack = null; // hovering over an item and then immediately pausing and viewing a settings tooltip would otherwise render the durability tooltip
    }

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "HEAD"))
    private void durabilityrender$saveSlotStatck(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        savedSlotStack = stack;
    }

    @ModifyVariable(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(value = "STORE"), ordinal = 5)
    private int durabilityrender$modifyTooltipLength(int m) {
        if (savedFocusedStack != null && savedFocusedStack.isDamageable() && savedFocusedStack.getDamage() != 0) {
            return m + modifyTooltipLength; // change the length of the tooltip if stack will have durability bar drawn
        }
        return m;
    }

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw(Ljava/lang/Runnable;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void durabilityrender$drawTooltipDurabilityForeground(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci, int i, int j, int l, int m, Vector2ic vector2ic, int n, int o, int p) {
        if (savedFocusedStack != null) {
            if (tooltipBarThickness != 0) {
                DrawUtil.drawTooltipDurability(savedFocusedStack, ((DrawContext) (Object) this), n, o, l, m);
            }
        }
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 0))
    public void durabilityrender$redirectSlotDurabilityBackground(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
        ((DrawContext)(Object)this).fill(RenderLayer.getGuiOverlay(), slotDurabilityBarXOffset+x1+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y1, slotDurabilityBarXOffset+x2, (-slotDurabilityBarYOffset)+y2-2+slotDurabilityBarBackgroundHeight, Util.parseConfigHex(slotDurabilityBackgroundColorRRGGBB) | Util.getOpacity(slotDurabilityBackgroundOpacity));
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 1))
    public void durabilityrender$redirectSlotDurabilityForeground(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
        int i = Util.getItemBarStep(savedSlotStack);
        ((DrawContext)(Object)this).fill(RenderLayer.getGuiOverlay(), slotDurabilityBarXOffset+x1+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y1, slotDurabilityBarXOffset+x1+i+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y2-1+slotDurabilityBarHeight, getDurabilityColor(savedSlotStack) | Util.getOpacity(slotDurabilityOpacity));
    }
}