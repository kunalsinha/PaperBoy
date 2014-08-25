package com.annihilate.paperboy;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.annihilate.paperboy.async.GoogleNewsFetcher;
import com.annihilate.paperboy.model.NewsItem;
import com.annihilate.paperboy.parser.GoogleNewsXmlParser;
import com.annihilate.paperboy.services.AsyncCallBackMan;
import com.annihilate.paperboy.services.AsyncCallBackMan.NewsFetchAsyncListener;
import com.annihilate.paperboy.utils.UtilsFn;

public class ActivityMain extends FragmentActivity implements
		ActionBar.OnNavigationListener, NewsFetchAsyncListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item",
			TAG = "ActivityMain";
	public static int screen_width, screen_height;
	private DisplayMetrics mDisplay;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_main);
		mDisplay = this.getResources().getDisplayMetrics();
		screen_width = mDisplay.widthPixels;
		screen_height = mDisplay.heightPixels;

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		AsyncCallBackMan.setThreadListener(this);
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3), }), this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		// if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
		// getActionBar().setSelectedNavigationItem(
		// savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		// }
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		// outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
		// .getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		// Create the search view
		final SearchView searchView = new SearchView(getActionBar()
				.getThemedContext());
		searchView.setQueryHint("Search...");
		menu.add(Menu.NONE, Menu.NONE, 1, "Search")
				.setIcon(R.drawable.search)
				.setActionView(searchView)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				InputMethodManager imm = (InputMethodManager) ActivityMain.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
				handleSearchMan(query);
				return false;
			}
		});

		return true;
	}

	protected void handleSearchMan(String query) {
		// TODO Auto-generated method stub
		String str = query.replace("\\s", "");
		if (!str.equals("")) {
			query = query.replaceAll("\\s+", "+");
			showProgressDialog("Fetching News...", false);
			GoogleNewsFetcher nf = new GoogleNewsFetcher();
			nf.execute(query);
		} else
			UtilsFn.makeToast(getApplicationContext(),
					"Please enter a search string..", 0);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new NewsListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_main_container, fragment).commit();
		return true;
	}

	public void parseGoogleNewsXml(String s) throws XmlPullParserException,
			IOException {
		// parse this and show in textview of fragment
		GoogleNewsXmlParser gnp = new GoogleNewsXmlParser();
		ArrayList<NewsItem> list = gnp.getNewsFeedList(s);
		gnp = null;
		Fragment fragment = new NewsListFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList("news_list", list);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_main_container, fragment).addToBackStack(null)
				.commit();
	}

	public void showProgressDialog(String msg, boolean isCancelable) {
		dismissProgressDialog();
		progressDialog = new ProgressDialog(new ContextThemeWrapper(
				ActivityMain.this, android.R.style.Theme_Holo_Light_Dialog));
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(isCancelable);
		progressDialog.setMessage(msg);
		progressDialog.show();
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Log.v(TAG, "progressdialog >> OnDismiss()");
			}
		});
	}

	private void dismissProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
		progressDialog = null;
	}

	// -------------------------- Thread callback methods--------------------

	@Override
	public void onGoogleNewsFetchComplete(boolean gotNews, String responseXml) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Thread Callback: onGoogleNewsFetchComplete()");
		dismissProgressDialog();
		if (gotNews) {
			try {
				parseGoogleNewsXml(responseXml);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			UtilsFn.makeToast(getApplicationContext(),
					"Failed to fetch news...", 0);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AsyncCallBackMan.removeListener(this);
	}

}
