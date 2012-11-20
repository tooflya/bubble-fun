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
import com.tooflya.bubblefun.database.Level;
import com.tooflya.bubblefun.entities.Bonus;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Coin;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.entities.Sprike;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.entities.TutorialText;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class LevelsManager<T> extends EntityManager<Entity> {

	private float PADDING, PADDING_B;
	private float X, Y;

	private EntityManager<Sprite> mNumbers;

	private static LevelLoader mLevelLoader;

	public LevelsManager(int capacity, Entity element) {
		super(capacity, element);

		mLevelLoader = new LevelLoader();

		PADDING = 45f;
		PADDING_B = 23f;

		X = PADDING_B + 5f;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;
	}

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
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;

	}

	public void generate(final EntityManager<Sprite> pNumbers) {
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

			Level level = Game.db.getLevel(icon.id);

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
		final LevelScreen screen = (LevelScreen) Game.screens.get(Screen.LEVEL);

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

				screen.mBlueBird.setIgnoreUpdate(!isBlueBirdNeed);

				screen.gradientSource.changeColors(screen.bitmap, Color.rgb(r1, g1, b1), Color.rgb(r2, g2, b2));
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("chiky", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final Chiky chiky = screen.chikies.create();

				final float startX = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");
				final float startY = SAXUtils.getFloatAttributeOrThrow(pAttributes, "y");

				final float normalStepX = SAXUtils.getFloatAttributeOrThrow(pAttributes, "speed");
				final float speedyStepX = SAXUtils.getFloatAttribute(pAttributes, "speedyStepX", normalStepX);
				final float parashuteStepY = SAXUtils.getFloatAttribute(pAttributes, "parashuteStepY", 0);
				
				final float vectorX = SAXUtils.getFloatAttribute(pAttributes, "vectorX", 0);
				final float vectorY = SAXUtils.getFloatAttribute(pAttributes, "vectorY", 0);

				final float offsetX = SAXUtils.getFloatAttribute(pAttributes, "offsetX", 0);

				final float scale = SAXUtils.getFloatAttribute(pAttributes, "scale", 1);

				final int properties = SAXUtils.getIntAttribute(pAttributes, "properties", 0);

				chiky.initStartX(startX * Options.cameraWidth);

				chiky.initNormalStepX(normalStepX);
				chiky.initSpeedyStepX(speedyStepX);
				chiky.initParashuteStepY(parashuteStepY);
				chiky.initVectorMoveSteps(vectorX, vectorY);

				chiky.initOffsetX(offsetX);

				chiky.initScale(scale);

				chiky.initProperties(properties);

				chiky.initStartY(Options.menuHeight + chiky.getHeightScaled() / 2 + startY * (Options.cameraHeight - Options.menuHeight - Options.touchHeight - chiky.getHeightScaled()));
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */
		mLevelLoader.registerEntityLoader("electrod", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				/*final Electrod electrod = screen.electrods.create();

				final float x = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");
				final float y = SAXUtils.getFloatAttributeOrThrow(pAttributes, "y");

				electrod.setPosition(x, y);*/
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
				final Coin air = screen.airs.create();

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
		mLevelLoader.registerEntityLoader("sprike", new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final Sprike sprike = screen.sprikes.create();

				final float x = SAXUtils.getFloatAttributeOrThrow(pAttributes, "x");
				final float y = SAXUtils.getFloatAttributeOrThrow(pAttributes, "y");
				final float z = SAXUtils.getFloatAttributeOrThrow(pAttributes, "z");

				final float speed = SAXUtils.getFloatAttributeOrThrow(pAttributes, "speed");

				final int size = SAXUtils.getIntAttribute(pAttributes, "size", 1);

				sprike.setCenterPosition(x, y);
				sprike.setSpeedX(speed);
				sprike.init(z, size);
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

				final float rotation = SAXUtils.getFloatAttribute(pAttributes, "rotation", 0);
				final float time = SAXUtils.getFloatAttribute(pAttributes, "time", 0);

				final int index = SAXUtils.getIntAttributeOrThrow(pAttributes, "index");

				final TutorialText sprite = new TutorialText(x, y, rotation, time, screen.mTutorialTextureRegion[index], screen);
				sprite.create().setPosition(x, y);
				sprite.index = index;
				
				screen.mTutorialSprites.add(sprite);
			}
		});

		/**
		 * 
		 * 
		 * 
		 * 
		 */

		if (false) {
			try {
				mLevelLoader.loadLevelFromAsset(Game.instance, "lfx/" + pLevel * (Options.boxNumber + 1) + ".xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("TMXLevel: Loading level from SD");
			InputStream inputStream;
			String state = Environment.getExternalStorageState();
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + pLevel * (Options.boxNumber + 1) + ".xml"); // Can be done with 2 params : folder & file.
			try {
				inputStream = new FileInputStream(file);
				mLevelLoader.loadLevelFromStream(inputStream);
				inputStream.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				if (!Environment.MEDIA_MOUNTED.equals(state) && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
					System.out.println("TMXLevel: SD Storage unavailable.");
				} else {
					System.out.println("TMXLevel: File not found, falling back to regular level. Does it exist ? (" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + pLevel + ".xml" + ")");
				}
			}
		}

	}
}
