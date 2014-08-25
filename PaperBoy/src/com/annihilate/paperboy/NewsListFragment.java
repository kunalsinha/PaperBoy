package com.annihilate.paperboy;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.annihilate.paperboy.adapter.NewsListAdapter;
import com.annihilate.paperboy.model.NewsItem;
import com.annihilate.paperboy.utils.UtilsFn;

public class NewsListFragment extends Fragment {

	private ArrayList<NewsItem> newsList = null;
	private ListView lvNews;

	public NewsListFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("NewsListFragment", "FRAG0: onCreateView()");
		View rootView = inflater.inflate(R.layout.fragment_newslist, container,
				false);
		lvNews = (ListView) rootView.findViewById(R.id.lv_newslist);
		newsList = getArguments().getParcelableArrayList("news_list");
		if (newsList != null) {
			lvNews.setAdapter(new NewsListAdapter(getActivity(),
					R.layout.row_newsitem, newsList));
			if (newsList.size() == 0)
				UtilsFn.makeToast(getActivity().getApplicationContext(),
						"No news results...", 0);
		}

		lvNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Fragment fragment = new WebNewsFragment();
				Bundle args = new Bundle();
				args.putString("url", newsList.get(pos).getLink());
				fragment.setArguments(args);
				getActivity().getActionBar().hide();
				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.add(R.id.fl_main_container, fragment).addToBackStack(null)
						.commit();
				getActivity().getActionBar().hide();
			}
		});
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("NewsListFragment", "FRAG0: onResume()");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d("NewsListFragment", "FRAG0: onAttach()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("NewsListFragment", "FRAG0: onStart()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("NewsListFragment", "FRAG0: onPause()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("NewsListFragment", "FRAG0: onStop()");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d("NewsListFragment", "FRAG0: onDetach()");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("NewsListFragment", "FRAG0: onDestroy()");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("NewsListFragment", "FRAG0: onDestroyView()");
	}

}
