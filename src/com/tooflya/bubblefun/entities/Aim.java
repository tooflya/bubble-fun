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

	private AimArrow mAimArrow;
	private Entity mTimeBar;
	private TimerNumber mTimeNumber;

	private boolean mIsSecond;

	private Chiky mParent;

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
		this.mIsSecond = false;

		this.mTime = -1f;

		this.mAimArrow = ((LevelScreen) Game.screens.get(Screen.LEVEL)).arrows.create();
		this.mTimeBar = ((LevelScreen) Game.screens.get(Screen.LEVEL)).timerBars.create();
		this.mTimeNumber = ((LevelScreen) Game.screens.get(Screen.LEVEL)).timerNumbers.create();

		this.mTimeBar.enableBlendFunction();
		this.mTimeBar.setAlpha(0f);
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
			this.mTimeBar.setCenterPosition(this.getCenterX() - 15f, this.getCenterY() + 15f);
			this.mTimeNumber.setCenterPosition(this.mTimeBar.getCenterX() - 2f, this.mTimeBar.getCenterY() + 2f);

			if (!this.mIsSecond) {
				this.mAimArrow.setAlpha(this.mAlpha);
			} else {
				this.mAimArrow.setAlpha(0f);
			}
		}

		if (this.mIsGoningToDeath) {
			this.mAlpha -= 0.01f;

			if (this.mAlpha <= 0f) {
				this.destroy();
			}
		} else {
			if (this.mTime > 0f) {
				this.mTime -= pSecondsElapsed;
				this.mTimeBar.setAlpha(1f);
			} else {
				if (this.mTime != -1) {
					if (this.mAlpha > 0f) {
						this.mAlpha -= 0.05f;
						this.mTimeBar.setAlpha(this.mAlpha);
						this.mParent.mIsFirst = false;
						this.mParent.mIsFirstForTime = false;

						if (this.mParent.mIsWasSecond) {
							this.mParent.mIsWasSecond = false;
							this.mParent.setSecond();
						}
					}
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

	public void setChiky(final Chiky pChiky) {
		this.mParent = pChiky;
	}

	public void setTime(final float pTime) {
		this.mTime = pTime;
		this.mTimeNumber.animate();
		this.mIsSecond = false;
	}

	public void animate() {
		this.mIsGoningToDeath = true;
		this.mTimeBar.destroy();
		this.mTimeNumber.destroy();
	}

	public boolean isAnimate() {
		return this.mIsGoningToDeath;
	}

	public void setSecond() {
		this.mIsSecond = true;
		this.mTimeBar.setAlpha(0f);
		this.mTimeNumber.setAlpha(0f);
	}

	public void setFirst() {
		this.mIsSecond = false;
		this.mTimeBar.setAlpha(0f);
		this.mTimeNumber.setAlpha(0f);
		this.mTime = -1;
	}
}
