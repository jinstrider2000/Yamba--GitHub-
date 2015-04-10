package com.example.yamba1;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class PrefsActivity extends PreferenceActivity
{
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}
