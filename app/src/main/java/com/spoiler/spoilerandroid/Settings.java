package com.spoiler.spoilerandroid;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
// import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Settings extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FileInputStream fin;
        String secondPassText = "";
        try
        {

            fin = openFileInput("config.txt");
            int c;
            while((c = fin.read()) != -1){
                secondPassText = secondPassText + Character.toString((char)c);
            }
            RadioButton r;

            if(secondPassText.equals("5"))
                r = (RadioButton)findViewById(R.id.timeSelection5Sec);
            else if(secondPassText.equals("10"))
                r = (RadioButton)findViewById(R.id.timeSelection10Sec);
            else
                r = (RadioButton)findViewById(R.id.timeSelection30Sec);
            r.setChecked(true);
            fin.close();

        }
        // Catches any error conditions
        catch (IOException e)
        {
            RadioButton r = (RadioButton)findViewById(R.id.timeSelection5Sec);
            r.setChecked(true);
        }

        FileInputStream measFin;
        String secondPassTextMeas = "";

        try
        {

            measFin = openFileInput("measurement.txt");
            int c;
            while((c = measFin.read()) != -1){
                secondPassTextMeas = secondPassTextMeas + Character.toString((char)c);
            }
            RadioButton r;

            if(secondPassTextMeas.equals("metric"))
                r = (RadioButton)findViewById(R.id.metricButton);
            else if(secondPassTextMeas.equals("english"))
                r = (RadioButton)findViewById(R.id.englishButton);
            else
                r = (RadioButton)findViewById(R.id.seaButton);
            r.setChecked(true);
            measFin.close();

        }
        // Catches any error conditions
        catch (IOException e)
        {
            RadioButton r = (RadioButton)findViewById(R.id.metricButton);
            r.setChecked(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    // Removed this method because of the changed settings menu files
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //called on return home button click
    public void moveHome(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }

    //will change log time separation to five seconds
    public void fiveSec(View view){
        FileOutputStream fileOutput;

        try{

            // Open an output stream
            fileOutput = openFileOutput ("config.txt", Context.MODE_PRIVATE);

            // place a line of text in output stream
            String tempLog = "5";
            fileOutput.write(tempLog.getBytes());

            // Close our output stream
            fileOutput.close();
        }
        // Catches any error conditions
        catch (IOException e){
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }
    }

    //will change log time separation to five seconds
    public void tenSec(View view){
        FileOutputStream fileOutput;

        try{

            // Open an output stream
            fileOutput = openFileOutput ("config.txt", Context.MODE_PRIVATE);

            // Print a line of text
            String tempLog = "10";
            fileOutput.write(tempLog.getBytes());

            // Close our output stream
            fileOutput.close();
        }
        // Catches any error conditions
        catch (IOException e){
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }
    }

    //will change log time separation to five seconds
    public void thirtySec(View view){
        FileOutputStream fileOutput;

        try{

            // Open an output stream
            fileOutput = openFileOutput ("config.txt", Context.MODE_PRIVATE);

            // Print a line of text
            String tempLog = "30";
            fileOutput.write(tempLog.getBytes());

            // Close our output stream
            fileOutput.close();
        }
        // Catches any error conditions
        catch (IOException e){
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }
    }

    // cahnges the measurement type on click
    public void metricButton(View view){
        FileOutputStream fileOutput;

        try{
            // open and output stream
            fileOutput = openFileOutput ("measurement.txt", Context.MODE_PRIVATE);

            String tempLog = "metric";
            fileOutput.write(tempLog.getBytes());

            fileOutput.close();
        }
        catch(IOException e){
            System.err.println("Unable to write to file");
            System.exit(-1);
        }
    }


    // cahnges the measurement type on click
    public void englishButton(View view){
        FileOutputStream fileOutput;

        try{
            // open and output stream
            fileOutput = openFileOutput ("measurement.txt", Context.MODE_PRIVATE);

            String tempLog = "english";
            fileOutput.write(tempLog.getBytes());

            fileOutput.close();
        }
        catch(IOException e){
            System.err.println("Unable to write to file");
            System.exit(-1);
        }
    }

    // cahnges the measurement type on click
    public void seaButton(View view){
        FileOutputStream fileOutput;

        try{
            // open and output stream
            fileOutput = openFileOutput ("measurement.txt", Context.MODE_PRIVATE);

            String tempLog = "sea";
            fileOutput.write(tempLog.getBytes());

            fileOutput.close();
        }
        catch(IOException e){
            System.err.println("Unable to write to file");
            System.exit(-1);
        }
    }


}
