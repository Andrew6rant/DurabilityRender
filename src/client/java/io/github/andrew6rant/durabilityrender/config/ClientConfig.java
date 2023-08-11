package io.github.andrew6rant.durabilityrender.config;

public class ClientConfig extends MidnightConfig {
    @Entry public static int slotDurabilityBarWidth = 8;
    @Entry public static int slotDurabilityBarHeight = 1;
    @Entry public static int slotDurabilityBarBackgroundHeight = 2;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarXOffset = 0;
    @Entry(min=-1000, max=1000) public static int slotDurabilityBarYOffset = 0;
    @Entry public static int modifyTooltipLength = 3;
    @Entry(min=0, max=100, isSlider = true) public static int hideSlotDurabilityAbovePercent = 100;
    @Entry(min=-1000, max=1000) public static int tooltipLeftOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipRightOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipBottomOffset = 3;
    @Entry(min=-1000, max=1000) public static int tooltipBarThickness = 1;
}
