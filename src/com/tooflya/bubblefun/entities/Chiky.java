package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class Chiky extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private static final int[] pNormalMoveFrames = new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 };
	private static final int[] pNormalMoveWithGumFrames = new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 };
	private static final int[] pSpeedyMoveFrames = new int[] { 12, 13, 14, 15, 16, 17, 16, 15, 14, 13 };
	private static final int[] pSpeedyMoveWithGumFrames = new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 };

	private enum States {
		NormalMove, SpeedyMove, Fall, Parachute
	};

	private static final int isJumplyFlag = 1;
	private static final int isSpeedyFlag = 2;
	private static final int isWavelyFlag = 4;
	private static final int isParachuteFlag = 8;
	// ===========================================================
	// Fields
	// ===========================================================

	private States mState = States.NormalMove;

	private float mTime = 0f; // Seconds.
	private float mTimeWithGum = 0f; // Seconds.
	private float mAngle = 0f; // Degree.

	private float mStartX = 0;
	private float mStartY = 0;
	private float mNormalStepX = 0;
	private float mSpeedyStepX = 0;
	private float mOffsetX = 0;

	private int mProperties = 0;

	private float mX_ = 0; // Last (or old) x.

	private Bubble mAirgum = null;
	private Acceleration mWind = null;
	private Entity mParahute = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Chiky(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
		this.mRotationCenterX = this.mWidth / 2;
		this.mRotationCenterY = this.mHeight / 2;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public Entity create() {
		this.setRotation(0);

		mState = States.NormalMove;

		// Center of used region.
		this.mStartX = Options.cameraWidth / 2;
		this.mStartY = Options.chikyEtalonSize / 2 + Options.chikyOffsetY + (Options.cameraHeight - Options.touchHeight - Options.chikyEtalonSize - 2 * Options.chikyOffsetY) / 2;

		this.mNormalStepX = Game.random.nextFloat() * (Game.correctSpeed(Options.chikyMaxStepX) - Game.correctSpeed(Options.chikyMinStepX)) + Game.correctSpeed(Options.chikyMinStepX);
		this.mSpeedyStepX = this.mNormalStepX * Game.correctSpeed(Options.chikySpeedCoeficient);
		this.setSpeedX(this.mNormalStepX);

		this.mOffsetX = 0;

		this.mProperties = 0;

		this.mX_ = 0;

		this.mTime = 0f;
		this.mTimeWithGum = 0f;

		this.mAngle = 0f; // Degree.

		this.mAirgum = null;
		this.mWind = null;

		this.animate(pFrameDuration, pNormalMoveFrames, 9999);

		return super.create();
	}

	public void initStartX(final float startX) {
		this.mX = startX;
		this.mStartX = startX;
	}

	public void initStartY(final float startY) {
		this.mY = startY;
		this.mStartY = startY;
	}

	public void initNormalStepX(final float normalStepX) {
		this.mNormalStepX = normalStepX;
		this.setSpeedX(this.mNormalStepX);
	}

	public void initSpeedyStepX(final float speedyStepX) {
		this.mSpeedyStepX = speedyStepX;
	}

	public void initOffsetX(final float offsetX) {
		this.setSpeedX(offsetX);
	}

	public void initScale(final float scale) {
		this.setWidth(this.mBaseWidth * scale);
		this.setHeight(this.mBaseHeight * scale);
	}

	public void initIsJumply() {
		this.mProperties = this.mProperties | isJumplyFlag;
	}

	public void initIsSpeedy() {
		this.mProperties = this.mProperties | isSpeedyFlag;
	}

	public void initIsWavely() {
		this.mProperties = this.mProperties | isWavelyFlag;
	}

	public void initIsParachute() {
		this.mProperties = this.mProperties | isParachuteFlag;
		this.setSpeedY(1f);
	}

	public void initStateByNumber(final int pNumber) {
		switch (pNumber) {
		case isJumplyFlag:
			this.initIsJumply();
			break;
		case isSpeedyFlag:
			this.initIsSpeedy();
			break;
		case isWavelyFlag:
			this.initIsWavely();
			break;
		case isParachuteFlag:
			this.initIsParachute();
			break;
		}
	}

	private boolean IsProperty(int flag) {
		return (this.mProperties & flag) == flag;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void isCollide(Bubble airgum) {
		if (this.mAirgum == null) {
			this.mAirgum = airgum.getParent();
			this.mAirgum.AddChildCount();
			this.mTimeWithGum = 0;

			this.mAirgum.mLastX = this.getCenterX();
			this.mAirgum.mLastY = this.getCenterY();

			if (this.mState == States.NormalMove) {
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
			}
			else { // States.SpeedyMove.
				this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
			}
		}
	}

	// ===========================================================
	// Getters
	// ===========================================================

	public boolean isCanCollide() {
		return this.mAirgum == null;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	private void onManagedUpdateWithGum(final float pSecondsElapsed) {
		this.mTimeWithGum += pSecondsElapsed;
		if (this.mTimeWithGum >= Options.chikyMaxTimeWithGum) {
			this.mTime = 0;
			if (this.mState == States.SpeedyMove) {
				this.mWind.destroy();
			}
			this.mState = States.Fall;
			this.mStartX = this.getCenterX();
			this.mStartY = this.getCenterY();
			if (this.getCenterX() < Options.cameraWidth / 2) {
				this.setSpeedX(this.mNormalStepX);
			}
			else {
				this.setSpeedX(-this.mNormalStepX);
			}

			final Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
			if (airgum != null) {
				airgum.initStartPosition(this.getCenterX(), this.getCenterY());
				airgum.initFinishPosition(this.getCenterX(), this.getCenterY() + this.mAirgum.getOriginSpeedY());
				airgum.setSize(this.mAirgum.getWidth(), this.mAirgum.getHeight());
				airgum.setParent(mAirgum);
			}

			Feather particle;
			for (int i = 0; i < Options.particlesCount; i++) {
				particle = ((LevelScreen) Game.screens.get(Screen.LEVEL)).feathers.create();
				if (particle != null) {
					particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
				}
			}

			this.stopAnimation(6);
		}
	}

	private void onManagedUpdateMove(final float pSecondsElapsed) {
		float x = this.getCenterX() + this.getSpeedX();

		boolean isBorder = false;
		final float minX = 0 - this.mOffsetX + this.mWidth / 2;
		if (x < minX) {
			x = 2 * minX - x;
			this.setSpeedX(Math.abs(this.getOriginSpeedX()));
			isBorder = true;
		}
		final float maxX = Options.cameraWidth + this.mOffsetX - this.mWidth / 2;
		if (x > maxX) {
			x = 2 * maxX - x;
			this.setSpeedX(-Math.abs(this.getOriginSpeedX()));
			isBorder = true;
		}
		if (isBorder && this.IsProperty(isJumplyFlag)) {
			this.mStartY = Options.chikyEtalonSize / 2 + Game.random.nextFloat() * (Options.cameraHeight - Options.touchHeight - Options.chikyEtalonSize);
		}

		float y = this.mStartY;
		if (this.IsProperty(isWavelyFlag)) {
			y += FloatMath.sin(this.mAngle * Options.PI * Math.abs(this.getSpeedX()) / Options.cameraWidth) * Options.chikyOffsetY;
		}

		this.setCenterPosition(x, y);

		if (this.mAirgum != null) {
			this.onManagedUpdateWithGum(pSecondsElapsed);
		}
	}

	private void onManagedUpdateNormal(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);
		if (this.mTime >= Options.chikyMaxTimeNormal) {
			this.mTime = 0;
			if (this.IsProperty(isSpeedyFlag)) {
				this.setSpeedX(Math.signum(this.getOriginSpeedX()) * this.mSpeedyStepX);
				this.mState = States.SpeedyMove;
				this.mWind = ((Acceleration) ((LevelScreen) Game.screens.get(Screen.LEVEL)).accelerators.create());
				this.mWind.mFollowEntity = this;
				if (this.mAirgum == null) {
					this.animate(pFrameDuration, pSpeedyMoveFrames, 9999);
				}
				else {
					this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
				}
			} else if (this.IsProperty(isParachuteFlag)) {
				this.mState = States.Parachute;
				this.mParahute = ((LevelScreen) Game.screens.get(Screen.LEVEL)).parachutes.create();
			}
			// !!! Maybe need use switch for another moving.
		}
	}

	private void onManagedUpdateSpeedy(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);
		if (this.mTime >= Options.chikyMaxTimeSpeedy) {
			this.mTime = 0;
			this.setSpeedX(Math.signum(this.getOriginSpeedX()) * this.mNormalStepX);
			this.mState = States.NormalMove;
			this.mWind.destroy();
			this.mWind = null;
			if (this.mAirgum == null) {
				this.animate(pFrameDuration, pNormalMoveFrames, 9999);
			}
			else {
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
			}
			// !!! Maybe need use switch for another moving.
		}
	}

	private void onManagedUpdateFall(final float pSecondsElapsed) {
		this.mY += Game.correctSpeed(Options.chikyFallSpeed);

		this.setRotation(this.getRotation() + 5); // Rotate at 1 degree. Maybe need to correct.

		if (this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}

	private void onManagedUpdateParachute(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);

		this.mStartY += this.getSpeedY();

		this.mParahute.setCenterPosition(this.getCenterX(), this.getY() - this.getHeight() / 2 + 12);

		if (this.mY > Options.touchHeight * 2 - this.getHeight()) {
			this.setSpeedY(-Math.abs(this.getSpeedY()));
		}
		if (this.mY < this.getHeight()) {
			this.setSpeedY(Math.abs(this.getSpeedY()));
		}

		this.mParahute.getTextureRegion().setFlippedHorizontal(this.getTextureRegion().isFlippedHorizontal());

		if (mTime >= Options.chickyMaxTimeParachute) {
			this.mTime = 0;
			this.mState = States.NormalMove;

			this.mParahute.destroy();
			this.mParahute = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mTime += pSecondsElapsed;
		this.mAngle += Options.chikyAngleStep;

		this.mX_ = this.mX;

		switch (this.mState) {
		case NormalMove:
			this.onManagedUpdateNormal(pSecondsElapsed);
			break;
		case SpeedyMove:
			this.onManagedUpdateSpeedy(pSecondsElapsed);
			break;
		case Fall:
			this.onManagedUpdateFall(pSecondsElapsed);
			break;
		case Parachute:
			this.onManagedUpdateParachute(pSecondsElapsed);
			break;
		}

		if (this.mX - this.mX_ > 0) {
			this.getTextureRegion().setFlippedHorizontal(false);
		}
		else {
			this.getTextureRegion().setFlippedHorizontal(true);
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		LevelScreen.deadBirds--;
	}
}
