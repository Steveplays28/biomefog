package io.github.steveplays28.biomefog.util;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;

public class Vec3dUtil {
	/**
	 * Discards the w component of the Vector4f.
	 */
	public static Vec3d vector4fToVec3d(Vector4f vector4f) {
		return new Vec3d(vector4f.x, vector4f.y, vector4f.z);
	}
}
