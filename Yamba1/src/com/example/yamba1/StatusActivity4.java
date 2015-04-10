package com.example.yamba1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class StatusActivity4 extends Activity implements TextWatcher, OnClickListener
{
	private static final String TAG = "StatusActivity";
	public static final int MAX_STATUS_LENGTH = 140;
	Editable maxStatus;
	EditText editText;
	Button updateButton;
	TextView textCount;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//inflating layout
		setContentView(R.layout.status2);
		//inflating views
		editText = (EditText) findViewById(R.id.editText);
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		textCount = (TextView) findViewById(R.id.textCount);
		updateButton.setOnClickListener(this);
		textCount.setText(Integer.toString(140));
		textCount.setTextColor(Color.GREEN);
		editText.addTextChangedListener(this);
	}
	
	class PostToTwitter extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String...Twitstatus)
		{
			try
			{
				YambaApplication1 yamba = (YambaApplication1) getApplication();
				Twitter.Status status = yamba.getTwitter().updateStatus(Twitstatus[0]);
				return status.text;
			}
			catch(TwitterException e)
			{
				Log.e(TAG,e.toString());
				e.printStackTrace();
				return "Failed to post to Twitter";
			}
		}
		
		@Override
		protected void onProgressUpdate(Void...voids) 
		{
			super.onProgressUpdate(voids);
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(StatusActivity4.this, result, Toast.LENGTH_LONG).show();
		}
	}
	
	public void onClick(View v)
	{
		String status = editText.getText().toString();
		new PostToTwitter().execute(status);
		editText.setText("");
		Log.d(TAG,"onClicked");
	}
	
	public void afterTextChanged(Editable statusText)
	{
		if(statusText.length()> 140)
			statusText.delete(140, statusText.length());
	}
	
	public void beforeTextChanged(CharSequence statusText, int start, int count, int after)
	{
		int newLength = statusText.length() - count + after;
		
		if(newLength <= MAX_STATUS_LENGTH/3)
			textCount.setTextColor(Color.GREEN);
		else if(newLength <= 2*MAX_STATUS_LENGTH/3)
			textCount.setTextColor(Color.YELLOW);
		else
			textCount.setTextColor(Color.RED);
			
	}
	
	public void onTextChanged(CharSequence statusText, int start, int count, int after)
	{
		textCount.setText(Integer.toString(MAX_STATUS_LENGTH-statusText.length()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.itemPrefs:startActivity(new Intent(this,PrefsActivity.class));
			break;
		}
		return true;
	}
}
