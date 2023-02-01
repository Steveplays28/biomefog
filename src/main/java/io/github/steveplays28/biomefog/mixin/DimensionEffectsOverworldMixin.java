package io.github.steveplays28.biomefog.mixin;

import io.github.steveplays28.biomefog.util.Vec3dUtil;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.Overworld.class)
public class DimensionEffectsOverworldMixin {
	// Changes sky horizon color (clear color)
	@Inject(method = "adjustFogColor", at = @At("RETURN"), cancellable = true)
	public void adjustFogColor(Vec3d color, float sunHeight, CallbackInfoReturnable<Vec3d> cir) {
		cir.setReturnValue(Vec3dUtil.vector4fToVec3d(BiomeFogConfigLoader.CONFIG.fogColor).subtract(0.03f, 0.03f, 0.03f));

//		cir.setReturnValue(new Vec3d(1f, 0f, 0f));
	}
}
