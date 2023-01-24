package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
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
			RenderSystem.setShaderFogColor(0.15f, 0.42f, 0.13f, 1f);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("desert")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);
			RenderSystem.setShaderFogColor(0.84f, 0.78f, 0.6f, 1f);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_plains"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_taiga"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_slopes"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("snowy_beach")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);
			RenderSystem.setShaderFogColor(0.96f, 0.98f, 0.94f, 1f);
		} else if (MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("swamp"))) || MinecraftClient.getInstance().world.getBiome(camera.getBlockPos()).getKey().get().equals(RegistryKey.of(RegistryKeys.BIOME, new Identifier("mangrove_swamp")))) {
			RenderSystem.setShaderFogStart(0f);
			RenderSystem.setShaderFogEnd(viewDistance / 3);
			RenderSystem.setShaderFogColor(0.28f, 0.28f, 0.05f, 1f);
		}
	}
}
