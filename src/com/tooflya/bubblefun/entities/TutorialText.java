package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.modifiers.AlphaModifier;

public class TutorialText extends Sprite {

	public boolean finish;

	private float mElapsedTime, mTime;

	private final AlphaModifier mAlphaModifier1 = new AlphaModifier(1.5f, 0f, 1f) {
		@Override
		public void onFinished() {
			mAlphaModifier.reset();
		}
	};

	private final AlphaModifier mAlphaModifier = new AlphaModifier(10f, 1f, 0f) {
		@Override
		public void onFinished() {
			finish = true;
		}
	};

	public TutorialText(final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public TutorialText(final float x, final float y, final float rotation, final float time, final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, pParentScreen);

		this.setCenterPosition(x, y);

		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotation(rotation);

		this.mTime = time;
		this.mElapsedTime = 0;

		this.enableBlendFunction();

		this.registerEntityModifier(mAlphaModifier1);
		this.registerEntityModifier(mAlphaModifier);
	}

	public Entity create() {
		this.finish = false;

		this.show();

		this.mAlpha = 0;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mAlpha > 0 || finish)
			return;

		this.mElapsedTime += pSecondsElapsed;

		if (this.mElapsedTime >= this.mTime) {
			this.mAlphaModifier1.reset();
		}
	}
	
	
	@Override
	public void destroy() {
		super.destroy();
		
		this.detachSelf();
	}
}
