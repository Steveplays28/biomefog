package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.BiomeFogConfigScreen;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import net.minecraft.client.font.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListOptionCustomWidget<K> extends CustomWidget {
	public final List<K> list;

	public int height = 0;

	public ListOptionCustomWidget(@NotNull List<K> list, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		super(positionX, positionY);
		this.list = list;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		for (int i = 0; i < list.size(); i++) {
			var widget = BiomeFogConfigScreen.getOptionCustomWidget(
					list.get(i), String.valueOf(i), positionX, positionY + i * textRenderer.fontHeight, width, height, textRenderer);

			height += widget.getActualHeight();
			childWidgets.add(widget);
		}
	}

	@Override
	public int getActualHeight() {
		return height;
	}
}
