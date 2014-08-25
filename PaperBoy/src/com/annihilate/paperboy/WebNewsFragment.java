package com.annihilate.paperboy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebNewsFragment extends Fragment {

	private WebView wvnews;
	private String url;

	public WebNewsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("WebNewsFragment", "FRAG1: onCreateView()");
		View rootView = inflater.inflate(R.layout.fragment_webnews, container,
				false);
		url = getArguments().getString("url");
		wvnews = (WebView) rootView.findViewById(R.id.wv_news);
		wvnews.getSettings().setJavaScriptEnabled(true);
		wvnews.getSettings().setBuiltInZoomControls(true);
		// wvnews.setWebChromeClient(new WebChromeClient() {
		//
		// @Override
		// public void onProgressChanged(WebView view, int progress) {
		// // TODO Auto-generated method stub
		// super.onProgressChanged(view, progress);
		// getActivity().setTitle("Loading...");
		// getActivity().setProgress(progress * 100);
		// if (progress == 100)
		// getActivity().setTitle(url);
		// }
		// });
		wvnews.setWebViewClient(new WebViewClient());
		wvnews.loadUrl(url);
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("WebNewsFragment", "FRAG1: onResume()");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d("WebNewsFragment", "FRAG1: onAttach()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("WebNewsFragment", "FRAG1: onStart()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("WebNewsFragment", "FRAG1: onPause()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("WebNewsFragment", "FRAG1: onStop()");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d("WebNewsFragment", "FRAG1: onDetach()");
		getActivity().getActionBar().show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("WebNewsFragment", "FRAG1: onDestroy()");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("WebNewsFragment", "FRAG1: onDestroyView()");
	}

}