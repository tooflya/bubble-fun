package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class TimerNumber extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int counter;

	private final ScaleModifier modifier1 = new ScaleModifier(1f, 1.5f, 0.5f) {
		@Override
		public void onFinished() {
			setCurrentTileIndex(++counter);

			if (counter >= 5) {
				mAlpha = 0f;
			} else {
				modifier1.reset();
			}
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public TimerNumber(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();

		this.registerEntityModifier(this.modifier1);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#create()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		this.mAlpha = 0f;

		this.counter = 0;

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setCurrentTileIndex(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void animate() {
		this.mAlpha = 1f;
		this.counter = 0;
		this.modifier1.reset();
		this.setCurrentTileIndex(0);
	}
}
