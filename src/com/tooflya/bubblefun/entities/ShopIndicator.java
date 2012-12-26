package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class ShopIndicator extends Entity {

	private final MoveYModifier mModifier1;
	private final MoveYModifier mModifier2;

	public ShopIndicator(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.create().setCenterPosition(105f, 10f);

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

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#setPosition(float, float)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		super.setPosition(pX, pY, true);
	}

}
