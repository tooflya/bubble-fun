package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class AimArrow extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ScaleModifier modifier1 = new ScaleModifier(0.5f, 1f, 1.3f) {
		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier2.reset();
		}
	};

	private final ScaleModifier modifier2 = new ScaleModifier(0.5f, 1.3f, 1f) {
		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier1.reset();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public AimArrow(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();

		this.registerEntityModifier(this.modifier1);
		this.registerEntityModifier(this.modifier2);
		
		this.modifier1.reset();
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

		this.setAlpha(1f);

		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		
		this.setRotation(0f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mRotation -= 2f;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}
