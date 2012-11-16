package com.tooflya.bubblefun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;

public class Beta {
	public static String mail;
	public static String device;

	public static void update() {
		Debug.countFPS++;
		Debug.sumFPS += Game.mCurrentFramesPerSecond;
		Debug.deltaFPS = (Debug.sumFPS + Game.mCurrentFramesPerSecond) / Debug.countFPS;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	public static void sendFirstInformation() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://bubblefun.tooflya.com/");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("mail", mail));
			nameValuePairs.add(new BasicNameValuePair("device", device));
			nameValuePairs.add(new BasicNameValuePair("fps", Debug.deltaFPS + ""));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			InputStream is = response.getEntity().getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			StringBuilder str = new StringBuilder();

			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					str.append(line + "\n");
				}
			} catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}
}
