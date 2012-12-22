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

	private static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private static final int[] pNormalMoveFrames = new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 };
	private static final int[] pNormalMoveWithGumFrames = new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 };
	private static final int[] pSpeedyMoveFrames = new int[] { 12, 13, 14, 15, 16, 17, 16, 15, 14, 13 };
	private static final int[] pSpeedyMoveWithGumFrames = new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 };

	private static final long[] pSpaceFrameDuration = new long[] { 50, 50, 50, 300, 50, 50 };
	private static final int[] pSpaceNormalMoveFrames = new int[] { 0, 1, 2, 3, 2, 1 };
	private static final int[] pSpaceWithGumFrames = new int[] { 4, 5, 6, 7, 6, 5 }; // TODO: (R) Ups! I lose where using this code. :-( Find!

	private enum States {
		NormalMove, UnnormalMove, WithGumMove, Fall
	};

	public static final int isPauseUpdateFlag = 1;
	public static final int isUnnormalMoveFlag = 2;
	// TODO: (R) Is this state need? public static final int isBorderFlag = 2;

	private final Text mName = new Text(0, 0, Resources.mFont, "1234567890123456"); // TODO: (R) What do this code?

	// ===========================================================
	// Fields
	// ===========================================================

	private float mX_ = 0; // Last (or old) x.

	private int mProperties = 0;

	private States mState = States.NormalMove;

	// > NormalMove state.
	private float mNormalTime = 0; // Seconds.
	private float mNormalMaxTime = Float.MAX_VALUE; // Seconds.
	private float mNormalSpeedTime = 0; // Seconds.
	// < NormalMove state.

	// > UnnormalMove state.
	private float mUnnormalTime = 0; // Seconds.
	private float mUnnormalMaxTime = 0; // Seconds.
	private float mUnnormalSpeedTime = 0; // Seconds.
	// < UnnormalMove state.

	// > WithGumMove state.
	private float mWithGumTime = 0f; // Seconds.
	private float mWithGumMaxTime = 0; // Seconds.
	// > WithGumMove state.

	private Bubble mBubble = null;
	protected Acceleration mWind = null;
	private CristmasHat mCristmasHat = null;

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

		pParentScreen.attachChild(this.mName);

		this.mName.setVisible(false);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void initScale(final float scale) {
		this.setWidth(this.mBaseWidth * scale);
		this.setHeight(this.mBaseHeight * scale);
	}

	public void initName(final String pName) {
		this.mName.setText(pName);
	}

	public void initNormalMaxTime(final float pNormalMaxTime) {
		this.mNormalMaxTime = pNormalMaxTime;
	}

	public void initNormalSpeedTime(final float pNormalSpeedTime) {
		this.mNormalSpeedTime = pNormalSpeedTime;
	}

	public void initUnnormalMaxTime(final float pUnnormalMaxTime) {
		this.mUnnormalMaxTime = pUnnormalMaxTime;
	}

	public void initUnnormalSpeedTime(final float pUnnormalSpeedTime) {
		this.mUnnormalSpeedTime = pUnnormalSpeedTime;
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

	private void onManagedUpdateNormalMove(final float pSecondsElapsed) {
		this.mNormalTime += pSecondsElapsed * EntityBezier.mKoefSpeedTime;
		if (this.mNormalTime > this.mNormalMaxTime) {
			if (this.IsProperty(isUnnormalMoveFlag)) {
				this.mState = States.UnnormalMove;
				this.mUnnormalTime = this.mNormalTime - this.mNormalMaxTime;
				this.mSpeedTime = this.mSpeedTime == 0 ? this.mUnnormalSpeedTime : Math.signum(this.mSpeedTime) * this.mUnnormalSpeedTime;

				this.mWind = ((Acceleration) ((LevelScreen) Game.screens.get(Screen.LEVEL)).accelerators.create());
				this.mWind.mFollowEntity = this;

				// TODO: (R) Try to move to individual method?
				if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion) || this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
					if (this.mBubble == null) {
						this.animate(pFrameDuration, pSpeedyMoveFrames, 9999);
					}
					else {
						this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
					}
				}
			}

			// TODO: (R) Add conditions for another flags?
		}
	}

	private void onManagedUpdateUnnormalMove(final float pSecondsElapsed) {
		this.mUnnormalTime += pSecondsElapsed * EntityBezier.mKoefSpeedTime;
		if (this.mUnnormalTime > this.mUnnormalMaxTime) {
			this.mState = States.NormalMove;
			this.mNormalTime = this.mUnnormalTime - this.mUnnormalMaxTime;
			this.mSpeedTime = this.mSpeedTime == 0 ? this.mNormalSpeedTime : Math.signum(this.mSpeedTime) * this.mNormalSpeedTime;

			this.mWind.destroy();
			this.mWind = null;

			// TODO: (R) Try to move to individual method?
			if (this.mBubble == null) {
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
		}
	}

	// onManagedUpdateVectorMovement --> Add two control points: begin and end.

	protected void onManagedUpdateWithGumMove(final float pSecondsElapsed) {
		this.mWithGumTime += pSecondsElapsed * EntityBezier.mKoefSpeedTime;
		if (this.mWithGumTime >= this.mWithGumMaxTime) {
			this.prepareToFall();

			// Some not good code.
			if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion)) {
				if (this.mBubble != null) {
					final Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
					if (airgum.getTextureRegion().e(Resources.mBubbleTextureRegion)) {
						if (airgum != null) {
							airgum.setParent(mBubble);
							airgum.setSize(this.mBubble.getWidth(), this.mBubble.getHeight());
							airgum.initStartPosition(this.getCenterX(), this.getCenterY());
							airgum.initFinishPositionWithCorrection(airgum.getCenterX(), airgum.getCenterY());
						}
					} else {
						airgum.setParent(mBubble);
						airgum.initStartPosition(this.getCenterX(), this.getCenterY());
						airgum.initFinishPositionWithCorrection(airgum.getCenterX(), airgum.getCenterY());
					}
				}

				Feather particle;
				for (int i = 0; i < Options.particlesCount; i++) {
					particle = ((LevelScreen) Game.screens.get(Screen.LEVEL)).feathers.create();
					if (particle != null) {
						particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
					}
				}
			} else if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
				this.mCristmasHat.Init();
			} // Condition (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) don't have another action.
		}
	}

	private void prepareToFall() {
		// Disable options.
		if (this.mWind != null) {
			this.mWind.destroy();
			this.mWind = null;
		}

		this.stopAnimation(6);

		if (Options.isMusicEnabled) {
			final int randomInt = Game.random.nextInt(3);
			switch (randomInt) {
			case 1:
				Options.mBirdsDeath1.play();
				break;
			case 2:
				Options.mBirdsDeath2.play();
				break;
			case 3:
				Options.mBirdsDeath3.play();
				break;
			}
		}

		// Enable options.
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

	private void onManagedUpdateFall(final float pSecondsElapsed) {
		this.setRotation(this.getRotation() + 5); // Rotate at 5 degree. Maybe need to correct.
		if (this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}


	// ===========================================================
	// Setters
	// ===========================================================

	@Override
	public void onCollide() {
		super.onCollide();

		this.stopAnimation(6);

		this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);

		this.mWithGumTime = 0;

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

		if (Options.isMusicEnabled) {
			if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
				Options.mGlassBroke.play();
			} else {
				if (Game.random.nextInt(2) == 1) {
					Options.mBirdsShotted1.play();
				} else {
					Options.mBirdsShotted2.play();
				}
			}
		}

		this.mState = States.WithGumMove;
	}

	@Override
	public void onCollide(final Entity pEntity) {
		super.onCollide(pEntity);

		final Bubble airgum = (Bubble) pEntity;

		if (this.mBubble == null) {
			this.mBubble = airgum.getParent();
			this.mBubble.AddChildCount();
			this.mWithGumTime = 0;

			this.mBubble.mLastX = this.getCenterX();
			this.mBubble.mLastY = this.getCenterY();

			if (this.mState == States.NormalMove) {
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

			if (Options.isMusicEnabled) {
				if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
					Options.mGlassBroke.play();
				} else {
					if (Game.random.nextInt(2) == 1) {
						Options.mBirdsShotted1.play();
					} else {
						Options.mBirdsShotted2.play();
					}
				}
			}
		}
		this.mState = States.WithGumMove;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onCreate() {
		super.onCreate();

		super.init();

		this.mName.setText("");
		this.mName.setVisible(true);

		this.setRotation(0);

		this.mX_ = 0;
		this.mProperties = 0;
		this.mState = States.NormalMove;

		// > NormalMove state.
		this.mNormalTime = 0; // Seconds.
		this.mNormalMaxTime = Float.MAX_VALUE; // Seconds.
		this.mNormalSpeedTime = 0; // Seconds.
		// < NormalMove state.

		// > UnnormalMove state.
		this.mUnnormalTime = 0; // Seconds.
		this.mUnnormalMaxTime = 0; // Seconds.
		this.mUnnormalSpeedTime = 0; // Seconds.
		// < UnnormalMove state.

		// > WithGumMove state.
		this.mWithGumTime = 0f; // Seconds.
		this.mWithGumMaxTime = 0; // Seconds.
		// > WithGumMove state.

		this.mBubble = null;
		this.mWind = null;
		this.mCristmasHat = null;

		if (this.mTextureRegion.e(Resources.mRegularBirdsTextureRegion)) {
			this.animate(pFrameDuration, pNormalMoveFrames, 9999);
		} else if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			this.animate(pFrameDuration, pNormalMoveFrames, 9999);
		} else if (this.mTextureRegion.e(Resources.mSpaceBirdsTextureRegion)) {
			this.animate(pSpaceFrameDuration, pSpaceNormalMoveFrames, 9999);
		}

		if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			this.mCristmasHat = ((LevelScreen) Game.screens.get(Screen.LEVEL)).mCristmasHats.create();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		if (!this.IsProperty(isPauseUpdateFlag)) {
			super.onManagedUpdate(pSecondsElapsed);
			switch (this.mState) {
			case NormalMove:
				this.onManagedUpdateNormalMove(pSecondsElapsed);
				break;
			case UnnormalMove:
				this.onManagedUpdateUnnormalMove(pSecondsElapsed);
				break;
			case WithGumMove:
				this.onManagedUpdateWithGumMove(pSecondsElapsed);
				break;
			case Fall:
				this.onManagedUpdateFall(pSecondsElapsed);
				break;
			}

			this.getTextureRegion().setFlippedHorizontal(this.mX - this.mX_ < 0);
			this.mX_ = this.mX;
		}

		this.mName.setPosition(this.getCenterX(), this.getY() - 10f);

		// TODO: (R) Strange code.
		if (this.mTextureRegion.e(Resources.mSnowyBirdsTextureRegion)) {
			if (!this.mCristmasHat.mIsParticle) {
				this.mCristmasHat.setScale(this.mWidth / this.mBaseWidth);
				this.mCristmasHat.setCenterPosition(this.getCenterX(), this.getCenterY() - this.mWidth / 2);
				this.mCristmasHat.getTextureRegion().setFlippedHorizontal(this.getTextureRegion().isFlippedHorizontal());
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.mName.setVisible(false);

		// TODO: (R) Strange code. Try to find correct place for this code.
		LevelScreen.deadBirds--;
	}
}
