package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen;

public class Bubble extends BubbleBase {

	// ===========================================================
	// Constants
	// ===========================================================

	private enum States {Creating, Moving, Destroying, WaitingForText};

	// ===========================================================
	// Fields
	// ===========================================================

	private States mState = States.Creating;
	
	private float mTime = 0f; // Seconds.

	private float mLostedSpeed = 0;

	public Bubble mParent = null;
	public float mLastX = 0;
	public float mLastY = 0;
	public int mChildCount = 0;
	
	public int birdsKills;

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

	// ===========================================================
	// Getters
	// ===========================================================

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
		if (distance < Options.eps){
			this.mSpeedX = 0;
			this.mSpeedY = (Options.bubbleMinSpeed + Options.bubbleMaxSpeed) / 2 - this.mLostedSpeed;
			this.mSpeedY = this.mSpeedY < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : this.mSpeedY;
			this.mSpeedY = -this.mSpeedY;
		}
		else{
		distance -= this.mLostedSpeed;
		distance = distance < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : distance;
		distance = distance > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : distance;		
		
		if (0 < angle) {
			angle -= Options.PI;
		}

		this.mSpeedX = distance * FloatMath.cos(angle);
		this.mSpeedY = distance * FloatMath.sin(angle);

		if(Options.bubbleMinSpeed < distance){
			// TODO: (R) Is it needed here?
			Glint particle;
			for (int i = 0; i < 15; i++) {
				particle = ((Glint) ((LevelScreen)Game.screens.get(Screen.LEVEL)).glints.create());
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
			if (this.birdsKills == 1) {
				screen.mAwesomeKillText.create().setCenterPosition(this.mLastX, this.mLastY);		
				final Entity bonus = screen.mBonusesText.create();
				bonus.setCenterPosition(this.mLastX, this.mLastY);
				bonus.setCurrentTileIndex(2);
			}
			else if (this.birdsKills == 2) {
				screen.mDoubleKillText.create().setCenterPosition(this.mLastX, this.mLastY);
				final Entity bonus = screen.mBonusesText.create();
				bonus.setCenterPosition(this.mLastX, this.mLastY);
				bonus.setCurrentTileIndex(0);
			} else if (this.birdsKills == 3) {
				screen.mTripleKillText.create().setCenterPosition(this.mLastX, this.mLastY);		
				final Entity bonus = screen.mBonusesText.create();
				bonus.setCenterPosition(this.mLastX, this.mLastY);
				bonus.setCurrentTileIndex(3);
			}
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

		this.mSpeedX = 0;
		this.mSpeedY = 0;
		this.mLostedSpeed = 0;

		this.mWidth = Options.bubbleMinSize;
		this.mHeight = Options.bubbleMinSize;

		this.birdsKills = 0;

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

		this.mX += this.mSpeedX;
		this.mY += this.mSpeedY;

		if (this.mTime > Options.bubbleMaxTimeMove) {
			this.animate(40, 0);
			this.mState = States.Destroying;
		}
		else if (this.mY + this.getHeightScaled() < 0) {
			this.mState = States.Destroying;
		}
	}

	private void onManagedUpdateDestroying(final float pSecondsElapsed) {
		this.mX += this.mSpeedX;
		this.mY += this.mSpeedY;
		
		if (!this.isAnimationRunning()) {
			if(this.mParent == null){
				this.mY = -this.mHeight;
				this.mState = States.WaitingForText;
			}
			else{
				this.mParent.mChildCount--;
				destroy();
			}
		}
	}

	private void onManagedUpdateWaitingForText(final float pSecondsElapsed) {
		if(this.mChildCount == 0){
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
	}
}
