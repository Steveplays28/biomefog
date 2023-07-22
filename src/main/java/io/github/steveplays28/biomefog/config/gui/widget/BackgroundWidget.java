package io.github.steveplays28.biomefog.config.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BackgroundWidget extends DrawableHelper implements Drawable {
	public final int positionX;
	public final int positionY;
	public final int width;
	public final int height;
	public final int vOffset;
	public final float red;
	public final float green;
	public final float blue;

	public BackgroundWidget(int positionX, int positionY, int width, int height, int vOffset) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.vOffset = vOffset;
		this.red = 1.0f;
		this.green = 1.0f;
		this.blue = 1.0f;
	}

	public BackgroundWidget(int positionX, int positionY, int width, int height, int vOffset, float red, float green, float blue) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.vOffset = vOffset;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices, vOffset);
	}

	public void renderBackground(MatrixStack matrices, int vOffset) {
		this.renderBackgroundTexture(positionX, positionY, width, height, vOffset);
	}

	public void renderBackgroundTexture(int positionX, int positionY, int width, int height, int vOffset) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, OPTIONS_BACKGROUND_TEXTURE);
		RenderSystem.setShaderColor(red, green, blue, 1.0F);

		float f = 32.0F;
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(positionX, (double) this.height + positionY, 0.0).texture(
				0.0F, (float) this.height / f + (float) vOffset).color(64, 64, 64, 255).next();
		bufferBuilder.vertex((double) this.width + positionX, (double) this.height + positionY, 0.0).texture(
				(float) this.width / f, (float) this.height / f + (float) vOffset).color(64, 64, 64, 255).next();
		bufferBuilder.vertex((double) this.width + positionX, positionY, 0.0).texture((float) this.width / f, (float) vOffset).color(
				64, 64, 64, 255).next();
		bufferBuilder.vertex(positionX, positionY, 0.0).texture(0.0F, (float) vOffset).color(64, 64, 64, 255).next();
		tessellator.draw();
	}
}
