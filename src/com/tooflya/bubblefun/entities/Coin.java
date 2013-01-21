package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;

public class Coin extends Entity {

	private static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private static final int[] pNormalMoveFrames = new int[] { 12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3 };

	private final static float TIME_TO_FOLLOW = 0.4f;
	private final static float TIME_TO_SCALE = 0.2f;
	private final static float TIME_TO_ALPHA = 0.2f;
	private final static float TIME_TO_ANIMATION = 0.02f;

	private final static float SCALE_FACTOR = 0.2f;
	private final static float ALPHA_FACTOR = 0.2f;

	// ===========================================================
	// Fields
	// ===========================================================

	private final AlphaModifier mAlphaModifier = new AlphaModifier(TIME_TO_ALPHA, 1f, ALPHA_FACTOR);

	public boolean mIsAnimationReverse;
	public boolean mIsAlreadyFollow;

	public final float mMaxOffsetY = 1.0f, mMinOffsetY = -1.0f;
	public float mOffsetY;

	private final ScaleModifier mFinishScaleModifier = new ScaleModifier(1f, 1f, 2f);
	private MoveModifier mFinishMoveModifier;

	public Coin(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
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

		this.stopAnimation();
		this.animate(pFrameDuration, pNormalMoveFrames, 9999);

		this.clearEntityModifiers();

		this.registerEntityModifier(this.mFinishScaleModifier);
		this.registerEntityModifier(mAlphaModifier);

		this.mIsAnimationReverse = true;
		this.mIsAlreadyFollow = false;
		this.mOffsetY = -1.0f;

		this.setAlpha(1f);
		this.setScale(1f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mIsAnimationReverse) {
			this.mOffsetY += TIME_TO_ANIMATION;
			if (this.mOffsetY > this.mMaxOffsetY) {
				this.mIsAnimationReverse = false;
			}
		} else {
			this.mOffsetY -= TIME_TO_ANIMATION;
			if (this.mOffsetY < this.mMinOffsetY) {
				this.mIsAnimationReverse = true;
			}
		}

		this.mY += mOffsetY;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 */
	public void remove() {
		this.mFinishMoveModifier = new MoveModifier(0.3f, this.getX(), 10f, this.getY(), Options.cameraHeight - 30f) {
			@Override
			public void onFinished() {
				mFinishScaleModifier.stop();

				destroy();
			}
		};

		this.registerEntityModifier(this.mFinishMoveModifier);

		this.mFinishScaleModifier.reset();
		this.mFinishMoveModifier.reset();

		if (Options.isSoundEnabled) {
			Options.mCoinPickup.play();
		}
	}

	/**
	 * 
	 */
	public void follow(final Entity pEntity) {
		if (!this.mIsAlreadyFollow) {
			this.mIsAlreadyFollow = true;

			final MoveModifier move = new MoveModifier(TIME_TO_FOLLOW, this.mX, pEntity.getX(), this.mY, pEntity.getY());
			this.registerEntityModifier(move);

			move.reset();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		LevelScreen.mPicupedCoins++;
	}
}
