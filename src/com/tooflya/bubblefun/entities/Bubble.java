package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.FloatMath;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;

public class Bubble extends BubbleBase {

	// ===========================================================
	// Constants
	// ===========================================================

	public final static float mMaxFastSpeed = 4f;

	//private final static float mDecrementStep = 0.05f / Options.SPEED;
	//private final static float mFastDecrementStep = 0.1f / Options.SPEED;

	private enum States {
		Creating, Moving, Destroying
	};

	// ===========================================================
	// Fields
	// ===========================================================

	private States pState = States.Creating;
	private float pTime = 0f; // Seconds.

	//protected float mSpeedDecrement;
	//protected float mFastSpeedDecrement;

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
	}

	public void initFinishPosition(final float x, final float y) {

		float angle = (float) Math.atan2(y - this.getCenterY(), x - this.getCenterX());
		float distance = MathUtils.distance(this.getCenterX(), this.getCenterY(), x, y);
		distance = distance > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : distance;

		if (0 < angle) {
			angle -= Options.PI;
		}

		this.mSpeedX = distance * FloatMath.cos(angle);
		this.mSpeedY = distance * FloatMath.sin(angle);

		// TODO: (R) Is it needed here?
		//Glint particle;
		//for (int i = 0; i < 15; i++) {
		//particle = ((Glint) glints.create());
		//if (particle != null) {
		//particle.Init(i, this.lastAirgum);
		//}
		//}

		this.pState = States.Moving;
	}

	public void isCollide() {
		this.animate(40, 0);
		this.pState = States.Destroying;
	}

	public boolean isCanCollide() {
		return this.pState != States.Destroying;
	}

	// ===========================================================
	// Virtual Methods
	// ===========================================================

	@Override
	public Entity create() {
		this.stopAnimation();
		this.setCurrentTileIndex(0);

		this.pState = States.Creating;
		this.pTime = 0f; // Seconds.

		this.mSpeedX = 0;
		this.mSpeedY = Options.bubbleMinSpeed;

		this.mWidth = Options.bubbleMinSize;
		this.mHeight = Options.bubbleMinSize;

		this.birdsKills = 0;

		return super.create();
	}

	protected void onManagedUpdateCreating(final float pSecondsElapsed) {
		if (this.mWidth + Options.bubbleSizeStep < Math.min(Options.bubbleMaxSize, Options.bubbleSizePower)) {

			//this.mSpeedDecrement += mDecrementStep;
			//this.mFastSpeedDecrement += mFastDecrementStep;

			this.mWidth += Options.bubbleSizeStep;
			this.mHeight += Options.bubbleSizeStep;

			Options.bubbleSizePower -= Options.bubbleSizeStep;

			LevelScreen.AIR--;
		}
	}

	protected void onManagedUpdateMoving(final float pSecondsElapsed) {

		this.mX += this.mSpeedX;
		this.mY += this.mSpeedY;

		if (this.pTime > Options.bubbleMaxTimeMove) {
			this.animate(40, 0);
			this.pState = States.Destroying;
		}
		else if (this.mY + this.getHeightScaled() < 0) {
			this.pState = States.Destroying;
		}
	}

	protected void onManagedUpdateDestroying(final float pSecondsElapsed) {
		if (!this.isAnimationRunning()) {
			//			if (this.birdsKills > 0 && !this.mShowsLabel) {
			//
			//				boolean hasTopChiks = false;
			//
			//				EntityManager<Chiky> chikies = ((LevelScreen) Game.screens.get(Screen.LEVEL)).chikies;
			//				
			//				for (int i = chikies.getCount() - 1; i >= 0; --i) {
			//					if (chikies.getByIndex(i).getY() < this.getY()) {
			//						hasTopChiks = true;
			//					}
			//				}
			//
			//				LevelScreen screen = ((LevelScreen) Game.screens.get(Screen.LEVEL));
			//				
			//				if (!hasTopChiks) {
			//					this.mShowsLabel = true;
			//
			//					if (this.birdsKills == 1 && chikies.getCount() <= 1) {
			//						screen.mAwesomeKillText.create().setCenterPosition(this.getCenterX(), this.getCenterY());
			//
			//						final Entity bonus = screen.mBonusesText.create();
			//						bonus.setCenterPosition(this.getCenterX(), this.getCenterY());
			//						bonus.setCurrentTileIndex(2);
			//					}
			//					else if (this.birdsKills == 2) {
			//						screen.mDoubleKillText.create().setCenterPosition(this.getCenterX(), this.getCenterY());
			//
			//						final Entity bonus = screen.mBonusesText.create();
			//						bonus.setCenterPosition(this.getCenterX(), this.getCenterY());
			//						bonus.setCurrentTileIndex(0);
			//					} else if (this.birdsKills == 3) {
			//						screen.mTripleKillText.create().setCenterPosition(this.getCenterX(), this.getCenterY());
			//
			//						final Entity bonus = screen.mBonusesText.create();
			//						bonus.setCenterPosition(this.getCenterX(), this.getCenterY());
			//						bonus.setCurrentTileIndex(3);
			//					}
			//				}
			//			}

			this.destroy();
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

		this.pTime += pSecondsElapsed;

		switch (this.pState) {
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
	}
}
