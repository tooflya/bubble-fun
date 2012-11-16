package com.tooflya.bubblefun.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.planetbattle.modifiers.BaseModifier;
import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.handlers.UpdateHandler;
import com.tooflya.bubblefun.interfaces.IAnimationListener;
import com.tooflya.bubblefun.interfaces.IEntityEvents;
import com.tooflya.bubblefun.interfaces.ITouchListener;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class Entity extends Sprite implements IAnimationListener, ITouchListener, IEntityEvents {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static int mDefaultFramesCountX = 1;
	private final static int mDefaultFramesCountY = 1;

	// ===========================================================
	// Fields
	// ===========================================================

	protected ArrayList<Entity> mChildrens = new ArrayList<Entity>();
	protected ArrayList<BaseModifier<?>> mModifiers = new ArrayList<BaseModifier<?>>();
	protected ArrayList<UpdateHandler<?>> mUpdateHandlers = new ArrayList<UpdateHandler<?>>();

	private Entity mParent;

	protected float mBaseWidth = -1;
	protected float mBaseHeight = -1;

	protected boolean mIsIgnoreUpdate;
	protected boolean mIsVisibility;

	public boolean mIsWasClicked;
	public boolean mIsHovered;
	private boolean mIsClickable = true;

	private TextureRegion[][] mTextureRegion;

	private int mCurrentFrameX;
	private int mCurrentFrameY;

	private int mFramesCountX;
	private int mFramesCountY;

	private boolean mIsAnimationRunnig = false;
	private int mAnimationSleep;
	private int mAnimationSleepForward;
	private int mAnimationRepeatCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Constructor for creating a new instance of {@link Entity}.
	 */
	public Entity() {
		this.setVisible(true);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}. Builds a
	 * texture from a {@link String}.
	 * 
	 * @param {@link String} texture
	 */
	public Entity(final String texture) {
		this(texture, mDefaultFramesCountX, mDefaultFramesCountY);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}.
	 * 
	 * @param {@link Texture} texture
	 */
	public Entity(final Texture texture) {
		this(texture, mDefaultFramesCountX, mDefaultFramesCountY);
	}

	public Entity(final float pX, final float pY, final Texture texture) {
		this(texture, mDefaultFramesCountX, mDefaultFramesCountY);

		this.setCenterPosition(pX, pY);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final Texture texture, final Screen pScreen) {
		this(pX, pY, texture);

		pScreen.attachChild(this);
		this.setVisible(true);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final Texture texture, final Entity pEntity) {
		this(pX, pY, texture);

		pEntity.attachChild(this);
		this.setVisible(true);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}. Builds a
	 * texture from a {@link String}.
	 * 
	 * @param {@link String} texture
	 * @param {@link Integer} countFramesX
	 * @param {@link Integer} countFramesY
	 */
	public Entity(final String texture, final int countFramesX, final int countFramesY) {
		this(new Texture(Gdx.files.internal(texture)), countFramesX, countFramesY);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final String texture) {
		this(new Texture(Gdx.files.internal(texture)));

		this.setCenterPosition(pX, pY);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final String texture, final Screen pScreen) {
		this(pX, pY, new Texture(Gdx.files.internal(texture)));

		pScreen.attachChild(this);
		this.setVisible(true);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final String texture, final Entity pEntity) {
		this(pX, pY, new Texture(Gdx.files.internal(texture)));

		pEntity.attachChild(this);
		this.setVisible(true);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	public Entity(final float pX, final float pY, final int countFramesX, final int countFramesY, final String texture, final Entity pEntity) {
		this(new Texture(Gdx.files.internal(texture)), countFramesX, countFramesY);

		pEntity.attachChild(this);
		this.setVisible(true);

		this.setCenterPosition(pX, pY);

		this.setBaseWidth(this.getWidth());
		this.setBaseHeight(this.getHeight());
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}.
	 * 
	 * @param {@link Texture} texture
	 * @param {@link Integer} countFramesX
	 * @param {@link Integer} countFramesY
	 */
	public Entity(final Texture texture, final int countFramesX, final int countFramesY) {
		this.setTexture(texture);
		this.setSize(getTexture().getWidth() / countFramesX, getTexture().getHeight() / countFramesY);

		this.setOrigin(getWidth() / 2, getHeight() / 2);

		this.setBaseWidth(getWidth());
		this.setBaseHeight(getHeight());

		this.mTextureRegion = TextureRegion.split(getTexture(), getTexture().getWidth() / countFramesX, getTexture().getHeight() / countFramesY);
		this.flipTextureRegion(countFramesX, countFramesY);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.gdx.graphics.g2d.Sprite#draw(com.badlogic.gdx.graphics.g2d
	 * .SpriteBatch)
	 */
	@Override
	public void draw(final SpriteBatch pSpriteBatch) {
		if (!this.isVisible() || !this.hasParent()) {
			return;
		}

		/**
		 * Draw the entity of the canvas, taking into account all of its
		 * parameters
		 **/
		pSpriteBatch.begin();
		pSpriteBatch.setColor(getColor());
		pSpriteBatch.draw(getTextureRegion(),
				getX() + this.getParentX() + (this.getParent().getBaseWidth() - this.getParent().getWidth() * this.getParent().getScaleX()) / 2,
				getY() + this.getParentY() + (this.getParent().getHeight() - this.getParent().getHeight() * this.getParent().getScaleY()) / 2,
				getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		pSpriteBatch.setColor(Color.CLEAR);
		pSpriteBatch.end();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Function for render all child entities.
	 */
	public void onManagedDraw(final float pSecondsElapsed) {
		this.draw(Screen.mSpriteBatch);

		for (Entity entity : this.mChildrens) {
			entity.onManagedDraw(pSecondsElapsed);
		}
	}

	public void onManagedUpdate(final float pSecondsElapsed) {
		this.onManagedUpdate(pSecondsElapsed, true);
	}

	/**
	 * Function for update all child entities.
	 */
	public void onManagedUpdate(final float pSecondsElapsed, final boolean pIsTouchUpdate) {
		for (Entity entity : this.mChildrens) {
			entity.onManagedUpdate(pSecondsElapsed);

			if (pIsTouchUpdate) {
				if (!entity.isClickable()) {
					continue;
				}

				if (Game.mMouseX > entity.getX() && Game.mMouseX < entity.getX() + entity.getWidth() && Game.mMouseY > entity.getY()
						&& Game.mMouseY < entity.getY() + entity.getHeight()) {
					if (!Game.mMouseDown || entity.mIsHovered) {
						entity.onHover();

						if (Game.mMouseDown && entity.mIsHovered) {
							entity.mIsWasClicked = true;
						}

						if (!Game.mMouseDown && entity.mIsWasClicked) {
							entity.onTouch();
							entity.mIsWasClicked = false;
						}
					}
				} else {
					entity.mIsHovered = false;
					entity.mIsWasClicked = false;
				}
			}
		}

		/** Maybe is animation available? **/
		this.runAnimation();

		/** Check for some modifiers registered **/
		for (UpdateHandler<?> handler : mUpdateHandlers) {
			handler.onManagedUpdate(pSecondsElapsed);
		}

		/** Check for some modifiers registered **/
		for (BaseModifier<?> modifier : mModifiers) {
			modifier.onManagedUpdate(pSecondsElapsed);
		}
	}

	/**
	 * Function to add entity to an existing entity.
	 * 
	 * @param {@link Entity} entity
	 */
	public void attachChild(final Entity pEntity) {
		if (pEntity.hasParent()) {
			throw new IllegalStateException("Entity already has a parent!");
		}

		this.mChildrens.add(pEntity);

		pEntity.setParent(this);
		pEntity.onAttached();
	}

	/**
	 * Function to remove entity from an existing entity.
	 * 
	 * @param {@link Entity} entity
	 */
	public void detachChild(final Entity pEntity) {
		this.mChildrens.remove(pEntity);

		pEntity.setParent(null);
		pEntity.onDetached();
	}

	/**
	 * Function for detach self from parent.
	 */
	public void detachSelf() {
		try {
			((Entity) this.mParent).detachChild(this);
		} catch (NullPointerException e) {
			throw new NullPointerException("Entity doesn't has a parent");
		}
	}

	/**
	 * Register entity modifier.
	 * 
	 * @param modifier
	 *            the modifier
	 */
	public void registerEntityModifier(final BaseModifier modifier) {
		this.mModifiers.add(modifier);
		modifier.setEntity(this);
	}

	/**
	 * Unregister entity modifier.
	 * 
	 * @param modifier
	 *            the modifier
	 */
	public void unregisterEntityModifier(final BaseModifier<?> modifier) {
		this.mModifiers.remove(modifier);
	}

	/**
	 * Clear entity modifiers.
	 */
	public void clearEntityModifiers() {
		this.mModifiers.clear();
	}

	/**
	 * Register entity modifier.
	 * 
	 * @param modifier
	 *            the modifier
	 */
	public void registerUpdateHandler(final UpdateHandler handler) {
		this.mUpdateHandlers.add(handler);
		handler.setEntity(this);
	}

	/**
	 * Unregister entity modifier.
	 * 
	 * @param modifier
	 *            the modifier
	 */
	public void unregisterUpdateHandler(final UpdateHandler<?> handler) {
		this.mUpdateHandlers.remove(handler);
	}

	/**
	 * Clear entity modifiers.
	 */
	public void clearUpdateHandlers() {
		this.mUpdateHandlers.clear();
	}

	/**
	 * Function for return the entity from the pool.
	 * 
	 * @return {@link Entity} entity
	 */
	public Entity create() {
		this.reset();

		return this;
	}

	/**
	 * Function for reset all parameters of entity before it return from pool.
	 */
	public void reset() {
		this.setVisible(true);
		this.setIgnoreUpdate(false);
	}

	/**
	 * Collides with.
	 * 
	 * @param pElement
	 *            the element
	 * @return true, if successful
	 */
	public boolean collidesWith(final Entity pElement) {
		final Entity element = this;
		final Entity element_ = pElement;

		final float x = element_.getCenterX() - element.getCenterX();
		final float y = element_.getCenterY() - element.getCenterY();
		final float s = (element.getWidth() + element_.getWidth()) / 2;
		if (x * x + y * y <= s * s) {
			return true;
		}

		return false;
	}

	/**
	 * Deep copy.
	 * 
	 * @return {@link Entity} entity
	 */
	public Entity deepCopy() {
		return null;
	}

	// ===========================================================
	// Events
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IEntityEvents#onAttached()
	 */
	@Override
	public void onAttached() {
		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IEntityEvents#onDetached()
	 */
	@Override
	public void onDetached() {
		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationStarted()
	 */
	@Override
	public void onStarted() {
		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationFinished()
	 */
	@Override
	public void onFinished() {
		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.ITouchListener#onHover()
	 */
	@Override
	public void onHover() {
		this.mIsHovered = true;

		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.ITouchListener#onTouch()
	 */
	@Override
	public void onTouch() {
		// It's function must be override
	}

	// ===========================================================
	// Methods for animations
	// ===========================================================

	/**
	 * Method for the start of frame-based animation.
	 */
	public void animate() {
		this.animate(0, 1);
	}

	/**
	 * Method for the start of frame-based animation.
	 * 
	 * @param {@link Integer} animationSleep the animation sleep
	 */
	public void animate(final int animationSleep) {
		this.animate(animationSleep, 1);
	}

	/**
	 * Method for the start of frame-based animation.
	 * 
	 * @param {@link Integer} animationSleep the animation sleep
	 * @param {@link Integer} animationRepeatCount the animation repeat count
	 */
	public void animate(final int animationSleep, final int animationRepeatCount) {
		this.mIsAnimationRunnig = true;
		this.mAnimationRepeatCount = animationRepeatCount - 1;
		this.mAnimationSleep = animationSleep;

		this.onStarted();
	}

	/**
	 * Run animation.
	 */
	private void runAnimation() {
		if (mIsAnimationRunnig) {
			if (this.mAnimationSleepForward == 0) {
				if (this.mCurrentFrameY < this.mFramesCountY - 1) {
					this.mCurrentFrameY++;
				} else {
					this.mCurrentFrameY = 0;

					if (this.mCurrentFrameX < this.mFramesCountX - 1) {
						this.mCurrentFrameX++;
					} else {
						this.mCurrentFrameX = 0;
						this.mCurrentFrameY = 0;

						if (this.mAnimationRepeatCount == 0) {
							mIsAnimationRunnig = false;

							this.onFinished();
						} else {
							this.mAnimationRepeatCount--;
						}
					}
				}

				this.mAnimationSleepForward = this.mAnimationSleep;
			} else {
				this.mAnimationSleepForward--;
			}
		}
	}

	// ===========================================================
	// Private methods
	// ===========================================================

	/**
	 * Flip texture region.
	 * 
	 * @param countFramesX
	 *            the count frames x
	 * @param countFramesY
	 *            the count frames y
	 */
	private void flipTextureRegion(final int countFramesX, final int countFramesY) {
		for (int i = 0; i <= countFramesY - 1; i++) {
			for (int j = 0; j <= countFramesX - 1; j++) {
				this.mTextureRegion[i][j].flip(false, true);
			}
		}

		this.mFramesCountX = countFramesY;
		this.mFramesCountY = countFramesX;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	/**
	 * Sets the width.
	 * 
	 * @param pWidth
	 *            the new width
	 */
	public void setWidth(final float pWidth) {
		this.setSize(pWidth, this.getHeight());
	}

	/**
	 * Sets the height.
	 * 
	 * @param pHeight
	 *            the new height
	 */
	public void setHeight(final float pHeight) {
		this.setSize(this.getWidth(), pHeight);
	}

	/**
	 * Sets the center x.
	 * 
	 * @param centerX
	 *            the new center x
	 */
	public void setCenterX(final float centerX) {
		this.setPosition(centerX - getWidth() / 2, getY());
	}

	/**
	 * Sets the center y.
	 * 
	 * @param centerY
	 *            the new center y
	 */
	public void setCenterY(final float centerY) {
		this.setPosition(getX(), centerY - getHeight() / 2);
	}

	/**
	 * Sets the center position.
	 * 
	 * @param centerX
	 *            the center x
	 * @param centerY
	 *            the center y
	 */
	public void setCenterPosition(final float centerX, final float centerY) {
		this.setPosition(centerX - getWidth() / 2, centerY - getHeight() / 2);
	}

	/**
	 * Sets the visible.
	 * 
	 * @param pVisiblity
	 *            the new visible
	 */
	public void setVisible(final boolean pVisiblity) {
		this.mIsVisibility = pVisiblity;
	}

	/**
	 * Sets the clickable.
	 * 
	 * @param pClickable
	 *            the new clickable
	 */
	public void setClickable(final boolean pClickable) {
		this.mIsClickable = pClickable;
	}

	/**
	 * Sets the ignore update.
	 * 
	 * @param ignoreUpdate
	 *            the new ignore update
	 */
	public void setIgnoreUpdate(final boolean ignoreUpdate) {
		this.mIsIgnoreUpdate = ignoreUpdate;
	}

	/**
	 * Sets the parent.
	 * 
	 * @param pEntity
	 *            the new parent
	 */
	public void setParent(final Entity pEntity) {
		this.mParent = pEntity;
	}

	/**
	 * Sets the current tile.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setCurrentTile(final int x, final int y) {
		mCurrentFrameX = x;
		mCurrentFrameY = y;
	}

	/**
	 * Sets the origin width.
	 * 
	 * @param width
	 *            the new origin width
	 */
	public void setBaseWidth(final float width) {
		this.mBaseWidth = width;
	}

	/**
	 * Sets the origin height.
	 * 
	 * @param height
	 *            the new origin height
	 */
	public void setBaseHeight(final float height) {
		this.mBaseHeight = height;
	}

	public void setScale(final float pScale) {
		super.setScale(pScale);

		for (Entity entity : this.mChildrens) {
			entity.setScale(pScale);
		}
	}

	// ===========================================================
	// Getters
	// ===========================================================

	/*@Override
	public float getWidth() {
		if (this.mBaseWidth == -1) {
			return super.getWidth();
		} else {
			return this.getBaseWidth() * this.getScaleX();
		}
	}

	@Override
	public float getHeight() {
		if (this.mBaseHeight == -1) {
			return super.getHeight();
		} else {
			return this.getBaseHeight() * this.getScaleY();
		}
	}y

	/**
	 * Gets the origin width.
	 * 
	 * @return the origin width
	 */
	public float getBaseWidth() {
		return this.mBaseWidth;
	}

	/**
	 * Gets the origin height.
	 * 
	 * @return the origin height
	 */
	public float getBaseHeight() {
		return this.mBaseHeight;
	}

	/**
	 * Gets the center x.
	 * 
	 * @return the center x
	 */
	public float getCenterX() {
		return getX() + getWidth() / 2;
	}

	/**
	 * Gets the center y.
	 * 
	 * @return the center y
	 */
	public float getCenterY() {
		return getY() + getHeight() / 2;
	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return mIsVisibility;
	}

	/**
	 * Checks if is clickable.
	 * 
	 * @return true, if is clickable
	 */
	public boolean isClickable() {
		return this.mIsClickable;
	}

	/**
	 * Checks if is ignore update.
	 * 
	 * @return true, if is ignore update
	 */
	public boolean isIgnoreUpdate() {
		return mIsIgnoreUpdate;
	}

	/**
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	public Entity getParent() {
		return this.mParent;
	}

	public float getParentX() {
		if (!this.hasParent())
			return 0;

		return this.getParent().getX();
	}

	public float getParentY() {
		if (!this.hasParent())
			return 0;

		return this.getParent().getY();
	}

	/**
	 * Checks for parent.
	 * 
	 * @return true, if successful
	 */
	public boolean hasParent() {
		if (mParent == null) {
			return false;
		}

		return true;
	}

	/**
	 * Gets the texture region.
	 * 
	 * @return the texture region
	 */
	public TextureRegion getTextureRegion() {
		return mTextureRegion[mCurrentFrameX][mCurrentFrameY];
	}
}
