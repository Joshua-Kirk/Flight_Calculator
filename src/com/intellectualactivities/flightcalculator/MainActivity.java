package com.intellectualactivities.flightcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity 
{
	public Button button;
	public EditText editText;
	public TextView textView;
	private Handler mhandler = new Handler();
	
	String remainingFlightTime;
	
	int remainingHours;
	int remainingMinutes;
	int buttonOption = 0;
	int burnRatePerHour;
	double burnRatePerMinute;
	int initialGallons = 0;
	int endGallons;
	double seconds;
	double minutes;
	double hours;
	double mili;
	double startTime;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button) findViewById(R.id.button);
		editText = (EditText) findViewById(R.id.edit);
		textView = (TextView) findViewById(R.id.text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void onClickCheck(View view)
	{
		try 
		{
			if(buttonOption == 1)
			{
				buttonOption = 0;
				button.setText(R.string.begin);
				mhandler.removeCallbacks(Time);
				endGallons = Integer.parseInt(editText.getText().toString());
				editText.setText(null);
				
				Calculate(initialGallons, endGallons);
			}
				
			else
			{
			
				buttonOption = 1;
				button.setText(R.string.calculate);
				initialGallons = Integer.parseInt(editText.getText().toString());
				editText.setText(null);
				startTime = SystemClock.uptimeMillis();
				mhandler.post(Time);
			}
		}
		
		catch(Exception e)
		{
			Log.d("onClickCheck", "flag");
		}
		
	}
	
	public void Calculate(int initialGallons, int endGallons)
	{
		burnRatePerHour = (int) ((initialGallons - endGallons) * (60 / minutes));
		burnRatePerMinute = ((initialGallons - endGallons) * (60 / minutes)) / 60;

		remainingHours = endGallons / burnRatePerHour; 
		remainingMinutes = (int) ((endGallons % burnRatePerHour) / burnRatePerMinute);
		
		remainingFlightTime = remainingHours + ":" + String.format("%02d", remainingMinutes) + ":00";
		
		textView.setText("Remaining Flight Time: " + remainingFlightTime + "\n Burn Rate/Hr: " + burnRatePerHour + "\n" + "Burn Rate/Min: " + 
						String.format("%.2f", burnRatePerMinute) +
						"\n\n Initial Fuel: " + initialGallons + "\n End Fuel: " + endGallons +
						"\n\n Seconds: " + seconds + "\n Minutes: " + minutes + "\n Hours: " + hours );
	}
	
	private Runnable Time = new Runnable()
	{

		@Override
		public void run() 
		{
			mili = SystemClock.uptimeMillis() - startTime;
			
			seconds = (int) (mili / 1000);
			
			minutes = seconds / 60;
			
			seconds = seconds % 60;
			
			//minutes = seconds / 60;
			
			hours = minutes / 60;
			
			if(minutes == 15)
			{
				
			}
			
			textView.setText("Burn Rate: " + burnRatePerHour + "\n Initial Fuel: " + initialGallons + "\n End Fuel: " + endGallons + "\n" +
	                  "\n Seconds: " + seconds + "\n Minutes: " + minutes + "\n Hours: " + hours );
			
			mhandler.post(Time);
		}
	};
	
}
