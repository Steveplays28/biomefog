package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.LOGGER;

public class MapOptionCustomWidget<K, V> extends CustomWidget {
	protected final Map<K, V> map;
	protected final TextRenderer textRenderer;

	@SuppressWarnings("unchecked")
	public MapOptionCustomWidget(@NotNull Map<K, V> map, Text mapName, Field configField, int positionX, int positionY, int width, int height, Object configClass, TextRenderer textRenderer) {
		super(positionX, positionY);
		this.map = map;
		this.textRenderer = textRenderer;

		var horizontalPadding = width / 10;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		childWidgets.add(new TextCustomWidget(positionX, positionY, mapName, new Color(255, 255, 255).toInt(), true, textRenderer));

		var index = 1;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			var entryPositionY = positionY + textRenderer.fontHeight * 4 * index;
			var keyWidget = new TextFieldCustomWidget(positionX - width / 2 + horizontalPadding, entryPositionY,
					width / 2 - horizontalPadding * 2, height, entry.getKey().toString(), textRenderer, key -> {
				map.put((K) key, map.get(key));
				try {
					configField.set(configClass, map);
				} catch (IllegalAccessException e) {
					LOGGER.error("Error while creating {}: {}", this.getClass().getName(), e.getStackTrace());
				}
			}
			);
			var valueWidget = new TextFieldCustomWidget(positionX + width / 2 + horizontalPadding, entryPositionY,
					width / 2 - horizontalPadding * 2, height, entry.getValue().toString(), textRenderer, value -> {
				map.put((K) keyWidget.getText(), (V) value);
				try {
					configField.set(configClass, map);
				} catch (IllegalAccessException e) {
					LOGGER.error("Error while creating {}: {}", this.getClass().getName(), e.getStackTrace());
				}
			}
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
