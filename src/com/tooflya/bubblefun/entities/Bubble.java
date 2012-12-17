package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class Bubble extends BubbleBase {

	// ===========================================================
	// Constants
	// ===========================================================

	private enum States {
		Creating, Moving, Destroying, WaitingForText
	};

	// ===========================================================
	// Fields
	// ===========================================================

	private Sprite speed;
	private States mState = States.Creating;

	private float mTime = 0f; // Seconds.

	public float mLostedSpeed = 0;

	private Bubble mParent = null;
	float mLastX = 0;
	float mLastY = 0;
	private int mChildCount = 0;

	public int mBirdsKills;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setRotationCenter(Options.bubbleMinSize / 2, Options.bubbleMinSize / 2);
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void setParent(Bubble pBubble) {
		this.mParent = pBubble.getParent();
		this.mParent.mLastX = this.getCenterX();
		this.mParent.mLastY = this.getCenterY();
		this.mLostedSpeed = this.mParent.mLostedSpeed;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	public Bubble getParent() {
		if (this.mParent == null) {
			return this;
		}
		return this.mParent;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void initStartPosition(final float x, final float y) {
		this.setCenterPosition(x, y);
		this.mLostedSpeed = 0;

		if (this.mTextureRegion.e(Resources.mSpaceBubbleTextureRegion)) {
			LevelScreen.AIR -= 10;
		}
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
		this.mState = States.Moving;

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
		this.mState = States.Moving;

		if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion)) {
			((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.clear();
		}
	}

	protected void onSpeedyLaunch() {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			// TODO: (R) Is it needed here?
			Glint particle;
			for (int i = 0; i < 15; i++) {
				particle = ((Glint) ((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.create());
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
			this.speed = ((LevelScreen) Game.screens.get(Screen.LEVEL)).mSnowBallSpeed.create();
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

	public void isCollide() {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			this.animate(40, 0);
		} else {
			this.destroy();
		}
		this.mState = States.Destroying;
	}

	public boolean isCanCollide() {
		return this.mState == States.Moving;
	}

	private void writeText() {
		LevelScreen screen = ((LevelScreen) Game.screens.get(Screen.LEVEL));
		if (this.mBirdsKills == 1 && screen.chikies.getCount() <= 1 && LevelScreen.deadBirds <= 0) {
			this.mBirdsKills--;
			final Entity text = screen.mAwesomeKillText.create();
			text.setCenterPosition(this.mLastX, this.mLastY);
			final Entity bonus = screen.mBonusesText.create();
			bonus.setCenterPosition(text.getCenterX(), text.getCenterY());
			bonus.setCurrentTileIndex(2);
		}
		else if (this.mBirdsKills == 2) {
			this.mBirdsKills -= 2;
			final Entity text = screen.mDoubleKillText.create();
			text.setCenterPosition(this.mLastX, this.mLastY);
			final Entity bonus = screen.mBonusesText.create();
			bonus.setCenterPosition(text.getCenterX(), text.getCenterY());
			bonus.setCurrentTileIndex(0);
		} else if (this.mBirdsKills >= 3) {
			this.mBirdsKills -= 3;
			final Entity text = screen.mTripleKillText.create();
			text.setCenterPosition(this.mLastX, this.mLastY);
			final Entity bonus = screen.mBonusesText.create();
			bonus.setCenterPosition(text.getCenterX(), text.getCenterY());
			bonus.setCurrentTileIndex(3);
		}
	}

	public void addBirdsKills() {
		this.getParent().mBirdsKills++;
	}

	public void AddChildCount() {
		this.getParent().mChildCount++; // TODO: (R) Need raise at setParent(). How?
	}

	// ===========================================================
	// Virtual Methods
	// ===========================================================

	@Override
	public Entity create() {
		this.stopAnimation();
		this.setCurrentTileIndex(0);

		this.mState = States.Creating;
		this.mTime = 0f; // Seconds.

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

		return super.create();
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

				LevelScreen.AIR--;
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
			this.mState = States.Destroying;
		}
		else if (this.mY + this.getHeight() < 0) {
			this.mState = States.Destroying;
		}
	}

	private void onManagedUpdateDestroying(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (!this.isAnimationRunning()) {
			if (this.mParent == null) {
				this.mState = States.WaitingForText;
			}
			else {
				this.mParent.mChildCount--;
				destroy();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bubblefun.entities.Entity#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();

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
				((LevelScreen) Game.screens.get(Screen.LEVEL)).mBubbleBrokes.create().init(this.getCenterX(), this.getCenterY());
			}
		}
	}

	private void onManagedUpdateWaitingForText(final float pSecondsElapsed) {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion)) {
			final boolean isNoChikies = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies.getCount() == 0;
			final int b = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies.getCount();
			if (this.mChildCount == 0 || isNoChikies || this.mBirdsKills > 2 || (this.mBirdsKills > 0 && b - this.mBirdsKills <= 0) || b <= 2) {
				this.writeText();
				this.destroy(); // TODO: (R) Can be lost memory.
			}
		} else {
			this.writeText();
			this.destroy(); // TODO: (R) Can be lost memory.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		if (this.mTextureRegion.e(Resources.mBubbleTextureRegion) && this.mState != States.Creating) {
			super.onManagedUpdate(pSecondsElapsed);
		}

		this.mTime += pSecondsElapsed;

		switch (this.mState) {
		case Creating:
			this.onManagedUpdateCreating(pSecondsElapsed);
			break;
		case Moving:
			this.onManagedUpdateMoving(pSecondsElapsed);
			break;
		case Destroying:
			this.onManagedUpdateDestroying(pSecondsElapsed);
			break;
		case WaitingForText:
			this.onManagedUpdateWaitingForText(pSecondsElapsed);
			break;
		}

		final int b = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies.getCount();
		if (this.mChildCount == 0 || this.mBirdsKills > 2 || (this.mBirdsKills > 0 && b - this.mBirdsKills <= 0)) {
			this.writeText();
		}

		if (this.mTextureRegion.e(Resources.mSnowyBubbleTextureRegion) || this.mTextureRegion.e(Resources.mSpaceBubbleTextureRegion)) {
			this.mRotation += 5;

			if (this.speed != null) {
				this.speed.setCenterPosition(this.getCenterX(), this.getCenterY() + this.getHeight());
				this.speed.setRotation((float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI) + 90);
			}
		}
	}
}
