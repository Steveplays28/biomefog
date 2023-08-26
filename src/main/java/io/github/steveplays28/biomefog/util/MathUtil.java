package io.github.steveplays28.biomefog.util;

import net.minecraft.util.math.MathHelper;

public class MathUtil {
	public static float lerp(float start, float end, float t) {
		return MathHelper.lerp(t, start, end);
	}

	public static float clamp(float min, float max, float value) {
		return MathHelper.clamp(value, min, max);
	}

	public static int clamp(int value, int min, int max) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}

		return value;
	}
}
