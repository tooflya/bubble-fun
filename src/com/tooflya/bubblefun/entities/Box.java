package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Box extends ButtonScaleable {

	private ScaleModifier modifier1 = new ScaleModifier(0.2f, 1f, 0.9f, 1f, 1.1f) {
		@Override
		public void onFinished() {
			modifier2.reset();
		}
	};

	private ScaleModifier modifier2 = new ScaleModifier(0.2f, 0.9f, 1.1f, 1.1f, 0.9f) {
		@Override
		public void onFinished() {
			modifier3.reset();
		}
	};

	private ScaleModifier modifier3 = new ScaleModifier(0.2f, 1.1f, 1f, 0.9f, 1f);

	public Box(TiledTextureRegion pTiledTextureRegion, Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableFullBlendFunction();

		this.registerEntityModifier(modifier1);
		this.registerEntityModifier(modifier2);
		this.registerEntityModifier(modifier3);
	}

	public void animation() {
		modifier1.reset();

		if (this.getAlpha() < 1f) {
			//modifier4.reset();
		}
	}
}
