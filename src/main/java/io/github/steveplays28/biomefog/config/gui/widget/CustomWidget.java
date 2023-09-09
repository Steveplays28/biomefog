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

	public List<CustomWidget> getChildWidgets() {
		return childWidgets;
	}

	/**
	 * @return The actual height of the widget, useful for dynamic widgets that can have changing heights, but still want proper spacing when rendering.
	 */
	public int getActualHeight() {
		return 0;
	}
}
