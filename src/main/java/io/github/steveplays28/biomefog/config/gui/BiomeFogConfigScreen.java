package io.github.steveplays28.biomefog.config.gui;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogBaseConfig;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.config.gui.screen.CustomScreen;
import io.github.steveplays28.biomefog.config.gui.widget.BackgroundWidget;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.option.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	/**
	 * @param option    The value that should be displayed in the widget.
	 * @param positionX The X position of the widget.
	 * @param positionY The Y position of the widget.
	 */
	public static @NotNull CustomWidget getOptionCustomWidget(Object option, String optionName, int positionX, int positionY, int width, int height, TextRenderer textRenderer) {
		CustomWidget customWidget = null;

		if (option instanceof Float floatOption) {
			customWidget = new FloatOptionCustomWidget(positionX, positionY, width - 50, height, floatOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer
			);
		}

		if (option instanceof String stringOption) {
			customWidget = new StringOptionCustomWidget(positionX, positionY, width, height, stringOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer
			);
		}

		if (option instanceof Vec3d vec3dOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3dOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer
			);
		}

		if (option instanceof Vec3f vec3fOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3fOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer
			);
		}

		if (option instanceof Vec3i vec3iOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3iOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer
			);
		}

		if (option instanceof Map<?, ?> mapOption) {
			customWidget = new MapOptionCustomWidget<>(mapOption, positionX, positionY, width, height, textRenderer);
		}

		if (option instanceof List<?> listOption) {
			customWidget = new ListOptionCustomWidget<>(listOption, positionX, positionY, width, height, textRenderer);
		}

		if (customWidget == null) {
			throw new UnsupportedOperationException(
					String.format("Custom option widget couldn't be created for option of type %s", option.getClass().getName()));
		}

		return customWidget;
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
					var optionWidget = getOptionCustomWidget(option, configOption.getName(), optionPositionX, optionPositionY, width,
							textRenderer.fontHeight * 2, textRenderer
					);

					addDrawableChild(optionWidget);
					optionPositionY = getNextOptionWidgetPositionY(optionWidget, optionPositionY);
				}
			} catch (IllegalAccessException e) {
				BiomeFogClient.LOGGER.info("Failed to create {} config screen, see stacktrace below:", NAMESPACE);
				e.printStackTrace();
			}
		}
	}

	public int getNextOptionWidgetPositionY(@NotNull CustomWidget optionWidget, int positionY) {
		return positionY + optionWidget.getActualHeight() + OPTION_SPACING;
	}
}
