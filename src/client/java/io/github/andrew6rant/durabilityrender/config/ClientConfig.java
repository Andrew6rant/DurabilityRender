package io.github.andrew6rant.durabilityrender.config;

import io.github.andrew6rant.durabilityrender.config.ConfigEnums.*;

public class ClientConfig extends MidnightConfig {
    @Comment(centered = true) public static String globalSettings;
    @Entry(width = 7, isColor = true) public static String slotDurabilityColorStartRRGGBB = "#00FF00";
    @Entry(width = 7, isColor = true) public static String slotDurabilityColorEndRRGGBB = "#FF0000";
    @Entry public static DurabilityColorEnum durabilityColor = DurabilityColorEnum.HSL_CLOCKWISE;
    @Entry public static DurabilityFillEnum durabilityFillDirection = DurabilityFillEnum.LEFT_TO_RIGHT;

    @Comment(centered = true) public static String slotSettings;

    @Entry(width = 7, isColor = true) public static String slotDurabilityBackgroundColorRRGGBB = "#000000";

    @Entry(min=0, max=100, isSlider = true) public static int slotDurabilityOpacity = 100;

    @Entry(min=0, max=100, isSlider = true) public static int slotDurabilityBackgroundOpacity = 45;

    @Entry(min=0, max=100, isSlider = true) public static int hideSlotDurabilityAbovePercent = 100;

    @Entry(min=0, max=1000) public static int slotDurabilityBarWidth = 8;
    @Entry(min=0, max=1000) public static int slotDurabilityBarHeight = 1;
    @Entry(min=0, max=1000) public static int slotDurabilityBarBackgroundHeight = 2;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarXOffset = 0;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarYOffset = 0;

    @Comment(centered = true) public static String tooltipDurabilitySettings;
    @Entry public static TooltipFormatEnum tooltipFormat = TooltipFormatEnum.PERCENT_AND_REMAINING;
    @Entry public static TooltipNameFormatEnum tooltipNameFormat = TooltipNameFormatEnum.NONE;
    @Entry(min=0, max=10) public static int percentPrecision = 1;
    @Entry(min=-1000, max=1000) public static int modifyTooltipLength = 3;

    @Entry public static TooltipSnapEnum snapToTopOrBottom = TooltipSnapEnum.BOTTOM;

    @Entry(min=-1000, max=1000) public static int tooltipLeftOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipRightOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipYOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipBarThickness = 1;
    @Comment(centered = true) public static String tooltipSettings;

    @Entry(min=-1000, max=1000) public static int changeEmptyTextHeight = 1;

}
