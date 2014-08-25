package com.annihilate.paperboy.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annihilate.paperboy.ActivityMain;
import com.annihilate.paperboy.R;
import com.annihilate.paperboy.async.ThumbFetch;
import com.annihilate.paperboy.model.NewsItem;

public class NewsListAdapter extends ArrayAdapter<NewsItem> {

	private ArrayList<NewsItem> list;
	private Context c;
	private LayoutInflater inflater;
	private View view;
	private TextView tvtitle, tvdate, tvlink;
	private ImageView ivpic;
	private Bitmap pic;
	public static SparseArray<Bitmap> thumbs;
	private LinearLayout.LayoutParams lp;
	private AbsListView.LayoutParams alp;

	static {
		thumbs = new SparseArray<Bitmap>();
	}

	public NewsListAdapter(Context context, int resource,
			ArrayList<NewsItem> newsItems) {
		super(context, resource, newsItems);
		this.c = context;
		this.list = newsItems;
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		thumbs.clear();
		int size = newsItems.size();
		thumbs = new SparseArray<Bitmap>(size);
		for (int i = 0; i < size; ++i)
			thumbs.put(i, null);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		view = inflater.inflate(R.layout.row_newsitem, null);
		alp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				ActivityMain.screen_height / 6);
		view.setLayoutParams(alp);
		tvtitle = (TextView) view.findViewById(R.id.tv_rowni_title);
		tvdate = (TextView) view.findViewById(R.id.tv_rowni_date);
		tvlink = (TextView) view.findViewById(R.id.tv_rowni_link);
		ivpic = (ImageView) view.findViewById(R.id.iv_rowni_thumb);
		tvtitle.setText(list.get(position).getTitle());
		tvdate.setText(list.get(position).getDate());
		tvlink.setText("Read more...");
		tvlink.setTag(list.get(position).getLink());
		lp = new LinearLayout.LayoutParams(ActivityMain.screen_height / 7,
				ActivityMain.screen_height / 7);
		lp.leftMargin = 20;
		ivpic.setLayoutParams(lp);
		if (!list.get(position).getPicurl().equals(""))
			if (thumbs.get(position) == null) {
				ThumbFetch thread = new ThumbFetch(ivpic, position);
				thread.execute(list.get(position).getPicurl());
			} else
				ivpic.setImageBitmap(thumbs.get(position));
		return view;
	}
}
