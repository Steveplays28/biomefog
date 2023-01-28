package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow private @Nullable VertexBuffer lightSkyBuffer;

	// Changes sky color in a circle gradient around the top of the skybox
	// The gradient appears to always want to go towards gray/blueish at the horizon in vanilla
	@ModifyVariable(method = "renderSky*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
	public Vec3d renderSky(Vec3d original) {
		// TODO: Figure out if enableBlend() is needed
		RenderSystem.enableBlend();
		return new Vec3d(0f, 0f, 1f);
//		return new Vec3d(1f, 0f, 0f);
	}

//	@Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableTexture()V"), cancellable = true, remap = false)
//	public void renderSkyInject(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
//		matrices.push();
//
//		RenderSystem.enableTexture();
//		RenderSystem.blendFuncSeparate(0, 1, 1, 1);
//		matrices.pop();
//
//		ci.cancel();
//	}

	// The lower the multiplier is the more sky that appears to render with my custom color (from the renderSky @ModifyVariable above) at the top of the skybox :o
	@ModifyVariable(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;signum(F)F"))
	private static float renderSkyBufferBuilderInject(float g) {
		return Math.signum(16.0f) * 128.0f;
	}

	// FIXME: Setting color of top of sky doesn't work
	@Inject(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilder;vertex(DDD)Lnet/minecraft/client/render/VertexConsumer;", ordinal = 0))
	private static void renderSkyBufferBuilderColorInject(BufferBuilder builder, float f, CallbackInfoReturnable<BufferBuilder.BuiltBuffer> cir) {
		builder.color(0f, 0f, 1f, 1f);
		RenderSystem.setShaderColor(0f, 0f, 1f, 1f);
	}

	// FIXME: Setting color of top of sky doesn't work
	@Inject(method = "renderSky(Lnet/minecraft/client/render/BufferBuilder;F)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilder;vertex(DDD)Lnet/minecraft/client/render/VertexConsumer;", ordinal = 1))
	private static void renderSkyBufferBuilderColorInject2(BufferBuilder builder, float f, CallbackInfoReturnable<BufferBuilder.BuiltBuffer> cir) {
		builder.color(0f, 0f, 1f, 1f);
		RenderSystem.setShaderColor(0f, 0f, 1f, 1f);
	}

//	/**
//	 * @author Steveplays28
//	 * @reason
//	 */
//	@Overwrite
//	private static BufferBuilder.BuiltBuffer renderSky(BufferBuilder builder, float f) {
//		float g = Math.signum(16.0f) * 1024.0f;
//		float h = 512.0f; // FIXME: Unused
//
//		RenderSystem.setShader(GameRenderer::getPositionProgram);
//		builder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION);
//		builder.vertex(0.0, f, 0.0).color(0f, 0f, 1f, 1f).next();
//
//		for (int i = -180; i <= 180; i += 45) {
//			builder.vertex(g * MathHelper.cos((float)i * ((float)Math.PI / 180)), f, 512.0f * MathHelper.sin((float)i * ((float)Math.PI / 180))).color(0f, 0f, 1f, 1f).next();
//		}
//
//		return builder.end();
//	}

	// Changes clouds color
	// Already fixed by Sodium, only required on vanilla
//	@ModifyVariable(method = "renderClouds*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getCloudsColor(F)Lnet/minecraft/util/math/Vec3d;"))
//	public Vec3d renderClouds(Vec3d original) {
//		return new Vec3d(1f, 1f, 1f);
//	}
}
