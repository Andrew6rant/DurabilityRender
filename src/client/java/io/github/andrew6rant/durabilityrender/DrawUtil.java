package io.github.andrew6rant.durabilityrender;

import io.github.andrew6rant.durabilityrender.config.ConfigEnums;
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

                if (snapToTopOrBottom == ConfigEnums.TooltipSnapEnum.TOP) {
                    l = 1;
                }
                drawContext.fill(i+tooltipLeftOffset, j+l-tooltipYOffset, i+k-tooltipRightOffset, j+l-(tooltipYOffset+tooltipBarThickness), 401, -11184811);
                drawContext.fill(i+tooltipLeftOffset, j+l-tooltipYOffset, i+step, j+l-(tooltipYOffset+tooltipBarThickness), 401, Util.getDurabilityColor(savedStack) | 0xFF000000);
            }
        }
    }
}
