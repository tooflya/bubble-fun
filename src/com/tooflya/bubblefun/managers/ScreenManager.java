package com.tooflya.bubblefun.managers;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.util.modifier.IModifier;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelEndScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.PauseScreen;
import com.tooflya.bubblefun.screens.PreloaderScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager {

	// ===========================================================
	// Constants
	// ===========================================================

	private final HUD hud = new HUD();

	private final AlphaModifier modifierOn = new AlphaModifier(0.2f, 0f, 1f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			Game.engine.getScene().onDetached();
			screens[Z].setScene(Game.engine);
			screens[Z].onAttached();
			Screen.screen = Z;

			if (ao) {
				ScreenManager.this.modifierOff.reset();
			} else {
				ScreenManager.this.rectangle.registerEntityModifier(ScreenManager.this.modifierOff);
				ao = true;
			}
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final AlphaModifier modifierOff = new AlphaModifier(0.2f, 1f, 0f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ScreenManager.this.rectangle.setAlpha(0f);
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	// ===========================================================
	// Fields
	// ===========================================================

	/** List of available screens */
	public Screen[] screens;

	private Rectangle rectangle;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScreenManager() {
		this.rectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

		Game.camera.setHUD(this.hud);

		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		screens[Screen.CHOISE] = new LevelChoiseScreen();
		screens[Screen.PRELOAD] = new PreloaderScreen();
		screens[Screen.LEVEL] = new LevelScreen();
		screens[Screen.LEVELEND] = new LevelEndScreen();
		screens[Screen.EXIT] = new ExitScreen();
		screens[Screen.PAUSE] = new PauseScreen();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.screenWidth, Options.screenHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.modifierOn.setRemoveWhenFinished(false);
		this.modifierOff.setRemoveWhenFinished(false);

		coloredRect.setAlpha(0f);

		this.hud.attachChild(coloredRect);

		return coloredRect;
	}

	private static int Z = 666;

	private boolean ai = false;
	private boolean ao = false;

	public void set(final int pScreen) {
		if (Z == pScreen)
			return;

		Z = pScreen;

		if (ai) {
			this.modifierOn.reset();
		} else {
			this.rectangle.registerEntityModifier(this.modifierOn);
			ai = true;
		}
	}

	public Screen get(final int pScreen) {
		return screens[pScreen];
	}

	public Screen getCurrent() {
		return screens[Screen.screen];
	}
}
