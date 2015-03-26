package com.spoiler.spoilerandroid;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
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


}
