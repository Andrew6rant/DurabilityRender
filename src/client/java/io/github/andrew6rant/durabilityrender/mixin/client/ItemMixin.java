package io.github.andrew6rant.durabilityrender.mixin.client;

import io.github.andrew6rant.durabilityrender.Util;
import io.github.andrew6rant.durabilityrender.config.ConfigEnums;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;

@Mixin(Item.class)
public abstract class ItemMixin {


    @Shadow public abstract String getTranslationKey(ItemStack stack);

    @Inject(method = "getName(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/text/Text;", at = @At("HEAD"), cancellable = true)
    public void durabilityrender$injectDurabilityInName(ItemStack itemStack, CallbackInfoReturnable<Text> cir) {
        if (tooltipNameFormat != ConfigEnums.TooltipNameFormatEnum.NONE) {
            if (itemStack.isDamageable()) {
                int maxDamage = itemStack.getMaxDamage();
                int remainingDurability = maxDamage - itemStack.getDamage();
                double percentDurability = ((double) remainingDurability / (double) maxDamage) * 100.0D;
                Number roundedPercent = Util.round(percentDurability, percentPrecision);
                switch (tooltipNameFormat) {
                    case PERCENT_ONLY -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_percent_only", Text.translatable(this.getTranslationKey(itemStack)), roundedPercent));
                    case DURABILITY_ONLY -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_durability_only", Text.translatable(this.getTranslationKey(itemStack)), remainingDurability, maxDamage));
                    case REMAINING_ONLY -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_remaining_only", Text.translatable(this.getTranslationKey(itemStack)), remainingDurability));
                    case DURABILITY_AND_PERCENT -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_durability_and_percent", Text.translatable(this.getTranslationKey(itemStack)), remainingDurability, maxDamage, roundedPercent));
                    case PERCENT_AND_DURABILITY -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_percent_and_durability", Text.translatable(this.getTranslationKey(itemStack)), roundedPercent, remainingDurability, maxDamage));
                    case PERCENT_AND_REMAINING -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_percent_and_remaining", Text.translatable(this.getTranslationKey(itemStack)), roundedPercent, remainingDurability));
                    case REMAINING_AND_PERCENT -> cir.setReturnValue(Text.translatable("durabilityrender.tooltip.name_and_remaining_and_percent", Text.translatable(this.getTranslationKey(itemStack)), remainingDurability, roundedPercent));
                }
            }
        }
    }
}
