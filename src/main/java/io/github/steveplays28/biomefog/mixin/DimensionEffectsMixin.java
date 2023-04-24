package io.github.steveplays28.biomefog.mixin;

import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DimensionEffects.class)
public class DimensionEffectsMixin {
	// Changes sunrise/sunset fog color override (fog around the sun)
//	@Inject(method = "getFogColorOverride", at = @At("RETURN"), cancellable = true)
//	public void getFogColorOverrideInject(float skyAngle, float tickDelta, CallbackInfoReturnable<float[]> cir) {
//		cir.setReturnValue(null);
//		cir.setReturnValue(BiomeFogClient.fogColor);
//	}
}
