package io.github.steveplays28.biomefog.config;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;

public class BiomeFogConfig {
	public Vec3d skyColor = new Vec3d(0.68f, 0.83f, 1f);

	public transient Vector4f fogColor = new Vector4f(this.skyColor.toVector3f(), 1f);
	public Vector4f fogColorSwamp = new Vector4f(0.28f, 0.28f, 0.05f, 1f);
	public Vector4f fogColorSnow = new Vector4f(0.96f, 0.98f, 0.94f, 1f);
	public Vector4f fogColorDesert = new Vector4f(0.84f, 0.78f, 0.6f, 1f);
	public Vector4f fogColorJungle = new Vector4f(0.15f, 0.42f, 0.13f, 1f);
}
