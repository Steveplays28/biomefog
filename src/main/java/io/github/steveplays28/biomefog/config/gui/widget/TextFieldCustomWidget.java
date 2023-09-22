package io.github.steveplays28.biomefog.config.gui.widget;

import io.github.steveplays28.biomefog.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class TextFieldCustomWidget extends SelectableCustomWidget {
	protected static final int BORDER_RADIUS = 1;
	protected static final int CARET_WIDTH = 2;
	protected static final float CARET_HEIGHT_PERCENTAGE = 2f / 3f;
	// Background colors
	protected static final int NORMAL_BACKGROUND_COLOR = -new Color(250, 250, 250).toInt();
	// Border colors
	protected static final int NORMAL_BORDER_COLOR = -new Color(160, 160, 160).toInt();
	// White color
	protected static final int HIGHLIGHT_BORDER_COLOR = -new Color(1, 1, 1).toInt();
	protected static final int DISABLED_BORDER_COLOR = -new Color(255, 255, 255).toInt();
	// Text colors
	protected static final int NORMAL_TEXT_COLOR = new Color(255, 255, 255).toInt();

	protected final Consumer<String> onTextChanged;

	protected int backgroundColor;
	protected int borderColor;
	protected int textColor;
	protected StringBuilder text;
	protected TextRenderer textRenderer;
	protected int caretPosition;
	protected int selectionStartPosition;
	protected int selectionEndPosition;

	public TextFieldCustomWidget(int positionX, int positionY, int width, int height, String text, TextRenderer textRenderer, Consumer<String> onTextChanged, CustomWidget... childWidgets) {
		super(positionX, positionY, width, height, childWidgets);
		this.backgroundColor = NORMAL_BACKGROUND_COLOR;
		this.borderColor = NORMAL_BORDER_COLOR;
		this.textColor = NORMAL_TEXT_COLOR;
		this.text = new StringBuilder(text);
		this.textRenderer = textRenderer;
		setCaretPositionToEnd();
		clearSelection();
		this.onTextChanged = onTextChanged;
	}

	// TODO: Reimplement Minecraft's TextFieldWidget, also a good time to add validation and add type checking (generics?)
	// TODO: Set cursor to text selection cursor on hover

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

		var selectionStartPositionX = getCharPositionX(getSelectionStartPosition());
		var selectionEndPositionX = getCharPositionX(getSelectionEndPosition());

		// Render the border
		fill(matrices, positionX - width / 2 - BORDER_RADIUS, positionY - height / 2 - BORDER_RADIUS, positionX + width / 2 + BORDER_RADIUS,
				positionY + height / 2 + BORDER_RADIUS, borderColor
		);
		// Render the background
		fill(matrices, positionX - width / 2, positionY - height / 2, positionX + width / 2, positionY + height / 2, backgroundColor);

		// Render the selection indicator if there's a selection
		if (isFocused()) {
			if (hasSelection()) {
				var lastSelectedCharIndex = getSelectionEndPosition();
				if (lastSelectedCharIndex < 0) {
					lastSelectedCharIndex = 0;
				}
				var lastCharWidth = textRenderer.getWidth(Character.toString(text.charAt(lastSelectedCharIndex)));

				fill(matrices, positionX - textWidth / 2 + selectionStartPositionX,
						(int) (positionY - height / 2 * CARET_HEIGHT_PERCENTAGE),
						positionX - textWidth / 2 + selectionEndPositionX + lastCharWidth,
						(int) (positionY + height / 2 * CARET_HEIGHT_PERCENTAGE), borderColor
				);
			} else {
				// Render the caret if there's no selection
				fill(matrices, positionX - textWidth / 2 + caretCharPositionX + caretCharWidth,
						(int) (positionY - height / 2 * CARET_HEIGHT_PERCENTAGE),
						positionX - textWidth / 2 + caretCharPositionX + caretCharWidth + CARET_WIDTH,
						(int) (positionY + height / 2 * CARET_HEIGHT_PERCENTAGE), borderColor
				);
			}
		}

		// Render the text inside the text field widget
		drawCenteredTextWithShadow(matrices, textRenderer, Text.of(text.toString()).asOrderedText(), positionX + BORDER_RADIUS,
				positionY - textRenderer.fontHeight / 2 + BORDER_RADIUS, textColor
		);
	}

	@Override
	public void mouseClickedSuccessfully(double mouseX, double mouseY) {
		if (!isEnabled() || !isFocused()) return;

		borderColor = HIGHLIGHT_BORDER_COLOR;

		int distanceBetweenMouseAndCaretX = MathHelper.floor(mouseX) - getCaretPosition();
		int caretPositionPlusDistance = getCaretPosition() + distanceBetweenMouseAndCaretX;
		if (caretPositionPlusDistance >= text.length()) {
			caretPositionPlusDistance = text.length() - 1;
		}
		if (caretPositionPlusDistance <= 0) {
			caretPositionPlusDistance = 0;
		}
		var caretPosition = getCaretPosition();
		if (caretPositionPlusDistance > caretPosition) {
			caretPosition = caretPositionPlusDistance;
			caretPositionPlusDistance = getCaretPosition();
		}
		var textWidthBetweenMouseAndCaret = textRenderer.getWidth(text.substring(caretPositionPlusDistance, caretPosition));

		setCaretPosition(getCaretPosition() - textWidthBetweenMouseAndCaret);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (!isEnabled()) return false;

		borderColor = NORMAL_BORDER_COLOR;
		return true;
	}

	@Override
	public void disable() {
		super.disable();
		borderColor = DISABLED_BORDER_COLOR;
	}

	@Override
	public void enable() {
		super.enable();
		borderColor = NORMAL_BORDER_COLOR;
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		if (!isEnabled() || !isFocused()) {
			return false;
		}

		// TODO: Limit size of text and add an ellipsis
		if (hasSelection()) {
			deleteSelection();
		}

		text.insert(getCaretPosition(), chr);
		addToCaretPosition(1);

		onTextChanged.accept(text.toString());

		return true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (!isEnabled() || !isFocused()) {
			return false;
		}

		if (Screen.isSelectAll(keyCode)) {
			setCaretPositionToEnd();
			setSelectionStartPosition(0);
			setSelectionEndPosition(text.length() - 1);
			return true;
		}
		if (Screen.isCopy(keyCode) && hasSelection()) {
			MinecraftClient.getInstance().keyboard.setClipboard(getSelectedText());
			return true;
		}
		if (Screen.isPaste(keyCode)) {
			if (hasSelection()) {
				deleteSelection();
			}

			text.insert(getSelectionEndPosition(), MinecraftClient.getInstance().keyboard.getClipboard());
			setCaretPosition(getSelectionEndPosition());
			return true;
		}
		if (Screen.isCut(keyCode)) {
			if (hasSelection()) {
				MinecraftClient.getInstance().keyboard.setClipboard(getSelectedText());

				deleteSelection();
				setCaretPositionToEnd();
			}

			return true;
		}

		switch (keyCode) {
			case GLFW.GLFW_KEY_LEFT -> {
				if (Screen.hasShiftDown()) {
					if (!hasSelection()) {
						setSelectionStartPosition(getCaretPosition());
					}
				}

				if (Screen.hasControlDown()) {
					setCaretPosition(getWordSkipPosition(true));

					if (Screen.hasShiftDown()) {
						setSelectionEndPosition(getWordSkipPosition(true));
					}
				} else {
					addToCaretPosition(-1);

					if (Screen.hasShiftDown()) {
						if (getCaretPosition() < 0) {
							return false;
						}

						setSelectionEndPosition(getCaretPosition());
					} else {
						clearSelection();
					}
				}
				return true;
			}
			case GLFW.GLFW_KEY_RIGHT -> {
				if (Screen.hasShiftDown()) {
					if (!hasSelection()) {
						setSelectionStartPosition(getCaretPosition());
					}
				}

				if (Screen.hasControlDown()) {
					setCaretPosition(getWordSkipPosition(false));

					if (Screen.hasShiftDown()) {
						setSelectionEndPosition(getWordSkipPosition(false));
					}
				} else {
					addToCaretPosition(1);

					if (Screen.hasShiftDown()) {
						if (getCaretPosition() < 0) {
							addToCaretPosition(1);
						}

						var caretPosition = getCaretPosition() - 1;
						if (caretPosition < 0) {
							caretPosition = 0;
						}
						setSelectionEndPosition(caretPosition);
					} else {
						clearSelection();
					}
				}
				return true;
			}
			case GLFW.GLFW_KEY_BACKSPACE -> {
				if (hasSelection()) {
					deleteSelection();
					return true;
				}

				// TODO: Refactor into method
				if (getCaretPosition() < 1 || getCaretPosition() > text.length()) {
					return false;
				}

				if (Screen.hasControlDown()) {
					var wordSkipPosition = getWordSkipPosition(true);
					var caretPositionToAdd = wordSkipPosition - getCaretPosition();

					text.delete(getWordSkipPosition(true), getCaretPosition());
					addToCaretPosition(caretPositionToAdd);
				} else {
					text.deleteCharAt(getCaretPosition() - 1);
					addToCaretPosition(-1);
				}

				return true;
			}
			case GLFW.GLFW_KEY_DELETE -> {
				if (hasSelection()) {
					deleteSelection();
					return true;
				}

				// TODO: Refactor into method
				if (getCaretPosition() >= text.length()) {
					return false;
				}

				if (Screen.hasControlDown()) {
					text.delete(getCaretPosition(), getWordSkipPosition(false));
				} else {
					text.deleteCharAt(getCaretPosition());
				}

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
			case GLFW.GLFW_KEY_ESCAPE -> clearSelection();
		}

		return false;
	}

	public StringBuilder getText() {
		return text;
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
		setCaretPosition(text.length());
	}

	protected int getWordSkipPosition(Boolean searchBackwards) {
		var wordSkipPosition = searchBackwards ? text.lastIndexOf(" ", getCaretPosition()) : text.indexOf(" ", getCaretPosition());
		if (wordSkipPosition == getCaretPosition()) {
			wordSkipPosition = searchBackwards ? text.lastIndexOf(" ", getCaretPosition() - 1) : text.indexOf(" ", getCaretPosition() + 1);
		}

		if (wordSkipPosition == -1) {
			// Skip to the start/end of the text field
			if (searchBackwards) {
				return 0;
			} else {
				return text.length();
			}
		} else {
			return wordSkipPosition;
		}
	}

	protected int getSelectionStartPosition() {
		return selectionStartPosition;
	}

	protected void setSelectionStartPosition(int position) {
		if (position < -1) {
			return;
		}

		selectionStartPosition = position;
	}

	protected int getSelectionEndPosition() {
		return selectionEndPosition;
	}

	protected void setSelectionEndPosition(int position) {
		if (position < -1) {
			return;
		}

		selectionEndPosition = position;
	}

	protected Boolean hasSelection() {
		return getSelectionStartPosition() >= 0 && getSelectionEndPosition() >= 0;
	}

	protected String getSelectedText() {
		return text.substring(getSelectionStartPosition(), getSelectionEndPosition());
	}

	protected void clearSelection() {
		setSelectionStartPosition(-1);
		setSelectionEndPosition(-1);
	}

	protected void deleteSelection() {
		var caretEndPosition = getSelectionEndPosition() - (getSelectionEndPosition() - getSelectionStartPosition());

		if (getSelectionEndPosition() - getSelectionStartPosition() == 0) {
			text.deleteCharAt(getSelectionEndPosition());
		} else {
			text.delete(getSelectionStartPosition(), getCaretPosition());
		}

		onTextChanged.accept(text.toString());

		clearSelection();
		setCaretPosition(caretEndPosition);
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
