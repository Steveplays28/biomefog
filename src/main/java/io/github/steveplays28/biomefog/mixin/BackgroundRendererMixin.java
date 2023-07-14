package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.util.MathUtil;
import io.github.steveplays28.biomefog.util.RenderSystemUtil;
import io.github.steveplays28.biomefog.util.TimeUtil;
import io.github.steveplays28.biomefog.util.WorldUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	private static Boolean shouldApplyFog = true;

	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void applyFogInject(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
		var world = MinecraftClient.getInstance().world;
		var cameraSubmersionType = camera.getSubmersionType();
		if (world == null || world.getBiome(camera.getBlockPos()).getKey().isEmpty() || !(cameraSubmersionType.equals(
				CameraSubmersionType.NONE) || cameraSubmersionType.equals(CameraSubmersionType.WATER))) {
			shouldApplyFog = false;
			return;
		}

		// Get current biome and dimension (at the camera's position)
		BiomeFogClient.currentBiome = WorldUtil.GetBiomeBelowCamera(camera).toString();
		BiomeFogClient.currentDimension = WorldUtil.GetDimension().toString();

		// Check if current dimension is enabled
		if (!BiomeFogConfigLoader.CONFIG.enabledDimensions.contains(
				BiomeFogClient.currentDimension) && !BiomeFogConfigLoader.CONFIG.enabledDimensions.contains("all")) {
			return;
		}

		shouldApplyFog = true;

		// Set custom fog and sky color
		Vec3d currentBiomeFogColor;
		Map<String, Float> fogStartAdditions;
		Map<String, Float> fogEndAdditions;
		Map<String, Vec3d> fogColors;
		Vec3d defaultFogColor;

		if (world.isRaining() || world.isThundering()) {
			fogStartAdditions = BiomeFogConfigLoader.CONFIG.fogStartAdditionsRain;
			fogEndAdditions = BiomeFogConfigLoader.CONFIG.fogEndAdditionsRain;
			fogColors = BiomeFogConfigLoader.CONFIG.fogColorsRain;
			defaultFogColor = BiomeFogConfigLoader.CONFIG.defaultFogColorRain;
		} else if (TimeUtil.isNight(world)) {
			fogStartAdditions = BiomeFogConfigLoader.CONFIG.fogStartAdditionsNight;
			fogEndAdditions = BiomeFogConfigLoader.CONFIG.fogEndAdditionsNight;
			fogColors = BiomeFogConfigLoader.CONFIG.fogColorsNight;
			defaultFogColor = BiomeFogConfigLoader.CONFIG.defaultFogColorNight;
		} else {
			fogStartAdditions = BiomeFogConfigLoader.CONFIG.fogStartAdditions;
			fogEndAdditions = BiomeFogConfigLoader.CONFIG.fogEndAdditions;
			fogColors = BiomeFogConfigLoader.CONFIG.fogColors;
			defaultFogColor = BiomeFogConfigLoader.CONFIG.defaultFogColor;
		}

		BiomeFogConfigLoader.CONFIG.fogStartAddition = MathUtil.lerp(BiomeFogConfigLoader.CONFIG.fogStartAddition,
				fogStartAdditions.getOrDefault(
						BiomeFogClient.currentBiome,
						fogStartAdditions.getOrDefault(BiomeFogClient.currentDimension, 0f)
				), 0.001f
		);
		BiomeFogConfigLoader.CONFIG.fogEndAddition = MathUtil.lerp(
				BiomeFogConfigLoader.CONFIG.fogEndAddition, fogEndAdditions.getOrDefault(
						BiomeFogClient.currentBiome,
						fogEndAdditions.getOrDefault(BiomeFogClient.currentDimension, 0f)
				), 0.001f);

		if (fogColors.containsKey(BiomeFogClient.currentBiome)) {
			currentBiomeFogColor = fogColors.get(BiomeFogClient.currentBiome);
		} else if (BiomeFogConfigLoader.CONFIG.fogColorsRain.containsKey(BiomeFogClient.currentDimension)) {
			currentBiomeFogColor = fogColors.get(BiomeFogClient.currentDimension);
		} else {
			currentBiomeFogColor = defaultFogColor;
		}

		if (fogStartAdditions.containsKey(BiomeFogClient.currentBiome) || fogEndAdditions.containsKey(
				BiomeFogClient.currentBiome) || fogColors.containsKey(
				BiomeFogClient.currentBiome) || fogStartAdditions.containsKey(
				BiomeFogClient.currentDimension) || fogEndAdditions.containsKey(
				BiomeFogClient.currentDimension) || fogColors.containsKey(BiomeFogClient.currentDimension)) {
			BiomeFogConfigLoader.CONFIG.fogColorLerpTime = MathUtil.clamp(
					0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime + tickDelta * 0.001f);
		} else {
			BiomeFogConfigLoader.CONFIG.fogColorLerpTime = MathUtil.clamp(
					0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime - tickDelta * 0.001f);
		}

		// Linearly interpolate fog color
		BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColor.lerp(currentBiomeFogColor, 0.001f);

		// Update fog
		RenderSystem.setShaderFogStart(
				MathUtil.lerp(vanillaFogStart(viewDistance), 0f + BiomeFogConfigLoader.CONFIG.fogStartAddition,
						BiomeFogConfigLoader.CONFIG.fogColorLerpTime
				));
		RenderSystem.setShaderFogEnd(
				MathUtil.lerp(viewDistance, viewDistance / 3 + BiomeFogConfigLoader.CONFIG.fogEndAddition,
						BiomeFogConfigLoader.CONFIG.fogColorLerpTime
				));
		RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);

		// Re-render light and dark skies to update WorldRendererMixin changes
		MinecraftClient.getInstance().worldRenderer.renderLightSky();
		MinecraftClient.getInstance().worldRenderer.renderDarkSky();

		// DEBUG
//		BiomeFogClient.LOGGER.info("\nfogColor: {}\nactualFogColor: {}\nlerpTime: {}", BiomeFogConfigLoader.CONFIG.fogColor, RenderSystem.getShaderFogColor(), BiomeFogConfigLoader.CONFIG.fogColorLerpTime);
//		BiomeFogClient.LOGGER.info("fogStartAddition: {}\nfogEndAddition: {}", BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogEndAddition);
	}

	private static float vanillaFogStart(float viewDistance) {
		float f = MathUtil.clamp(viewDistance / 10.0f, 4.0f, 64.0f);
		return viewDistance - f;
	}

	@Shadow
	public static void clearFog() {
	}

	// Changes the color of bottom part of the sky
	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
	private static void renderInject(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		if (shouldApplyFog) {
			RenderSystemUtil.setClearColor(BiomeFogConfigLoader.CONFIG.fogColor);
			clearFog();
			ci.cancel();
		}
	}

	// Changes the color of the seam/transition in the sky
	@Inject(method = "setFogBlack", at = @At("HEAD"), cancellable = true)
	private static void setFogBlackInject(CallbackInfo ci) {
		if (shouldApplyFog) {
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
			ci.cancel();
		}
	}
}
