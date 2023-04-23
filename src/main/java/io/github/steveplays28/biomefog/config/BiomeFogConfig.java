package io.github.steveplays28.biomefog.config;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;

import java.util.Map;

import static java.util.Map.entry;

public class BiomeFogConfig {
	public Vec3d skyColor = new Vec3d(0.68f, 0.83f, 1f);

	public transient Vector4f fogColor = new Vector4f(this.skyColor.toVector3f(), 1f);
	public transient float fogColorLerpTime;

	public Map<String, Vector4f> fogColors = Map.ofEntries(
			entry("minecraft:swamp", new Vector4f(0.28f, 0.28f, 0.05f, 1f)),
			entry("minecraft:mangrove_swamp", new Vector4f(0.28f, 0.28f, 0.05f, 1f)),
			entry("minecraft:jungle", new Vector4f(0.15f, 0.42f, 0.13f, 1f)),
			entry("minecraft:bamboo_jungle", new Vector4f(0.15f, 0.42f, 0.13f, 1f)),
			entry("minecraft:sparse_jungle", new Vector4f(0.15f, 0.42f, 0.13f, 1f)),
			entry("minecraft:snowy_plains", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_slopes", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_taiga", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_beach", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:frozen_peaks", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:jagged_peaks", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:frozen_ocean", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:grove", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:desert", new Vector4f(0.84f, 0.78f, 0.6f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f))
	);
	public Map<String, Vector4f> skyColorAdditions = Map.ofEntries(
			entry("minecraft:swamp", new Vector4f(-0.02f, -0.04f, -0.11f, 1f)),
			entry("minecraft:mangrove_swamp", new Vector4f(-0.02f, -0.04f, -0.11f, 1f)),
			entry("minecraft:jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:bamboo_jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:sparse_jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:snowy_plains", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_slopes", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_taiga", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_beach", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:frozen_peaks", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:jagged_peaks", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:frozen_ocean", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:grove", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:desert", new Vector4f(0.02f, 0.01f, 0f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f))
	);
	public Map<String, Vector4f> skyColorAdditionsRain = Map.ofEntries(
			entry("minecraft:swamp", new Vector4f(-0.02f, -0.04f, -0.11f, 1f)),
			entry("minecraft:mangrove_swamp", new Vector4f(-0.02f, -0.04f, -0.11f, 1f)),
			entry("minecraft:jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:bamboo_jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:sparse_jungle", new Vector4f(-0.03f, -0.03f, -0.10f, 1f)),
			entry("minecraft:snowy_plains", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_slopes", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_taiga", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:snowy_beach", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:frozen_peaks", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:jagged_peaks", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:frozen_ocean", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:grove", new Vector4f(0.04f, 0.05f, 0.01f, 1f)),
			entry("minecraft:desert", new Vector4f(0.02f, 0.01f, 0f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.04f, -0.04f, -0.11f, 1f))
	);
}
