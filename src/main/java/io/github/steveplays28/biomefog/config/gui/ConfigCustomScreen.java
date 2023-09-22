package io.github.steveplays28.biomefog.config.gui;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogBaseConfig;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.config.gui.screen.CustomScreen;
import io.github.steveplays28.biomefog.config.gui.widget.BackgroundWidget;
import io.github.steveplays28.biomefog.config.gui.widget.CustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.ScrollContainerCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextCustomWidget;
import io.github.steveplays28.biomefog.config.gui.widget.option.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.MOD_NAMESPACE;

// TODO: Disable exiting the screen using the escape key when a text field is being edited and has a selection
@Environment(EnvType.CLIENT)
public class ConfigCustomScreen extends CustomScreen {
	public static final String TITLE_FORMAT = "%s.screen.config.title";
	public static final int BAR_HEIGHT = 40;
	public static final int OPTION_SPACING = 10;

	public final String MOD_ID;
	public final Text TITLE;

	public ConfigCustomScreen(Screen parent, String modId) {
		super(Text.translatable(String.format(TITLE_FORMAT, modId)), parent);
		this.MOD_ID = modId;
		this.TITLE = Text.translatable(String.format(TITLE_FORMAT, modId));
	}

	/**
	 * @param option    The value that should be displayed in the widget.
	 * @param positionX The X position of the widget.
	 * @param positionY The Y position of the widget.
	 */
	public static @NotNull CustomWidget getOptionCustomWidget(int positionX, int positionY, int width, int height, TextRenderer textRenderer, Object configClass, Object option, String optionName, Field configField) {
		CustomWidget customWidget = null;

		if (option instanceof Float floatOption) {
			customWidget = new FloatOptionCustomWidget(positionX, positionY, width - 50, height, textRenderer, floatOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), configClass, configField
			);
		}

		if (option instanceof String stringOption) {
			customWidget = new StringOptionCustomWidget(positionX, positionY, width, height, stringOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer, configClass, configField
			);
		}

		if (option instanceof Vec3d vec3dOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3dOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer, configClass, configField
			);
		}

		if (option instanceof Vec3f vec3fOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3fOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer, configClass, configField
			);
		}

		if (option instanceof Vec3i vec3iOption) {
			customWidget = new Vector3OptionCustomWidget(positionX, positionY, width, height, vec3iOption,
					Text.translatable(String.format("biomefog.screen.config.%s", optionName)), textRenderer, configClass, configField
			);
		}

		if (option instanceof Map<?, ?> mapOption) {
			customWidget = new MapOptionCustomWidget<>(mapOption, Text.translatable(String.format("biomefog.screen.config.%s", optionName)),
					configField, positionX, positionY, width, height, configClass, textRenderer
			);
		}

		if (option instanceof List<?> listOption) {
			customWidget = new ListOptionCustomWidget<>(
					listOption, optionName, configClass, configField, positionX, positionY, width, height, textRenderer);
		}

		if (customWidget == null) {
			throw new UnsupportedOperationException(
					String.format("Custom option widget couldn't be created for option of type %s (%s)", option.getClass().getName(),
							optionName
					));
		}

		return customWidget;
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
				Text.translatable("biomefog.screen.config.cancel"), widget -> close()
		));
		addDrawableChild(new ButtonWidget(width / 2 + 160 / 2, height - BAR_HEIGHT / 2 - 20 / 2, 160, 20,
				Text.translatable("biomefog.screen.config.done"), widget -> {
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
		var scrollContainerChildWidgets = new ArrayList<CustomWidget>();

		for (Field configuration : configurations) {
			try {
				var config = configuration.get(BiomeFogBaseConfig.class);
				var configOptions = config.getClass().getFields();

				for (Field configOption : configOptions) {
					var option = configOption.get(config);
					var optionWidget = getOptionCustomWidget(optionPositionX, optionPositionY, width, textRenderer.fontHeight * 2,
							textRenderer, config, option, configOption.getName(), configOption
					);

					scrollContainerChildWidgets.add(optionWidget);
					optionPositionY = getNextOptionWidgetPositionY(optionWidget, optionPositionY);
				}
			} catch (IllegalAccessException e) {
				BiomeFogClient.LOGGER.info("Failed to create {} config screen, see stacktrace below:", MOD_NAMESPACE);
				e.printStackTrace();
			}
		}

		addDrawableChild(new ScrollContainerCustomWidget(width / 2, height / 2, scrollContainerChildWidgets.toArray(new CustomWidget[0])));
	}

	@Override
	public Text getTitle() {
		return TITLE;
	}

	public int getNextOptionWidgetPositionY(@NotNull CustomWidget optionWidget, int positionY) {
		return positionY + optionWidget.getActualHeight() + OPTION_SPACING;
	}
}
