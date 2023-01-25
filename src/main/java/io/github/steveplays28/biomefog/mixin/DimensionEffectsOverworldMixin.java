package io.github.steveplays28.biomefog.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.Overworld.class)
public class DimensionEffectsOverworldMixin {
//	@Inject(method = "getFogColorOverride", at = @At("RETURN"), cancellable = true)
//	public void getFogColorOverrideInject(float skyAngle, float tickDelta, CallbackInfoReturnable<float[]> cir) {
//		cir.setReturnValue(new float[]{1f, 0, 0, 1f});
//	}

	@Inject(method = "adjustFogColor", at = @At("RETURN"), cancellable = true)
	public void adjustFogColor(Vec3d color, float sunHeight, CallbackInfoReturnable<Vec3d> cir) {
		cir.setReturnValue(new Vec3d(0.28f, 0.28f, 0.05f));

//		cir.setReturnValue(new Vec3d(1f, 0f, 0f));
	}
}
