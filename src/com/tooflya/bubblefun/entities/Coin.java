package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

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

	private final ScaleModifier mScaleModifier = new ScaleModifier(
			TIME_TO_SCALE,
			1f,
			1f + SCALE_FACTOR) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.util.modifier.BaseModifier#onModifierFinished(java.lang.Object)
		 */
		@Override
		public void onFinished() {
			destroy();
		}
	};

	private final AlphaModifier mAlphaModifier = new AlphaModifier(TIME_TO_ALPHA, 1f, ALPHA_FACTOR);

	public boolean mIsAnimationReverse;
	public boolean mIsAlreadyFollow;

	public final float mMaxOffsetY = 1.0f, mMinOffsetY = -1.0f;
	public float mOffsetY;

	public Coin(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();

		this.animate(pFrameDuration, pNormalMoveFrames, 9999);
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
	public Entity create() {
		this.clearEntityModifiers();

		this.registerEntityModifier(mScaleModifier);
		this.registerEntityModifier(mAlphaModifier);

		this.mIsAnimationReverse = true;
		this.mIsAlreadyFollow = false;
		this.mOffsetY = -1.0f;

		this.setAlpha(1);

		return super.create();
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
		if (mScaleModifier.isFinished()) {
			mScaleModifier.reset();
			mAlphaModifier.reset();
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
}
