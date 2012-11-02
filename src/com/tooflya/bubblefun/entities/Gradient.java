package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Screen;

public class Gradient extends org.anddev.andengine.entity.sprite.Sprite {

	public Gradient(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion, final Screen mParent) {
		super(pX, pY, pWidth, pHeight, pTextureRegion);

		mParent.attachChild(this);
	}

	@Override
	protected void onInitDraw(final GL10 pGL) {
		super.onInitDraw(pGL);
		GLHelper.enableTextures(pGL);
		GLHelper.enableTexCoordArray(pGL);
		GLHelper.enableDither(pGL);
	}
}
