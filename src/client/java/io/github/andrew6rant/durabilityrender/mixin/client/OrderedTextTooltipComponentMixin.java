package io.github.andrew6rant.durabilityrender.mixin.client;

import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.changeEmptyTextHeight;

@Mixin(OrderedTextTooltipComponent.class)
public class OrderedTextTooltipComponentMixin {
    @Shadow @Final private OrderedText text;

    @Inject(method = "getHeight()I", at = @At(value = "HEAD"), cancellable = true)
    private void durabilityrender$changeEmptyTextTooltipComponentHeight(CallbackInfoReturnable<Integer> cir) {
        if (this.text.equals(OrderedText.EMPTY)) {
            cir.setReturnValue(changeEmptyTextHeight);
        }
    }
}
