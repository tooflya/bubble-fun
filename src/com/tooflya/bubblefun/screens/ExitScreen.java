package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.util.modifier.IModifier;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.modifiers.ScaleModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class ExitScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mAnimationRunning = false;

	@SuppressWarnings("unused")
	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	private final Entity mPanel = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/popup-exit.png", 0, 0, 1, 1), this) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final Entity mYIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/accept-btn.png", 0, 150, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.setScale(1f);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				Game.close();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final Entity mNIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/decline-btn.png", 55, 150, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.setScale(1f);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				ExitScreen.this.modifier4.reset();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final ScaleModifier modifier1 = new ScaleModifier(0.3f, 0f, Options.cameraRatioFactor + Options.cameraRatioFactor / 2, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ExitScreen.this.modifier2.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	}) {
		@Override
		public void reset() {
			super.reset();

			ExitScreen.this.mAnimationRunning = true;
		}
	};

	private final ScaleModifier modifier2 = new ScaleModifier(0.2f, Options.cameraRatioFactor + Options.cameraRatioFactor / 2, Options.cameraRatioFactor / 2, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ExitScreen.this.modifier3.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final ScaleModifier modifier3 = new ScaleModifier(0.2f, Options.cameraRatioFactor / 2, Options.cameraRatioFactor, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ExitScreen.this.mAnimationRunning = false;
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final ScaleModifier modifier4 = new ScaleModifier(0.2f, Options.cameraRatioFactor, Options.cameraRatioFactor + Options.cameraRatioFactor / 2, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ExitScreen.this.modifier5.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final ScaleModifier modifier5 = new ScaleModifier(0.3f, Options.cameraRatioFactor, 0f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			Game.screens.get(Screen.MENU).clearChildScene();
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
	// Constructors
	// ===========================================================

	public ExitScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.mPanel.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		this.mYIcon.create().setCenterPosition(30 * Options.cameraRatioFactor, this.mPanel.getHeight());
		this.mNIcon.create().setCenterPosition(this.mPanel.getWidth() - 30 * Options.cameraRatioFactor, this.mPanel.getHeight());

		this.registerTouchArea(this.mYIcon);
		this.registerTouchArea(this.mNIcon);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);
		this.mPanel.registerEntityModifier(modifier5);

		this.mPanel.setScale(0f);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0.7f);

		this.attachChild(coloredRect);

		return coloredRect;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		this.modifier1.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		if (!this.mAnimationRunning) {
			this.modifier4.reset();
		}
	}

	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

}
