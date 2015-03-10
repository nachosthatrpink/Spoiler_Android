package com.spoiler.spoilerandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.*;

import java.io.FileInputStream;


//this entire class will need to be rewritten, to accommodate for the logs now being named by date
/*
Plan as follows:
-implement listview to show list of all logs
-allow click on each log to open in a new window, could actually use this class for that
-make displaying look nicer and not terrible

Future:
-make a graph feature to view speed over time
-highlight hard accelerations
 */
public class LogStore extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_store);


        String text = "";
        FileInputStream fin;

        try
        {

            fin = openFileInput("logs.txt");

            int c;
            while((c = fin.read()) != -1){
                text = text + Character.toString((char)c);
            }
            fin.close();

        }
        // Catches any error conditions
        catch (IOException e)
        {
            text= "Unable to find file!";
        }

        TextView t = (TextView)findViewById(R.id.logText);
        t.setMovementMethod(new ScrollingMovementMethod());
        t.setText(text);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store, menu);
        return true;
    }

    // Removed this method because of the changed log store menu files
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

    //called on erase logs button click
    public void eraseLogs(View view){

        // Stream to write file
        FileOutputStream fout;

        try
        {
            // Open an output stream
            fout = openFileOutput ("logs.txt", Context.MODE_PRIVATE);

            //write empty string to the file to erase it
            fout.write("".getBytes());

            // Close our output stream
            fout.close();
        }
        // Catches any error conditions
        catch (IOException e)
        {
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }

        TextView t = (TextView)findViewById(R.id.logText);
        t.setText("");

    }

}