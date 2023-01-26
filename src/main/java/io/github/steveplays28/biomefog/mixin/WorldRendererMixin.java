package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	// Changes sky color in a circle gradient around the top of the skybox
	// The gradient appears to always want to go towards gray/blueish at the horizon in vanilla
	@ModifyVariable(method = "renderSky*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderSky(Vec3d original) {
		RenderSystem.enableBlend();
		return new Vec3d(0.28f, 0.28f, 0.05f);
//		return new Vec3d(1f, 0f, 0f);
	}

	@Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableTexture()V"), cancellable = true, remap = false)
	public void renderSkyInject(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
		ci.cancel();
	}

	// Changes clouds color
	// Already fixed by Sodium, only required on vanilla
//	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
//	public Vec3d renderClouds(Vec3d original) {
//		return new Vec3d(1f, 1f, 1f);
//	}
}
