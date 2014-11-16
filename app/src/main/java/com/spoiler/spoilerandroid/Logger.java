package com.spoiler.spoilerandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.content.DialogInterface;
import android.provider.Settings;
import java.io.*;
import java.util.Calendar;


import android.os.Handler;  //used instead of timer

public class Logger extends ActionBarActivity {

    //THESE GLOBAL VARIABLES ARE PROBABLY REALLY BAD STYLE
    // the value that will store how many seconds between logs is desired
    private int secondPass;
    private Bundle b;

    private Handler mHandler = new Handler();
    int i = 0; //just a placeholder counter for debugging
    private LocationManager locationManager;
    private LocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logger);



        String text = "";
        FileInputStream fin;

        try
        {

            fin = openFileInput("config.txt");

            int c;
            while((c = fin.read()) != -1){
                text = text + Character.toString((char)c);
            }
            secondPass = Integer.parseInt(text);
            fin.close();

        }
        // Catches any error conditions
        catch (IOException e)
        {
            secondPass = 5;
        }
        TextView t = (TextView)findViewById(R.id.logView);
        t.setText("New log every " + String.valueOf(secondPass) + " seconds.");
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
            TextView t = (TextView)findViewById(R.id.logView);
            //who knows if this next part works correctly
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                showSettingsAlert();
            }

            Location current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            float speed = 0.0f;
            if (current != null){
                speed = current.getSpeed(); //not sure if this works....it may on an actual device with gps enabled...
                t.setText(String.valueOf(speed));
            }else{
                t.setText("No location available.");
                mHandler.removeCallbacks(mTick);
            }
//            t.setText(String.valueOf(i));
//            i++;



            // Stream to write file
            FileOutputStream fout;

            try{
                // Open an output stream
                fout = openFileOutput("logs.txt", Context.MODE_APPEND);

                //create temp string for log
                String tempLog = "Current Speed: " + speed + "\n";
                // print current log
                fout.write(tempLog.getBytes());

                // Close our output stream
                fout.close();
            }
            // Catches any error conditions
            catch (IOException e){
                System.err.println ("Unable to write to file");
                System.exit(-1);
            }

            mHandler.postDelayed(mTick, secondPass * 1000);


        }
    };

    //the following function was taken mostly in part from http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(findViewById(android.R.id.content).getContext());

        alertDialog.setTitle("Enabling GPS");
        alertDialog.setMessage("GPS is required for Spoiler to run. Do you want to go to the settings menu now to activate GPS?");
        alertDialog.setIcon(R.drawable.ic_launcher);

        //settings button press
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                findViewById(android.R.id.content).getContext().startActivity(intent);
            }
        });

        //cancel button press
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }



    //called on log start click
    public void logStart(View view){
    	TextView t = (TextView)findViewById(R.id.logView);
    	t.setText("Logging...");

        // Stream to write file
        FileOutputStream fout;

        try
        {
            // Open an output stream
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            fout = openFileOutput ("logs.txt", Context.MODE_APPEND);



            String tempLog = "New Log, Date/Time: " + (1+month) + "/" + day + "/" + year + " " + hour + ":" + minute + ", rate: " + secondPass + "\n";
            fout.write(tempLog.getBytes());

            // Close our output stream
            fout.close();
        }
        // Catches any error conditions
        catch (IOException e)
        {
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }


        //following is for obtaining gps locations (and speeds)
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mHandler.removeCallbacks(mTick);
        mHandler.postDelayed(mTick, secondPass * 1000);


    }

    //called on log stop click
    public void logEnd(View view){
    	TextView t = (TextView)findViewById(R.id.logView);
    	t.setText("Stopped Logging");

        mHandler.removeCallbacks(mTick);
    }


}


