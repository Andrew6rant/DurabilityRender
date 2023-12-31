package io.github.andrew6rant.durabilityrender.config;

public class ConfigEnums {
    public enum TooltipFormatEnum {
        DURABILITY_AND_PERCENT, PERCENT_AND_DURABILITY, REMAINING_AND_PERCENT, PERCENT_AND_REMAINING, PERCENT_ONLY, DURABILITY_ONLY, REMAINING_ONLY, NONE
    }

    public enum TooltipNameFormatEnum {
        DURABILITY_AND_PERCENT, PERCENT_AND_DURABILITY, REMAINING_AND_PERCENT, PERCENT_AND_REMAINING, PERCENT_ONLY, DURABILITY_ONLY, REMAINING_ONLY, NONE
    }

    public enum DurabilityFillEnum {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public enum DurabilityColorEnum {
        HSL_CLOCKWISE, HSL_COUNTERCLOCKWISE, RGB, RGB_INVERTED
    }
    public enum TooltipSnapEnum {
        TOP, BOTTOM
    }
}
