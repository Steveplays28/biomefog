package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.util.TimeUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	// The amount of transition between the top of the sky and the bottom of the sky
	@ModifyVariable(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;signum(F)F"), argsOnly = true)
	private static float renderSkyBufferBuilderInject(float g) {
		return Math.signum(16.0f) * 8.0f;
	}

	// Changes the color of the top of the sky
	@ModifyVariable(method = "renderSky*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderSkyInject(Vec3d original) {
		var world = MinecraftClient.getInstance().world;
		if (world == null) return original;

		BiomeFogConfigLoader.CONFIG.skyColorAddition = BiomeFogConfigLoader.CONFIG.skyColorAddition.lerp(BiomeFogConfigLoader.CONFIG.skyColorAdditions.getOrDefault(BiomeFogClient.currentBiome, BiomeFogConfigLoader.CONFIG.skyColorAdditions.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)), 0.001f);
		if (world.isRaining() || world.isThundering()) {
			BiomeFogConfigLoader.CONFIG.skyColorAddition = BiomeFogConfigLoader.CONFIG.skyColorAddition.lerp(BiomeFogConfigLoader.CONFIG.skyColorAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, BiomeFogConfigLoader.CONFIG.skyColorAdditionsRain.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)), 0.001f);
		} else if (TimeUtil.isNight(world)) {
			BiomeFogConfigLoader.CONFIG.skyColorAddition = BiomeFogConfigLoader.CONFIG.skyColorAddition.lerp(BiomeFogConfigLoader.CONFIG.skyColorAdditionsNight.getOrDefault(BiomeFogClient.currentBiome, BiomeFogConfigLoader.CONFIG.skyColorAdditionsNight.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)), 0.001f);
		}

		return original.add(BiomeFogConfigLoader.CONFIG.skyColorAddition);
	}

	// Changes clouds color
	// Already fixed by Sodium, only required on vanilla
//	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
//	public Vec3d renderClouds(Vec3d original) {
//		return new Vec3d(1f, 1f, 1f);
//	}
}
