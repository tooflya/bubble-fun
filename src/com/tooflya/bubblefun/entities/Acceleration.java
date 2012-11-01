package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Acceleration extends Entity {

	public Entity mFollowEntity = null;

	public Acceleration(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.animate(50);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (mFollowEntity != null) {
			float position;
			if (mFollowEntity.getTextureRegion().isFlippedHorizontal()) {
				position = mFollowEntity.getWidth();
				this.getTextureRegion().setFlippedHorizontal(true);
			} else {
				position = -this.getWidth();
				this.getTextureRegion().setFlippedHorizontal(false);
			}
			this.setPosition(mFollowEntity.getX() + position, mFollowEntity.getY());
		}
	}

	@Override
	public Entity deepCopy() {
		return new Acceleration(getTextureRegion(), this.mParentScreen);
	}

}
