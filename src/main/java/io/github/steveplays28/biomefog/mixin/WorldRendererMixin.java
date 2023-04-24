package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	// The lower the multiplier is the more sky that appears to render with my custom color (from the renderSky @ModifyVariable above) at the top of the skybox :o
	@ModifyVariable(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;signum(F)F"), argsOnly = true)
	private static float renderSkyBufferBuilderInject(float g) {
		return Math.lerp(Math.signum(16.0f) * 16.0f, Math.signum(16.0f) * 8.0f, BiomeFogConfigLoader.CONFIG.fogColorLerpTime);
	}

	// Changes sky color in a circle gradient around the top of the skybox
	// The gradient appears to always want to go towards gray/blueish at the horizon in vanilla (fixed by renderSkyBufferBuilderInject below)
	@ModifyVariable(method = "renderSky*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderSkyInject(Vec3d original) {
		return BiomeFogConfigLoader.CONFIG.skyColor;
	}

	// Changes clouds color
	// Already fixed by Sodium, only required on vanilla
//	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
//	public Vec3d renderClouds(Vec3d original) {
//		return new Vec3d(1f, 1f, 1f);
//	}
}
