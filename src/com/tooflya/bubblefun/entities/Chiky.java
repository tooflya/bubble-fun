package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

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
public class Chiky extends EntityBezier {

	// ===========================================================
	// Constants
	// ===========================================================

	protected static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	protected static final int[] pNormalMoveFrames = new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 };
	protected static final int[] pNormalMoveWithGumFrames = new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 };
	protected static int[] pSpeedyMoveFrames = new int[] { 12, 13, 14, 15, 16, 17, 16, 15, 14, 13 };
	private static final int[] pSpeedyMoveWithGumFrames = new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 };

	protected static final long[] pSpaceFrameDuration = new long[] { 50, 50, 50, 300, 50, 50 };
	protected static final int[] pSpaceNormalMoveFrames = new int[] { 0, 1, 2, 3, 2, 1 };
	protected static final int[] pSpaceWithGumFrames = new int[] { 4, 5, 6, 7, 6, 5 };

	protected enum States {
		NormalMove, SpeedyMove, Fall, Vector, Move, MoveWithGum
	};

	public static final int isPauseUpdateFlag = 1;
	public static final int isJumplyFlag = 2;
	public static final int isSpeedyFlag = 4;
	public static final int isWavelyFlag = 8;
	public static final int isVectorFlag = 32;
	// ===========================================================
	// Fields
	// ===========================================================

	protected States mState = States.NormalMove;

	protected float mTime = 0f; // Seconds.
	protected float mTimeWithGum = 0f; // Seconds.
	private float mAngle = 0f; // Degree.

	protected float mStartX = 0;
	protected float mStartY = 0;
	protected float mNormalStepX = 0;
	private float mSpeedyStepX = 0;
	private float mOffsetX = 0;

	private int mProperties = 0;

	private float mX_ = 0; // Last (or old) x.

	private Bubble mAirgum = null;
	protected Acceleration mWind = null;

	private float vectorX, vectorY, vectorLimit, vectorUpdates, vectorStopUpdates;
	private boolean vectorReverse, vectorStopSecond;

	private CristmasHat hat;

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

	public void initState(final boolean isOldStyle) {
		if (isOldStyle) {
			this.mState = States.NormalMove;
		}
		else {
			this.mState = States.Move;
		}
	}

	public void initStartX(final float startX) {
		this.setCenterX(startX);
		this.mStartX = startX;
	}

	public void initStartY(final float startY) {
		this.setCenterY(startY);
		this.mStartY = startY;
	}

	public void initNormalStepX(final float normalStepX) {
		this.mNormalStepX = normalStepX;
		this.setSpeedX(this.mNormalStepX);
	}

	public void initSpeedyStepX(final float speedyStepX) {
		this.mSpeedyStepX = speedyStepX;
	}

	public void initParashuteStepY(final float parashuteStepY) {
		this.setSpeedY(parashuteStepY);
	}

	public void initOffsetX(final float offsetX) {
		this.mOffsetX = offsetX;
	}

	public void initScale(final float scale) {
		this.setWidth(this.mBaseWidth * scale);
		this.setHeight(this.mBaseHeight * scale);
	}

	public void initVectorMoveSteps(final float pValueX, final float pValueY, final float pSpeedX, final float pSpeedY, final float pVectorStartStopUpdates, final float pStopUpdates, final float pVectorLimit, final boolean pStopSecond) {
		this.vectorX = pValueX;
		this.vectorY = pValueY;

		this.vectorStopSecond = pStopSecond;

		this.vectorLimit = pVectorLimit;

		this.vectorStopUpdates = pStopUpdates;
		this.vectorUpdates = -pVectorStartStopUpdates;

		this.setSpeed(pSpeedX, pSpeedY);

		if (this.IsProperty(isVectorFlag)) {
			this.mState = States.Vector;
		}
	}

	public void initProperties(final int properties) {
		this.mProperties = properties;
	}

	public void addProperties(final int properties) {
		this.mProperties = this.mProperties | properties;
	}

	public void delProperties(final int properties) {
		this.mProperties = this.mProperties & ~properties;
	}

	private boolean IsProperty(int flag) {
		return (this.mProperties & flag) == flag;
	}

	public boolean isCanCollide() {
		return this.mAirgum == null && !this.IsProperty(isPauseUpdateFlag);
	}

	protected void onManagedUpdateWithGum(final float pSecondsElapsed) {
		if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion)) {
			this.mTimeWithGum += pSecondsElapsed;
			if (this.mTimeWithGum >= Options.chikyMaxTimeWithGum) {
				this.mTime = 0;
				if (this.mState == States.SpeedyMove) {
					this.mWind.destroy();
				}
				this.prepareToFall();

				final Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
				if (airgum.getTextureRegion().e(Resources.mBubbleTextureRegion)) {
					if (airgum != null) {
						airgum.setParent(mAirgum);
						airgum.setSize(this.mAirgum.getWidth(), this.mAirgum.getHeight());
						airgum.initStartPosition(this.getCenterX(), this.getCenterY());
						airgum.initFinishPosition(airgum.getCenterX(), airgum.getCenterY());
					}
				} else {
					airgum.setParent(mAirgum);
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
		} else if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			this.mTime = 0;
			if (this.mState == States.SpeedyMove) {
				this.mWind.destroy();
			}
			this.prepareToFall();
			this.mState = States.Fall;
			this.mStartX = this.getCenterX();
			this.mStartY = this.getCenterY();
			if (this.getCenterX() < Options.cameraWidth / 2) {
				this.setSpeedX(this.mNormalStepX);
			}
			else {
				this.setSpeedX(-this.mNormalStepX);
			}

			this.stopAnimation(6);

			this.hat.Init();

			if (Game.random.nextInt(3) == 1) {
				Options.mBirdsDeath1.play();
			} else if (Game.random.nextInt(3) == 2) {
				Options.mBirdsDeath2.play();
			} else {
				Options.mBirdsDeath3.play();
			}

		}
		else if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
			this.mTimeWithGum += pSecondsElapsed;
			if (this.mTimeWithGum >= Options.chikyMaxTimeWithGum) {
				this.mTime = 0;
				if (this.mState == States.SpeedyMove) {
					this.mWind.destroy();
				}
				this.prepareToFall();
				this.mState = States.Fall;
				this.mStartX = this.getCenterX();
				this.mStartY = this.getCenterY();
				if (this.getCenterX() < Options.cameraWidth / 2) {
					this.setSpeedX(this.mNormalStepX);
				}
				else {
					this.setSpeedX(-this.mNormalStepX);
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
	}

	private void onManagedUpdateVectorMovement(final float pSecondsElapsed) {
		this.vectorUpdates++;

		final float convertedDistance = 1 / FloatMath.sqrt(this.vectorX * this.vectorX + this.vectorY * this.vectorY);
		final float x = this.vectorX * convertedDistance;
		final float y = this.vectorY * convertedDistance;

		if (this.vectorUpdates >= this.vectorLimit) {
			this.vectorReverse = !this.vectorReverse;

			if (!this.vectorReverse) {
				this.vectorUpdates = -this.vectorStopUpdates;
			} else {
				if (this.vectorStopSecond) {
					this.vectorUpdates = -this.vectorStopUpdates;
				} else {
					this.vectorUpdates = 0;
				}
			}
		}

		if (this.vectorUpdates > 0) {
			if (this.vectorReverse) {
				this.mX -= x * this.getSpeedX();
				this.mY -= y * this.getSpeedY();
			} else {
				this.mX += x * this.getSpeedX();
				this.mY += y * this.getSpeedY();
			}
		}

		if (this.mAirgum != null) {
			this.onManagedUpdateWithGum(pSecondsElapsed);
		}
	}

	private void onManagedUpdateMoveHelper(final float pSecondsElapsed) {
		float x = this.getCenterX() + this.getSpeedX();

		boolean isBorder = false;
		final float minX = 0 - this.mOffsetX + this.mWidth / 2;
		if (x < minX) {
			if (this.getSpeedX() < 0) {
				x = 2 * minX - x;
			}
			this.setSpeedX(Math.abs(this.getSpeedX()));
			isBorder = true;
		}
		final float maxX = Options.cameraWidth + this.mOffsetX - this.mWidth / 2;
		if (maxX < x) {
			if (this.getSpeedX() > 0) {
				x = 2 * maxX - x;
			}
			this.setSpeedX(-Math.abs(this.getSpeedX()));
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
		this.onManagedUpdateMoveHelper(pSecondsElapsed);
		if (this.mTime >= Options.chikyMaxTimeNormal) {
			this.mTime = 0;
			if (this.IsProperty(isSpeedyFlag)) {
				this.setSpeedX(Math.signum(this.getSpeedX()) * this.mSpeedyStepX);
				this.mState = States.SpeedyMove;
				this.mWind = ((Acceleration) ((LevelScreen) Game.screens.get(Screen.LEVEL)).accelerators.create());
				this.mWind.mFollowEntity = this;

				if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion) || this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
					if (this.mAirgum == null) {
						this.animate(pFrameDuration, pSpeedyMoveFrames, 9999);
					}
					else {
						this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
					}
				}
			}
			// !!! Maybe need use switch for another moving.
		}
	}

	private void onManagedUpdateSpeedy(final float pSecondsElapsed) {
		this.onManagedUpdateMoveHelper(pSecondsElapsed);
		if (this.mTime >= Options.chikyMaxTimeSpeedy) {
			this.mTime = 0;
			this.setSpeedX(Math.signum(this.getSpeedX()) * this.mNormalStepX);
			this.mState = States.NormalMove;
			this.mWind.destroy();
			this.mWind = null;

			if (this.mAirgum == null) {
				if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion) || this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
					this.animate(pFrameDuration, pNormalMoveFrames, 9999);
				} else {
					this.animate(pSpaceFrameDuration, pSpaceNormalMoveFrames, 9999);
				}
			}
			else {
				if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion) || this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
					this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
				} else {
					this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
				}
			}
			// !!! Maybe need use switch for another moving.
		}
	}

	private void prepareToFall() {
		this.mState = States.Fall;
		super.init();
		final float x = (this.getCenterX() - this.mWidth / 2) / (Options.cameraWidth - this.mWidth) * 100;
		final float y = (this.getCenterY() - this.mHeight / 2 - Options.menuHeight) / (Options.cameraHeight - Options.touchHeight - Options.menuHeight - this.mHeight) * 100;
		super.addControlPoint((short) x, (short) y);
		if (x > 50) {
			super.addControlPoint((short) (x - 20), (short) (y - 40));
			super.addControlPoint((short) (x - 40), (short) y);
		}
		else {
			super.addControlPoint((short) (x + 20), (short) (y - 40));
			super.addControlPoint((short) (x + 40), (short) y);
		}
		super.initMaxTime(Float.MAX_VALUE);
		super.initSpeedTime(1f);
	}

	private void onManagedUpdateMove(final float pSecondsElapsed) {
		if (this.mAirgum != null) {
			this.mState = States.MoveWithGum;
			this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
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

	public void setCollide(Bubble airgum) {
		if (this.mAirgum == null) {
			this.mAirgum = airgum.getParent();
			this.mAirgum.AddChildCount();
			this.mTimeWithGum = 0;

			this.mAirgum.mLastX = this.getCenterX();
			this.mAirgum.mLastY = this.getCenterY();

			if (this.mState == States.NormalMove || this.mState == States.Vector) {
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
			}
			else { // States.SpeedyMove.
				this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
			}

			if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
				Glass particle;
				for (int i = 0; i < Options.particlesCount; i++) {
					particle = ((LevelScreen) Game.screens.get(Screen.LEVEL)).glasses.create();
					if (particle != null) {
						particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
					}
				}
			}

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
		super.init();

		this.setRotation(0);

		mState = States.NormalMove;

		this.vectorReverse = false;
		this.vectorUpdates = 0;

		// Center of used region.
		this.mStartX = Options.cameraWidth / 2;
		this.mStartY = Options.chikyEtalonSize / 2 + Options.chikyOffsetY + (Options.cameraHeight - Options.touchHeight - Options.chikyEtalonSize - 2 * Options.chikyOffsetY) / 2;

		this.mNormalStepX = Game.random.nextFloat() * (Options.chikyMaxStepX - Options.chikyMinStepX) + Options.chikyMinStepX;
		this.mSpeedyStepX = this.mNormalStepX * Options.chikySpeedCoeficient;
		this.setSpeedX(this.mNormalStepX);

		this.mOffsetX = 0;

		this.mProperties = 0;

		this.mX_ = 0;

		this.mTime = 0f;
		this.mTimeWithGum = 0f;

		this.mAngle = 0f; // Degree.

		this.mAirgum = null;
		this.mWind = null;

		if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion)) {
			this.animate(pFrameDuration, pNormalMoveFrames, 9999);
		} else if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			this.animate(pFrameDuration, pNormalMoveFrames, 9999);
		} else if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
			this.animate(pSpaceFrameDuration, pSpaceNormalMoveFrames, 9999);
		}

		if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			this.hat = ((LevelScreen) Game.screens.get(Screen.LEVEL)).mCristmasHats.create();
		}

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

		if (!this.IsProperty(isPauseUpdateFlag)) {
			this.mTime += pSecondsElapsed;
			this.mAngle += Options.chikyAngleStep;

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
			case Vector:
				this.onManagedUpdateVectorMovement(pSecondsElapsed);
				break;
			case Move:
				this.onManagedUpdateMove(pSecondsElapsed);
				break;
			case MoveWithGum:
				this.onManagedUpdateWithGum(pSecondsElapsed);
				break;
			}

			this.getTextureRegion().setFlippedHorizontal(this.mX - this.mX_ < 0);
			this.mX_ = this.mX;
		}

		if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			if (!this.hat.mIsParticle) {
				this.hat.setScale(this.mWidth / this.mBaseWidth);
				this.hat.setCenterPosition(this.getCenterX(), this.getCenterY() - this.mWidth / 2);
				this.hat.getTextureRegion().setFlippedHorizontal(this.getTextureRegion().isFlippedHorizontal());
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		LevelScreen.deadBirds--;
	}
}
