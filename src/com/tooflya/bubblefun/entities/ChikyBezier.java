package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class ChikyBezier extends EntityBezier {

	// ===========================================================
	// Constants
	// ===========================================================

	protected static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	protected static final int[] pNormalMoveFrames = new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 };
	protected static final int[] pNormalMoveWithGumFrames = new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 };

	protected enum States {
		Move, MoveWithGum, Fall
	};

	// ===========================================================
	// Fields
	// ===========================================================

	private States mState = States.Move;
	private float mX_ = 0; // Last (or old) x.
	private float mTimeWithGum = 0f; // Seconds.

	protected BubbleGum mAirgum = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public ChikyBezier(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
		this.mRotationCenterX = this.mWidth / 2;
		this.mRotationCenterY = this.mHeight / 2;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean isCanCollide() {
		return this.mAirgum == null && this.mState == States.Move;
	}

	private void onManagedUpdateMove(final float pSecondsElapsed) {
		if (this.mAirgum != null) {
			this.mState = States.MoveWithGum;
			this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
		}
	}

	private void onManagedUpdateMoveWithGum(final float pSecondsElapsed) {
		this.mTimeWithGum += pSecondsElapsed;
		if (this.mTimeWithGum >= Options.chikyMaxTimeWithGum) {
			this.mState = States.Fall;
			super.init();
			final float x = (this.getCenterX() - this.mWidth / 2) / (Options.screenWidth - this.mWidth) * 100;
			final float y = (this.getCenterY() - this.mHeight / 2) / (Options.screenHeight - Options.touchHeight - Options.menuHeight - this.mHeight) * 100;
			super.addControlPoint((short)x, (short)y);
			if(x > 50) {
				super.addControlPoint((short)(x + 20), (short)(y - 40));
				super.addControlPoint((short)(x + 40), (short)y);
			}
			else {
				super.addControlPoint((short)(x - 20), (short)(y - 40));
				super.addControlPoint((short)(x - 40), (short)y);
			}
			super.initMaxTime(Float.MAX_VALUE);			

			final BubbleGum airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
			if (airgum != null) {
				airgum.setParent(mAirgum);
				airgum.setSize(this.mAirgum.getWidth(), this.mAirgum.getHeight());
				airgum.initStartPosition(this.getCenterX(), this.getCenterY());
				airgum.initFinishPosition(airgum.getCenterX(), airgum.getCenterY());
			}

			Feather particle;
			for (int i = 0; i < Options.particlesCount; i++) {
				particle = ((LevelScreen) Game.screens.get(Screen.LEVEL)).feathers.create();
				if (particle != null) {
					particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
				}
			}

			this.stopAnimation(6);

			if (Game.random.nextInt(3) == 1) {
				Options.mBirdsDeath1.play();
			} else if (Game.random.nextInt(3) == 2) {
				Options.mBirdsDeath2.play();
			} else {
				Options.mBirdsDeath3.play();
			}
		}
	}

	private void onManagedUpdateFall(final float pSecondsElapsed) {
		this.setRotation(this.getRotation() + 5); // Rotate at 1 degree. Maybe need to correct.
		if (this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void setCollide(BubbleGum airgum) {
		if (this.mAirgum == null) {
			this.mAirgum = airgum.getParent();
			this.mAirgum.AddChildCount();
			this.mTimeWithGum = 0;

			this.mAirgum.mLastX = this.getCenterX();
			this.mAirgum.mLastY = this.getCenterY();

			LevelScreen.Score += 50;

			if (Game.random.nextInt(2) == 1) {
				Options.mBirdsShotted1.play();
			} else {
				Options.mBirdsShotted2.play();
			}
		}
	}

	// ===========================================================
	// Getters
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.setRotation(0);
		
		super.init();

		mState = States.Move;
		this.mX_ = 0;
		this.mTimeWithGum = 0f;

		this.mAirgum = null;

		this.animate(pFrameDuration, pNormalMoveFrames, 9999);

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

		this.mX_ = this.mX;

		switch (this.mState) {
		case Move:
			this.onManagedUpdateMove(pSecondsElapsed);
			break;
		case MoveWithGum:
			this.onManagedUpdateMoveWithGum(pSecondsElapsed);
			break;
		case Fall:
			this.onManagedUpdateFall(pSecondsElapsed);
			break;
		}

		this.getTextureRegion().setFlippedHorizontal(this.mX - this.mX_ < 0);
	}

	@Override
	public void destroy() {
		super.destroy();

		LevelScreen.deadBirds--;
	}
}
