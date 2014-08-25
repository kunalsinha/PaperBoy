package com.annihilate.paperboy.services;

import java.util.ArrayList;

public class AsyncCallBackMan {

	private static ArrayList<NewsFetchAsyncListener> listener = null;

	public static void setThreadListener(NewsFetchAsyncListener l) {
		if (listener == null)
			listener = new ArrayList<AsyncCallBackMan.NewsFetchAsyncListener>();
		if (!listener.contains(l))
			listener.add(l);
	}

	public static void removeListener(NewsFetchAsyncListener l) {
		listener.remove(l);
	}

	public static interface NewsFetchAsyncListener {

		public void onGoogleNewsFetchComplete(boolean gotNews,
				String responseXml);
	}

	public static void setGoogleNewsFetchComplete(boolean gotNews,
			String responseXml) {
		if (listener != null)
			for (NewsFetchAsyncListener l : listener)
				l.onGoogleNewsFetchComplete(gotNews, responseXml);
	}
}
