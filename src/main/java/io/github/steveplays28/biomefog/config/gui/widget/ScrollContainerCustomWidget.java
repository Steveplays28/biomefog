package io.github.steveplays28.biomefog.config.gui.widget;

import org.jetbrains.annotations.NotNull;

public class ScrollContainerCustomWidget extends CustomWidget {
	protected int contentPositionY;

	public ScrollContainerCustomWidget(int positionX, int positionY, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.contentPositionY = 0;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		super.mouseScrolled(mouseX, mouseY, amount);

		// Add the scroll amount to contentPositionY, multiplied by a sensitivity
		// TODO: Allow configuring the sensitivity and inverted scrolling
		positionY += amount * 10;

		scrollRecursive(this, amount);

		return true;
	}

	// TODO: Refactor into a method in CustomWidget
	protected void scrollRecursive(@NotNull CustomWidget customWidget, double amount) {
		if (customWidget.childWidgets.size() == 0) return;

		for (var childWidget : customWidget.childWidgets) {
			childWidget.positionY += amount * 10;
			scrollRecursive(childWidget, amount);
		}
	}
}
