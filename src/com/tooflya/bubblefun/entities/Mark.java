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

		this.enableBlendFunction();

		this.registerEntityModifier(this.modifier);

		//this.setColor(222f / 255f, 105f / 255f, 219f / 255f);
	}

	@Override
	public Entity create() {
		modifier.reset();

		return super.create();
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		//this.mX += this.getSpeedX();
		//this.mY += this.getSpeedY();
	}

}
