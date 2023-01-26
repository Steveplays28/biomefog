package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.client.BiomeFogClient;
import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.class)
public class DimensionEffectsMixin {
	// Changes sunrise/sunset fog color override (fog around the sun)
	@Inject(method = "getFogColorOverride", at = @At("RETURN"), cancellable = true)
	public void getFogColorOverrideInject(float skyAngle, float tickDelta, CallbackInfoReturnable<float[]> cir) {
		// TODO: Figure out if this changes anything at all, and if it does, what it changes
		cir.setReturnValue(null);
//		cir.setReturnValue(BiomeFogClient.fogColor);
	}
}
