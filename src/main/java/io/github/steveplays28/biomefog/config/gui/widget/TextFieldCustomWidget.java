package io.github.steveplays28.biomefog.config.gui.widget;

import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextFieldCustomWidget extends SelectableCustomWidget {
	protected static final int BORDER_RADIUS = 1;
	// Background colors
	protected static final int NORMAL_BACKGROUND_COLOR = -new Color(100, 100, 100).toInt();
	// Border colors
	protected static final int NORMAL_BORDER_COLOR = -new Color(100, 100, 100).toInt();
	protected static final int HIGHLIGHT_BORDER_COLOR = -new Color(100, 100, 100).toInt();
	protected static final int DISABLED_BORDER_COLOR = -new Color(255, 255, 255).toInt();
	// Text colors
	protected static final int NORMAL_TEXT_COLOR = new Color(255, 255, 255).toInt();

	protected int backgroundColor;
	protected int borderColor;
	protected int textColor;
	protected StringBuilder text;
	protected TextRenderer textRenderer;

	public TextFieldCustomWidget(int positionX, int positionY, int width, int height, String text, TextRenderer textRenderer, CustomWidget... childWidgets) {
		super(positionX, positionY, width, height, childWidgets);
		this.backgroundColor = NORMAL_BACKGROUND_COLOR;
		this.borderColor = NORMAL_BORDER_COLOR;
		this.textColor = NORMAL_TEXT_COLOR;
		this.text = new StringBuilder(text);
		this.textRenderer = textRenderer;
	}

	public TextFieldCustomWidget(int positionX, int positionY, int width, int height, String text, Boolean isFocused, TextRenderer textRenderer, CustomWidget... childWidgets) {
		super(positionX, positionY, width, height, childWidgets);
		this.backgroundColor = NORMAL_BACKGROUND_COLOR;
		this.borderColor = NORMAL_BORDER_COLOR;
		this.textColor = NORMAL_TEXT_COLOR;
		this.text = new StringBuilder(text);
		this.isFocused = isFocused;
		this.textRenderer = textRenderer;
	}

	// TODO: Reimplement Minecraft's TextFieldWidget, also a good time to add validation and add type checking (generics?)

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		// Render the border
		fill(matrices, positionX, positionY, positionX + width, positionY + height, borderColor);
		// Render the background, BORDER_RADIUS pixels in so there's room for the border
		fill(matrices, positionX + BORDER_RADIUS, positionY + BORDER_RADIUS, positionX + width - BORDER_RADIUS,
				positionY + height - BORDER_RADIUS, backgroundColor
		);
		// Render the text inside the text field widget
		drawCenteredTextWithShadow(matrices, textRenderer, Text.of(text.toString()).asOrderedText(), positionX + BORDER_RADIUS,
				positionY + BORDER_RADIUS, textColor
		);
	}

	@Override
	public void mouseClickedSuccessfully(double mouseX, double mouseY) {
		if (!isEnabled) return;

		backgroundColor = HIGHLIGHT_BORDER_COLOR;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (!isEnabled) return true;

		backgroundColor = NORMAL_BACKGROUND_COLOR;
		return true;
	}

	@Override
	public void disable() {
		super.disable();
		backgroundColor = DISABLED_BORDER_COLOR;
	}

	@Override
	public void enable() {
		super.enable();
		backgroundColor = NORMAL_BACKGROUND_COLOR;
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		text.append(chr);
		return true;
	}
}
