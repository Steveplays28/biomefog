package io.github.steveplays28.biomefog.config.gui.widget.option;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class FloatOptionWidget extends TextFieldWidget {
	public FloatOptionWidget(int positionX, int positionY, int width, int height, TextRenderer textRenderer, Text text, boolean centerHorizontally) {
		super(textRenderer, centerHorizontally ? positionX - width / 2 : positionX, positionY, width, height, text);
	}
}
