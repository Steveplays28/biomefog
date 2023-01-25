package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@ModifyVariable(method = "renderSky*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderSky(Vec3d original) {
		return new Vec3d(0.5f, 0.5f, 1f);
//		return new Vec3d(BiomeFogClient.fogColor[0], BiomeFogClient.fogColor[1], BiomeFogClient.fogColor[2]);
	}

	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderClouds(Vec3d original) {
		return new Vec3d(1f, 1f, 1f);
	}
}
