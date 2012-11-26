package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class TutorialText extends Sprite {

	public boolean finish;

	private float mElapsedTime, mTime;

	public int index;

	private final AlphaModifier mAlphaModifier1 = new AlphaModifier(1.5f, 0f, 1f) {
		@Override
		public void onFinished() {
			mAlphaModifier.reset();
		}
	};

	private final AlphaModifier mAlphaModifier = new AlphaModifier(10f, 1f, 0f) {
		@Override
		public void onFinished() {
			finish = true;
		}
	};

	public TutorialText(final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public TutorialText(final float x, final float y, final float rotation, final float time, final TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, pParentScreen);

		this.setCenterPosition(x, y);

		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotation(rotation);

		this.mTime = time;
		this.mElapsedTime = 0;

		this.enableBlendFunction();

		this.registerEntityModifier(mAlphaModifier1);
		this.registerEntityModifier(mAlphaModifier);
	}

	public Entity create() {
		this.finish = false;

		this.show();

		this.mAlpha = 0;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mAlpha > 0 || finish)
			return;

		this.mElapsedTime += pSecondsElapsed;

		if (this.mElapsedTime >= this.mTime) {
			this.mAlphaModifier1.reset();
		}
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#onManagedDraw(javax.microedition.khronos.opengles.GL10, org.anddev.andengine.engine.camera.Camera)
	 */
	@Override
	public void onManagedDraw(final GL10 GL, final Camera pCamera) {
		super.onManagedDraw(GL, pCamera);

		
		
	}

	@Override
	public void destroy() {
		super.destroy();

		//this.detachSelf();
	}
}
