package com.annihilate.paperboy.async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.annihilate.paperboy.ActivityMain;
import com.annihilate.paperboy.adapter.NewsListAdapter;

public class ThumbFetch extends AsyncTask<String, Void, Bitmap> {

	private ImageView ivthumb;
	private int pos;
	private boolean gotThumb;

	public ThumbFetch(ImageView ivpic, int position) {
		this.ivthumb = ivpic;
		this.pos = position;
		this.gotThumb = false;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			URL urlConnection = new URL(params[0]);
			HttpURLConnection connection = (HttpURLConnection) urlConnection
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			this.gotThumb = true;
			return myBitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (this.gotThumb) {
			Bitmap b = Bitmap.createScaledBitmap(result,
					ActivityMain.screen_height / 7,
					ActivityMain.screen_height / 7, false);
			this.ivthumb.setImageBitmap(b);
			NewsListAdapter.thumbs.put(pos, b);
			b = null;
			ivthumb = null;
		}
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onCancelled(Bitmap result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}

}
