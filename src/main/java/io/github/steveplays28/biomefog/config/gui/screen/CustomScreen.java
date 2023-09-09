package io.github.steveplays28.biomefog.config.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
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
	protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
//		if (drawableElement instanceof CustomWidget widget) {
//			for (var childWidget : widget.getChildWidgets()) {
//				addDrawableChild(childWidget);
//			}
//		}

		return super.addDrawableChild(drawableElement);
	}

	@Override
	public void resize(MinecraftClient client, int width, int height) {
		// Fixes issues with the positions and sizes of widgets not updating
		clearAndInit();
		super.resize(client, width, height);
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
