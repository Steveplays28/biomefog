package io.github.steveplays28.biomefog.config.gui.screen;

import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CustomScreen extends Screen {
	public static final int WHITE_COLOR = 16777215;

	public final Text title;
	public final Screen parent;

	protected CustomScreen(Text title, Screen parent) {
		super(title);
		this.title = title;
		this.parent = parent;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (Element element : this.children()) {
			element.mouseClicked(mouseX, mouseY, button);

			if (element instanceof CustomWidget customWidget) {
				if (customWidget.isMouseOver(mouseX, mouseY)) {
					this.setFocused(customWidget);

					if (button == 0) {
						this.setDragging(true);
					}
				}
			}
		}

		return true;
	}

	@Override
	public void close() {
		if (client == null) return;

		client.setScreen(parent);
	}

	public Text getTitle() {
		return title;
	}
}
