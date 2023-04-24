package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.util.BiomeUtil;
import io.github.steveplays28.biomefog.util.RenderSystemUtil;
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

		if (BiomeFogConfigLoader.CONFIG.fogColors.containsKey(BiomeFogClient.currentBiome)) {
			// Set custom fog and sky color
			var fogStartAddition = 0f;
			var fogEndAddition = 0f;
			var currentBiomeFogColor = BiomeFogConfigLoader.CONFIG.fogColors.get(BiomeFogClient.currentBiome);

			if (world.isRaining() || world.isThundering()) {
				fogStartAddition = BiomeFogConfigLoader.CONFIG.fogStartAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, 0f);
				fogEndAddition = BiomeFogConfigLoader.CONFIG.fogEndAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, 0f);
				currentBiomeFogColor = BiomeFogConfigLoader.CONFIG.fogColorsRain.get(BiomeFogClient.currentBiome);
			} else {
				fogStartAddition = BiomeFogConfigLoader.CONFIG.fogStartAdditions.getOrDefault(BiomeFogClient.currentBiome, 0f);
				fogEndAddition = BiomeFogConfigLoader.CONFIG.fogEndAdditions.getOrDefault(BiomeFogClient.currentBiome, 0f);
			}

			RenderSystem.setShaderFogStart(Math.lerp(vanillaFogStart(viewDistance), 0f + fogStartAddition, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));
			RenderSystem.setShaderFogEnd(Math.lerp(viewDistance, viewDistance / 3 + fogEndAddition, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));

			BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColor.lerp(currentBiomeFogColor, 0.001f);
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
			BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime + tickDelta * 0.001f);
		} else {
			// Return fog and sky color to normal
			RenderSystem.setShaderFogStart(Math.lerp(vanillaFogStart(viewDistance), 0f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));
			RenderSystem.setShaderFogEnd(Math.lerp(viewDistance, viewDistance / 3, BiomeFogConfigLoader.CONFIG.fogColorLerpTime));

			if (world.isRaining() || world.isThundering()) {
				BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColor.lerp(new Vector4f(BiomeFogConfigLoader.CONFIG.skyColorRain.toVector3f(), 1f), 0.001f);
			} else {
				BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColor.lerp(new Vector4f(BiomeFogConfigLoader.CONFIG.skyColor.toVector3f(), 1f), 0.001f);
			}

			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
			BiomeFogConfigLoader.CONFIG.fogColorLerpTime = Math.clamp(0f, 1f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime - tickDelta * 0.001f);
		}

		// Re-render light and dark skies to update WorldRendererMixin changes
		MinecraftClient.getInstance().worldRenderer.renderLightSky();
		MinecraftClient.getInstance().worldRenderer.renderDarkSky();
//		BiomeFogClient.LOGGER.info("\nfogColor: {}\nactualFogColor: {}\nlerpTime: {}", BiomeFogConfigLoader.CONFIG.fogColor, RenderSystem.getShaderFogColor(), BiomeFogConfigLoader.CONFIG.fogColorLerpTime);
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
