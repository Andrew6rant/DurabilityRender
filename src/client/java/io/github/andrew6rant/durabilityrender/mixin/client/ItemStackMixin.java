package io.github.andrew6rant.durabilityrender.mixin.client;

import io.github.andrew6rant.durabilityrender.DrawUtil;
import io.github.andrew6rant.durabilityrender.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "isItemBarVisible()Z", at = @At(value = "HEAD"), cancellable = true)
    public void durabilityrender$hideSlotStackDurability(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = ((ItemStack)(Object)this);
        if (itemStack.isDamageable()) {
            int maxDamage = itemStack.getMaxDamage();
            int durability = maxDamage - itemStack.getDamage();

            if (((int)Math.floor(((float)durability / (float)maxDamage) * 100)) >= Util.getShownDurabilityPercent()) {
                cir.setReturnValue(false);
            }
        }
    }
}
