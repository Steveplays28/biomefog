package io.github.steveplays28.biomefog.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.user.BiomeFogBlackListedWorlds;
import io.github.steveplays28.biomefog.config.user.BiomeFogConfig;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.MOD_LOADER_CONFIG_FOLDER_PATH;
import static io.github.steveplays28.biomefog.client.BiomeFogClient.MOD_PATH;

public class BiomeFogConfigLoader {
	public static final Path CONFIG_FOLDER_PATH = MOD_LOADER_CONFIG_FOLDER_PATH.resolve(MOD_PATH);

	public static void load() {
		createConfigurationFoldersIfNeeded();
		migrateOldConfigurationFile();

		var configurationFilePaths = BiomeFogConfigurationFilePaths.class.getFields();
		var configurations = BiomeFogConfigurations.class.getFields();

		for (int i = 0; i < configurationFilePaths.length; i++) {
			createMissingConfigurationFiles();

			try (InputStream stream = Files.newInputStream((Path) configurationFilePaths[i].get(Path.class))) {
				var gson = createGson();
				BiomeFogClient.LOGGER.info(configurations[i]);
				configurations[i].set(
						BiomeFogConfigurations.class,
						gson.fromJson(
								new JsonReader(new BufferedReader(new InputStreamReader(stream))),
								configurations[i].getType()
						)
				);
			} catch (IOException | IllegalAccessException e) {
				BiomeFogClient.LOGGER.error(
						"Unable to load Biome Fog configuration files at {}. Loading default configuration. See stack trace below:",
						CONFIG_FOLDER_PATH
				);
				e.printStackTrace();
			}
		}
	}

	public static void save() {
		var success = true;
		var configurationFilePaths = BiomeFogConfigurationFilePaths.class.getFields();
		var configurations = BiomeFogConfigurations.class.getFields();

		for (int i = 0; i < configurationFilePaths.length; i++) {
			try (FileWriter writer = new FileWriter(configurationFilePaths[i].get(Path.class).toString())) {
				var gson = createGson();
				var json = gson.toJson(configurations[i].get(BiomeFogBaseConfig.class));
				writer.write(json);

			} catch (IOException | IllegalAccessException e) {
				BiomeFogClient.LOGGER.error(
						"Unable to save Biome Fog configuration files at {}. See stack trace below:",
						CONFIG_FOLDER_PATH
				);
				e.printStackTrace();
				success = false;
			}
		}

		if (success) {
			BiomeFogClient.LOGGER.info("Saved Biome Fog configuration files, located at {}", CONFIG_FOLDER_PATH);
		}
	}

	private static void createConfigurationFoldersIfNeeded() {
		try {
			if (!new File("/config").exists()) {
				Files.createDirectory(Path.of("/config"));
			}

			if (!CONFIG_FOLDER_PATH.toFile().exists()) {
				Files.createDirectory(CONFIG_FOLDER_PATH);
			}
		} catch (IOException e) {
			BiomeFogClient.LOGGER.error(
					"Unable to create Biome Fog configuration folder at {}. Loading default configuration. See stack trace below:",
					CONFIG_FOLDER_PATH
			);
			e.printStackTrace();
		}
	}

	private static @NotNull Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.setPrettyPrinting().create();
	}

	private static void createMissingConfigurationFiles() {
		if (!new File("/config").exists()) {
			BiomeFogClient.LOGGER.error(
					"Unable to create Biome Fog configuration files at {}. Configuration folder (/config) doesn't exist. Loading default configuration.",
					CONFIG_FOLDER_PATH
			);
			return;
		}

		var configurationFilePaths = BiomeFogConfigurationFilePaths.class.getFields();
		var defaultConfigurations = BiomeFogDefaultConfigurations.class.getFields();

		for (int i = 0; i < configurationFilePaths.length; i++) {
			try {
				var configurationFilePath = ((Path) configurationFilePaths[i].get(Path.class));

				if (!configurationFilePath.toFile().exists()) {
					Files.createFile(configurationFilePath);
				}
			} catch (IOException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			try (FileWriter writer = new FileWriter(configurationFilePaths[i].get(Path.class).toString())) {
				if (((Path) configurationFilePaths[i].get(Path.class)).toFile().length() > 0) continue;

				var gson = createGson();
				var json = gson.toJson(defaultConfigurations[i].get(null));
				writer.write(json);
			} catch (IOException | IllegalAccessException e) {
				BiomeFogClient.LOGGER.error(
						"Unable to create Biome Fog configuration files at {}. Loading default configuration. See stack trace below:",
						CONFIG_FOLDER_PATH
				);
				e.printStackTrace();
			}
		}

		BiomeFogClient.LOGGER.info("Created Biome Fog configuration files, located at {}", CONFIG_FOLDER_PATH);
	}

	private static void migrateOldConfigurationFile() {
		if (!Files.exists(Path.of(MessageFormat.format("config/{0}.json", MOD_PATH))) || Files.exists(
				BiomeFogConfigurationFilePaths.CONFIG_FILE_PATH)) return;

		var oldPath = Path.of(MessageFormat.format("config/{0}.json", MOD_PATH));

		try {
			Files.copy(oldPath, BiomeFogConfigurationFilePaths.CONFIG_FILE_PATH);
			Files.delete(oldPath);
		} catch (IOException e) {
			BiomeFogClient.LOGGER.error(
					"Unable to migrate Biome Fog configuration file from {} to {}. Loading default configuration. See stack trace below:",
					oldPath, BiomeFogConfigurationFilePaths.CONFIG_FILE_PATH
			);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static class BiomeFogConfigurationFilePaths {
		public static final Path CONFIG_FILE_PATH = Path.of(
				MessageFormat.format("{0}/{1}.json", CONFIG_FOLDER_PATH, MOD_PATH));
		public static final Path BLACKLISTED_WORLDS_FILE_PATH = Path.of(
				MessageFormat.format("{0}/{1}_blacklisted_worlds.json", CONFIG_FOLDER_PATH, MOD_PATH));
	}

	@SuppressWarnings("unused")
	public static class BiomeFogDefaultConfigurations {
		public static final BiomeFogConfig DEFAULT_CONFIG = new BiomeFogConfig();
		public static final BiomeFogBlackListedWorlds DEFAULT_BLACKLISTED_WORLDS = new BiomeFogBlackListedWorlds();
	}

	@SuppressWarnings("unused")
	public static class BiomeFogConfigurations {
		public static BiomeFogConfig CONFIG;
		public static BiomeFogBlackListedWorlds BLACKLISTED_WORLDS;
	}
}
