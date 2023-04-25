package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
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

		var skyColorAddition = BiomeFogConfigLoader.CONFIG.skyColorAdditions.getOrDefault(BiomeFogClient.currentBiome, new Vec3d(0f, 0f, 0f));
		if (world.isRaining() || world.isThundering()) {
			skyColorAddition = BiomeFogConfigLoader.CONFIG.skyColorAdditionsRain.getOrDefault(BiomeFogClient.currentBiome, new Vec3d(0f, 0f, 0f));
		}

		return original.add(skyColorAddition);
	}

	// Changes clouds color
	// Already fixed by Sodium, only required on vanilla
//	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
//	public Vec3d renderClouds(Vec3d original) {
//		return new Vec3d(1f, 1f, 1f);
//	}
}
