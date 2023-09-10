package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.ConfigCustomScreen;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import net.minecraft.client.font.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListOptionCustomWidget<K> extends CustomWidget {
	protected final List<K> list;
	protected final TextRenderer textRenderer;

	public ListOptionCustomWidget(@NotNull List<K> list, String listName, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		super(positionX, positionY);
		this.list = list;
		this.textRenderer = textRenderer;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		for (int i = 0; i < list.size(); i++) {
			var widget = ConfigCustomScreen.getOptionCustomWidget(list.get(i), String.format("%s.%s", listName, i), positionX,
					positionY + i * textRenderer.fontHeight * 3, width, height, textRenderer
			);

			childWidgets.add(widget);
		}
	}

	@Override
	public int getActualHeight() {
		return textRenderer.fontHeight * 3 * list.size();
	}
}
