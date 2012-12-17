package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class Airplane extends Entity {

	private float mTime;

	private float mShootTime;

	private boolean isReverse;

	private float _mX;

	public Airplane(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public void init() {
		this.setCenterPosition(Options.cameraWidth, Game.random.nextInt(200) + 100);
		this.mTime = 10f;
		this.isReverse = false;
		this.mShootTime = 2f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mTime -= pSecondsElapsed;

		this.mShootTime -= pSecondsElapsed;

		this._mX = this.mX;

		if (this.mTime <= 0f) {
			this.destroy();
		}

		if (this.isReverse) {
			this.mX += 1f;
			if (this.mX >= 200f) {
				this.isReverse = false;
			}
		} else {
			this.mX -= 1f;
			if (this.mX <= -this.getWidth()) {
				this.isReverse = true;
			}
		}

		this.getTextureRegion().setFlippedHorizontal(this._mX - this.mX > 0);

		if (this.mShootTime <= 0f) {
			final LevelScreen screen = (LevelScreen) Game.screens.get(Screen.LEVEL);
			final Chiky chiky = screen.chikies.getByIndex(Game.random.nextInt(screen.chikies.getCount() + 1));

			if (chiky.isCanCollide()) {
				screen.mRedLasers.create().init(this.getCenterX(), this.getCenterY(), chiky.getCenterX(), chiky.getCenterY());

				if (Options.isMusicEnabled) {
					Options.mLaser.play();
				}
			}

			this.mShootTime = 0.2f;
		}
	}
}
