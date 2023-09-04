package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.BiomeFogConfigScreen;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MapOptionWidget<K, V> extends CustomWidget {
	public final int entryHorizontalSpacing;

	public MapOptionWidget(@NotNull Map<K, V> map, int positionX, int positionY, @NotNull BiomeFogConfigScreen configScreen) {
		super(positionX, positionY);
		entryHorizontalSpacing = configScreen.width / 100;

		// TODO: Add dropdown widget that encapsulates all entry widgets

		for (Map.Entry<K, V> entry : map.entrySet()) {
			var keyWidget = configScreen.addOptionWidgetByType(entry.getKey(), entry.getKey().toString(), positionX - 160, positionY);
			var valueWidget = configScreen.addOptionWidgetByType(
					entry.getValue(), entry.getKey().toString(), positionX + entryHorizontalSpacing + 160, positionY);

//			childWidgets.add(keyWidget);
//			childWidgets.add(valueWidget);
		}
	}
}
