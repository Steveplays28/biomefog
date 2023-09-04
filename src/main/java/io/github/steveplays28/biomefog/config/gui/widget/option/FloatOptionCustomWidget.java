package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class FloatOptionCustomWidget extends CustomWidget {
	public FloatOptionCustomWidget(int positionX, int positionY, int width, int height, float option, Text optionName, TextRenderer textRenderer, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);

		this.childWidgets.add(
				new TextCustomWidget(positionX / 2, positionY, optionName, new Color(255, 255, 255).toInt(), true, textRenderer));
		this.childWidgets.add(
				new TextFieldCustomWidget(positionX / 2 + width / 2, positionY, width / 2, height, String.valueOf(option), textRenderer));
	}
}
