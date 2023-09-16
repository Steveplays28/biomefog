package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MapOptionCustomWidget<K, V> extends CustomWidget {
	protected final Map<K, V> map;
	protected final TextRenderer textRenderer;

	public MapOptionCustomWidget(@NotNull Map<K, V> map, Text mapName, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		super(positionX, positionY);
		this.map = map;
		this.textRenderer = textRenderer;

		var horizontalPadding = width / 100;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		childWidgets.add(new TextCustomWidget(positionX, positionY, mapName, new Color(255, 255, 255).toInt(), true, textRenderer));

		var index = 1;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			var entryPositionY = positionY + textRenderer.fontHeight * 4 * index;
			var keyWidget = new TextFieldCustomWidget(positionX - width / 2 + horizontalPadding, entryPositionY,
					width / 2 - horizontalPadding * 2, height, entry.getKey().toString(), textRenderer
			);
			var valueWidget = new TextFieldCustomWidget(positionX + width / 2 + horizontalPadding, entryPositionY,
					width / 2 - horizontalPadding * 2, height, entry.getValue().toString(), textRenderer
			);

			childWidgets.add(keyWidget);
			childWidgets.add(valueWidget);

			index++;
		}
	}

	@Override
	public int getActualHeight() {
		return textRenderer.fontHeight * 5 * map.size();
	}
}
