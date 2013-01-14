package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class Aim extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mIsGoningToDeath;

	private float mTime;
	private boolean mIsAlphaReverse;

	private AimArrow mAimArrow;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Aim(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();
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

		this.setAlpha(0f);

		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotation(0f);

		this.mIsGoningToDeath = false;

		this.mTime = -1f;
		this.mIsAlphaReverse = false;

		this.mAimArrow = ((LevelScreen) Game.screens.get(Screen.LEVEL)).arrows.create();
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#setAlpha(float)
	 */
	@Override
	public void setAlpha(final float pAlpha) {
		super.setAlpha(pAlpha);

		this.mTime = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mRotation += 3f;

		if (this.mAimArrow != null) {
			this.mAimArrow.setCenterPosition(this.getCenterX(), this.getCenterY());
			this.mAimArrow.setAlpha(this.mAlpha);
		}

		if (this.mIsGoningToDeath) {
			this.mAlpha -= 0.01f;

			if (this.mAlpha <= 0f) {
				this.destroy();
			}
		} else {
			if (this.mTime > 0f) {
				this.mTime -= pSecondsElapsed;

				if (this.mIsAlphaReverse) {
					this.mAlpha -= 0.02f;

					if (this.mAlpha <= 0f) {
						this.mIsAlphaReverse = false;
					}
				} else {
					this.mAlpha += 0.02f;

					if (this.mAlpha >= 1f) {
						this.mIsAlphaReverse = true;
					}
				}
			} else {
				if (this.mTime != -1) {
					this.mAlpha = 0f;
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.mAimArrow.destroy();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setTime(final float pTime) {
		this.mTime = pTime;
	}

	public void animate() {
		this.mIsGoningToDeath = true;
	}

	public boolean isAnimate() {
		return this.mIsGoningToDeath;
	}
}
