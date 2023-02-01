package io.github.steveplays28.biomefog;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector4f;

public class RenderSystemUtil {
	public static void setShaderFogColor(Vector4f fogColor) {
		RenderSystem.setShaderFogColor(fogColor.x, fogColor.y, fogColor.z, fogColor.w);
	}
}
