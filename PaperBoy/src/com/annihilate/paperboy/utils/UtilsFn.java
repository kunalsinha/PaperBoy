package com.annihilate.paperboy.utils;

import android.content.Context;
import android.widget.Toast;

public class UtilsFn {
	private UtilsFn() {
	}

	public static void makeToast(Context c, String msg, int duration) {
		if (duration == 0)
			Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
		else if (duration == 1)
			Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
		c = null;
	}
}
