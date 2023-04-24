package io.github.steveplays28.biomefog.mixin;

import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.class)
public class DimensionEffectsMixin {
	// Changes sunrise/sunset fog color override (fog around the sun)
//	@Inject(method = "getFogColorOverride", at = @At("RETURN"), cancellable = true)
//	public void getFogColorOverrideInject(float skyAngle, float tickDelta, CallbackInfoReturnable<float[]> cir) {
//		cir.setReturnValue(null);
//		cir.setReturnValue(BiomeFogClient.fogColor);
//	}

	// This is not it
	@Inject(method = "shouldBrightenLighting", at = @At("HEAD"), cancellable = true)
	public void shouldBrightenLightingInject(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	// This is not it
	@Inject(method = "isDarkened", at = @At("HEAD"), cancellable = true)
	public void isDarkenedInject(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}
