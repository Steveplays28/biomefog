package io.github.steveplays28.biomefog.util;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;

import static io.github.steveplays28.biomefog.config.BiomeFogConfigLoader.BiomeFogConfigurations.BLACKLISTED_WORLDS;

public class WorldUtil {
	public static Identifier GetBiomeBelowCamera(Camera camera) {
		var errorIdentifier = Identifier.of(BiomeFogClient.MOD_ID, "error_fetching_biome");
		if (camera == null) return errorIdentifier;

		var world = MinecraftClient.getInstance().world;
		if (world == null) return errorIdentifier;

		var biomeBelowCamera = MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey();
		if (biomeBelowCamera.isEmpty()) return errorIdentifier;

		return biomeBelowCamera.get().getValue();
	}

	public static Identifier GetDimension() {
		var errorIdentifier = Identifier.of(BiomeFogClient.MOD_ID, "error_fetching_dimension");

		var world = MinecraftClient.getInstance().world;
		if (world == null) return errorIdentifier;

		var dimension = MinecraftClient.getInstance().world.getDimensionKey();
		if (dimension == null) return errorIdentifier;

		return dimension.getValue();
	}

	public static boolean isWorldBlacklisted(String worldName) {
		return BLACKLISTED_WORLDS.blackListedWorlds.contains(worldName);
	}

	@Environment(EnvType.CLIENT)
	public static String getWorldOrServerName() {
		var minecraftClient = MinecraftClient.getInstance();
		var currentServerEntry = minecraftClient.getCurrentServerEntry();
		var integratedServer = minecraftClient.getServer();

		if (currentServerEntry != null && !currentServerEntry.isLocal()) {
			return currentServerEntry.name;
		} else if (minecraftClient.isInSingleplayer() && integratedServer != null) {
			return integratedServer.getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
		} else {
			return "";
		}
	}
}
