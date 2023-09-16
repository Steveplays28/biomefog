package io.github.steveplays28.biomefog.config.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A widget that can contain multiple other widgets as children.
 */
@Environment(EnvType.CLIENT)
public class CustomWidget extends DrawableHelper implements Drawable, Element, Selectable {
	protected int positionX;
	protected int positionY;
	protected List<CustomWidget> childWidgets = new ArrayList<>();

	public CustomWidget(int positionX, int positionY, CustomWidget... childWidgets) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.childWidgets.addAll(Arrays.asList(childWidgets));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for (var childWidget : childWidgets) {
			childWidget.render(matrices, mouseX, mouseY, delta);
		}
	}

	@Override
	public SelectionType getType() {
		// TODO: Implement focus and mouse hover detection, can be done by storing the values from render() and checking if they overlap with the widget
		return SelectionType.NONE;
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		// TODO: Implement narrations
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		var mouseClicked = false;

		for (var childWidget : getChildWidgets()) {
			if (childWidget.mouseClicked(mouseX, mouseY, button)) {
				mouseClicked = true;
			}
		}

		return mouseClicked;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		var mouseReleased = false;

		for (var childWidget : getChildWidgets()) {
			if (childWidget.mouseReleased(mouseX, mouseY, button)) {
				mouseReleased = true;
			}
		}

		return mouseReleased;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		var mouseScrolled = false;

		for (var childWidget : getChildWidgets()) {
			if (childWidget.mouseScrolled(mouseX, mouseY, amount)) {
				mouseScrolled = true;
			}
		}

		return mouseScrolled;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		var isMouseOver = false;

		for (var childWidget : getChildWidgets()) {
			if (childWidget.isMouseOver(mouseX, mouseY)) {
				isMouseOver = true;
			}
		}

		return isMouseOver;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for (var childWidget : getChildWidgets()) {
			childWidget.keyPressed(keyCode, scanCode, modifiers);
		}

		return Element.super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		for (var childWidget : getChildWidgets()) {
			childWidget.keyReleased(keyCode, scanCode, modifiers);
		}

		return Element.super.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		for (var childWidget : getChildWidgets()) {
			childWidget.charTyped(chr, modifiers);
		}

		return Element.super.charTyped(chr, modifiers);
	}

	public List<CustomWidget> getChildWidgets() {
		return childWidgets;
	}

	/**
	 * @return The actual height of the widget, useful for dynamic widgets that can have changing heights, but still want proper spacing when rendering.
	 */
	public int getActualHeight() {
		var actualHeight = 0;

		for (var childWidget : getChildWidgets()) {
			actualHeight += childWidget.getActualHeight();
		}

		return actualHeight;
	}
}
