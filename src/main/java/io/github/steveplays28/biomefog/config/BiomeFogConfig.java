package io.github.steveplays28.biomefog.config;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;

import java.util.Map;

import static java.util.Map.entry;

public class BiomeFogConfig {
	// Sky colors
	public Map<String, Vec3d> skyColorAdditions = Map.ofEntries(
			entry("minecraft:overworld", new Vec3d(0.15f, 0.15f, 0.15f))
	);
	public Map<String, Vec3d> skyColorAdditionsRain = Map.ofEntries(
			entry("minecraft:overworld", new Vec3d(0.15f, 0.15f, 0.15f))
	);
	public Map<String, Vec3d> skyColorAdditionsNight = Map.ofEntries(
			entry("minecraft:overworld", new Vec3d(0f, 0f, 0f))
	);

	// Fog colors
	public Vec3d defaultFogColor = new Vec3d(0.68f, 0.83f, 1f);
	public Vec3d defaultFogColorRain = new Vec3d(0.4f, 0.46f, 0.54f);
	public Vec3d defaultFogColorNight = new Vec3d(0f, 0f, 0f);
	public Map<String, Vector4f> fogColors = Map.ofEntries(
			entry("minecraft:the_nether", new Vector4f(0.26f, 0f, 0f, 1f)),
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
			entry("minecraft:frozen_river", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:ice_spikes", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:grove", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:desert", new Vector4f(0.84f, 0.78f, 0.6f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f))
	);
	public Map<String, Vector4f> fogColorsRain = Map.ofEntries(
			entry("minecraft:the_nether", new Vector4f(0.26f, 0f, 0f, 1f)),
			entry("minecraft:swamp", new Vector4f(0.042f, 0.042f, 0.0075f, 1f)),
			entry("minecraft:mangrove_swamp", new Vector4f(0.042f, 0.042f, 0.0075f, 1f)),
			entry("minecraft:jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:bamboo_jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:sparse_jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:snowy_plains", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_slopes", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_taiga", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:snowy_beach", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:frozen_peaks", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:jagged_peaks", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:frozen_ocean", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:frozen_river", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:ice_spikes", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:grove", new Vector4f(0.96f, 0.98f, 0.94f, 1f)),
			entry("minecraft:desert", new Vector4f(0.84f, 0.78f, 0.6f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f))
	);
	public Map<String, Vector4f> fogColorsNight = Map.ofEntries(
			entry("minecraft:the_nether", new Vector4f(0.26f, 0f, 0f, 1f)),
			entry("minecraft:swamp", new Vector4f(0.042f, 0.042f, 0.0075f, 1f)),
			entry("minecraft:mangrove_swamp", new Vector4f(0.042f, 0.042f, 0.0075f, 1f)),
			entry("minecraft:jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:bamboo_jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:sparse_jungle", new Vector4f(0.0225f, 0.063f, 0.0195f, 1f)),
			entry("minecraft:snowy_plains", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:snowy_slopes", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:snowy_taiga", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:snowy_beach", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:frozen_peaks", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:jagged_peaks", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:frozen_ocean", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:frozen_river", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:ice_spikes", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:grove", new Vector4f(0.1f, 0.1f, 0.1f, 1f)),
			entry("minecraft:desert", new Vector4f(0.84f, 0.78f, 0.6f, 1f)),
			entry("minecraft:badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:eroded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f)),
			entry("minecraft:wooded_badlands", new Vector4f(0.75f, 0.4f, 0.13f, 1f))
	);

	// Fog start additions and fog end additions
	public Map<String, Float> fogStartAdditions = Map.ofEntries(
			entry("minecraft:overworld", 100f),
			entry("minecraft:the_nether", 100f),
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);
	public Map<String, Float> fogEndAdditions = Map.ofEntries(
			entry("minecraft:overworld", 200f),
			entry("minecraft:the_nether", 200f),
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:snowy_plains", 150f),
			entry("minecraft:snowy_slopes", 150f),
			entry("minecraft:snowy_taiga", 150f),
			entry("minecraft:snowy_beach", 150f),
			entry("minecraft:frozen_peaks", 150f),
			entry("minecraft:jagged_peaks", 150f),
			entry("minecraft:frozen_ocean", 150f),
			entry("minecraft:frozen_river", 150f),
			entry("minecraft:ice_spikes", 150f),
			entry("minecraft:grove", 150f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);
	public Map<String, Float> fogStartAdditionsRain = Map.ofEntries(
			entry("minecraft:overworld", 100f),
			entry("minecraft:the_nether", 100f),
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:snowy_plains", 0f),
			entry("minecraft:snowy_slopes", 0f),
			entry("minecraft:snowy_taiga", 0f),
			entry("minecraft:snowy_beach", 0f),
			entry("minecraft:frozen_peaks", 0f),
			entry("minecraft:jagged_peaks", 0f),
			entry("minecraft:frozen_ocean", 0f),
			entry("minecraft:frozen_river", 0f),
			entry("minecraft:ice_spikes", 0f),
			entry("minecraft:grove", 0f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);
	public Map<String, Float> fogEndAdditionsRain = Map.ofEntries(
			entry("minecraft:overworld", 200f),
			entry("minecraft:the_nether", 200f),
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:snowy_plains", 0f),
			entry("minecraft:snowy_slopes", 0f),
			entry("minecraft:snowy_taiga", 0f),
			entry("minecraft:snowy_beach", 0f),
			entry("minecraft:frozen_peaks", 0f),
			entry("minecraft:jagged_peaks", 0f),
			entry("minecraft:frozen_ocean", 0f),
			entry("minecraft:frozen_river", 0f),
			entry("minecraft:ice_spikes", 0f),
			entry("minecraft:grove", 0f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);
	public Map<String, Float> fogStartAdditionsNight = Map.ofEntries(
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:snowy_plains", 100f),
			entry("minecraft:snowy_slopes", 100f),
			entry("minecraft:snowy_taiga", 100f),
			entry("minecraft:snowy_beach", 100f),
			entry("minecraft:frozen_peaks", 100f),
			entry("minecraft:jagged_peaks", 100f),
			entry("minecraft:frozen_ocean", 100f),
			entry("minecraft:frozen_river", 100f),
			entry("minecraft:ice_spikes", 100f),
			entry("minecraft:grove", 100f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);
	public Map<String, Float> fogEndAdditionsNight = Map.ofEntries(
			entry("minecraft:swamp", 0f),
			entry("minecraft:mangrove_swamp", 0f),
			entry("minecraft:jungle", 0f),
			entry("minecraft:bamboo_jungle", 0f),
			entry("minecraft:sparse_jungle", 0f),
			entry("minecraft:snowy_plains", 150f),
			entry("minecraft:snowy_slopes", 150f),
			entry("minecraft:snowy_taiga", 150f),
			entry("minecraft:snowy_beach", 150f),
			entry("minecraft:frozen_peaks", 150f),
			entry("minecraft:jagged_peaks", 150f),
			entry("minecraft:frozen_ocean", 150f),
			entry("minecraft:frozen_river", 150f),
			entry("minecraft:ice_spikes", 150f),
			entry("minecraft:grove", 150f),
			entry("minecraft:desert", 0f),
			entry("minecraft:badlands", 0f),
			entry("minecraft:eroded_badlands", 0f),
			entry("minecraft:wooded_badlands", 0f)
	);

	public transient Vector4f fogColor = new Vector4f(this.defaultFogColor.toVector3f(), 1f);
	public transient float fogColorLerpTime;
	public transient float fogStartAddition = 0f;
	public transient float fogEndAddition = 0f;
	public transient Vec3d skyColorAddition = Vec3d.ZERO;
}
