package io.github.steveplays28.biomefog.config.gui;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogBaseConfig;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.config.gui.screen.CustomScreen;
import io.github.steveplays28.biomefog.config.gui.widget.BackgroundWidget;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.option.FloatOptionCustomWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.NAMESPACE;

// TODO: Disable exiting the screen using the escape key when a text field is being edited and has a selection
@Environment(EnvType.CLIENT)
public class BiomeFogConfigScreen extends CustomScreen {
	public static final Text TITLE = Text.translatable("biomefog.screen.config.title");
	public static final int BAR_HEIGHT = 40;
	public static final int OPTION_HEIGHT = 20;
	public static final int OPTION_SPACING = 10;

	private final List<TextFieldWidget> textFieldWidgets = new ArrayList<>();

	public BiomeFogConfigScreen(Screen parent) {
		super(TITLE, parent);
	}

	@Override
	protected void init() {
		super.init();
		if (client == null) return;

		client.keyboard.setRepeatEvents(true);

		var optionSpacing = 20;

		// Top
		addDrawable(new BackgroundWidget(0, 0, width, BAR_HEIGHT, 0));
		addDrawable(new TextCustomWidget(width / 2, 8, TITLE, WHITE_COLOR, true, textRenderer));

		// Bottom
		addDrawable(new BackgroundWidget(0, height - BAR_HEIGHT, width, BAR_HEIGHT, 0));
		addDrawableChild(new ButtonWidget(width / 2 - 160 / 2, height - BAR_HEIGHT / 2 - 20 / 2, 160, 20,
				Text.translatable("biomefog.screen.config.cancel"), widget -> {
			BiomeFogConfigLoader.save();
			close();
		}
		));

		// Middle
		var middleWidth = client.world == null ? width : width / 2;
		addDrawable(new BackgroundWidget(0, BAR_HEIGHT, middleWidth, height - BAR_HEIGHT * 2, 0, 0.5f, 0.5f, 0.5f));

		var configurations = BiomeFogConfigLoader.BiomeFogConfigurations.class.getFields();
		var optionPositionX = width / 2;
		var optionPositionY = BAR_HEIGHT + optionSpacing;

		for (Field configuration : configurations) {
			try {
				var config = configuration.get(BiomeFogBaseConfig.class);
				var configOptions = config.getClass().getFields();

				for (Field configOption : configOptions) {
					var option = configOption.get(config);
					var optionWidget = addOptionWidgetByType(option, optionPositionX, optionPositionY);
					if (optionWidget != null) {
						optionPositionY = getNextOptionWidgetPositionY(optionPositionY);

						return;
					}
				}
			} catch (IllegalAccessException e) {
				BiomeFogClient.LOGGER.info("Failed to create {} config screen, see stacktrace below:", NAMESPACE);
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void clearAndInit() {
		textFieldWidgets.clear();
		super.clearAndInit();
	}

	@Override
	protected void clearChildren() {
		textFieldWidgets.clear();
		super.clearChildren();
	}

	/**
	 * @param option    The value that should be displayed in the widget.
	 * @param positionX The X position of the widget.
	 * @param positionY The Y position of the widget.
	 */
	public @Nullable CustomWidget addOptionWidgetByType(Object option, int positionX, int positionY) {
		CustomWidget customWidget = null;

		if (option instanceof Float floatOption) {
			customWidget = new FloatOptionCustomWidget(
					positionX, positionY, width - 50, textRenderer.fontHeight * 2, floatOption, Text.translatable("biomefog.screen.config.floatoptioncustomwidget"), textRenderer);
		}

//		if (option instanceof Map<?, ?> mapOption) {
//			customWidget = new MapOptionWidget<>(mapOption, positionX, positionY, this);
//		}

		if (customWidget != null) {
			addDrawableChild(customWidget);
		}

		return customWidget;
	}

	public int getNextOptionWidgetPositionY(int positionY) {
		return positionY + OPTION_HEIGHT + OPTION_SPACING;
	}
}
