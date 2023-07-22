package io.github.steveplays28.biomefog.config.gui;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogBaseConfig;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.config.gui.widget.BackgroundWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextWidget;
import io.github.steveplays28.biomefog.config.gui.widget.option.FloatOptionWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.NAMESPACE;

@Environment(EnvType.CLIENT)
public class BiomeFogConfigScreen extends Screen {
	public static final int WHITE_COLOR = 16777215;
	public static final Text TITLE = Text.translatable("biomefog.screen.config.title");

	public final Screen parent;

	public BiomeFogConfigScreen(Screen parent) {
		super(TITLE);
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();
		if (client == null) return;

		var barHeight = 64;

		// Top
		addDrawable(new BackgroundWidget(0, 0, width, barHeight, 0));
		addDrawable(new TextWidget(width / 2, 8, textRenderer, TITLE, WHITE_COLOR, true));

		// Bottom
		addDrawable(new BackgroundWidget(0, height - barHeight, width, barHeight, 0));
		addDrawableChild(new ButtonWidget(width / 2 - 160 / 2, height - barHeight / 2 - 20 / 2, 160, 20,
				Text.translatable("biomefog.screen.config.cancel"), widget -> {
			BiomeFogConfigLoader.save();
			close();
		}
		));

		// Middle
		var middleWidth = client.world == null ? width : width / 2;
		addDrawable(new BackgroundWidget(0, barHeight, middleWidth, height - barHeight * 2, 0, 0.5f, 0.5f, 0.5f));

		var configurations = BiomeFogConfigLoader.BiomeFogConfigurations.class.getFields();

		for (int i = 0; i < configurations.length; i++) {
			try {
				var config = configurations[i].get(BiomeFogBaseConfig.class);
				var configOptions = config.getClass().getFields();

				for (int j = 0; j < configOptions.length; j++) {
					var option = configOptions[j].get(config);
					addOptionWidgetByType(option);
				}
			} catch (IllegalAccessException e) {
				BiomeFogClient.LOGGER.info("Failed to create {} config screen, see stacktrace below:", NAMESPACE);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void close() {
		if (client == null) return;

		client.setScreen(parent);
	}

	private void addOptionWidgetByType(Object option) {
		if (option instanceof Float castedOption) {
			addDrawableChild(
					new FloatOptionWidget(width / 2, height / 2, 160, 20, textRenderer, Text.literal(castedOption.toString()), true));
			BiomeFogClient.LOGGER.info(castedOption.toString());
		}
	}
}
