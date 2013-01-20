package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class Bubble extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	/**
	 *
	 */
	private static enum States {
		Creating,
		Moving,
		Destroying
	};

	// ===========================================================
	// Fields
	// ===========================================================

	private float mScaleStepX;
	private float mScaleStepY;

	public float mLastX = 0;
	public float mLastY = 0;

	private States mCurrentState;

	private float mTime;

	private Entity speed;

	public float mLostedSpeed = 0;

	private Bubble mParent = null;
	private int mChildCount = 0;

	public int mBirdsKills;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pParentScreen
	 */
	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setRotationCenter(Options.bubbleMinSize / 2, Options.bubbleMinSize / 2);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.BubbleBase#create()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		this.mScaleStepX = Options.bubbleBaseStepScale;
		this.mScaleStepY = -Options.bubbleBaseStepScale;

		this.stopAnimation();
		this.setCurrentTileIndex(0);

		this.mCurrentState = States.Creating;
		this.mTime = 0f;

		this.setSpeedX(0);
		this.setSpeedY(0);
		this.mLostedSpeed = 0;

		this.setWidth(Options.bubbleMinSize);
		this.setHeight(Options.bubbleMinSize);

		this.setScale(1f);

		this.mBirdsKills = 0;

		this.mParent = null;
		this.mLastX = 0;
		this.mLastY = 0;
		this.mChildCount = 0;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 */
	private void updateBubbleScaleState() {
		this.mScaleX += this.mScaleStepX;
		this.mScaleY += this.mScaleStepY;

		if (this.mScaleX + this.mScaleStepX < Options.bubbleBaseMinScale) {
			this.mScaleStepX = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleX + this.mScaleStepX) {
			this.mScaleStepX = -Options.bubbleBaseStepScale;
		}

		if (this.mScaleY + this.mScaleStepY < Options.bubbleBaseMinScale) {
			this.mScaleStepY = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleY + this.mScaleStepY) {
			this.mScaleStepY = -Options.bubbleBaseStepScale;
		}
	}

	public void setParent(Bubble pBubble) {
		this.mParent = pBubble.getParent();
		this.mParent.mLastX = this.getCenterX();
		this.mParent.mLastY = this.getCenterY();
		this.mLostedSpeed = this.mParent.mLostedSpeed;
	}

	public Bubble getParent() {
		if (this.mParent == null) {
			return this;
		}
		return this.mParent;
	}

	public boolean isHasParent() {
		return this.mParent == null ? false : true;
	}

	public void initStartPosition(final float x, final float y) {
		this.setCenterPosition(x, y);
		this.mLostedSpeed = 0;
	}

	public void initFinishPosition(final float x, final float y) {
		float angle = (float) Math.atan2(y - this.getCenterY(), x - this.getCenterX());
		float distance = MathUtils.distance(this.getCenterX(), this.getCenterY(), x, y);

		this.setSpeedX(distance * FloatMath.cos(angle));
		this.setSpeedY(distance * FloatMath.sin(angle));

		if (Options.bubbleMinSpeed < distance) {
			this.onSpeedyLaunch();
		}

		this.mTime = 0;
		this.mCurrentState = States.Moving;

		if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion)) {
			((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.clear();
		}
	}

	public void initFinishPositionWithCorrection(final float x, final float y) {
		float angle = (float) Math.atan2(y - this.getCenterY(), x - this.getCenterX());
		float distance = MathUtils.distance(this.getCenterX(), this.getCenterY(), x, y);
		if (distance < Options.eps) {
			this.setSpeedX(0);
			this.setSpeedY((Options.bubbleMinSpeed + Options.bubbleMaxSpeed) / 2 / this.mLostedSpeed);
			this.setSpeedY(this.getSpeedY() < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : this.getSpeedY());
			this.setSpeedY(this.getSpeedY() > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : this.getSpeedY());
			this.setSpeedY(-this.getSpeedY());
		}
		else {
			distance = distance > 6 ? 6 : distance;
			distance /= this.mLostedSpeed;
			distance = distance < Options.bubbleSpeedMinSpeed ? Options.bubbleSpeedMinSpeed : distance;
			distance = distance > Options.bubbleSpeedMaxSpeed ? Options.bubbleSpeedMaxSpeed : distance;

			if (0 < angle) {
				angle -= Options.PI;
			}

			this.setSpeedX(distance * FloatMath.cos(angle));
			this.setSpeedY(distance * FloatMath.sin(angle));

			if (Options.bubbleMinSpeed < distance) {
				this.onSpeedyLaunch();
			}

		}
		this.mTime = 0;
		this.mCurrentState = States.Moving;

		if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion)) {
			((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.clear();
		}
	}

	protected void onSpeedyLaunch() {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			// TODO: (R) Is it needed here?
			Glint particle;
			for (int i = 0; i < 15; i++) {
				particle = ((Glint) ((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.createElement());
				if (particle != null) {
					particle.Init(i, this);
				}
			}

			if (Options.isMusicEnabled) {
				if (Game.random.nextInt(2) == 1) {
					Options.mBubbleFastCreate1.play();
				} else {
					Options.mBubbleFastCreate2.play();
				}
			}
		} else if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion) || this.mTextureRegion.e(Resources.mSpaceBubbleTextureRegion)) {
			this.speed = ((LevelScreen) Game.screens.get(Screen.LEVEL)).mSnowBallSpeed.createElement();
			this.speed.setRotationCenter(this.speed.getWidth() / 2, 0);

			if (Options.isMusicEnabled) {
				if (Game.random.nextInt(2) == 1) {
					Options.mBubbleFastCreate1.play();
				} else {
					Options.mBubbleFastCreate2.play();
				}
			}
		}
	}

	@Override
	public void onCollide() {
		super.onCollide();

		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			this.animate(40, 0);
		} else {
			this.destroy();
		}
		this.mCurrentState = States.Destroying;
	}

	public void addBirdsKills() {
		this.getParent().mBirdsKills++;
	}

	public void AddChildCount() {
		this.getParent().mChildCount++; // TODO: (R) Need raise at setParent(). How?
	}

	protected void onManagedUpdateCreating(final float pSecondsElapsed) {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			if (this.mWidth + Options.bubbleStepSize < Math.min(Options.bubbleMaxSize, Options.bubbleSizePower)) {

				this.mLostedSpeed += Options.bubbleStepSpeed;

				this.setWidth(mWidth + Options.bubbleStepSize);
				this.setHeight(mHeight + Options.bubbleStepSize);

				this.mX -= Options.bubbleStepSize / 2;
				this.mY -= Options.bubbleStepSize / 2;

				this.setScaleCenter(this.mWidth / 2, this.mHeight / 2);

				Options.bubbleSizePower -= Options.bubbleStepSize;
			}
		}
	}

	private void onManagedUpdateMoving(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mTime > Options.bubbleMaxTimeMove && this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
				this.animate(40, 0);
			} else {
				this.destroy();
			}
			this.mCurrentState = States.Destroying;
		}
		else if (this.mY + this.getHeight() < 0) {
			this.mCurrentState = States.Destroying;
		}
	}

	private void onManagedUpdateDestroying(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (!this.isAnimationRunning()) {
			if (this.mParent != null) {
				this.mParent.mChildCount--;
			}
			this.destroy();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bubblefun.entities.Entity#destroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion)) {
			if (Options.isMusicEnabled) {
				Options.mBubbleDeath.play();
			}
		}

		if (this.speed != null) {
			this.speed.destroy();
			this.speed = null;
		}

		if (this.mTextureRegion.e(Resources.mSpaceBubbleTextureRegion)) {
			for (int i = 0; i < 3; i++) {
				((LevelScreen) Game.screens.get(Screen.LEVEL)).mBubbleBrokes.createElement().init(this.getCenterX(), this.getCenterY());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mTime += pSecondsElapsed;

		switch (this.mCurrentState) {
		case Creating:
			this.onManagedUpdateCreating(pSecondsElapsed);
			break;
		case Moving:
			this.onManagedUpdateMoving(pSecondsElapsed);
			break;
		case Destroying:
			this.onManagedUpdateDestroying(pSecondsElapsed);
			break;
		}

		if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion) || this.mTextureRegion.e(Resources.mSpaceBubbleTextureRegion)) {
			this.mRotation += 5;

			if (this.speed != null) {
				this.speed.setCenterPosition(this.getCenterX(), this.getCenterY() + this.getHeight());
				this.speed.setRotation((float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI) + 90);
			}
		}

		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion) && this.mCurrentState != States.Creating) {
			this.updateBubbleScaleState();
		}
	}
}
