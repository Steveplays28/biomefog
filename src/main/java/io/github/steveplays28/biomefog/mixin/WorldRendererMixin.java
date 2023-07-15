package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.util.TimeUtil;
import io.github.steveplays28.biomefog.util.WorldUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.steveplays28.biomefog.config.BiomeFogConfigLoader.BiomeFogConfigurations.CONFIG;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	// The amount of transition between the top of the sky and the bottom of the sky
	@ModifyVariable(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;signum(F)F"), argsOnly = true)
	private static float renderSkyBufferBuilderInject(float g) {
		return Math.signum(16.0f) * 8.0f;
	}

	// Changes the color of the top of the sky
	@ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	@SuppressWarnings("InvalidInjectorMethodSignature")
	public Vec3d renderSkyInject(Vec3d original) {
		var world = MinecraftClient.getInstance().world;
		if (world == null || WorldUtil.isWorldBlacklisted(WorldUtil.getWorldOrServerName())) return original;

		CONFIG.skyColorAddition = CONFIG.skyColorAddition.lerp(CONFIG.skyColorAdditions.getOrDefault(
				BiomeFogClient.currentBiome,
				CONFIG.skyColorAdditions.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)
		), 0.001f);
		if (world.isRaining() || world.isThundering()) {
			CONFIG.skyColorAddition = CONFIG.skyColorAddition.lerp(CONFIG.skyColorAdditionsRain.getOrDefault(
					BiomeFogClient.currentBiome,
					CONFIG.skyColorAdditionsRain.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)
			), 0.001f);
		} else if (TimeUtil.isNight(world)) {
			CONFIG.skyColorAddition = CONFIG.skyColorAddition.lerp(CONFIG.skyColorAdditionsNight.getOrDefault(
					BiomeFogClient.currentBiome,
					CONFIG.skyColorAdditionsNight.getOrDefault(BiomeFogClient.currentDimension, Vec3d.ZERO)
			), 0.001f);
		}

		return original.add(CONFIG.skyColorAddition);
	}
}
