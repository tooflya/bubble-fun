package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class PauseScreen extends PopupScreen {

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mPanel = new Entity(Resources.mPopupBackgroundTextureRegion, this);

	private final ButtonScaleable b1 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final ButtonScaleable b2 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			Game.mDatabase.updateLevel(Options.levelNumber, 1, 0, 0);

			if (Options.levelNumber % 25 == 0) {
				ScreenManager.mChangeAction = 3;
				Game.mScreens.set(Screen.PRELOAD);
			} else {
				Options.levelNumber++;
				((LevelScreen) Game.mScreens.get(Screen.LEVEL)).reInit();

				modifier4.reset();
			}
		}
	};

	private final ButtonScaleable b3 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.mScreens.set(Screen.PRELOAD);

			modifier4.reset();
		}
	};

	private final ButtonScaleable b4 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 1;
			Game.mScreens.set(Screen.PRELOAD);

			modifier4.reset();
		}
	};

	private final ButtonScaleable mMusicIcon = new ButtonScaleable(Resources.mMusicIconTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Options.isMusicEnabled = !Options.isMusicEnabled;

			if (Options.isMusicEnabled) {
				this.setCurrentTileIndex(0);
				Options.mLevelSound.play();
			} else {
				this.setCurrentTileIndex(1);
				Options.mLevelSound.pause();
			}
		}
	};

	private final ButtonScaleable mSoundIcon = new ButtonScaleable(Resources.mLevelSoundIconTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Options.isSoundEnabled = !Options.isSoundEnabled;

			if (Options.isSoundEnabled) {
				this.setCurrentTileIndex(0);
			} else {
				this.setCurrentTileIndex(1);
			}
		}
	};

	private final EntityManager<Entity> mLables = new EntityManager<Entity>(4, new Entity(Resources.mButtonsLabelsTextureRegion));

	// ===========================================================
	// Constructors
	// ===========================================================

	public PauseScreen() {
		this.b1.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 - 90f);
		this.b2.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 - 30f);
		this.b3.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 + 30f);
		this.b4.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 + 90f);

		this.mSoundIcon.create().setCenterPosition(35f, this.mPanel.getHeight() - 40f);
		this.mMusicIcon.create().setCenterPosition(75f, this.mPanel.getHeight() - 40f);

		Entity Entity;

		Entity = mLables.create();
		Entity.setCenterPosition(this.b1.getWidth() / 2, this.b1.getHeight() / 2);
		Entity.setCurrentTileIndex(0);
		this.b1.attachChild(Entity);

		Entity = mLables.create();
		Entity.setCenterPosition(this.b2.getWidth() / 2, this.b2.getHeight() / 2);
		Entity.setCurrentTileIndex(1);
		this.b2.attachChild(Entity);

		Entity = mLables.create();
		Entity.setCenterPosition(this.b3.getWidth() / 2, this.b3.getHeight() / 2);
		Entity.setCurrentTileIndex(2);
		this.b3.attachChild(Entity);

		Entity = mLables.create();
		Entity.setCenterPosition(this.b4.getWidth() / 2, this.b4.getHeight() / 2);
		Entity.setCurrentTileIndex(3);
		this.b4.attachChild(Entity);

		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOff);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void onClose() {
		Game.mScreens.get(Screen.LEVEL).clearChildScene();
	}
}
