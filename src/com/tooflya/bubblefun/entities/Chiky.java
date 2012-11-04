package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen;

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

	private enum States {NormalMove, SpeedyMove, Fall};
	private static final int isJumplyFlag = 1;
	private static final int isSpeedyFlag = 2;
	private static final int isWavelyFlag = 4;
	// ===========================================================
	// Fields
	// ===========================================================

	private States pState = States.NormalMove;

	private float pTime = 0f; // Seconds.
	private float pTimeWithGum = 0f; // Seconds.

	private float pStartX = 0;
	private float pStartY = 0;
	private float pNormalStepX = 0;
	private float pSpeedyStepX = 0;
	private float pOffsetX = 0;
	private int pProperties = 0;

	private float mX_ = 0; // Last (or old) x.
		
	private Bubble pAirgum = null;
	private Acceleration pWind = null;

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

		pState = States.NormalMove;

		// Center of used region.
		this.pStartX = Options.cameraOriginRatioX / 2;
		this.pStartY = Options.chikyEtalonSize / 2 + Options.chikyOffsetY + (Options.cameraOriginRatioY - Options.touchHeight - Options.chikyEtalonSize - 2 * Options.chikyOffsetY) / 2;

		this.pNormalStepX = Game.random.nextFloat() * (Options.chikyMaxStepX - Options.chikyMinStepX) + Options.chikyMinStepX;
		if (Game.random.nextBoolean()){
			this.mSpeedX = -this.mSpeedX;
		}
		this.pSpeedyStepX = this.pNormalStepX * Options.chikySpeedCoeficient;
		this.mSpeedX = this.pNormalStepX;
		
		this.pOffsetX = 0;
		
		this.pProperties = 0;
		
		this.mX_ = 0;
	
		this.pTime = 0f;
		this.pTimeWithGum = 0f;

		this.pAirgum = null;
		this.pWind = null;
		
		this.animate(pFrameDuration, pNormalMoveFrames, 9999);

		return super.create();
	}

	public void initStartX(final float startX){
		this.pStartX = startX;
	}
	
	public void initStartY(final float startY){
		this.pStartY = startY;
	}

	public void initNormalStepX(final float normalStepX){
		this.pNormalStepX = normalStepX;
		this.mSpeedX = this.pNormalStepX; 
	}

	public void initSpeedyStepX(final float speedyStepX){
		this.pSpeedyStepX = speedyStepX;
	}
	
	public void initOffsetX(final float offsetX){
		this.pOffsetX = offsetX;
	}

	public void initIsJumply(){
		this.pProperties = this.pProperties | isJumplyFlag;
	}
	
	public void initIsSpeedy(){
		this.pProperties = this.pProperties | isSpeedyFlag;
	}
	
	public void initIsWavely(){
		this.pProperties = this.pProperties | isWavelyFlag;
	}
	
	private boolean IsProperty(int flag){
		return (this.pProperties & flag) == flag;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void isCollide(Bubble airgum) {
		if(this.pAirgum == null){
			this.pAirgum = airgum;
			this.pTimeWithGum = 0;
			
			if (this.pState == States.NormalMove){
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
			}
			else{ // States.SpeedyMove.
				this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);
			}
		}
	}

	// ===========================================================
	// Getters
	// ===========================================================

	public boolean isCanCollide() {
		return this.pAirgum == null;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	private void onManagedUpdateWithGum(final float pSecondsElapsed) {
		this.pTimeWithGum += pSecondsElapsed;
		if (this.pTimeWithGum >= Options.chikyMaxTimeWithGum) {
			this.pTime = 0;
			if(this.pState == States.SpeedyMove){
				this.pWind.destroy();
			}
			this.pState = States.Fall;
			this.pStartX = this.getCenterX();
			this.pStartY = this.getCenterY();
			if(this.getCenterX() < Options.cameraOriginRatioX / 2){
				this.mSpeedX = this.pNormalStepX;
			}
			else{
				this.mSpeedX = -this.pNormalStepX;				
			}

			Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
			if(airgum != null){
				airgum.initStartPosition(this.getCenterX(), this.getCenterY());
				airgum.initFinishPosition(this.getCenterX(), this.getCenterY() + this.pAirgum.mSpeedY);
				// TODO: (R) Correct! airgum.setSize(Math.max(this.pAirgum.getWidth(), Options.bubbleBaseMinScale));
				airgum.birdsKills = this.pAirgum.birdsKills;
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
		float x = this.getCenterX() + this.mSpeedX;

		boolean isBorder = false;
		final float minX = 0 - this.pOffsetX + this.mWidth / 2;		
		if (x < minX) {
			x = 2 * minX - x;
			this.mSpeedX = +Math.abs(this.mSpeedX);
			isBorder = true;
		}
		final float maxX = Options.cameraOriginRatioX + this.pOffsetX - this.mWidth / 2;		
		if (x > maxX) {
			x = 2 * maxX - x;
			this.mSpeedX = -Math.abs(this.mSpeedX);
			isBorder = true;
		}
		if (isBorder && this.IsProperty(isJumplyFlag)) {
			this.pStartY = Options.chikyEtalonSize / 2 + Game.random.nextFloat() * (Options.cameraOriginRatioY - Options.touchHeight - Options.chikyEtalonSize);
		}

		float y = this.pStartY;
		if (this.IsProperty(isWavelyFlag)) {
			y += FloatMath.sin(this.pTime * Options.PI * Math.abs(this.mSpeedX) / Options.cameraOriginRatioX) * Options.chikyOffsetY;
		}
		
		this.setCenterPosition(x,  y);
		
		if(this.pAirgum != null){
			this.onManagedUpdateWithGum(pSecondsElapsed);
		}		
	}
	
	private void onManagedUpdateNormal(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);
		if (this.pTime >= Options.chikyMaxTimeNormal){
			this.pTime = 0;
			if(this.IsProperty(isSpeedyFlag)){
				this.mSpeedX = Math.signum(this.mSpeedX) * this.pSpeedyStepX;
				this.pState = States.SpeedyMove;
				this.pWind = ((Acceleration) ((LevelScreen) Game.screens.get(Screen.LEVEL)).accelerators.create());
				this.pWind.mFollowEntity = this;
				if(this.pAirgum == null){
					this.animate(pFrameDuration, pSpeedyMoveFrames, 9999);
				}
				else{
					this.animate(pFrameDuration, pSpeedyMoveWithGumFrames, 9999);					
				}
			}			
			// !!! Maybe need use switch for another moving.
		}
	}
	
	private void onManagedUpdateSpeedy(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);
		if (this.pTime >= Options.chikyMaxTimeSpeedy){
			this.pTime = 0;
			this.mSpeedX = Math.signum(this.mSpeedX) * this.pNormalStepX;
			this.pState = States.NormalMove;
			this.pWind.destroy();
			this.pWind = null;
			if(this.pAirgum == null){
				this.animate(pFrameDuration, pNormalMoveFrames, 9999);
			}
			else{
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);					
			}
			// !!! Maybe need use switch for another moving.
		}
	}

	private void onManagedUpdateFall(final float pSecondsElapsed) {
		// For remember: y = y_2 - y_1, where y_n = (x_n - x_) ^ 2.
		final float x_ = FloatMath.sqrt(Options.chikyOffsetY);
		final float x = this.getCenterX() - this.pStartX + this.mSpeedX;
		final float y = this.mSpeedX * (2 * (x - x_) - this.mSpeedX);		
		this.setCenterPosition(this.pStartX + x, this.pStartY + y);
		// TODO: (R) Need to do easy? Can you understand this?

		this.setRotation(this.getRotation() + 1); // Rotate at 1 degree. Maybe need to correct.

		if (this.mY > Options.cameraOriginRatioY) {
			this.destroy();
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

		this.pTime += pSecondsElapsed;
		
		this.mX_ = this.mX;
		
		switch (this.pState) {
		case NormalMove:
			this.onManagedUpdateNormal(pSecondsElapsed);
			break;
		case SpeedyMove:
			this.onManagedUpdateSpeedy(pSecondsElapsed);
			break;
		case Fall:
			this.onManagedUpdateFall(pSecondsElapsed);			
			break;
		}

		if (this.mX - this.mX_ > 0) {
			this.getTextureRegion().setFlippedHorizontal(false);
		}
		else {
			this.getTextureRegion().setFlippedHorizontal(true);
		}
	}
}
