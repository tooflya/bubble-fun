package com.tooflya.airbubblegum.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Entity extends AnimatedSprite {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mId;

	private EntityManager mEntityManager;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pNeedParent
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent) {
		super(0, 0, pTiledTextureRegion.deepCopy());

		this.hide();

		this.setScaleCenter(0, 0);
		this.setScaleY(Options.CAMERA_RATIO_FACTOR);
		this.setScaleX(Options.CAMERA_RATIO_FACTOR);

		this.mX = this.mX - (this.getWidthScaled() - Options.cameraWidth) / 2;

		if (pNeedParent) {
			Game.screens.get(Screen.LEVEL).attachChild(this);
		}
	}

	/**
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 */
	public Entity(final int pX, final int pY, final TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, true);

		this.setCenterPosition(pX, pY);
	}

	/**
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 * @param pNeedParent
	 */
	public Entity(final int pX, final int pY, final TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent) {
		this(pTiledTextureRegion, pNeedParent);

		this.setCenterPosition(pX, pY);
	}

	/**
	 * @param pTiledTextureRegion
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, true);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	public Entity create() {
		this.show();

		return this;
	}

	/**
	 * 
	 */
	public void destroy() {
		if (this.isManagerExist()) {
			this.mEntityManager.destroy(this.mId);
		}

		this.hide();
	}

	/**
	 * 
	 */
	public void update() {
	}

	/**
	 * 
	 */
	public void show() {
		this.setVisible(true);
		this.setIgnoreUpdate(false);
	}

	/**
	 * 
	 */
	public void hide() {
		this.setVisible(false);
		this.setIgnoreUpdate(true);
		// this.setCullingEnabled(true);
	}

	// ===========================================================
	// Validate methods
	// ===========================================================

	/**
	 * @return
	 */
	public boolean isManagerExist() {
		if (this.mEntityManager != null) {
			return true;
		}

		return false;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	/**
	 * @param id
	 */
	public void setID(final int id) {
		this.mId = id;
	}

	/**
	 * @param centerX
	 */
	public void setCenterX(final float pCenterX) {
		this.mX = pCenterX - getWidthScaled() / 2;
	}

	/**
	 * @param pCenterY
	 */
	public void setCenterY(final float pCenterY) {
		this.mY = pCenterY - getHeightScaled() / 2;
	}

	/**
	 * @param pCenterX
	 * @param pCenterY
	 */
	public void setCenterPosition(final float pCenterX, final float pCenterY) {
		this.mX = pCenterX - getWidthScaled() / 2;
		this.mY = pCenterY - getHeightScaled() / 2;
	}

	/**
	 * @param pEntityManager
	 */
	public void setManager(final EntityManager pEntityManager) {
		this.mEntityManager = pEntityManager;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	/**
	 * @return
	 */
	public int getID() {
		return mId;
	}

	/**
	 * @return
	 */
	public float getCenterX() {
		return this.mX + getWidthScaled() / 2;
	}

	/**
	 * @return
	 */
	public float getCenterY() {
		return this.mY + getHeightScaled() / 2;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.BaseSprite#onInitDraw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	protected void onInitDraw(final GL10 pGL) {
		super.onInitDraw(pGL);

		GLHelper.enableDither(pGL);
		GLHelper.enableTextures(pGL);
		GLHelper.enableTexCoordArray(pGL);
	}

	// ===========================================================
	// Abstract methods
	// ===========================================================

	public abstract Entity deepCopy();
}
