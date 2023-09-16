package io.github.steveplays28.biomefog.config.gui.widget;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class ScrollContainerCustomWidget extends CustomWidget {
	protected int contentPositionY;

	public ScrollContainerCustomWidget(int positionX, int positionY, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.contentPositionY = 0;
	}

	@Override
	public void render(@NotNull MatrixStack matrices, int mouseX, int mouseY, float delta) {
		matrices.push();
		matrices.translate(0, contentPositionY, 0);

		super.render(matrices, mouseX, mouseY, delta);

		matrices.pop();
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		super.mouseScrolled(mouseX, mouseY, amount);

		// Add the scroll amount to contentPositionY, multiplied by a sensitivity
		// TODO: Allow configuring the sensitivity and inverted scrolling
		contentPositionY = (int) Math.round(MathHelper.clamp(contentPositionY + amount * 10, -getActualHeight(), 0d));

		return true;
	}
}
