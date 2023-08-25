package io.github.andrew6rant.durabilityrender;

import io.github.andrew6rant.durabilityrender.config.ClientConfig;
import io.github.andrew6rant.durabilityrender.config.ConfigEnums;
import io.github.andrew6rant.durabilityrender.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.github.andrew6rant.durabilityrender.config.ClientConfig.*;

public class DurabilityRenderClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MidnightConfig.init("durabilityrender", ClientConfig.class);

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, textList) -> {
			if (tooltipFormat != ConfigEnums.TooltipFormatEnum.NONE) {
				if (!tooltipContext.isAdvanced() && itemStack.isDamageable()) {
					int maxDamage = itemStack.getMaxDamage();
					int remainingDurability = maxDamage - itemStack.getDamage();
					double percentDurability = ((double)remainingDurability/(double)maxDamage) * 100.0D;
					Number roundedPercent = Util.round(percentDurability, percentPrecision);
					switch (tooltipFormat) {
						case PERCENT_ONLY -> textList.add(Text.translatable("durabilityrender.tooltip.percent_only", roundedPercent).formatted(Formatting.DARK_GRAY));
						case DURABILITY_ONLY -> textList.add(Text.translatable("durabilityrender.tooltip.durability_only", remainingDurability, maxDamage).formatted(Formatting.DARK_GRAY));
						case REMAINING_ONLY -> textList.add(Text.translatable("durabilityrender.tooltip.remaining_only", remainingDurability).formatted(Formatting.DARK_GRAY));
						case DURABILITY_AND_PERCENT -> textList.add(Text.translatable("durabilityrender.tooltip.durability_and_percent", remainingDurability, maxDamage, roundedPercent).formatted(Formatting.DARK_GRAY));
						case PERCENT_AND_DURABILITY -> textList.add(Text.translatable("durabilityrender.tooltip.percent_and_durability", roundedPercent, remainingDurability, maxDamage).formatted(Formatting.DARK_GRAY));
						case PERCENT_AND_REMAINING -> textList.add(Text.translatable("durabilityrender.tooltip.percent_and_remaining", roundedPercent, remainingDurability).formatted(Formatting.DARK_GRAY));
						case REMAINING_AND_PERCENT -> textList.add(Text.translatable("durabilityrender.tooltip.remaining_and_percent", remainingDurability, roundedPercent).formatted(Formatting.DARK_GRAY));
					}
				}
			}
		});
	}
}