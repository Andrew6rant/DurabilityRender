package io.github.andrew6rant.durabilityrender;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;

public class DrawUtil {

    public static void drawTooltipDurability(ItemStack savedStack, DrawContext drawContext, int x, int y, int width, int height) {
        if (savedStack != null && savedStack.isDamageable()) {
            int stackDamage = savedStack.getDamage();
            int maxDamage = savedStack.getMaxDamage();
            if (stackDamage != 0) {
                int i = x - 3;
                int j = y - 3;
                int k = width + 3 + 3;
                int l = height + 3 + 3;

                int step = Math.round((float)(k- tooltipLeftOffset - tooltipRightOffset) - (float)stackDamage * (float)(k- tooltipLeftOffset - tooltipRightOffset) / (float)maxDamage);

                drawContext.fill(i+ tooltipLeftOffset, j+l- tooltipBottomOffset, i+k- tooltipRightOffset, j+l-(tooltipBottomOffset + tooltipBarThickness), 401, -11184811);
                drawContext.fill(i+ tooltipLeftOffset, j+l- tooltipBottomOffset, i+step, j+l-(tooltipBottomOffset + tooltipBarThickness), 401, savedStack.getItemBarColor() | 0xFF000000);
            }
        }
    }
}
