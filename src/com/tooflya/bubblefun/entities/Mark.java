package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Mark extends Entity {

	private final AlphaModifier modifier = new AlphaModifier(1f, 1f, 0f) {
		@Override
		public void onFinished() {
			destroy();
		}
	};

	public Mark(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableFullBlendFunction();

		this.registerEntityModifier(this.modifier);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		modifier.reset();
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		//this.mX += this.getSpeedX();
		//this.mY += this.getSpeedY();
	}

}
