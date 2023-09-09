package io.github.steveplays28.biomefog.config.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TextCustomWidget extends SelectableCustomWidget {
	private final TextRenderer textRenderer;
	private final Text text;
	private final int color;
	private final boolean centerTextHorizontally;

	public TextCustomWidget(int positionX, int positionY, Text text, int color, Boolean centerTextHorizontally, TextRenderer textRenderer, CustomWidget... childWidgets) {
		super(positionX, positionY, textRenderer.getWidth(text), textRenderer.fontHeight, childWidgets);

		this.textRenderer = textRenderer;
		this.text = text;
		this.color = color;
		this.centerTextHorizontally = centerTextHorizontally;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		var actualPositionX = centerTextHorizontally ? positionX - textRenderer.getWidth(text) / 2 : positionX;
		drawTextWithShadow(matrices, textRenderer, text, actualPositionX, positionY, color);
	}
}
