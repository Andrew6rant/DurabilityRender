package io.github.andrew6rant.durabilityrender;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class DrawUtil {

    public static int changeVerticalLength() {
        return 3;
    }
    public static void drawTooltipDurability(ItemStack savedStack, DrawContext drawContext, int x, int y, int width, int height) {
        //System.out.println(savedStack);
        if (savedStack != null && savedStack.isDamageable()) {
            int stackDamage = savedStack.getDamage();
            int maxDamage = savedStack.getMaxDamage();
            //System.out.println("maxDamage: " + maxDamage+" damage: " + stackDamage);
            if (stackDamage != 0) {
                int i = x - 3;
                int j = y - 3;
                int k = width + 3 + 3;
                int l = height + 3 + 3;


                int leftOffset = 3;
                int rightOffset = 3;
                int bottomOffset = 3;
                int barThickness = 1;

                int step = Math.round((float)(k-leftOffset-rightOffset) - (float)stackDamage * (float)(k-leftOffset-rightOffset) / (float)maxDamage);

                //((DrawContext)object).fill(i+1, j+l-1, i+k-1, j+l-2, 401, savedStack.getItemBarColor() | 0xFF000000);
                drawContext.fill(i+leftOffset, j+l-bottomOffset, i+k-rightOffset, j+l-(bottomOffset+barThickness), 401, -11184811);
                drawContext.fill(i+leftOffset, j+l-bottomOffset, i+step+rightOffset, j+l-(bottomOffset+barThickness), 401, savedStack.getItemBarColor() | 0xFF000000);
                //renderHorizontalLine(((DrawContext)object), i, j + l, k, 401, savedStack.getItemBarColor() | 0xFF000000);
            }
        }
        //System.out.println("drawTooltipDurability");
        //int i = x - 3;
        //int j = y - 3;
        //int k = width + 3 + 3;
        //int l = height + 3 + 3;
        //((DrawContext)object).fill(i, j+l, i+k, j, 401, 1343420415);

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

    //public int getItemBarStep(ItemStack stack) {
    //    return Math.round(13.0F - (float)stack.getDamage() * 13.0F / (float)this.maxDamage);
    //}

    private static void renderHorizontalLine(DrawContext context, int x, int y, int width, int z, int color) {
        context.fill(x+1, y-1, x + width-1, y-2, z, color);
    }
}
