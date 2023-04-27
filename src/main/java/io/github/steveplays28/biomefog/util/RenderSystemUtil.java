package io.github.steveplays28.biomefog.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.Vec3d;

public class RenderSystemUtil {
	public static void setShaderFogColor(Vec3d fogColor) {
		RenderSystem.setShaderFogColor((float) fogColor.x, (float) fogColor.y, (float) fogColor.z, 1f);
	}

	public static void setClearColor(Vec3d clearColor) {
		RenderSystem.clearColor((float) clearColor.x, (float) clearColor.y, (float) clearColor.z, 1f);
	}
}
