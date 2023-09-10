package io.github.steveplays28.biomefog.config.gui.widget;

import org.lwjgl.glfw.GLFW;

public class SelectableCustomWidget extends CustomWidget {
	protected int width;
	protected int height;
	protected Boolean isEnabled = true;
	protected Boolean isFocused = false;

	public SelectableCustomWidget(int positionX, int positionY, int width, int height, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.width = width;
		this.height = height;
	}

	public SelectableCustomWidget(int positionX, int positionY, int width, int height, Boolean isFocused, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.width = width;
		this.height = height;
		this.isFocused = isFocused;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (isMouseOver(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
			mouseClickedSuccessfully(mouseX, mouseY);
		}

		return true;
	}

	public void mouseClickedSuccessfully(double mouseX, double mouseY) {}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (isMouseOver(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
			mouseReleasedSuccessfully(mouseX, mouseY);
		}

		return true;
	}

	public void mouseReleasedSuccessfully(double mouseX, double mouseY) {}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		// The pivot point of selectable custom widgets is in the center
		return mouseX >= positionX - (double) width / 2 && mouseX < positionX + (double) width / 2 && mouseY >= positionY - (double) height / 2 && mouseY < positionY + (double) height / 2;
	}

	public Boolean isEnabled() {
		return isEnabled;
	}

	public void disable() {
		isEnabled = false;
	}

	public void enable() {
		isEnabled = true;
	}
}
