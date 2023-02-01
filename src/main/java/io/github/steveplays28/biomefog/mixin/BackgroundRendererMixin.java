package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.biomefog.util.RenderSystemUtil;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void applyFogInject(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
		if (MinecraftClient.getInstance().world == null || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().isEmpty() || !camera.getSubmersionType().equals(CameraSubmersionType.NONE)) {
			return;
		}

		if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("jungle")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);

			BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColorJungle;
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("desert")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);

			BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColorDesert;
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_plains"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_taiga"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_slopes"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_beach")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);

			BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColorSnow;
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("swamp"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("mangrove_swamp")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);

			BiomeFogConfigLoader.CONFIG.fogColor = BiomeFogConfigLoader.CONFIG.fogColorSwamp;
			RenderSystemUtil.setShaderFogColor(BiomeFogConfigLoader.CONFIG.fogColor);
		} else {
			BiomeFogConfigLoader.CONFIG.fogColor = new Vector4f(BiomeFogConfigLoader.CONFIG.skyColor.toVector3f(), 1f);
		}
	}

	// Removes fog from clouds (makes clouds visible)
//	@Inject(method = "setFogBlack", at = @At("HEAD"), cancellable = true)
//	private static void setFogBlackInject(CallbackInfo ci) {
//		RenderSystem.setShaderFogColor(0.67f, 0.8f, 0.95f, 1f);
//		ci.cancel();
//	}
}
