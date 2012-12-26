package com.tooflya.bubblefun.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.R;
import com.tooflya.bubblefun.screens.Screen;

public class AdvertisementManager {

	private AdView mSmallAdvertisiment;
	private AdView mBigAdvertisiment;

	private boolean isAdvertisementDisabled = false;

	private final Runnable mShowSmallRunnable = new Runnable() {
		@Override
		public void run() {
			mSmallAdvertisiment.setVisibility(View.VISIBLE);
		}
	};

	private final Runnable mHideSmallRunnable = new Runnable() {
		@Override
		public void run() {
			mSmallAdvertisiment.setVisibility(View.GONE);
		}
	};

	private final Runnable mShowBigRunnable = new Runnable() {
		@Override
		public void run() {
			mBigAdvertisiment.loadAd(new AdRequest());
			mBigAdvertisiment.setVisibility(View.VISIBLE);
		}
	};

	private final Runnable mHideBigRunnable = new Runnable() {
		@Override
		public void run() {
			mBigAdvertisiment.setVisibility(View.GONE);
		}
	};

	public AdvertisementManager() {
		mSmallAdvertisiment = (AdView) Game.instance.findViewById(R.id.adView);
		mBigAdvertisiment = (AdView) Game.instance.findViewById(R.id.adViewBig);

		this.isAdvertisementDisabled = Game.db.isAdvertisimentDisabled();

		if (!this.isAdvertisementDisabled && this.isConnectingToInternet()) {
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
		Game.instance.runOnUiThread(this.mShowBigRunnable);
	}

	public void hideBig() {
		Game.instance.runOnUiThread(this.mHideBigRunnable);
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) Game.instance.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}
}
