package com.example.yamba1;

import android.app.Application;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.SharedPreferences;
import winterwell.jtwitter.Twitter;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class YambaApplication1 extends Application implements OnSharedPreferenceChangeListener
{
	private static final String TAG = YambaApplication1.class.getSimpleName();
	public Twitter twitter;
	private SharedPreferences prefs;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Log.i(TAG, "onCreated");
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		Log.i(TAG, "onTerminated");
	}
	
	public synchronized Twitter getTwitter()
	{
		if(twitter == null)
		{
			String username = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String apiRoot = prefs.getString("apiRoot", "http://yamba.marakana.com/api");
			
			if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(apiRoot))
			{
				twitter = new Twitter(username,password);
				twitter.setAPIRootUrl(apiRoot);
			}
		}
		
		return twitter;
	}
	
	
	public synchronized void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		twitter = null;
	}
}