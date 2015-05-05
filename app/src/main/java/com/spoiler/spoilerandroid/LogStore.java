package com.spoiler.spoilerandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;


//this class is being currently revised
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


        File dirFiles = getApplicationContext().getFilesDir();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_row, R.id.logTitle, dirFiles.list());

        ListView fileList = (ListView) findViewById(R.id.logList);
        fileList.setAdapter(adapter);

        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int position, long id)
            {


                Intent intent = new Intent(view.getContext(), LogView.class);
                String logTitle = (String) parent.getItemAtPosition(position);
                intent.setData(Uri.parse(logTitle));
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store, menu);
        return true;
    }


    //called on return home button click
    public void moveHome(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }



}