package io.github.steveplays28.biomefog.config.gui.widget;

import net.minecraft.client.util.math.MatrixStack;
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

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
			mouseClickedSuccessfully(mouseX, mouseY);
			return true;
		}

		return false;
	}

	public void mouseClickedSuccessfully(double mouseX, double mouseY) { }

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
			mouseReleasedSuccessfully(mouseX, mouseY);
			return true;
		}

		return false;
	}

	public void mouseReleasedSuccessfully(double mouseX, double mouseY) { }

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX >= (double) this.positionX && mouseX < (double) (this.positionX + this.width) && mouseY >= (double) this.positionY && mouseY < (double) (this.positionY + this.height);
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
