package com.annihilate.paperboy.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.annihilate.paperboy.model.NewsItem;

public class GoogleNewsXmlParser {
	private ArrayList<NewsItem> list;
	private static final String ns = null;
	private static final String TAG = "GoogleNewsParser";

	public ArrayList<NewsItem> getNewsFeedList(String responseBody)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		Log.d(TAG, "GNP():  getNewsFeedList()");
		list = new ArrayList<NewsItem>();
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new StringReader(responseBody));
		parser.nextTag();
		readFeed(parser);
		return list;
	}

	private void readFeed(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		Log.d(TAG, "GNP():  readFeed()");
		parser.require(XmlPullParser.START_TAG, ns, "rss");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("channel")) {
				// list.add(readEntry(parser));
				parser.require(XmlPullParser.START_TAG, ns, "channel");
				while (parser.next() != XmlPullParser.END_TAG) {
					if (parser.getEventType() != XmlPullParser.START_TAG) {
						continue;
					}
					name = parser.getName();
					if (name.equals("item")) {
						list.add(readItem(parser));
					} else {
						skip(parser);
					}
				}
			} else {
				skip(parser);
			}
		}
	}

	private NewsItem readItem(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "GNP():  readItem()");
		parser.require(XmlPullParser.START_TAG, ns, "item");
		String title = null;
		String date = null;
		String link = null;
		String picUrl = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				title = readTitle(parser);
			} else if (name.equals("link")) {
				link = readLink(parser);
			} else if (name.equals("pubDate")) {
				date = readDate(parser);
			} else if (name.equals("description")) {
				picUrl = readDescription(parser);
			} else {
				skip(parser);
			}
		}
		return new NewsItem(title, date, link, picUrl);
	}

	private String readDescription(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "GNP():  readDescription()");
		parser.require(XmlPullParser.START_TAG, ns, "description");
		String text = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "description");
		return readPicUrl(text);
	}

	private String readPicUrl(String text) {
		// TODO Auto-generated method stub
		Log.d(TAG, "GNP():  readPicUrl()");
		String picUrl = "";
		if (text.contains("src")) {
			int i = text.indexOf("src");
			picUrl = text.substring(i + 5);
			i = picUrl.indexOf("\"");
			picUrl = picUrl.substring(0, i);
			picUrl = "https:" + picUrl;
			Log.d(TAG, "GNP():  picUrl=" + picUrl);
		} else
			picUrl = "";
		return picUrl;
	}

	private String readDate(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "GNP():  readDate()");
		parser.require(XmlPullParser.START_TAG, ns, "pubDate");
		String date = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "pubDate");
		Log.d(TAG, "GNP:  date=" + date);
		return date;
	}

	private String readLink(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "GNP():  readLink()");
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String link = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "link");
		Log.d(TAG, "GNP:  link=" + link);
		return link;
	}

	private String readTitle(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "GNP():  readTitle()");
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		Log.d(TAG, "GNP:  title=" + title);
		return title;
	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
