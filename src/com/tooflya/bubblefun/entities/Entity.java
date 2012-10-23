package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * The basic essence of the project, inherited from <b>AnimatedSprite</b> base class. May have a few frames or just one, depending on the type of <b>TextureRegion</b>.
 * 
 * Example of instantinate:
 * 
 * <pre>
 * private final Entity mEntityExample = new Entity(0, 0, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, &quot;sprite.png&quot;, 0, 660, 1, 1), this) {
 * 	&#064;Override
 * 	public Entity deepCopy() {
 * 		return null;
 * 	}
 * };
 * </pre>
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
	protected Screen mParentScreen;

	/** <b>EntityManager</b> which is parent manager of this <b>Entity</b>. This object may be <b>null</b>. */
	private EntityManager mEntityManager;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Base contructor for instantinate this Entity class.
	 * 
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 * @param pParentScreen
	 *            instance of <b>Screen</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 * @param pRegisterTouchArea
	 *            boolean value for know if you are want to register this entity as clickable.
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen, final boolean pRegisterTouchArea) {
		super(0, 0, pTiledTextureRegion.deepCopy());

		/** As some entities may be elements of a manager we need to hide them here. They will appers to ther screen after call <i>create()</i> method. */
		this.hide();

		/** This section is scale object to the real size for adapt size of entity to the screen resolution. */
		this.setScaleCenter(0, 0);
		this.setScale(Options.cameraRatioFactor);

		/** After scale action we need to find center of entity position. */
		this.mX = (Options.cameraWidth - this.getWidthScaled()) / 2;
		this.mY = (Options.cameraHeight- this.getHeightScaled()) / 2;

		/** Also attach this entity to the <b>Screen</b> if <i>pParentScreen</i> defined. */
		if (pParentScreen != null) {
			this.mParentScreen = pParentScreen;
			this.mParentScreen.attachChild(this);

			/** If <i>pRegisterTouchArea</i> is defined we need to register this entity as clickable. */
			if (pRegisterTouchArea) {
				this.mParentScreen.registerTouchArea(this);
			}
		}
	}

	/**
	 * Constructor that allows you to not specify the need to register this entity as clickable.
	 * 
	 * @param pTiledTextureRegion
	 *            Region of the texture on the <b>BitmapTextureAtlas</b>
	 * @param pParentScreen
	 *            instance of <b>Screen</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 */
	public Entity(final TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
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
	 *            instance of <b>Screen</b> class. This is <b>Scene</b> which will be a parent of this entity.
	 */
	public Entity(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
		this(pTiledTextureRegion, pParentScreen);

		this.setCenterPosition(pX, pY);
	}

	/**
	 * Simple contructor which takes only one parameter.
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
		/* TODO: Check is culling needed! >>  this.setCullingEnabled(true); */
	}

	// ===========================================================
	// Validate methods
	// ===========================================================

	/**
	 * Method which checking if this entity is parent of some manager.
	 * 
	 * @return boolean Result of of inspection.
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
	 * @param pSpeedX
	 */
	public void setSpeedX(final float pSpeedX) {
		this.mSpeedX = pSpeedX * Options.cameraRatioFactor;
	}

	/**
	 * @param pSpeedY
	 */
	public void setSpeedY(final float pSpeedY) {
		this.mSpeedY = pSpeedY * Options.cameraRatioFactor;
	}

	/**
	 * @param pSpeedX
	 * @param pSpeedY
	 */
	public void setSpeed(final float pSpeedX, final float pSpeedY) {
		this.mSpeedX = pSpeedX * Options.cameraRatioFactor;
		this.mSpeedY = pSpeedY * Options.cameraRatioFactor;
	}

	/**
	 * @param centerX
	 */
	public void setCenterX(final float pCenterX) {
		this.mX = pCenterX - this.getWidthScaled() / 2;
	}

	/**
	 * @param pCenterY
	 */
	public void setCenterY(final float pCenterY) {
		this.mY = pCenterY - this.getHeightScaled() / 2;
	}

	/**
	 * @param pCenterX
	 * @param pCenterY
	 */
	public void setCenterPosition(final float pCenterX, final float pCenterY) {
		this.mX = pCenterX - this.getWidthScaled() / 2;
		this.mY = pCenterY - this.getHeightScaled() / 2;
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
	public float getSpeedX() {
		return this.mSpeedX;
	}

	/**
	 * @return
	 */
	public float getSpeedY() {
		return this.mSpeedY / Options.cameraRatioFactor;
	}

	/**
	 * @return
	 */
	public float getCenterX() {
		return this.mX + this.getWidthScaled() / 2;
	}

	/**
	 * @return
	 */
	public float getCenterY() {
		return this.mY + this.getHeightScaled() / 2;
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
