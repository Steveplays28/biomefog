package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.BiomeFogConfigScreen;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.LOGGER;

public class MapOptionCustomWidget<K, V> extends CustomWidget {
	public final int entryHorizontalSpacing;

	public MapOptionCustomWidget(@NotNull Map<K, V> map, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		super(positionX, positionY);
		entryHorizontalSpacing = width / 4;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		for (Map.Entry<K, V> entry : map.entrySet()) {
			LOGGER.info("{}, {}", entry.getKey(), entry.getValue());

			var keyWidget = BiomeFogConfigScreen.getOptionCustomWidget(
					entry.getKey(), entry.getKey().toString(), positionX - 160, positionY, width, height, textRenderer);
			var valueWidget = BiomeFogConfigScreen.getOptionCustomWidget(
					entry.getValue(), entry.getKey().toString(), positionX + entryHorizontalSpacing + 160, positionY, width, height,
					textRenderer
			);

			childWidgets.add(new TextCustomWidget(positionX, positionY, Text.of("e"), 0, true, textRenderer));
			childWidgets.add(valueWidget);
		}
	}
}
