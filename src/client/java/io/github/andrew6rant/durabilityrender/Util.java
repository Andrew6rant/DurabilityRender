package io.github.andrew6rant.durabilityrender;

import net.minecraft.item.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.slotDurabilityBarWidth;

public class Util {
    public static int getItemBarStep(ItemStack stack) {
        return Math.round((float)slotDurabilityBarWidth - (float)stack.getDamage() * (float)slotDurabilityBarWidth / (float)stack.getMaxDamage());
    }

    public static int getDurabilityOpacity() {
        return -16777216; //-16777216 // 1157627904
    }

    public static int getDurabilityBackgroundOpacity() {
        return 0x77000000;
    }

    public static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}
