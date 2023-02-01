package io.github.steveplays28.biomefog.client;

import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class BiomeFogClient implements ClientModInitializer {
	private static final String MOD_ID = "biome-fog";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		BiomeFogConfigLoader.CONFIG = BiomeFogConfigLoader.load();
	}
}
