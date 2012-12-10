package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class EntityBezier extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final byte mListCapacity = 10;
	private static final float[] mListX = new float[mListCapacity];
	private static final float[] mListY = new float[mListCapacity];

	// ===========================================================
	// Fields
	// ===========================================================

	private float mX_ = 0; // Last (or old) x.

	protected float mTime = 0f; // Seconds.

	public float mMinTime = 0; // Seconds.
	public float mMaxTime = 1; // Seconds.
	public float mSpeedTime = 0; // Points per seconds.
	public float mOffsetTime = 0; // Seconds.
	public boolean mIsReverseTime = true;

	public static float mKoefSpeedTime = 1;


	public final byte mListBX[] = new byte[mListCapacity];
	public final byte mListBY[] = new byte[mListCapacity];
	private byte mListCount = 0;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public EntityBezier(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void updatePoints()	{
		for(int i = 0; i< this.mListCount; i++)	{
			mListX[i] = this.mListBX[i];
			mListY[i] = this.mListBY[i];
		}
		for (int j = 1; j < this.mListCount; j++) {
			int count = this.mListCount - j;
			for (int i = 0; i < count; i++) {
				mListX[i] = mListX[i] + (mListX[i + 1] - mListX[i]) * this.mTime;
				mListY[i] = mListY[i] + (mListY[i + 1] - mListY[i]) * this.mTime;
			}
		}
		this.mX_ = this.mX;
		this.setCenterPosition(mListX[0] / 100 * Options.screenWidth, mListY[0] / 100 * Options.screenHeight); // TODO: Correct using size and touchHeight.
	}

	public void reset()	{
		this.mTime = this.mOffsetTime;
		this.onManagedUpdate(0);
	}

	// ===========================================================
	// Setters
	// ===========================================================

	// ===========================================================
	// Getters
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.mX_ = 0; // Last (or old) x.

		this.mTime = 0f; // Seconds.

		this.mMinTime = 0; // Seconds.
		this.mMaxTime = 1; // Seconds.
		this.mSpeedTime = 0; // Points per seconds.
		this.mOffsetTime = 0; // Seconds.

		this.mIsReverseTime = true;

		this.mListCount = 0;
		
		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		this.mTime += this.mSpeedTime * pSecondsElapsed * mKoefSpeedTime;

		if (this.mTime < this.mMinTime)	{
			if (this.mIsReverseTime) {
				this.mTime = 2 * this.mMinTime - this.mTime;
				if (this.mSpeedTime < 0) {
					this.mSpeedTime = -this.mSpeedTime;
				}
			}
			else {
				this.mTime += this.mMaxTime - this.mMinTime;
			}
		}
		else if (this.mMaxTime < this.mTime) {
			if (this.mIsReverseTime) {
				this.mTime = 2 * this.mMaxTime - this.mTime;
				if (0 < this.mSpeedTime) {
					this.mSpeedTime = -this.mSpeedTime;
				}
			}
			else {
				this.mTime -= this.mMaxTime - this.mMinTime;
			}
		}

		this.updatePoints();
	}
}
