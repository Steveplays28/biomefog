package io.github.steveplays28.biomefog.mixin;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	private static ShaderProgram positionProgram;

//	@Inject(method = "getPositionProgram", at = @At("HEAD"), cancellable = true)
//	private static void getPositionProgram(CallbackInfoReturnable<ShaderProgram> cir) {
//		positionProgram.colorModulator =
//		cir.setReturnValue(positionProgram);
//	}
}
