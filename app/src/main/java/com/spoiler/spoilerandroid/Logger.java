package com.spoiler.spoilerandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.location.Location;

import android.os.Handler;  //used instead of timer
//import java.util.Timer;
//import java.util.TimerTask;
import android.util.Log;

public class Logger extends ActionBarActivity {

//    Timer timer;
    private Handler mHandler = new Handler();
    int i = 0; //just a placeholder counter for debugging

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logger);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logger, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//called on return home button click
    public void moveHome(View view){
    	Intent intent = new Intent(view.getContext(), MainActivity.class);
    	startActivity(intent);
    }

    //created with run function to run every tick of logger
    public Runnable mTick = new Runnable(){
        public void run(){
            TextView t = (TextView)findViewById(R.id.textView1);
            t.setText("" + i); //this is not displaying correctly
            i++;
            mHandler.postDelayed(mTick, 1000);
        }
    };

    //called on log start click
    public void logStart(View view){
    	TextView t = (TextView)findViewById(R.id.textView1);
    	t.setText("Logging...");

        mHandler.removeCallbacks(mTick);
        mHandler.postDelayed(mTick, 1000);

//        timer = new Timer();
//        LogTask tick = new LogTask();
//        timer.scheduleAtFixedRate(tick, 0, 5000); //this is not working
    }

    //called on log stop click
    public void logEnd(View view){
    	TextView t = (TextView)findViewById(R.id.textView1);
    	t.setText("Stopped Logging");

        mHandler.removeCallbacks(mTick);
    }


}


