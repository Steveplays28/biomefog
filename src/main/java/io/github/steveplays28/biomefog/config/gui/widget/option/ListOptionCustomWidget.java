package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import net.minecraft.client.font.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.LOGGER;

public class ListOptionCustomWidget<K> extends CustomWidget {
	protected final List<K> list;
	protected final TextRenderer textRenderer;

	@SuppressWarnings("unchecked")
	public ListOptionCustomWidget(@NotNull List<K> list, String listName, Object configClass, Field configField, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		super(positionX, positionY);
		this.list = list;
		this.textRenderer = textRenderer;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		for (int i = 0; i < list.size(); i++) {
			var widget = new TextFieldCustomWidget(positionX, positionY + i * textRenderer.fontHeight * 3, width, height,
					String.format("%s.%s", listName, i), textRenderer, value -> {
				try {
					list.add((K) value);
					configField.set(configClass, list);
				} catch (IllegalAccessException e) {
					LOGGER.error("Error while creating {}: {}", this.getClass().getName(), e.getStackTrace());
				}
			}
			);

			childWidgets.add(widget);
		}
	}

	@Override
	public int getActualHeight() {
		return textRenderer.fontHeight * 3 * list.size();
	}
}
