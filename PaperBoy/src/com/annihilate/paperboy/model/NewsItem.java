package com.annihilate.paperboy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable {

	private String title;
	private String date;
	private String desc = "";
	private String link;
	private String picurl;
	private String content = "";
	private boolean isViewableLocally = false;

	public NewsItem(String title, String date, String link, String picurl) {
		super();
		this.title = title;
		this.date = date;
		this.link = link;
		this.picurl = picurl;
	}

	public NewsItem(Parcel in) {
		this.title = in.readString();
		this.date = in.readString();
		this.desc = in.readString();
		this.link = in.readString();
		this.picurl = in.readString();
		this.content = in.readString();
		this.isViewableLocally = (in.readInt() == 1) ? true : false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isViewableLocally() {
		return isViewableLocally;
	}

	public void setViewableLocally(boolean isViewableLocally) {
		this.isViewableLocally = isViewableLocally;
	}

	public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
		public NewsItem createFromParcel(Parcel in) {
			return new NewsItem(in);
		}

		@Override
		public NewsItem[] newArray(int size) {
			return new NewsItem[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(title);
		out.writeString(date);
		out.writeString(desc);
		out.writeString(link);
		out.writeString(picurl);
		out.writeString(content);
		out.writeInt(isViewableLocally ? 1 : 0);
	}

}
