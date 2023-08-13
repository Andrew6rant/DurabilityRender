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

    // modified from https://stackoverflow.com/questions/23090019/fastest-formula-to-get-hue-from-rgb
    public static int getHue(int red, int green, int blue) {

        float min = Math.min(Math.min(red, green), blue);
        float max = Math.max(Math.max(red, green), blue);

        if (min == max) {
            return 0;
        }

        float hue;
        if (max == red) {
            hue = (green - blue) / (max - min);

        } else if (max == green) {
            hue = 2f + (blue - red) / (max - min);

        } else {
            hue = 4f + (red - green) / (max - min);
        }

        hue = hue * 60;
        if (hue < 0) hue = hue + 360;

        return Math.round(hue);
    }

    // modified from https://stackoverflow.com/questions/2630925/whats-the-most-effective-way-to-interpolate-between-two-colors-pseudocode-and
    public static int mixHexColors(int startColor, int endColor, float lerp) {
        int mask1 = 0x00ff00ff;
        int mask2 = 0xff00ff00;

        int roundedLerp = (int)(lerp);
        int roundedDifference = 256 - roundedLerp;

        return (((((startColor & mask1) * roundedDifference) + ((endColor & mask1) * roundedLerp)) >> 8) & mask1)
                | (((((startColor & mask2) * roundedDifference) + ((endColor & mask2) * roundedLerp)) >> 8) & mask2);
    }
    public static int parseConfigHex(String configColor) {
        return (int) Long.parseUnsignedLong(configColor.substring(1), 16);
    }
}
