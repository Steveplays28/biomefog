package io.github.steveplays28.biomefog.util;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.Identifier;

public class BiomeUtil {
	public static Identifier GetBiomeBelowCamera(Camera camera) {
		var errorIdentifier = Identifier.of(BiomeFogClient.MOD_ID, "error_fetching_biome");
		if (camera == null) return errorIdentifier;

		var world = MinecraftClient.getInstance().world;
		if (world == null) return errorIdentifier;

		var biomeBelowCamera = MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey();
		if (biomeBelowCamera.isEmpty()) return errorIdentifier;

		return biomeBelowCamera.get().getValue();
	}
}
