package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
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

	private States mState = States.Creating;

	private float mTime = 0f; // Seconds.

	public float mLostedSpeed = 0;

	private Bubble mParent = null;
	float mLastX = 0;
	float mLastY = 0;
	private int mChildCount = 0;

	private int mBirdsKills;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setScaleCenter(this.mWidth / 2, this.mHeight / 2);
		this.setRotationCenter(this.mWidth / 2, this.mHeight / 2);
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void setParent(Bubble pBubble) {
		this.mParent = pBubble.getParent();
		this.mParent.mLastX = this.getCenterX();
		this.mParent.mLastY = this.getCenterY();
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
	}

	public void initFinishPosition(final float x, final float y) {

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
			distance = distance < Options.bubbleSpeedMinSpeed? Options.bubbleSpeedMinSpeed : distance;
			distance = distance > Options.bubbleSpeedMaxSpeed ? Options.bubbleSpeedMaxSpeed : distance;

			if (0 < angle) {
				angle -= Options.PI;
			}

			this.setSpeedX(distance * FloatMath.cos(angle));
			this.setSpeedY(distance * FloatMath.sin(angle));

			if (Options.bubbleMinSpeed < distance) {
				// TODO: (R) Is it needed here?
				Glint particle;
				for (int i = 0; i < 15; i++) {
					particle = ((Glint) ((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.create());
					if (particle != null) {
						particle.Init(i, this);
					}
				}
			}
		}
		this.mTime = 0;
		this.mState = States.Moving;
	}

	public void isCollide() {
		this.animate(40, 0);
		this.mState = States.Destroying;
	}

	public boolean isCanCollide() {
		return this.mState == States.Moving;
	}

	private void writeText() {
		LevelScreen screen = ((LevelScreen) Game.screens.get(Screen.LEVEL));
		if (this.mBirdsKills == 1 && screen.chikies.getCount() <= 1) {
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

		this.mWidth = Options.bubbleMinSize;
		this.mHeight = Options.bubbleMinSize;

		this.mBirdsKills = 0;

		this.mParent = null;
		this.mLastX = 0;
		this.mLastY = 0;
		this.mChildCount = 0;

		return super.create();
	}

	private void onManagedUpdateCreating(final float pSecondsElapsed) {
		if (this.mWidth + Options.bubbleStepSize < Math.min(Options.bubbleMaxSize, Options.bubbleSizePower)) {

			this.mLostedSpeed += Options.bubbleStepSpeed;

			this.setWidth(mWidth + Options.bubbleStepSize);
			this.setHeight(mHeight + Options.bubbleStepSize);
			this.mX -= Options.bubbleStepSize / 2;
			this.mY -= Options.bubbleStepSize / 2;

			Options.bubbleSizePower -= Options.bubbleStepSize;

			LevelScreen.AIR--;
		}
	}

	private void onManagedUpdateMoving(final float pSecondsElapsed) {

		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mTime > Options.bubbleMaxTimeMove) {
			this.animate(40, 0);
			this.mState = States.Destroying;
		}
		else if (this.mY + this.getHeightScaled() < 0) {
			this.mState = States.Destroying;
		}
	}

	private void onManagedUpdateDestroying(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (!this.isAnimationRunning()) {
			if (this.mParent == null) {
				this.mY = -this.mHeight;
				this.mState = States.WaitingForText;
			}
			else {
				this.mParent.mChildCount--;
				destroy();
			}
		}
	}

	private void onManagedUpdateWaitingForText(final float pSecondsElapsed) {
		final boolean isNoChikies = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies.getCount() == 0;
		final int b = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies.getCount();
		if (this.mChildCount == 0 || isNoChikies || this.mBirdsKills > 2 || (this.mBirdsKills > 0 && b - this.mBirdsKills <= 0)) {
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
		super.onManagedUpdate(pSecondsElapsed);

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
	}
}
