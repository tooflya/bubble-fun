package com.tooflya.bubblefun.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.anddev.andengine.level.LevelLoader;
import org.anddev.andengine.level.LevelLoader.IEntityLoader;
import org.anddev.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.graphics.Color;
import android.os.Environment;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.database.Level;
import com.tooflya.bubblefun.entities.Bonus;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Coin;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelsManager<T> extends EntityManager<Entity> {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float PADDING, PADDING_B;
	private float X, Y;

	private static Chiky chiky = null;

	private EntityManager<Entity> mNumbers;

	private static LevelLoader mLevelLoader;

	private static int tutorialCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelsManager(int capacity, Entity element) {
		super(capacity, element);

		mLevelLoader = new LevelLoader();

		PADDING = 45f;
		PADDING_B = 23f;

		X = PADDING_B + 5f;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 6) / 2;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bubblefun.managers.EntityManager#clear()
	 */
	@Override
	public void clear() {
		super.clear();

		PADDING = 45f;
		PADDING_B = 23f;

		X = PADDING_B + 5f;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 6) / 2;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void generate(final EntityManager<Entity> pNumbers) {
		this.mNumbers = pNumbers;

		this.generate();
	}

	public void generate() {
		this.mNumbers.clear();

		for (int i = 0; i < this.getCapacity(); i++) {

			if (i % 5 == 0) {
				X += 0;
			} else {
				X += PADDING + PADDING_B;
			}

			if (i % 5 == 0 && i != 0) {
				Y += PADDING + PADDING_B;
				X = PADDING_B + 5f;
			}

			LevelIcon icon = ((LevelIcon) this.create());

			icon.setCenterPosition(25f + X, 25f + Y);
			icon.id = (i + 1);

			Level level = Game.mDatabase.getLevel(icon.id);

			if (icon.id == 1 || level.isOpen()) {
				icon.blocked = false;
				icon.setCurrentTileIndex(level.getStarsCount());

				LevelChoiseScreen.starsCollected += level.getStarsCount();

				if (icon.id < 10) {
					final Entity text = this.mNumbers.create();
					text.setCurrentTileIndex(icon.id);
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2, icon.getHeight() / 2 - 2f);
				} else {
					float a;
					if ((int) Math.floor(icon.id / 10) == 1) {
						a = 1;
					} else {
						a = 0;
					}

					Entity text = this.mNumbers.create();
					text.setCurrentTileIndex((int) Math.floor(icon.id / 10));
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2 - text.getWidth() / 4 + a, icon.getHeight() / 2 - 2f);
					text = this.mNumbers.create();
					text.setCurrentTileIndex(icon.id % 10);
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2 + text.getWidth() / 4 - a, icon.getHeight() / 2 - 2f);
				}
			} else {
				icon.setCurrentTileIndex(4);
				icon.blocked = true;
				final Entity text = this.mNumbers.create();
				text.setVisible(false);
			}
		}
	}

	public static void generateLevel(final int pLevel) {
		final LevelScreen screen = (LevelScreen) Game.mScreens.get(Screen.LEVEL);

		tutorialCount = 0;
		LevelScreen.mBirdsCount = 0;

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("level", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final int r1 = SAXUtils.getIntAttributeOrThrow(pAttributes, "r1");
				final int g1 = SAXUtils.getIntAttributeOrThrow(pAttributes, "g1");
				final int b1 = SAXUtils.getIntAttributeOrThrow(pAttributes, "b1");

				final int r2 = SAXUtils.getIntAttributeOrThrow(pAttributes, "r2");
				final int g2 = SAXUtils.getIntAttributeOrThrow(pAttributes, "g2");
				final int b2 = SAXUtils.getIntAttributeOrThrow(pAttributes, "b2");

				final boolean isBlueBirdNeed = SAXUtils.getBooleanAttribute(pAttributes, "bluebird", true);

				screen.mBlueBird.create();
				screen.mBlueBird.setIgnoreUpdate(!isBlueBirdNeed);

				Resources.mLevelBackgroundGradientSource.changeColors(Resources.mLevelGradientBitmap, Color.rgb(r1, g1, b1), Color.rgb(r2, g2, b2));
				Resources.mBackgroundGradientTextureAtlas2.setUpdateOnHardwareNeeded(true);
			}
		});

		// Example:
		// <chiky name="tooflya.com" scale="0.5" minTime="0.1" maxTime="1.1" speedTime="0.5" offsetTime="0.3" isRTime="true" normalMaxTime="1" normalSpeedTime="0.5" unnormalMaxTime="0.5" unnormalSpeedTime="1" properties="3">
		// <ctrPoint x="10" y="50"/>
		// <ctrPoint x="90" y="50"/>
		// </chiky>
		mLevelLoader.registerEntityLoader("chiky", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				chiky = screen.chikies.create();
				if (chiky != null)
				{
					// TODO: (R) Try to not set default values. For example, how we can set Float.MAX_VALUE for normalMaxTime.
					final float scale = SAXUtils.getFloatAttribute(pAttributes, "scale", 1);
					chiky.initScale(scale);

					final float minTime = SAXUtils.getFloatAttribute(pAttributes, "minTime", 0);
					chiky.initMinTime(minTime);
					final float maxTime = SAXUtils.getFloatAttribute(pAttributes, "maxTime", 1);
					chiky.initMaxTime(maxTime);
					final float speedTime = SAXUtils.getFloatAttribute(pAttributes, "speedTime", 1);
					chiky.initSpeedTime(speedTime);
					final float offsetTime = SAXUtils.getFloatAttribute(pAttributes, "offsetTime", 0);
					chiky.initOffsetTime(offsetTime);
					final boolean isRTime = SAXUtils.getBooleanAttribute(pAttributes, "isRTime", true);
					chiky.initIsReverseTime(isRTime);

					final float normalMaxTime = SAXUtils.getFloatAttribute(pAttributes, "normalMaxTime", 0);
					chiky.initNormalMaxTime(normalMaxTime);
					float normalSpeedTime = SAXUtils.getFloatAttribute(pAttributes, "normalSpeedTime", 0);
					if (normalSpeedTime == 0) {
						normalSpeedTime = Float.MIN_VALUE;
					}
					chiky.initNormalSpeedTime(normalSpeedTime);
					final float unnormalMaxTime = SAXUtils.getFloatAttribute(pAttributes, "unnormalMaxTime", 0);
					chiky.initUnnormalMaxTime(unnormalMaxTime);
					float unnormalSpeedTime = SAXUtils.getFloatAttribute(pAttributes, "unnormalSpeedTime", 0);
					if (unnormalSpeedTime == 0) {
						unnormalSpeedTime = Float.MIN_VALUE;
					}
					chiky.initUnnormalSpeedTime(unnormalSpeedTime);

					final int properties = SAXUtils.getIntAttribute(pAttributes, "properties", 0);
					chiky.initProperties(properties);

					final int weight = SAXUtils.getIntAttribute(pAttributes, "weight", 0);
					chiky.setWeight(weight);
					
					LevelScreen.mBirdsCount++;
				}
			}
		});

		mLevelLoader.registerEntityLoader("ctrPoint", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				if (chiky != null)
				{
					final int x = SAXUtils.getIntAttribute(pAttributes, "x", 50);
					final int y = SAXUtils.getIntAttribute(pAttributes, "y", 50);
					chiky.addControlPoint((short) x, (short) y);
				}
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("coin", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final Coin air = screen.coins.create();

				final float x = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");
				final float y = SAXUtils.getFloatAttributeOrThrow(pAttributes, "y");

				air.setCenterPosition(x, y);
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("bonus", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final Bonus bonus = screen.bonuses.create();

				final float x = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");

				final float time = SAXUtils.getFloatAttributeOrThrow(pAttributes, "time");

				bonus.initTime(time, x, 1f);
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("tutorial", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final float x = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");
				final float y = SAXUtils.getFloatAttributeOrThrow(pAttributes, "y");

				final String text = Game.getString(SAXUtils.getAttributeOrThrow(pAttributes, "text"));

				final float waitTime = SAXUtils.getFloatAttribute(pAttributes, "wait", 0);
				final float showTime = SAXUtils.getFloatAttribute(pAttributes, "show", 5);

				screen.mTutorialTexts.get(tutorialCount).setPosition(x, y);
				screen.mTutorialTexts.get(tutorialCount).setText(text);

				screen.mTutorialTexts.get(tutorialCount).setWaitTime(waitTime);
				screen.mTutorialTexts.get(tutorialCount).setShowTime(showTime);
				screen.mTutorialTexts.get(tutorialCount).setVisible(true);

				tutorialCount++;
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */

		if (Options.DEBUG) {
			InputStream inputStream;
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + pLevel * (Options.boxNumber + 1) + ".xml");
			try {
				inputStream = new FileInputStream(file);
				mLevelLoader.loadLevelFromStream(inputStream);
				inputStream.close();
			} catch (IOException e) {

			}

		} else {
			try {
				mLevelLoader.loadLevelFromAsset(Game.mInstance, "lfx/" + String.valueOf(pLevel + (25 * Options.boxNumber)) + ".xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
