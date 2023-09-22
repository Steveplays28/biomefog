package io.github.steveplays28.biomefog.config.gui.widget.option;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextFieldCustomWidget;
import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Vector3OptionCustomWidget extends CustomWidget {
	protected final TextRenderer textRenderer;

	public Vector3OptionCustomWidget(int positionX, int positionY, int width, int height, @NotNull Vec3d option, Text optionName, TextRenderer textRenderer, Object configClass, Field configField, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.textRenderer = textRenderer;

		this.childWidgets.add(
				new TextCustomWidget(positionX / 2, positionY, optionName, new Color(255, 255, 255).toInt(), true, textRenderer));
		this.childWidgets.add(new TextFieldCustomWidget(positionX / 2 + width / 2, positionY, width / 2, height,
				String.format("%s, %s, %s", option.getX(), option.getY(), option.getZ()), textRenderer, text -> {
			try {
				configField.set(configClass, text);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		));
	}

	public Vector3OptionCustomWidget(int positionX, int positionY, int width, int height, @NotNull Vec3f option, Text optionName, TextRenderer textRenderer, Object configClass, Field configField, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.textRenderer = textRenderer;

		this.childWidgets.add(
				new TextCustomWidget(positionX / 2, positionY, optionName, new Color(255, 255, 255).toInt(), true, textRenderer));
		this.childWidgets.add(new TextFieldCustomWidget(positionX / 2 + width / 2, positionY, width / 2, height,
				String.format("%s, %s, %s", option.getX(), option.getY(), option.getZ()), textRenderer, text -> {
			try {
				configField.set(configClass, text);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		));
	}

	public Vector3OptionCustomWidget(int positionX, int positionY, int width, int height, @NotNull Vec3i option, Text optionName, TextRenderer textRenderer, Object configClass, Field configField, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.textRenderer = textRenderer;

		this.childWidgets.add(
				new TextCustomWidget(positionX / 2, positionY, optionName, new Color(255, 255, 255).toInt(), true, textRenderer));
		this.childWidgets.add(new TextFieldCustomWidget(positionX / 2 + width / 2, positionY, width / 2, height,
				String.format("%s, %s, %s", option.getX(), option.getY(), option.getZ()), textRenderer, text -> {
			try {
				configField.set(configClass, text);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		));
	}

	@Override
	public int getActualHeight() {
		return textRenderer.fontHeight * 2;
	}
}
