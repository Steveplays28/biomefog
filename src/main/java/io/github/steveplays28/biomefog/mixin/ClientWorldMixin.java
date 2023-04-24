package io.github.steveplays28.biomefog.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
	@Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
	public void getSkyColorInject(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> cir) {
		cir.setReturnValue(new Vec3d(1f, 0f, 0f));
	}
}
