package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;

import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;

public class LinearGradientFillBitmapTextureAtlasSourceDecorator extends org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator {

	public LinearGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor,
			org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator.LinearGradientDirection pLinearGradientDirection) {
		super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pFromColor, pToColor, pLinearGradientDirection);
	}

	public void changeColors(final IBitmapTextureAtlasSource pBitmapTextureAtlasSource, final int pFromColor, final int pToColor) {

		final int right = pBitmapTextureAtlasSource.getWidth() - 1;
		final int bottom = pBitmapTextureAtlasSource.getHeight() - 1;

		final float fromX = LinearGradientDirection.BOTTOM_TO_TOP.getFromX(right);
		final float fromY = LinearGradientDirection.BOTTOM_TO_TOP.getFromY(bottom);
		final float toX = LinearGradientDirection.BOTTOM_TO_TOP.getToX(right);
		final float toY = LinearGradientDirection.BOTTOM_TO_TOP.getToY(bottom);

		this.mPaint.setShader(new LinearGradient(fromX, fromY, toX, toY, new int[] { pFromColor, pToColor }, null, TileMode.CLAMP));
		this.mPaint.setDither(true);

		Resources.mBackgroundGradientTextureAtlas2.setUpdateOnHardwareNeeded(true);
	}

	public static enum LinearGradientDirection {
		// ===========================================================
		// Elements
		// ===========================================================

		LEFT_TO_RIGHT(1, 0, 0, 0),
		RIGHT_TO_LEFT(0, 0, 1, 0),
		BOTTOM_TO_TOP(0, 1, 0, 0),
		TOP_TO_BOTTOM(0, 0, 0, 1),
		TOPLEFT_TO_BOTTOMRIGHT(0, 0, 1, 1),
		BOTTOMRIGHT_TO_TOPLEFT(1, 1, 0, 0),
		TOPRIGHT_TO_BOTTOMLEFT(1, 0, 0, 1),
		BOTTOMLEFT_TO_TOPRIGHT(0, 1, 1, 0);

		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private final int mFromX;
		private final int mFromY;
		private final int mToX;
		private final int mToY;

		// ===========================================================
		// Constructors
		// ===========================================================

		private LinearGradientDirection(final int pFromX, final int pFromY, final int pToX, final int pToY) {
			this.mFromX = pFromX;
			this.mFromY = pFromY;
			this.mToX = pToX;
			this.mToY = pToY;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		final int getFromX(int pRight) {
			return this.mFromX * pRight;
		}

		final int getFromY(int pBottom) {
			return this.mFromY * pBottom;
		}

		final int getToX(int pRight) {
			return this.mToX * pRight;
		}

		final int getToY(int pBottom) {
			return this.mToY * pBottom;
		}
	}
}
