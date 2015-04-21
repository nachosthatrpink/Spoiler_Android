package com.spoiler.spoilerandroid;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class LogView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);


        String text = "";
        FileInputStream fin;

        try
        {

            fin = openFileInput(getIntent().getData().toString());

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

        TextView t = (TextView) findViewById(R.id.logData);
        t.setMovementMethod(new ScrollingMovementMethod());
        t.setText(text);

    }

    //called on the graph button click, set intent with the same data that came in here
    public void startGraph(View view){
        Intent intent = new Intent(view.getContext(), LogGraph.class);
        intent.setData(Uri.parse(getIntent().getData().toString()));
        startActivity(intent);
    }

    //called on back button click
    public void backToLogs(View view){
        Intent intent = new Intent(view.getContext(), LogStore.class);
        startActivity(intent);
    }


}
