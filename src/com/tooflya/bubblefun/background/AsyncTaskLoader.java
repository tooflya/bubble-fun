package com.tooflya.bubblefun.background;

import android.os.AsyncTask;

/**
 * @author Tooflya.com
 * @since
 */
public class AsyncTaskLoader extends AsyncTask<IAsyncCallback, Integer, Boolean> {

	// ===========================================================
	// Fields
	// ===========================================================

	IAsyncCallback[] _params;

	// ===========================================================
	// Inherited Methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Boolean doInBackground(IAsyncCallback... params) {
		this._params = params;
		int count = params.length;
		for (int i = 0; i < count; i++) {
			params[i].workToDo();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		int count = this._params.length;
		for (int i = 0; i < count; i++) {
			this._params[i].onComplete();
		}
	}
}
