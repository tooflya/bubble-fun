package com.tooflya.bubblefun.managers;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.util.modifier.IModifier;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.HUD;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.BoxLockedScreen;
import com.tooflya.bubblefun.screens.BoxScreen;
import com.tooflya.bubblefun.screens.BoxesUnlockScreen;
import com.tooflya.bubblefun.screens.CreditsScreen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelEndScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.MoreScreen;
import com.tooflya.bubblefun.screens.PauseScreen;
import com.tooflya.bubblefun.screens.PreloadScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mChangeAction = 0;

	private final HUD hud = new HUD();

	private final AlphaModifier modifierOn = new AlphaModifier(0.4f, 0f, 1f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			Game.engine.getScene().onDetached();

			if (Z != 666) {
				screens[Z].setScene(Game.engine);
				screens[Z].onAttached();
				Screen.screen = Z;
			} else {
				tempScreen.setScene(Game.engine);
				tempScreen.onAttached();
			}

			ScreenManager.this.modifierOff.reset();

		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final AlphaModifier modifierOff = new AlphaModifier(0.4f, 1f, 0f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ScreenManager.this.rectangle.setAlpha(0f);

			((Screen) Game.engine.getScene()).onPostAttached();
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

		ScreenManager.this.rectangle.registerEntityModifier(ScreenManager.this.modifierOn);
		ScreenManager.this.rectangle.registerEntityModifier(ScreenManager.this.modifierOff);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void createSurfaces() {
		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		screens[Screen.CHOISE] = new LevelChoiseScreen();
		screens[Screen.LEVEL] = new LevelScreen();
		screens[Screen.LEVELEND] = new LevelEndScreen();
		screens[Screen.EXIT] = new ExitScreen();
		screens[Screen.PAUSE] = new PauseScreen();
		screens[Screen.BOX] = new BoxScreen();
		screens[Screen.MORE] = new MoreScreen();
		screens[Screen.CREDITS] = new CreditsScreen();
		screens[Screen.PRELOAD] = new PreloadScreen();
		screens[Screen.BL] = new BoxLockedScreen();
		screens[Screen.BOXESUNLOCK] = new BoxesUnlockScreen();
	}

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.screenWidth, Options.screenHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		coloredRect.setAlpha(0f);

		this.hud.attachChild(coloredRect);

		return coloredRect;
	}

	private static int Z = 666;
	private static Screen tempScreen;

	public void set(final int pScreen) {
		if (Z == pScreen)
			return;

		Z = pScreen;

		this.modifierOn.reset();
	}

	public void set(final int pScreen, final boolean pNoAnimation) {
		if (Z == pScreen)
			return;

		Z = pScreen;

		Game.engine.getScene().onDetached();
		screens[Z].setScene(Game.engine);
		screens[Z].onAttached();
		Screen.screen = Z;
	}

	public void set(final Screen pScreen) {
		tempScreen = pScreen;

		this.modifierOn.reset();
	}

	public void setChildScreen(final Screen pScreen, final boolean pModalDraw, final boolean pModalUpdate, final boolean pModalTouch) {
		this.getCurrent().setChildScene(pScreen, pModalDraw, pModalUpdate, pModalTouch);
		pScreen.onAttached();
	}

	public void clearChildScreens() {
		this.getCurrent().getChildScene().onDetached();
	}

	public Screen get(final int pScreen) {
		return screens[pScreen];
	}

	public Screen getCurrent() {
		try {
			return screens[Screen.screen];
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		return null;
	}
}
