package io.github.steveplays28.biomefog.client;

import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class BiomeFogClient implements ClientModInitializer {
	public static final String MOD_ID = "biome-fog";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static String currentBiome = "";
	public static String currentDimension = "";

	@Override
	public void onInitializeClient() {
		BiomeFogConfigLoader.CONFIG = BiomeFogConfigLoader.load();

		// Listen for when the server is reloading (i.e. /reload), and reload the config
		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((s, m) -> {
			LOGGER.info("[Biome Fog] Reloading config!");
			BiomeFogConfigLoader.load();
		});
	}
}
