package com.planetbattle.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.planetbattle.interfaces.IEntityEvents;
import com.planetbattle.ui.Camera;
import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.entity.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Screen extends SpriteBatch implements IEntityEvents {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int SCREENS_COUNT = 1;

	public static final int SPLASH = 0;

	// ===========================================================
	// Fields
	// ===========================================================

	private ArrayList<Entity> childrens = new ArrayList<Entity>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public Screen(final Camera pCamera) {
		setProjectionMatrix(pCamera.combined);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Function for render all child entities.
	 */
	public void onManagedDraw() {
		this.begin();

		for (Entity entity : childrens) {
			entity.draw(this);

			entity.onManagedDraw();
		}

		this.end();
	}

	/**
	 * Function for update all child entities.
	 */
	public void onManagedUpdate(final float pSecondsElapsed, final Camera pCamera) {
		for (Entity entity : childrens) {
			entity.update();

			entity.onManagedUpdate();

			if (!entity.isClickable()) {
				continue;
			}

			/*if (Game.mMouseX > entity.getX() && Game.mMouseX < entity.getX() + entity.getWidth() && Game.mMouseY > entity.getY()
					&& Game.mMouseY < entity.getY() + entity.getHeight()) {
				if (!Game.mMouseDown || entity.hovered) {
					entity.onHover();

					if (Game.mMouseDown && entity.hovered) {
						entity.wasClicked = true;
					}

					if (!Game.mMouseDown && entity.wasClicked) {
						entity.onTouch();
						entity.wasClicked = false;
					}
				}
			} else {
				entity.hovered = false;
				entity.wasClicked = false;
			}*/
		}
	}

	/**
	 * Function to add entity to an existing entity.
	 * 
	 * @param {@link Entity} entity
	 */
	public void attachChild(final Entity entity) {
		if (entity.hasParent()) {
			throw new IllegalStateException("Entity already has a parent!");
		}

		childrens.add(entity);

		entity.setParent(this);
		entity.onAttached();
	}

	/**
	 * Function to remove entity from an existing entity.
	 * 
	 * @param {@link Entity} entity
	 */
	public void detachChild(final Entity entity) {
		childrens.remove(entity);

		entity.setParent(null);
		entity.onDetached();
	}

	// ===========================================================
	// Abstract methods
	// ===========================================================

	public abstract void onBackPressed();

	// ===========================================================
	// Classes
	// ===========================================================

}
