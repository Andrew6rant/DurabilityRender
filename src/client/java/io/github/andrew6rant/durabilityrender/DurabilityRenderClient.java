package io.github.andrew6rant.durabilityrender;

import io.github.andrew6rant.durabilityrender.config.ClientConfig;
import io.github.andrew6rant.durabilityrender.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DurabilityRenderClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MidnightConfig.init("durabilityrender", ClientConfig.class);

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, textList) -> {
			if (!tooltipContext.isAdvanced() && itemStack.isDamageable()) {
				int maxDamage = itemStack.getMaxDamage();
				int durability = maxDamage - itemStack.getDamage();
				textList.add(Text.translatable("tooltip.durability", durability, maxDamage, (int)Math.floor(((float)durability / (float)maxDamage) * 100)).formatted(Formatting.DARK_GRAY));
			}
		});
	}
}