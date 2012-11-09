package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Button;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.modifiers.ScaleModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class ExitScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mAnimationRunning = false;

	@SuppressWarnings("unused")
	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "popup-exit.png", 0, 0, 1, 1), this);

	private final Button mYIcon = new Button(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "accept-btn.png", 0, 150, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.close();
		}
	};

	private final Button mNIcon = new Button(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "decline-btn.png", 55, 150, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			ExitScreen.this.modifier4.reset();
		}
	};

	private final ScaleModifier modifier1 = new ScaleModifier(0.3f, 0f, Options.cameraRatioFactor + Options.cameraRatioFactor / 2) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier2.reset();
		}

		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#reset()
		 */
		@Override
		public void reset() {
			super.reset();

			mAnimationRunning = true;
		}
	};

	private final ScaleModifier modifier2 = new ScaleModifier(0.2f, Options.cameraRatioFactor + Options.cameraRatioFactor / 2, Options.cameraRatioFactor / 2) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier3.reset();
		}
	};

	private final ScaleModifier modifier3 = new ScaleModifier(0.2f, Options.cameraRatioFactor / 2, Options.cameraRatioFactor) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			mAnimationRunning = false;
		}
	};

	private final ScaleModifier modifier4 = new ScaleModifier(0.2f, Options.cameraRatioFactor, Options.cameraRatioFactor + Options.cameraRatioFactor / 2) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier5.reset();
		}
	};

	private final ScaleModifier modifier5 = new ScaleModifier(0.3f, Options.cameraRatioFactor, 0f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			Game.screens.get(Screen.MENU).clearChildScene();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public ExitScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mYIcon.create();
		this.mYIcon.setScaleCenter(this.mYIcon.getWidth() / 2, this.mYIcon.getHeight() / 2);
		this.mYIcon.setCenterPosition(50, this.mPanel.getHeight());

		this.mNIcon.create();
		this.mNIcon.setScaleCenter(this.mNIcon.getWidth() / 2, this.mNIcon.getHeight() / 2);
		this.mNIcon.setCenterPosition(this.mPanel.getWidth() - 50, this.mPanel.getHeight());

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
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
	}

	@Override
	public void onBackPressed() {
	}

}
