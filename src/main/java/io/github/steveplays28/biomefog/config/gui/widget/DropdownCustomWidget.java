package io.github.steveplays28.biomefog.config.gui.widget;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class DropdownCustomWidget extends CustomWidget {
	public static final Text DROPDOWN_TEXT = Text.translatable("stevesguilibrary.dropdown");

	public Boolean isDroppedDown = false;

	protected int widthX;
	protected int widthY;

	public DropdownCustomWidget(int positionX, int positionY, int widthX, int widthY, CustomWidget... childWidgets) {
		super(positionX, positionY, childWidgets);
		this.widthX = widthX;
		this.widthY = widthY;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (isDroppedDown) {
			super.render(matrices, mouseX, mouseY, delta);
		}
	}

	protected void toggleDropdown() {
		isDroppedDown = !isDroppedDown;
	}
}
