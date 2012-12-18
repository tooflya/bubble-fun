package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class Glint extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float mRotationAngle;

	private int mSleep;

	private boolean isParticle;

	private Entity mFollowObject;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Glint(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.setScaleCenter(this.getWidthScaled() / 2, this.getHeightScaled() / 2);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		this.isParticle = false;

		this.setCurrentTileIndex(4);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public Glint Init(final int i, final Entity pFollowObject) {
		this.mFollowObject = pFollowObject;

		this.setSpeedX(3f * FloatMath.sin(i * 2 * Options.PI / 10));
		this.setSpeedY(3f * FloatMath.cos(i * 2 * Options.PI / 10));

		final float scale = Game.random.nextFloat() * (0.7f - 0.1f) + 0.1f;
		this.mScaleX = scale;
		this.mScaleY = scale;

		this.mAlpha = 1f;

		this.mRotationAngle = Game.random.nextFloat();

		this.mSleep = Game.random.nextInt(50);

		this.isParticle = true;

		this.setVisible(false);
		this.setIgnoreUpdate(false);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.isParticle && --this.mSleep <= 0) {
			if (this.mSleep == 0) {
				this.setCenterPosition(this.mFollowObject.getCenterX(), this.mFollowObject.getCenterY());
				this.setVisible(true);
			}

			this.mX += this.getSpeedX();
			this.mY += this.getSpeedY();

			this.mAlpha -= 0.01f;
			this.mRotation -= this.mRotationAngle;

			if (this.mAlpha < 0) {
				this.destroy();
			}
		}
	}
}
