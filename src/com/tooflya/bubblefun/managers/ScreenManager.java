package com.tooflya.bubblefun.managers;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.primitive.Rectangle;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.HUD;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.AdvertisimentScreen;
import com.tooflya.bubblefun.screens.BoxLockedScreen;
import com.tooflya.bubblefun.screens.BoxScreen;
import com.tooflya.bubblefun.screens.BoxesUnlockScreen;
import com.tooflya.bubblefun.screens.CreditsScreen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.GetCoinsScreen;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelEndScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.MoreScreen;
import com.tooflya.bubblefun.screens.PauseScreen;
import com.tooflya.bubblefun.screens.PreloadScreen;
import com.tooflya.bubblefun.screens.RateScreen;
import com.tooflya.bubblefun.screens.ResetScreen;
import com.tooflya.bubblefun.screens.Screen;
import com.tooflya.bubblefun.screens.StoreScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mChangeAction = 0;

	private static Screen tempScreen;

	// ===========================================================
	// Fields
	// ===========================================================

	private final HUD mHeadUpDisplay = new HUD();

	/** List of available screens */
	private Screen[] screens;

	private Rectangle rectangle;

	/**
	 * 
	 */
	private final AlphaModifier modifierOn = new AlphaModifier(0.4f, 0f, 1f) {
		@Override
		public void onFinished() {
			Game.engine.getScene().onDetached();

			if (Screen.screen != -1) {
				Game.engine.setScene(screens[Screen.screen]);
				screens[Screen.screen].onAttached();
			} else {
				Game.engine.setScene(tempScreen);
				tempScreen.onAttached();
			}

			modifierOff.reset();
		}
	};

	/**
	 * 
	 */
	private final AlphaModifier modifierOff = new AlphaModifier(0.4f, 1f, 0f) {
		@Override
		public void onFinished() {
			((Screen) Game.engine.getScene()).onPostAttached();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * 
	 */
	public ScreenManager() {
		this.rectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

		this.rectangle.registerEntityModifier(this.modifierOn);
		this.rectangle.registerEntityModifier(this.modifierOff);

		Game.camera.setHUD(this.mHeadUpDisplay);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 */
	public void createSurfaces() {
		this.screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		this.screens[Screen.MENU] = new MenuScreen();
		this.screens[Screen.CHOISE] = new LevelChoiseScreen();
		this.screens[Screen.LEVEL] = new LevelScreen();
		this.screens[Screen.LEVELEND] = new LevelEndScreen();
		this.screens[Screen.EXIT] = new ExitScreen();
		this.screens[Screen.STORE] = new StoreScreen();
		this.screens[Screen.PAUSE] = new PauseScreen();
		this.screens[Screen.BOX] = new BoxScreen();
		this.screens[Screen.MORE] = new MoreScreen();
		this.screens[Screen.CREDITS] = new CreditsScreen();
		this.screens[Screen.PRELOAD] = new PreloadScreen();
		this.screens[Screen.BL] = new BoxLockedScreen();
		this.screens[Screen.BOXESUNLOCK] = new BoxesUnlockScreen();
		this.screens[Screen.ADS] = new AdvertisimentScreen();
		this.screens[Screen.COINS] = new GetCoinsScreen();
		this.screens[Screen.RATE] = new RateScreen();
		this.screens[Screen.RESET] = new ResetScreen();
	}

	// ===========================================================
	// Setters
	// ===========================================================

	/**
	 * @param pScreen
	 */
	public void set(final int pScreen) {
		if (Screen.screen == pScreen)
			return;

		Screen.screen = pScreen;

		this.modifierOn.reset();
	}

	/**
	 * @param pScreen
	 */
	public void set(final Screen pScreen) {
		tempScreen = pScreen;

		this.modifierOn.reset();
	}

	// ===========================================================
	// Getters
	// ===========================================================

	/**
	 * @param pScreen
	 * @return
	 */
	public Screen get(final int pScreen) {
		return screens[pScreen];
	}

	/**
	 * @return
	 */
	public Screen getCurrent() {
		return screens[Screen.screen];
	}

	// ===========================================================
	// Child screens
	// ===========================================================

	/**
	 * @param pScreen
	 * @param pModalDraw
	 * @param pModalUpdate
	 * @param pModalTouch
	 */
	public void setChildScreen(final Screen pScreen, final boolean pModalDraw, final boolean pModalUpdate, final boolean pModalTouch) {
		this.getCurrent().setChildScene(pScreen, pModalDraw, pModalUpdate, pModalTouch);
		pScreen.onAttached();
	}

	/**
	 * 
	 */
	public void clearChildScreens() {
		this.getCurrent().getChildScene().onDetached();
	}

	/**
	 * @param pX
	 * @param pY
	 * @param pRed
	 * @param pGreen
	 * @param pBlue
	 * @return
	 */
	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.screenWidth, Options.screenHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		coloredRect.setAlpha(0f);

		this.mHeadUpDisplay.attachChild(coloredRect);

		return coloredRect;
	}
}
