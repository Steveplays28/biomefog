package io.github.steveplays28.biomefog.util;

import net.minecraft.client.world.ClientWorld;

public class TimeUtil {
	public static boolean isNight(ClientWorld world) {
		return world.getTimeOfDay()%24000 >= 13000 && world.getTimeOfDay()%24000 <= 23000;
	}
}
