package io.github.steveplays28.biomefog.config.gui.widget;

import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TextFieldCustomWidget extends SelectableCustomWidget {
	protected static final int BORDER_RADIUS = 1;
	protected static final int CARET_WIDTH = 5;
	// Background colors
	protected static final int NORMAL_BACKGROUND_COLOR = -new Color(100, 100, 100).toInt();
	// Border colors
	protected static final int NORMAL_BORDER_COLOR = -new Color(160, 160, 160).toInt();
	// White color
	protected static final int HIGHLIGHT_BORDER_COLOR = -new Color(1, 1, 1).toInt();
	protected static final int DISABLED_BORDER_COLOR = -new Color(255, 255, 255).toInt();
	// Text colors
	protected static final int NORMAL_TEXT_COLOR = new Color(255, 255, 255).toInt();

	protected int backgroundColor;
	protected int borderColor;
	protected int textColor;
	protected StringBuilder text;
	protected TextRenderer textRenderer;
	protected int caretPosition;
	protected int selectionStartPosition;
	protected int selectionEndPosition;

	public TextFieldCustomWidget(int positionX, int positionY, int width, int height, String text, TextRenderer textRenderer, CustomWidget... childWidgets) {
		super(positionX, positionY, width, height, childWidgets);
		this.backgroundColor = NORMAL_BACKGROUND_COLOR;
		this.borderColor = NORMAL_BORDER_COLOR;
		this.textColor = NORMAL_TEXT_COLOR;
		this.text = new StringBuilder(text);
		this.textRenderer = textRenderer;
		setCaretPositionToEnd();
		this.selectionStartPosition = 0;
		this.selectionEndPosition = 0;
	}

	// TODO: Reimplement Minecraft's TextFieldWidget, also a good time to add validation and add type checking (generics?)

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		var textWidth = textRenderer.getWidth(text.toString());
		var caretCharPositionX = getCharPositionX(getCaretPosition() - 1);
		var caretCharWidthTotal = getCharWidthTotal(getCaretPosition() - 1);
		var caretCharWidth = caretCharWidthTotal - caretCharPositionX;

		// Place caret on the left of the leftmost character if needed
		if (getCaretPosition() <= 0) {
			caretCharPositionX = -caretCharPositionX;
			caretCharWidth = caretCharWidthTotal + caretCharPositionX;
		}

		// Render the background, BORDER_RADIUS pixels in so there's room for the border
		fill(matrices, positionX - width / 2 - BORDER_RADIUS, positionY - height / 2 - BORDER_RADIUS, positionX + width / 2 + BORDER_RADIUS,
				positionY + height / 2 + BORDER_RADIUS, backgroundColor
		);
		// Render the border
		fill(matrices, positionX - width / 2, positionY - height / 2, positionX + width / 2, positionY + height / 2, borderColor);
		// Render the caret
		fill(matrices, positionX - textWidth / 2 + caretCharPositionX + caretCharWidth, positionY - height / 2,
				positionX - textWidth / 2 + caretCharPositionX + caretCharWidth + CARET_WIDTH, positionY + height / 2, backgroundColor
		);
		// Render the text inside the text field widget
		drawCenteredTextWithShadow(matrices, textRenderer, Text.of(text.toString()).asOrderedText(), positionX + BORDER_RADIUS,
				positionY - textRenderer.fontHeight / 2 + BORDER_RADIUS, textColor
		);
	}

	@Override
	public void mouseClickedSuccessfully(double mouseX, double mouseY) {
		if (!isEnabled) return;

		backgroundColor = HIGHLIGHT_BORDER_COLOR;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (!isEnabled) return false;

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
		// TODO: Limit size of text and add an ellipsis
		text.append(chr);
		return true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (!isEnabled) {
			return false;
		}

		if (Screen.isSelectAll(keyCode)) {
			setCaretPositionToEnd();
			setSelectionStartPosition(0);
			setSelectionEndPosition(text.length());
			return true;
		}
		if (Screen.isCopy(keyCode) && hasSelection()) {
			MinecraftClient.getInstance().keyboard.setClipboard(getSelectedText());
			return true;
		}
		if (Screen.isPaste(keyCode)) {
			if (hasSelection()) {
				text.setLength(0);
			}

			text.append(MinecraftClient.getInstance().keyboard.getClipboard());
			return true;
		}
		if (Screen.isCut(keyCode)) {
			if (hasSelection()) {
				text.setLength(0);
			}

			MinecraftClient.getInstance().keyboard.setClipboard(getSelectedText());
			return true;
		}

		switch (keyCode) {
			case GLFW.GLFW_KEY_LEFT -> {
				if (Screen.hasControlDown()) {
					setCaretPosition(getWordSkipPosition(true));
				} else {
					addToCaretPosition(-1);
				}
				return true;
			}
			case GLFW.GLFW_KEY_RIGHT -> {
				if (Screen.hasControlDown()) {
					setCaretPosition(getWordSkipPosition(false));
				} else {
					addToCaretPosition(1);
				}
				return true;
			}
			case GLFW.GLFW_KEY_BACKSPACE -> {
				// TODO: Refactor into method
				if (getCaretPosition() < 1 || getCaretPosition() > text.length()) {
					return false;
				}

				text.deleteCharAt(getCaretPosition() - 1);
				addToCaretPosition(-1);
				return true;
			}
			case GLFW.GLFW_KEY_DELETE -> {
				// TODO: Refactor into method
				if (getCaretPosition() >= text.length()) {
					return false;
				}

				text.deleteCharAt(getCaretPosition());
				return true;
			}
			case GLFW.GLFW_KEY_HOME -> {
				setCaretPositionToStart();
				return true;
			}
			case GLFW.GLFW_KEY_END -> {
				setCaretPositionToEnd();
				return true;
			}
		}

		return false;
	}

	protected int getCaretPosition() {
		return caretPosition;
	}

	protected void setCaretPosition(int position) {
		if (position < 0 || position > text.length()) {
			return;
		}

		caretPosition = position;
	}

	protected void addToCaretPosition(int positionToAdd) {
		if (getCaretPosition() + positionToAdd > text.length() || getCaretPosition() + positionToAdd < 0) {
			return;
		}

		setCaretPosition(getCaretPosition() + positionToAdd);
	}

	protected void setCaretPositionToStart() {
		setCaretPosition(0);
	}

	protected void setCaretPositionToEnd() {
		setCaretPosition(text.length() + 1);
	}

	protected int getWordSkipPosition(Boolean previous) {
		var wordSkipPosition = previous ? text.lastIndexOf(" ", getCaretPosition()) : text.indexOf(" ", getCaretPosition());

		if (wordSkipPosition == -1) {
			return -1;
		} else {
			return wordSkipPosition - 1;
		}
	}

	protected int getSelectionStartPosition() {
		return selectionStartPosition;
	}

	protected void setSelectionStartPosition(int position) {
		if (position < 0 && position != -1) {
			return;
		}

		selectionStartPosition = position;
	}

	protected int getSelectionEndPosition() {
		return selectionEndPosition;
	}

	protected void setSelectionEndPosition(int position) {
		if (position < 0 && position != -1) {
			return;
		}

		selectionEndPosition = position;
	}

	protected Boolean hasSelection() {
		return getSelectionStartPosition() == -1 && getSelectionEndPosition() == -1;
	}

	protected String getSelectedText() {
		return text.substring(getSelectionStartPosition(), getSelectionEndPosition());
	}

	protected int getCharPositionX(int charIndex) {
		if (text.length() <= 0) {
			return 0;
		}
		if (charIndex < 0) {
			charIndex = 1;
		}
		if (charIndex >= text.length()) {
			return text.length() - 1;
		}

		return textRenderer.getWidth(text.substring(0, charIndex));
	}

	protected int getCharWidthTotal(int charIndex) {
		if (text.length() <= 0) {
			return 0;
		}
		if (charIndex < 0) {
			charIndex = 1;
		}
		if (charIndex >= text.length()) {
			return text.length() - 1;
		}

		return getCharPositionX(charIndex) + textRenderer.getWidth(String.valueOf(text.charAt(charIndex)));
	}
}
