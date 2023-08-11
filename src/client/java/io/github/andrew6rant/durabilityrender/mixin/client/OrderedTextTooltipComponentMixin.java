package io.github.andrew6rant.durabilityrender.mixin.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OrderedTextTooltipComponent.class)
public class OrderedTextTooltipComponentMixin {
    @Shadow
    @Final
    private OrderedText text;

    /**
     * @author Andrew6rant (Andrew6rant)
     * @reason Changing the spacing of empty tooltip strings. This Mixin is not enabled currently
     */
    @Overwrite
    public int getHeight() {
        if(this.text.equals(OrderedText.EMPTY)) {
            return 1;
        } else {
            return 10;
        }
    }


}
