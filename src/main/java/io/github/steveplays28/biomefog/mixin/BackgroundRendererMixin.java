package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.util.BiomeUtil;
import io.github.steveplays28.biomefog.util.RenderSystemUtil;
import io.github.steveplays28.biomefog.util.TimeUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.joml.Math;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void applyFogInject(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
		var world = MinecraftClient.getInstance().world;
		if (world == null || world.getBiome(camera.getBlockPos()).getKey().isEmpty() || !camera.getSubmersionType().equals(CameraSubmersionType.NONE)) {
			return;
		}

		// Get current biome (at the camera's position)
		BiomeFogClient.currentBiome = BiomeUtil.GetBiomeBelowCamera(camera).toString();

		// Set custom fog and sky color
		Vector4f currentBiomeFogColor;

		if (world.isRaining() || world.isThundering()) {
			BiomeFogConfigLoader.CONFIG.fogStartAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogStartAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);
			BiomeFogConfigLoader.CONFIG.fogEndAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogEndAddition, BiomeFogConfigLoader.CONFIG.fogEndAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);

			if (BiomeFogConfigLoader.CONFIG.fogColorsRain.containsKey(BiomeFogClient.currentBiome)) {
				currentBiomeFogColor = BiomeFogConfigLoader.CONFIG.fogColorsRain.get(BiomeFogClient.currentBiome);
			} else {
				currentBiomeFogColor = new Vector4f(BiomeFogConfigLoader.CONFIG.skyColorRain.toVector3f(), 1f);
			}

			if (BiomeFogConfigLoader.CONFIG.fogStartAdditionsRain.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogEndAdditionsRain.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogColorsRain.containsKey(BiomeFogClient.currentBiome)) {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime + tickDelta * 0.001f);
			} else {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime - tickDelta * 0.001f);
			}
		} else if (TimeUtil.isNight(world)) {
			BiomeFogConfigLoader.CONFIG.fogStartAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogStartAdditionsNight.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);
			BiomeFogConfigLoader.CONFIG.fogEndAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogEndAddition, BiomeFogConfigLoader.CONFIG.fogEndAdditionsNight.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);

			if (BiomeFogConfigLoader.CONFIG.fogColorsNight.containsKey(BiomeFogClient.currentBiome)) {
				currentBiomeFogColor = BiomeFogConfigLoader.CONFIG.fogColorsNight.get(BiomeFogClient.currentBiome);
			} else {
				currentBiomeFogColor = new Vector4f(BiomeFogConfigLoader.CONFIG.skyColorNight.toVector3f(), 1f);
			}

			if (BiomeFogConfigLoader.CONFIG.fogStartAdditionsNight.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogEndAdditionsNight.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogColorsNight.containsKey(BiomeFogClient.currentBiome)) {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime + tickDelta * 0.001f);
			} else {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime - tickDelta * 0.001f);
			}
		} else {
			BiomeFogConfigLoader.CONFIG.fogStartAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogStartAdditions.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);
			BiomeFogConfigLoader.CONFIG.fogEndAddition = Math.lerp(BiomeFogConfigLoader.CONFIG.fogEndAddition, BiomeFogConfigLoader.CONFIG.fogEndAdditions.getOrDefault(BiomeFogClient.currentBiome, 0f), 0.001f);

			if (BiomeFogConfigLoader.CONFIG.fogColors.containsKey(BiomeFogClient.currentBiome)) {
				currentBiomeFogColor = BiomeFogConfigLoader.CONFIG.fogColors.get(BiomeFogClient.currentBiome);
			} else {
				currentBiomeFogColor = new Vector4f(BiomeFogConfigLoader.CONFIG.skyColor.toVector3f(), 1f);
			}

			if (BiomeFogConfigLoader.CONFIG.fogStartAdditions.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogEndAdditions.containsKey(BiomeFogClient.currentBiome) || BiomeFogConfigLoader.CONFIG.fogColors.containsKey(BiomeFogClient.currentBiome)) {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime + tickDelta * 0.001f);
			} else {
				BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime - tickDelta * 0.001f);
			}
		}

		// Linearly interpolate fog color
		BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColor.lerp(currentBiomeFogColor, 0.001f);

		// Update fog
		RenderSystem.setShaderFogStart(Math.lerp(vanillaFogStart(viewDistance), 0f + BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));
		RenderSystem.setShaderFogEnd(Math.lerp(viewDistance, viewDistance / 3 + BiomeFogConfigLoader.CONFIG.fogEndAddition, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));
		RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);

		// Re-render light and dark skies to update WorldRendererMixin changes
		MinecraftClient.getInstance().worldRenderer.renderLightSky();
		MinecraftClient.getInstance().worldRenderer.renderDarkSky();

//		BiomeFogClient.LOGGER.info("\nfogColor: {}\nactualFogColor: {}\nlerpTime: {}", BiomeFogConfigLoader.CONFIG.fogColor, RenderSystem.getShaderFogColor(), BiomeFogConfigLoader.CONFIG.fogColorLerpTime);
//		BiomeFogClient.LOGGER.info("fogStartAddition: {}\nfogEndAddition: {}", BiomeFogConfigLoader.CONFIG.fogStartAddition, BiomeFogConfigLoader.CONFIG.fogEndAddition);
	}

	private static float vanillaFogStart(float viewDistance) {
		float f = MathHelper.clamp(viewDistance / 10.0f, 4.0f, 64.0f);
		return viewDistance - f;
	}

	@Shadow
	public static void clearFog() {
	}

	// Changes the color of bottom part of the sky
	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
	private static void renderInject(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		var fogColor = BiomeFogConfigLoader.CONFIG.fogColor;

		RenderSystem.clearColor(fogColor.x, fogColor.y, fogColor.z, fogColor.w);
		clearFog();
		ci.cancel();
	}

	// Changes the color of the seam/transition in the sky
	@Inject(method = "setFogBlack", at = @At("HEAD"), cancellable = true)
	private static void setFogBlackInject(CallbackInfo ci) {
		var fogColor = BiomeFogConfigLoader.CONFIG.fogColor;

		RenderSystem.setShaderFogColor(fogColor.x, fogColor.y, fogColor.z, fogColor.w);
		ci.cancel();
	}
}
