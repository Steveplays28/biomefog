package io.github.steveplays28.biomefog.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BiomeFogClient implements ClientModInitializer {
	public static float[] fogColor = new float[4];

	@Override
	public void onInitializeClient() {
	}
}
