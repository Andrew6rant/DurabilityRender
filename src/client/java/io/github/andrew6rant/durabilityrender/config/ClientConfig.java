package io.github.andrew6rant.durabilityrender.config;

public class ClientConfig extends MidnightConfig {
    @Entry public static boolean showDurabilityTooltip = true;

    @Entry public static TooltipFormatEnum tooltipFormat = TooltipFormatEnum.PERCENT_AND_REMAINING;
    @Entry(min=0, max=10) public static int percentPrecision = 1;
    public enum TooltipFormatEnum {
        DURABILITY_AND_PERCENT, PERCENT_AND_DURABILITY, REMAINING_AND_PERCENT, PERCENT_AND_REMAINING, PERCENT_ONLY, DURABILITY_ONLY, REMAINING_ONLY;
    }
    @Entry(min=0, max=1000) public static int slotDurabilityBarWidth = 8;
    @Entry(min=0, max=1000) public static int slotDurabilityBarHeight = 1;
    @Entry(min=0, max=1000) public static int slotDurabilityBarBackgroundHeight = 2;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarXOffset = 0;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarYOffset = 0;
    @Entry(min=-1000, max=1000) public static int modifyTooltipLength = 3;
    @Entry(min=0, max=100, isSlider = true) public static int hideSlotDurabilityAbovePercent = 100;
    @Entry(min=-1000, max=1000) public static int tooltipLeftOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipRightOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipBottomOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipBarThickness = 1;
}
