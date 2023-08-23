package io.github.andrew6rant.durabilityrender;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;
import static io.github.andrew6rant.durabilityrender.config.ClientConfig.durabilityColor;
import static io.github.andrew6rant.durabilityrender.config.ConfigEnums.DurabilityColorEnum.HSL_CLOCKWISE;
import static net.minecraft.util.math.ColorHelper.Abgr.*;
import static net.minecraft.util.math.ColorHelper.Abgr.getBlue;

public class Util {

    public static int getDurabilityColor(ItemStack itemStack) {
        float percentDamaged = Math.max(0.0F, (((float)itemStack.getMaxDamage() - (float)itemStack.getDamage()) / (float)itemStack.getMaxDamage()));
        int startColor = parseConfigHex(slotDurabilityColorStartRRGGBB);
        int endColor = parseConfigHex(slotDurabilityColorEndRRGGBB);

        int startHue = 0, endHue = 0;
        float hueValue;

        return switch (durabilityColor) {
            case RGB -> mixHexColors(endColor, startColor, percentDamaged);
            case RGB_INVERTED -> mixHexColors(startColor, endColor, percentDamaged);
            case HSL_CLOCKWISE, HSL_COUNTERCLOCKWISE -> {
                startHue = Util.getHue(getRed(startColor), getGreen(startColor), getBlue(startColor));
                endHue = Util.getHue(getRed(endColor), getGreen(endColor), getBlue(endColor));
                if (durabilityColor == HSL_CLOCKWISE) {
                    hueValue = ((endHue/360f)*percentDamaged)-((startHue/360f)*percentDamaged);
                } else {
                    hueValue = ((startHue/360f)*percentDamaged)+((endHue/360f)*percentDamaged);
                }
                if (hueValue < 0) {
                    hueValue = 1f - hueValue;
                }
                yield MathHelper.hsvToRgb(hueValue, 1.0F, 1.0F);
            }
        };
    }
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
        int mask1 = 0xff00ff;
        int mask2 = 0x00ff00;

        int roundedLerp = (int)(256 * lerp);
        int roundedDifference = 256 - roundedLerp;

        return (((((startColor & mask1) * roundedDifference) + ((endColor & mask1) * roundedLerp)) >> 8) & mask1)
                | (((((startColor & mask2) * roundedDifference) + ((endColor & mask2) * roundedLerp)) >> 8) & mask2);
    }
    public static int parseConfigHex(String configColor) {
        if (configColor.isEmpty() || configColor.substring(1).isEmpty()) return 0;
        return (int) Long.parseUnsignedLong(configColor.substring(1), 16);
    }
}
