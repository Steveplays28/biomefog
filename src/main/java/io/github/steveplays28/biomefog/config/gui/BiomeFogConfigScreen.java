package io.github.steveplays28.biomefog.config.gui;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogBaseConfig;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.config.gui.widget.BackgroundWidget;
import io.github.steveplays28.biomefog.config.gui.widget.TextWidget;
import io.github.steveplays28.biomefog.config.gui.widget.option.FloatOptionWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.NAMESPACE;

@Environment(EnvType.CLIENT)
public class BiomeFogConfigScreen extends Screen {
	public static final int WHITE_COLOR = 16777215;
	public static final Text TITLE = Text.translatable("biomefog.screen.config.title");
	public static final int BAR_HEIGHT = 40;
	public static final int OPTION_HEIGHT = 20;
	public static final int OPTION_SPACING = 10;

	public final Screen parent;

	private final List<TextFieldWidget> textFieldWidgets = new ArrayList<>();

	public BiomeFogConfigScreen(Screen parent) {
		super(TITLE);
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();
		if (client == null) return;

		client.keyboard.setRepeatEvents(true);

		var optionSpacing = 10;

		// Top
		addDrawable(new BackgroundWidget(0, 0, width, BAR_HEIGHT, 0));
		addDrawable(new TextWidget(width / 2, 8, textRenderer, TITLE, WHITE_COLOR, true));

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

		for (int i = 0; i < configurations.length; i++) {
			try {
				var config = configurations[i].get(BiomeFogBaseConfig.class);
				var configOptions = config.getClass().getFields();

				for (int j = 0; j < configOptions.length; j++) {
					var option = configOptions[j].get(config);
					optionPositionY = addOptionWidgetByType(option, optionPositionX, optionPositionY);
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

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void close() {
		if (client == null) return;

		client.setScreen(parent);
	}

	@Override
	public void resize(MinecraftClient client, int width, int height) {
		clearAndInit();
		super.resize(client, width, height);
	}

	@Override
	public void tick() {
//		textFieldWidgets.forEach(TextFieldWidget::tick);
	}

	/**
	 * @param option
	 * @param positionX
	 * @param positionY
	 * @return The Y position of the next option widget in the list.
	 */
	private int addOptionWidgetByType(Object option, int positionX, int positionY) {
		var couldCast = false;

		if (option instanceof Float castedOption) {
			var floatOptionWidget = new FloatOptionWidget(
					positionX, positionY, 160, OPTION_HEIGHT, textRenderer, Text.literal(castedOption.toString()), true);
			floatOptionWidget.setEditable(true);
			floatOptionWidget.setText(castedOption.toString());
			floatOptionWidget.setChangedListener(s -> {
				BiomeFogClient.LOGGER.info(s);
			});
			addDrawableChild(floatOptionWidget);
//			addSelectableChild(floatOptionWidget);
			textFieldWidgets.add(floatOptionWidget);
			BiomeFogClient.LOGGER.info(
					MessageFormat.format("castedOption: {0} size: {1}", castedOption.toString(), textFieldWidgets.size()));

			couldCast = true;
		}

		if (couldCast) {
			return positionY + OPTION_HEIGHT + OPTION_SPACING;
		} else {
			return positionY;
		}
	}
}
