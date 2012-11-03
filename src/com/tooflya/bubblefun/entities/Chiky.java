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

	 // TODO: Find correct numbers. Are they constant?
	private final float pMaxTimeNormal = 4f; // Time in seconds.
	private final float pMaxTimeSpeedy = 1f; // Time in seconds.
	private final float pMaxTimeWithGum = 2f; // Time in seconds.

	private static final long[] pFrameDuration = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private static final int[] pNormalMoveFrames = new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 };
	private static final int[] pNormalMoveWithGumFrames = new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 };
	private static final int[] pSpeedyMoveFrames = new int[] { 12, 13, 14, 15, 16, 17, 16, 15, 14, 13 };
	private static final int[] pSpeedyMoveWithGumFrames = new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 };

	// ===========================================================
	// Fields
	// ===========================================================

	private int pState = 0; // 0 - Normal move; 1 - Speedy move; 2 - fall;

	private float pStartX = 0;
	private float pStartY = 0;
	private float pStepX = 0;
	private float pNormalStepX = 0;
	private float pSpeedyStepX = 0;
	private float pOffsetX = 0;
	private boolean pIsJumply = false;
	private boolean pIsSpeedy = false;
	private boolean pIsWavely = false;

	private float mX_ = 0; // Last x.
	
	private float pTime = 0f;
	private float pTimeWithGum = 0f;
	
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

		pState = 0; // 0 - Normal move; 1 - Speedy move; 2 - fall;

		// Center of used region.
		this.pStartX = Options.cameraOriginRatioX / 2;
		this.pStartY = Options.chikySize / 2 + Options.ellipseHeight + (Options.cameraOriginRatioY - Options.touchHeight - Options.chikySize - 2 * Options.ellipseHeight) / 2;

		this.pNormalStepX = Game.random.nextFloat() * (Options.maxChikyStepX - Options.minChikyStepX) + Options.minChikyStepX;
		if (Game.random.nextBoolean()){
			this.pStepX = -this.pStepX;
		}
		this.pSpeedyStepX = this.pNormalStepX * 2.5f; // TODO: (R) Magic number. Work correct in previous version.
		this.pStepX = this.pNormalStepX;
		
		this.pOffsetX = 0;
		
		this.pIsJumply = false;
		this.pIsSpeedy = false;
		this.pIsWavely = false;
		
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
		this.pStepX = this.pNormalStepX; 
	}

	public void initSpeedyStepX(final float speedyStepX){
		this.pSpeedyStepX = speedyStepX;
	}
	
	public void initOffsetX(final float offsetX){
		this.pOffsetX = offsetX;
	}

	public void initIsJumply(){
		this.pIsJumply = true;
	}
	
	public void initIsSpeedy(){
		this.pIsSpeedy = true;
	}
	
	public void initIsWavely(){
		this.pIsWavely = true;
	}
	
	// ===========================================================
	// Setters
	// ===========================================================

	public void isCollide(Bubble airgum) {
		if(this.pAirgum == null){
			this.pAirgum = airgum;
			this.pTimeWithGum = 0;
			
			if (this.pState == 0){ // Normal move.
				this.animate(pFrameDuration, pNormalMoveWithGumFrames, 9999);
			}
			else{ // Speedy move.
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
		if (this.pTimeWithGum >= this.pMaxTimeWithGum) {
			this.pTime = 0;
			if(this.pState == 1){
				this.pWind.destroy();
			}
			this.pState = 2; // Fall state.
			this.pStartX = this.getCenterX();
			this.pStartY = this.getCenterY();
			if(this.getCenterX() < Options.cameraOriginRatioX / 2){
				this.pStepX = this.pNormalStepX;
			}
			else{
				this.pStepX = -this.pNormalStepX;				
			}

			Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
			if(airgum != null){
				airgum.setCenterPosition(this.getCenterX(), this.getCenterY());
				airgum.setScale(Math.max(this.pAirgum.getScaleX(), Bubble.minScale));
				airgum.setIsScale(false);
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
		float x = this.getCenterX() + this.pStepX;

		boolean isBorder = false;
		final float minX = 0 - this.pOffsetX + this.mWidth / 2;		
		if (x < minX) {
			x = 2 * minX - x;
			this.pStepX = +Math.abs(this.pStepX);
			isBorder = true;
		}
		final float maxX = Options.cameraOriginRatioX + this.pOffsetX - this.mWidth / 2;		
		if (x > maxX) {
			x = 2 * maxX - x;
			this.pStepX = -Math.abs(this.pStepX);
			isBorder = true;
		}
		if (isBorder && this.pIsJumply) {
			this.pStartY = Options.chikySize / 2 + Game.random.nextFloat() * (Options.cameraOriginRatioY - Options.touchHeight - Options.chikySize);
		}

		float y = this.pStartY;
		if (this.pIsWavely) {
			y += FloatMath.sin(this.pTime * Options.PI * Math.abs(this.pStepX) / Options.cameraOriginRatioX) * Options.ellipseHeight;
		}
		
		this.setCenterPosition(x,  y);
		
		if(this.pAirgum != null){
			this.onManagedUpdateWithGum(pSecondsElapsed);
		}		
	}
	
	private void onManagedUpdateNormal(final float pSecondsElapsed) {
		this.onManagedUpdateMove(pSecondsElapsed);
		if (this.pTime >= this.pMaxTimeNormal){
			this.pTime = 0;
			if(this.pIsSpeedy){
				this.pStepX = Math.signum(this.pStepX) * this.pSpeedyStepX;
				this.pState = 1; // Speedy move.
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
		if (this.pTime >= this.pMaxTimeSpeedy){
			this.pTime = 0;
			this.pStepX = Math.signum(this.pStepX) * this.pNormalStepX;
			this.pState = 0; // Normal move.
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
		final float x_ = FloatMath.sqrt(Options.ellipseHeight);
		final float x = this.getCenterX() - this.pStartX + this.pStepX;
		final float y = this.pStepX * (2 * (x - x_) - this.pStepX);		
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
		case 0: // Normal move.
			this.onManagedUpdateNormal(pSecondsElapsed);
			break;
		case 1: // Speedy move.
			this.onManagedUpdateSpeedy(pSecondsElapsed);
			break;
		case 2: // Fall.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Chiky(getTextureRegion(), this.mParentScreen);
	}
}
