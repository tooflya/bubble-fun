package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

public class ShopIndicator extends Entity {

	private final MoveYModifier mModifier1;
	private final MoveYModifier mModifier2;

	public ShopIndicator(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.create();

		this.mModifier1 = new MoveYModifier(1f, this.getY(), this.getY() + 10f) {
			@Override
			public void onFinished() {
				mModifier2.reset();
			}
		};

		this.mModifier2 = new MoveYModifier(0.5f, this.getY() + 10f, this.getY()) {
			@Override
			public void onFinished() {
				mModifier1.reset();
			}
		};

		this.registerEntityModifier(this.mModifier1);
		this.registerEntityModifier(this.mModifier2);

		this.mModifier1.reset();
	}

	public void update() {
		final int coins = Game.mDatabase.getTotalCoins();

		if (coins < 100) {
			this.mVisible = false;
		} else {
			this.mVisible = true;

			final int count = (coins / 100) - 1;
			this.setCurrentTileIndex(count >= 4 ? 4 : count);
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#setPosition(float, float)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		super.setPosition(pX, pY, true);
	}

}
