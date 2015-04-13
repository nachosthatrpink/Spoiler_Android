package com.spoiler.spoilerandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
// import android.view.MenuItem;
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
    private String measurement;
    private Bundle b;

    private Handler mHandler = new Handler();
    int i = 0; //just a placeholder counter for debugging
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location current = null;
    private Calendar date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logger);



        String text = "";
        FileInputStream fin;

        //for both of these, we need a more robust solution. Phil and I have discussed it over the telephone
        try
        {

            fin = openFileInput("config.txt");

            int c;
            while((c = fin.read()) != -1){
                text = text + Character.toString((char)c);
            }
            String timeVal;
            String measVal;
            if(text.indexOf("/") != -1) {
                timeVal = text.substring(0, text.indexOf("/"));
                measVal = text.substring(text.indexOf("/") + 1);
                secondPass = Integer.parseInt(timeVal);
                measurement = measVal;
            }
            fin.close();

        }
        // Catches any error conditions
        catch (IOException e)
        {
            secondPass = 5;
            measurement = "english";
        }


        TextView t = (TextView)findViewById(R.id.logView);
        if(measurement.equals("metric"))
            t.setText("New log every " + String.valueOf(secondPass) + " seconds in km/h.");
        else if(measurement.equals("english"))
            t.setText("New log every " + String.valueOf(secondPass) + " seconds in mph.");
        else
            t.setText("New log every " + String.valueOf(secondPass) + " seconds in knots.");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logger, menu);
		return true;
	}

    // Removed this method because of the changed logger menu files
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	//called on return home button click
    public void moveHome(View view){
    	Intent intent = new Intent(view.getContext(), MainActivity.class);
    	startActivity(intent);
    }

    //created with run function to run every tick of logger
    public Runnable mTick = new Runnable(){
        public void run(){
        TextView t = (TextView)findViewById(R.id.logView);


//            current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //wait why is this commented out?
        float speed = 0.0f;
        if (current != null){
            speed = current.getSpeed(); //get last known speed
            if(measurement.equals("metric"))
                speed = speed * 3600 * .0001f; // conversion m/s to km/h
            else if(measurement.equals("english"))
                speed = speed * 2.23694f; //conversion from m/s to mph
            else
                speed = speed * 1.9438f; // conversion from m/s to knots
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
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int day = date.get(Calendar.DAY_OF_MONTH);
            int hour = date.get(Calendar.HOUR);
            int minute = date.get(Calendar.MINUTE);

            // Open an output stream
            fout = openFileOutput("Log: " +(1 + month) + "-" + day + "-" + year + "-" + hour + ":" + minute + ".txt", Context.MODE_APPEND);

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


        //following is for obtaining gps locations (and speeds)
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener(){
            public void onLocationChanged(Location location){
                current = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        //ensure that message box continues to pop up if previously canceled....i think it will every time log start button is pressed
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showSettingsAlert();
        }else {

            t.setText("Logging...");


            // Stream to write file
            FileOutputStream fout;

            try {
                // Attach date and time to log
                date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int day = date.get(Calendar.DAY_OF_MONTH);
                int hour = date.get(Calendar.HOUR);
                int minute = date.get(Calendar.MINUTE);

                //fout = openFileOutput("logs.txt", Context.MODE_PRIVATE);
                fout = openFileOutput("Log: " +(1 + month) + "-" + day + "-" + year + "-" + hour + ":" + minute + ".txt", Context.MODE_PRIVATE);

                String tempLog = "New Log, Date/Time: " + (1 + month) + "/" + day + "/" + year + " " + hour + ":" + minute + ", rate: " + secondPass + "\n";
                fout.write(tempLog.getBytes());

                // Close our output stream
                fout.close();
            }
            // Catches any error conditions
            catch (IOException e) {
                System.err.println("Unable to write to file");
                System.exit(-1);
            }


            mHandler.removeCallbacks(mTick);
            mHandler.postDelayed(mTick, secondPass * 1000);

        }


    }

    //called on log stop click
    public void logEnd(View view){
    	TextView t = (TextView)findViewById(R.id.logView);
    	t.setText("Stopped Logging");

        mHandler.removeCallbacks(mTick);
    }


}


