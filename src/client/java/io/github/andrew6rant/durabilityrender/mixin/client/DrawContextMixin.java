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
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static io.github.andrew6rant.durabilityrender.Util.parseConfigHex;
import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;
import static net.minecraft.util.math.ColorHelper.Abgr.*;

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
            DrawUtil.drawTooltipDurability(savedFocusedStack, ((DrawContext)(Object)this), n, o, l, m);
        }
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 0))
    public void durabilityrender$redirectSlotDurabilityBackground(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
        //int i = Util.getItemBarStep(savedSlotStack);
        ((DrawContext)(Object)this).fill(RenderLayer.getGuiOverlay(), slotDurabilityBarXOffset+x1+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y1, slotDurabilityBarXOffset+x2, (-slotDurabilityBarYOffset)+y2-2+slotDurabilityBarBackgroundHeight, color);
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 1))
    public void durabilityrender$redirectSlotDurabilityForeground(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
        //float percentDamaged = Math.max(0.0F, (((float)savedSlotStack.getMaxDamage() - (float)savedSlotStack.getDamage()) / (float)savedSlotStack.getMaxDamage()) * 256F);
        int i = Util.getItemBarStep(savedSlotStack);

        ((DrawContext)(Object)this).fill(RenderLayer.getGuiOverlay(), slotDurabilityBarXOffset+x1+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y1, slotDurabilityBarXOffset+x1+i+(13-slotDurabilityBarWidth), (-slotDurabilityBarYOffset)+y2-1+slotDurabilityBarHeight, color);
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemBarColor()I", ordinal = 0))
    public int durabilityrender$redirectSlotGetItemBarColor(ItemStack itemStack) {
        float percentDamaged = Math.max(0.0F, (((float)itemStack.getMaxDamage() - (float)itemStack.getDamage()) / (float)itemStack.getMaxDamage()));
        //System.out.println("ok");
        int startColor = parseConfigHex(slotDurabilityColorStartAARRGGBB);
        int endColor = parseConfigHex(slotDurabilityColorEndAARRGGBB);
        //int hex = mixHexColors(endColor, startColor, percentDamaged * 256F);
        int startHue = Util.getHue(getRed(startColor), getGreen(startColor), getBlue(startColor));
        int endHue = Util.getHue(getRed(endColor), getGreen(endColor), getBlue(endColor));

        //return mixHexColors(endColor, startColor, percentDamaged);
        //int alpha = getAbgr(0x10000000, getBlue(hex), getGreen(hex), getRed(hex));
        //int alphaColor = getAlpha(startColor)
        //int alpha = withAlpha((int)percentDamaged, hex);
        //int alpha = getArgb((int)percentDamaged, getRed(itemStack.getItemBarColor() | -16777216), getGreen(itemStack.getItemBarColor() | -16777216), getBlue(itemStack.getItemBarColor() | -16777216));
        //return itemStack.getItemBarColor() | -16777216;


        float hueValue = ((startHue/360f)*percentDamaged)-((endHue/360f)*percentDamaged);
        //if (hueValue < 0) {
        //    hueValue = 1f + hueValue;
        //}
        //if (startHue <= endHue) {
        //    return MathHelper.hsvToRgb(((endHue/360f)*percentDamaged)-((startHue/360f)*percentDamaged), 1.0F, 1.0F);
        //} else {
        //return MathHelper.hsvToRgb(hueValue, 1.0F, 1.0F);
        //}
        if (hueValue < 0) {
                return MathHelper.hsvToRgb(((endHue/360f)*percentDamaged)-((startHue/360f)*percentDamaged), 1.0F, 1.0F);
            } else {
            //return MathHelper.hsvToRgb(((endHue/360f)*percentDamaged)+((startHue/360f)*percentDamaged), 1.0F, 1.0F);
            return MathHelper.hsvToRgb(hueValue, 1.0F, 1.0F);
        }

    }

    @ModifyConstant(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            constant = @Constant(intValue = -16777216, ordinal = 0))
    public int durabilityrender$modifySlotBackgroundOpacity(int constant) {
        return Util.getDurabilityBackgroundOpacity();
    }

    @ModifyConstant(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            constant = @Constant(intValue = -16777216, ordinal = 1))
    public int durabilityrender$modifySlotForegroundOpacity(int constant) {
        return Util.getDurabilityOpacity();
    }
}