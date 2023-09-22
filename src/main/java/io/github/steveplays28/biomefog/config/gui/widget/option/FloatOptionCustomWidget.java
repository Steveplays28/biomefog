package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import java.lang.reflect.Field;

public class FloatOptionCustomWidget extends CustomWidget {
	protected final TextRenderer textRenderer;

	public FloatOptionCustomWidget(int positionX, int positionY, int width, int height, TextRenderer textRenderer, float option, Text optionName, Object configClass, Field configField, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.textRenderer = textRenderer;

		this.childWidgets.add(
				new TextCustomWidget(positionX / 2, positionY, optionName, new Color(255, 255, 255).toInt(), true, textRenderer));
		this.childWidgets.add(
				new TextFieldCustomWidget(
						positionX / 2 + width / 2, positionY, width / 2, height, String.valueOf(option), textRenderer, text -> {
					try {
						configField.set(configClass, text);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}));
	}

	@Override
	public int getActualHeight() {
		return textRenderer.fontHeight * 2;
	}
}
