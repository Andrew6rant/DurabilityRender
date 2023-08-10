package io.github.andrew6rant.durabilityrender.mixin.client;

import io.github.andrew6rant.durabilityrender.DrawUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
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

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract int getScaledWindowWidth();

    @Shadow public abstract int getScaledWindowHeight();

    @Unique private static ItemStack savedStack;

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(value = "HEAD"))
    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        Screen currentScreen = client.currentScreen;
        if (currentScreen != null && currentScreen instanceof HandledScreen<?> handledScreen) {
            Slot focusedSlot = ((HandledScreenAccessor)handledScreen).getFocusedSlot();
            if (focusedSlot != null) {
                savedStack = focusedSlot.getStack();
            }
        }

    }

    /*@Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(value = "HEAD"))
    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        if (!components.isEmpty()) {
            int i = 0;
            int j = components.size() == 1 ? -2 : 0;

            for (TooltipComponent tooltipComponent : components) {
                int k = tooltipComponent.getWidth(textRenderer);
                if (k > i) {
                    i = k;
                }
                j += tooltipComponent.getHeight();
            }

            Vector2ic vector2ic = positioner.getPosition(this.getScaledWindowWidth(), this.getScaledWindowHeight(), x, y, i, j);

            Screen currentScreen = client.currentScreen;
            if (currentScreen != null) {
                System.out.println(currentScreen.getTitle());
            } else {
                System.out.println("INVALID SCREEN!");
            }

            if (currentScreen != null && currentScreen instanceof HandledScreen<?> handledScreen) {
                Slot focusedSlot = ((HandledScreenAccessor)handledScreen).getFocusedSlot();
                System.out.println("focusedSlot "+focusedSlot);
                if (focusedSlot != null) {
                    DrawUtil.drawTooltipDurability(focusedSlot.getStack(), this, vector2ic.x(), vector2ic.y(), i, j);
                }
            }

        }

    }*/

    @ModifyVariable(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At(value = "STORE"), ordinal = 5)
    private int drawTooltip(int m) {
        if (savedStack != null && savedStack.isDamageable() && savedStack.getDamage() != 0) {
            return m + DrawUtil.changeVerticalLength(); // change the length of the tooltip if stack will have durability bar drawn
        }
        return m;
    }

    /*@ModifyVariable(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
    at = @At(value = "STORE"))
    private Vector2ic drawTooltip(Vector2ic vector2ic) {
        return vector2ic;
    }*/

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw(Ljava/lang/Runnable;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci, int i, int j, int l, int m, Vector2ic vector2ic, int n, int o, int p) {
        /*Screen currentScreen = client.currentScreen;
        if (currentScreen != null && currentScreen instanceof HandledScreen<?> handledScreen) {
            Slot focusedSlot = ((HandledScreenAccessor)handledScreen).getFocusedSlot();
            if (focusedSlot != null) {
                DrawUtil.drawTooltipDurability(focusedSlot.getStack(), this, n, o, l, m);
            }
        }*/
        if (savedStack != null) {
            DrawUtil.drawTooltipDurability(savedStack, ((DrawContext)(Object)this), n, o, l, m);
        }
    }

    @ModifyConstant(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            constant = @Constant(intValue = -16777216, ordinal = 0))
    public int drawItemInSlot(int constant) {
        return DrawUtil.getDurabilityBackgroundOpacity();
    }

    @ModifyConstant(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            constant = @Constant(intValue = -16777216, ordinal = 1))
    public int drawItemInSlot2(int constant) {
        return DrawUtil.getDurabilityOpacity();
    }
}
