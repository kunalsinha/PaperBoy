package com.annihilate.paperboy.async;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.annihilate.paperboy.services.AsyncCallBackMan;

public class GoogleNewsFetcher extends AsyncTask<String, Void, Void> {

	private static final String TAG = "GoogleNewsFetcher";
	private boolean gotNews = true;
	private String responseBody = "";

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Log.v(TAG, "NF:  onPreExecute() ");
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.v(TAG, "NF:  doInBackground() ");
		String url = "https://news.google.com/news/feeds?q=" + params[0]
				+ "&output=rss";
		Log.v(TAG, "NF:  url: " + url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httpget, responseHandler);
			gotNews = true;
			Log.v(TAG, "NF:    response: " + responseBody);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "NF:   ClientProtocolException: " + e);
			gotNews = false;
			responseBody = "";
		} catch (IOException e) {
			Log.e(TAG, "NF:   IOException: " + e);
			gotNews = false;
			responseBody = "";
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.v(TAG, "NF:  onPostExecute() ");
		AsyncCallBackMan.setGoogleNewsFetchComplete(gotNews, responseBody);
	}
}
