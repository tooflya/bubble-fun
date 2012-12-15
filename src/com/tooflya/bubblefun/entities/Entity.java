package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import android.opengl.GLES10;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.screens.Screen;

/**
 * The basic essence of the project, inherited from <b>AnimatedSprite</b> base class. May have a few frames or just one, depending on the type of <b>TextureRegion</b>.
 * 
 * Base this class is abstract class we necessarily need to override <i>deepCopy()</i> method.
 * 
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

	/** ID of this instance in the <b>EntityManager</b>. Used to <i>destroy()</i> method like <i>destroySelf()</i> from her entity manager. */
	private int mId;

	/** Entity speed on axis X. The private identifier is used to adjust the speed depending on the screen resolution in the method <i>setSpeedX()</i>. */
	private float mSpeedX;
	/** Entity speed on axis Y. The private identifier is used to adjust the speed depending on the screen resolution in the method <i>setSpeedY()</i>. */
	private float mSpeedY;

	/** <b>Screen</b> which is the essence of the place rendering. */
	protected org.anddev.andengine.entity.Entity mParentScreen;

	/** <b>EntityManager</b> which is parent manager of this <b>Entity</b>. This object can be <b>null</b>. */
	private EntityManager<?> mEntityManager;

	private boolean mIsFullBlendingEnable = false;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Base constructor for instantinate this Entity class.
	 * 
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 * @param pParentScreen
	 *            instance of <b>org.anddev.andengine.entity.Entity</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 * @param pRegisterTouchArea
	 *            boolean value for know if you are want to register this entity as clickable.
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen, final boolean pRegisterTouchArea) {
		super(0, 0, pTiledTextureRegion.deepCopy());

		this.setCullingEnabled(false);

		/** As some entities may be elements of a manager we need to hide them here. They will appers to ther screen after call <i>create()</i> method. */
		this.hide();

		/** Also attach this entity to the <b>Screen</b> if <i>pParentScreen</i> defined. */
		if (pParentScreen != null) {
			this.mParentScreen = pParentScreen;
			this.mParentScreen.attachChild(this);

			/** If <i>pRegisterTouchArea</i> is defined we need to register this entity as clickable. */
			if (pRegisterTouchArea) {
				if (this.mParentScreen instanceof Screen) {
					((Screen) this.mParentScreen).registerTouchArea(this);
				} else {
					if (this.mParentScreen.getParent() instanceof Screen) {
						((Screen) this.mParentScreen.getParent()).registerTouchArea(this);
					} else {
						((Screen) this.mParentScreen.getParent().getParent()).registerTouchArea(this);
					}
				}
			}
		}
	}

	/**
	 * Constructor that allows you to not specify the need to register this entity as clickable.
	 * 
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 * @param pParentScreen
	 *            instance of <b>org.anddev.andengine.entity.Entity</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion.deepCopy(), pParentScreen, false);
	}

	/**
	 * Constructor that takes a value on the screen location of this entity. Do not attach an entity to the screen and does not register her as clickable.
	 * 
	 * @param pX
	 *            Coordinate of this entity on the X-axis
	 * @param pY
	 *            Coordinate of this entity on the Y-axis
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 */
	public Entity(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, null);

		this.setCenterPosition(pX, pY);
	}

	/**
	 * Constructor that takes a value on the screen location of this entity. Do not register entity as clickable.
	 * 
	 * @param pX
	 *            Coordinate of this entity on the X-axis
	 * @param pY
	 *            Coordinate of this entity on the Y-axis
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 * @param pParentScreen
	 *            instance of <b>org.anddev.andengine.entity.Entity</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 */
	public Entity(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, pParentScreen);

		this.setCenterPosition(pX, pY);
	}

	/**
	 * Simple constructor which takes only one parameter.
	 * 
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, null);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * The method, which is similar to the method <i>init()</i>. Shows the entity and indicates the need for rendering.
	 * 
	 * @return <b>Entity</b> Instance of this class.
	 */
	public Entity create() {
		this.show();

		return this;
	}

	/** The method, which is similar to the method <i>destroySelf()</i>. If <i>mEntityManager</i> is defined call them to destroy element with ID <i>mId</i>. Else do nothing. */
	public void destroy() {
		if (this.isManagerExist()) {
			this.mEntityManager.destroy(this.mId);
		}

		/** Let's hide this entity. */
		this.hide();
	}

	/** Method wich used only for apper (setting visible to true) this entity and set ignore to false. */
	public void show() {
		this.setVisible(true);
		this.setIgnoreUpdate(false);
	}

	/** Method wich used only for disapper (setting visible to false) this entity and set ignore to true. */
	public void hide() {
		this.setVisible(false);
		this.setIgnoreUpdate(true);
	}

	/** Method wich return new Object of current extended Class by using Reflection to know current class name. */
	public Entity deepCopy() {
		try {
			return (Entity) Class.forName(this.getClass().getName()).getConstructor(TiledTextureRegion.class, org.anddev.andengine.entity.Entity.class).newInstance(getTextureRegion(), this.mParentScreen);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 */
	public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
		throw new NullPointerException();
	}

	/**
	 * 
	 */
	public void enableBlendFunction() {
		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.mIsFullBlendingEnable = false;
	}

	/**
	 * 
	 */
	public void enableFullBlendFunction() {
		this.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.mIsFullBlendingEnable = true;
	}

	// ===========================================================
	// Validate methods
	// ===========================================================

	/**
	 * Method which checking if this entity is parent of some manager.
	 * 
	 * @return boolean Result of inspection.
	 */
	public boolean isManagerExist() {
		return this.mEntityManager != null;
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
	 * @param pSpeedX
	 */
	public void setSpeedX(final float pSpeedX) {
		this.mSpeedX = pSpeedX;
	}

	/**
	 * @param pSpeedY
	 */
	public void setSpeedY(final float pSpeedY) {
		this.mSpeedY = pSpeedY;
	}

	/**
	 * @param pSpeedX
	 * @param pSpeedY
	 */
	public void setSpeed(final float pSpeedX, final float pSpeedY) {
		this.mSpeedX = pSpeedX;
		this.mSpeedY = pSpeedY;
	}

	/**
	 * @param centerX
	 */
	public void setCenterX(final float pCenterX) {
		this.mX = pCenterX - this.mWidth / 2;
	}

	/**
	 * @param pCenterY
	 */
	public void setCenterY(final float pCenterY) {
		this.mY = pCenterY - this.mHeight / 2;
	}

	/**
	 * @param pCenterX
	 */
	public void setBackgroundCenterX(final float pCenterX) {
		this.mX = pCenterX - this.mScaleCenterX - (this.mWidth / 2 - this.mScaleCenterX) * this.mScaleX;
	}

	/**
	 * @param pCenterY
	 */
	public void setBackgroundCenterY(final float pCenterY) {
		this.mY = pCenterY - this.mScaleCenterY - (this.mHeight / 2 - this.mScaleCenterY) * this.mScaleY;
	}

	/**
	 * @param pCenterX
	 * @param pCenterY
	 */
	public void setCenterPosition(final float pCenterX, final float pCenterY) {
		this.mX = pCenterX - this.mWidth / 2;
		this.mY = pCenterY - this.mHeight / 2;
	}

	/**
	 * 
	 */
	public void setBackgroundCenterPosition() {
		this.mX = Options.screenWidth / 2 - this.mScaleCenterX - (this.mWidth / 2 - this.mScaleCenterX) * this.mScaleX;
		this.mY = Options.screenHeight / 2 - this.mScaleCenterY - (this.mHeight / 2 - this.mScaleCenterY) * this.mScaleY;
	}

	/**
	 * @param pEntityManager
	 */
	public void setManager(final EntityManager<?> pEntityManager) {
		this.mEntityManager = pEntityManager;
	}

	/**
	 * @param pTextureRegion
	 */
	public void changeTextureRegion(final TiledTextureRegion pTextureRegion) {
		this.mTextureRegion = pTextureRegion.deepCopy();

		this.mBaseWidth = pTextureRegion.getTileWidth();
		this.mBaseHeight = pTextureRegion.getTileHeight();
		
		this.mScaleX = 1;
		this.mScaleY = 1;
		
		this.mRotation = 0;
		
		this.setBaseSize();
	}

	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param pSetWithoutChecks
	 */
	public void setPosition(final float pX, final float pY, final boolean pSetWithoutChecks) {
		this.mX = pX;
		this.mY = pY;
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
	public float getSpeedX() {
		return this.mSpeedX;
	}

	/**
	 * @return
	 */
	public float getSpeedY() {
		return this.mSpeedY;
	}

	/**
	 * @return
	 */
	public float getCenterX() {
		return this.mX + this.mWidth / 2;
	}

	/**
	 * @return
	 */
	public float getCenterY() {
		return this.mY + this.mHeight / 2;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	* @see org.anddev.andengine.entity.Entity#setAlpha(float)
	*/
	@Override
	public void setAlpha(float pAlpha) {
		pAlpha = pAlpha > 1f ? 1f : pAlpha;

		super.setAlpha(pAlpha);

		if (this.mIsFullBlendingEnable) {
			super.setColor(pAlpha, pAlpha, pAlpha); // <-- This is the great trick !
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.shape.Shape#onManagedDraw(javax.microedition.khronos.opengles.GL10, org.anddev.andengine.engine.camera.Camera)
	 */
	@Override
	public void onManagedDraw(final GL10 GL, final Camera pCamera) {
		super.onManagedDraw(GL, pCamera);

	}

	public void doDraw(final GL10 pGL, final Camera pCamera) {
		super.doDraw(pGL, pCamera);

	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#setPosition(float, float)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		super.setPosition(pX, pY);

		float factorX, factorY;

		if (this.mX < Options.cameraCenterX) {
			factorX = (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2;
		} else {
			factorX = -(Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2;
		}

		if (this.mY < Options.cameraCenterY) {
			factorY = (Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2;
		} else {
			factorY = -(Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2;
		}

		this.mX += factorX / Options.cameraRatioFactor;
		this.mY += factorY / Options.cameraRatioFactor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.BaseSprite#onInitDraw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	protected void onInitDraw(final GL10 pGL) {
		super.onInitDraw(pGL);

		GLHelper.enableTextures(pGL);
		GLHelper.enableTexCoordArray(pGL);
		GLHelper.enableDither(pGL);
		GLHelper.enableDepthTest(pGL);
		GLHelper.enableBlend(pGL);

		GLHelper.disableCulling(pGL);

		GLES10.glEnable(GL10.GL_ALPHA_TEST);
	}
}
