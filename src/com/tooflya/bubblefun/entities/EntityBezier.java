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

	private float mTime = 0f; // Seconds.

	private float mMinTime = 0; // Seconds.
	private float mMaxTime = 1; // Seconds.
	private float mSpeedTime = 0; // Points per seconds.
	private float mOffsetTime = 0; // Seconds.
	private boolean mIsReverseTime = true;

	public static float mKoefSpeedTime = 1;


	private final short mListBX[] = new short[mListCapacity];
	private final short mListBY[] = new short[mListCapacity];
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

	public void init() {
		this.mTime = 0f; // Seconds.
		this.mMinTime = 0; // Seconds.
		this.mMaxTime = 1; // Seconds.
		this.mSpeedTime = 0; // Points per seconds.
		this.mOffsetTime = 0; // Seconds.
		this.mIsReverseTime = true;
		this.mListCount = 0;
	}
	
	public void initMinTime(final float pMinTime) {
		this.mMinTime = pMinTime;
	}
	
	public void initMaxTime(final float pMaxTime) {
		this.mMaxTime = pMaxTime;
	}
	
	public void initSpeedTime(final float pSpeedTime) {
		this.mSpeedTime = pSpeedTime;
	}
	
	public void initOffsetTime(final float pOffsetTime) {
		this.mOffsetTime = pOffsetTime;
	}
	
	public void initIsReverseTime(final boolean pIsReverseTime) {
		this.mIsReverseTime = pIsReverseTime;
	}
	
	public void addControlPoint(final short x, final short y) {
		if(this.mListCount < mListCapacity) {
			this.mListBX[this.mListCount] = x;
			this.mListBY[this.mListCount] = y;
			this.mListCount++;
		}
	}
	
	public void reset()	{
		this.mTime = this.mOffsetTime;
		this.onManagedUpdate(0);
	}
	
	private void updatePosition() {
		if(this.mListCount > 0) {
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
			this.setCenterPosition(
					this.mWidth / 2 + mListX[0] / 100 * (Options.cameraWidth - this.mWidth), 
					this.mHeight / 2 + mListY[0] / 100 * (Options.cameraHeight - Options.touchHeight - Options.menuHeight - this.mHeight) + Options.menuHeight); // TODO: Correct using scaled size.
		}	
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

		this.updatePosition();
	}
}
