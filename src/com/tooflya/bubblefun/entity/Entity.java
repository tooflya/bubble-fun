package com.tooflya.bubblefun.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.planetbattle.interfaces.IAnimationListener;
import com.planetbattle.interfaces.IEntityEvents;
import com.planetbattle.interfaces.ITouchListener;
import com.planetbattle.modifiers.BaseModifier;

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

	private ArrayList<Entity> mChildrens = new ArrayList<Entity>();
	private ArrayList<BaseModifier> mModifiers = new ArrayList<BaseModifier>();

	private Object mParent;

	protected float mOriginWidth;
	protected float mOriginHeight;

	protected boolean mIsIgnoreUpdate;
	protected boolean mIsVisibility;

	public boolean mIsWasClicked;
	public boolean mIsHovered;
	private boolean mIsClickable;

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
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}. Builds a
	 * texture from a {@link String}.
	 * 
	 * @param {@link String} texture
	 */
	public Entity(final String texture) {
		this(texture, mDefaultFramesCountX, mDefaultFramesCountY);
	}

	/**
	 * Constructor for creating a new instance of {@link Entity}.
	 * 
	 * @param {@link Texture} texture
	 */
	public Entity(final Texture texture) {
		this(texture, mDefaultFramesCountX, mDefaultFramesCountY);
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

		this.setOriginWidth(getWidth());
		this.setOriginHeight(getHeight());

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
		if (!this.isVisible()) {
			return;
		}

		/**
		 * Draw the entity of the canvas, taking into account all of its
		 * parameters
		 **/
		pSpriteBatch.setColor(getColor());
		pSpriteBatch.draw(getTextureRegion(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		pSpriteBatch.setColor(Color.CLEAR);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Function for render all child entities.
	 */
	public void onManagedDraw() {
		for (Entity entity : this.mChildrens) {
			entity.draw((SpriteBatch) mParent);
		}
	}

	/**
	 * Function for update all child entities.
	 */
	public void onManagedUpdate() {
		for (Entity entity : this.mChildrens) {
			entity.update();
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
		modifier.setParent(this);
		mModifiers.add(modifier);
	}

	/**
	 * Unregister entity modifier.
	 * 
	 * @param modifier
	 *            the modifier
	 */
	public void unregisterEntityModifier(final BaseModifier modifier) {
		mModifiers.remove(modifier);
	}

	/**
	 * Clear entity modifiers.
	 */
	public void clearEntityModifiers() {
		mModifiers.clear();
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
	 * Function for update entity parameters.
	 */
	public void update() {
		/** Don't update entity if he is not visible **/
		if (!this.isVisible()) {
			return;
		}

		/** Maybe is animation available? **/
		this.runAnimation();

		/** Check for some modifiers registered **/
		for (BaseModifier modifier : mModifiers) {
			modifier.update();
		}
	}

	/**
	 * Function of entity death.
	 */
	public void death() {
		this.delete();
	}

	/**
	 * Function which will delete entity from the manager list.
	 */
	public void delete() {
		this.setVisible(false);
		this.setIgnoreUpdate(true);
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

		/**
		 * 
		 * Check collisions only for elements with some states
		 * 
		 */
		if (false) {
			return false;
		}

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
	public void onAnimationStarted() {
		// It's function must be override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationFinished()
	 */
	@Override
	public void onAnimationFinished() {
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

		this.onAnimationStarted();
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

							this.onAnimationFinished();
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
	 * @param entity
	 *            the new parent
	 */
	public void setParent(final Object entity) {
		this.mParent = entity;
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
	public void setOriginWidth(final float width) {
		this.mOriginWidth = width;
	}

	/**
	 * Sets the origin height.
	 * 
	 * @param height
	 *            the new origin height
	 */
	public void setOriginHeight(final float height) {
		this.mOriginHeight = height;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	/**
	 * Gets the origin width.
	 * 
	 * @return the origin width
	 */
	public float getOriginWidth() {
		return this.mOriginWidth;
	}

	/**
	 * Gets the origin height.
	 * 
	 * @return the origin height
	 */
	public float getOriginHeight() {
		return this.mOriginHeight;
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
	public Object getParent() {
		return this.mParent;
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
