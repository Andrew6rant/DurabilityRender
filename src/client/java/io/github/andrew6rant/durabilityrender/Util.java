package io.github.andrew6rant.durabilityrender;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class Util {
    public static int getItemBarStep(ItemStack stack) {
        int barLength = 8; // this will be a config option
        return Math.round((float)barLength - (float)stack.getDamage() * (float)barLength / (float)stack.getMaxDamage());
    }

    public static int getShownDurabilityPercent() {
        return 100;
    }

    public static int getDurabilityOpacity() {
        return -16777216;
    }

    public static int getDurabilityBackgroundOpacity() {
        return 0x77000000;
    }
}
