package com.tooflya.bubblefun.managers;

import android.view.View;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.R;
import com.tooflya.bubblefun.screens.AdvertisimentScreen;
import com.tooflya.bubblefun.screens.Screen;

public class AdvertisementManager {

	private final AdView mSmallAdvertisiment;
	private final AdView mBigAdvertisiment;

	protected final View mNoSmallAdsView;
	protected final View mNoBigAdsView;

	protected final View mProgressBar;

	private final AdRequest mAdsRequest;

	private boolean isAdvertisementDisabled = false;

	private boolean isSAdvertisementAvailable = false;
	private boolean isBAdvertisementAvailable = false;

	private final Runnable mShowSmallRunnable = new Runnable() {
		@Override
		public void run() {
			if (isSAdvertisementAvailable) {
				mSmallAdvertisiment.setVisibility(View.VISIBLE);
			} else {
				mNoSmallAdsView.setVisibility(View.VISIBLE);
			}
		}
	};

	private final Runnable mHideSmallRunnable = new Runnable() {
		@Override
		public void run() {
			if (isSAdvertisementAvailable) {
				mSmallAdvertisiment.setVisibility(View.GONE);
			} else {
				mNoSmallAdsView.setVisibility(View.GONE);
			}

			mSmallAdvertisiment.stopLoading();
			mSmallAdvertisiment.loadAd(mAdsRequest);
		}
	};

	private final Runnable mShowBigRunnable = new Runnable() {
		@Override
		public void run() {
			if (isBAdvertisementAvailable) {
				mBigAdvertisiment.setVisibility(View.VISIBLE);
			} else {
				mNoBigAdsView.setVisibility(View.VISIBLE);
			}

			((AdvertisimentScreen) Game.screens.get(Screen.ADS)).mClose.setVisible(true);
			mProgressBar.setVisibility(View.GONE);
		}
	};

	private final Runnable mHideBigRunnable = new Runnable() {
		@Override
		public void run() {
			if (isBAdvertisementAvailable) {
				mBigAdvertisiment.setVisibility(View.GONE);
			} else {
				mNoBigAdsView.setVisibility(View.GONE);
			}
		}
	};

	private final Runnable mLoadBigRunnable = new Runnable() {
		@Override
		public void run() {
			mProgressBar.setVisibility(View.VISIBLE);
			mBigAdvertisiment.loadAd(mAdsRequest);
		}
	};

	public AdvertisementManager() {
		this.mAdsRequest = new AdRequest();
		this.mAdsRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		this.mAdsRequest.addTestDevice("75FB804D31DDACC071B1AAF9A074D67A");
		this.mAdsRequest.addTestDevice("2A1EBFDEA347D99BD1F7FBB84433AD72");

		this.mSmallAdvertisiment = (AdView) Game.instance.findViewById(R.id.adView);
		this.mBigAdvertisiment = (AdView) Game.instance.findViewById(R.id.adViewBig);

		this.mNoSmallAdsView = Game.instance.findViewById(R.id.noAdsSmall);
		this.mNoBigAdsView = Game.instance.findViewById(R.id.noAdsBig);

		this.mProgressBar = Game.instance.findViewById(R.id.progressBar);

		this.mBigAdvertisiment.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		this.mSmallAdvertisiment.setAdListener(new SmallAdListener());
		this.mBigAdvertisiment.setAdListener(new BigAdListener());

		this.isAdvertisementDisabled = Game.db.isAdvertisimentDisabled();

		if (!this.isAdvertisementDisabled) {
			this.mSmallAdvertisiment.loadAd(this.mAdsRequest);
			Screen.ADS_PADDING = 60f;
		}
	}

	public boolean isAdvertisementDisabled() {
		return this.isAdvertisementDisabled;
	}

	public void showSmall() {
		Game.instance.runOnUiThread(this.mShowSmallRunnable);
	}

	public void hideSmall() {
		Game.instance.runOnUiThread(this.mHideSmallRunnable);
	}

	public void showBig() {
		Game.instance.runOnUiThread(this.mLoadBigRunnable);
	}

	public void hideBig() {
		Game.instance.runOnUiThread(this.mHideBigRunnable);
	}

	private final class SmallAdListener implements AdListener {

		@Override
		public void onDismissScreen(Ad arg0) {
		}

		@Override
		public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
			AdvertisementManager.this.isSAdvertisementAvailable = false;
		}

		@Override
		public void onLeaveApplication(Ad arg0) {
		}

		@Override
		public void onPresentScreen(Ad arg0) {
		}

		@Override
		public void onReceiveAd(Ad arg0) {
			AdvertisementManager.this.isSAdvertisementAvailable = true;
		}

	}

	private final class BigAdListener implements AdListener {

		@Override
		public void onDismissScreen(Ad arg0) {
		}

		@Override
		public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
			AdvertisementManager.this.isBAdvertisementAvailable = false;
			Game.instance.runOnUiThread(AdvertisementManager.this.mShowBigRunnable);
		}

		@Override
		public void onLeaveApplication(Ad arg0) {
		}

		@Override
		public void onPresentScreen(Ad arg0) {
		}

		@Override
		public void onReceiveAd(Ad arg0) {
			AdvertisementManager.this.isBAdvertisementAvailable = true;
			Game.instance.runOnUiThread(AdvertisementManager.this.mShowBigRunnable);
		}
	}
}
