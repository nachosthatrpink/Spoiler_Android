package com.spoiler.spoilerandroid;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.androidplot.xy.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogGraph extends ActionBarActivity {

    private XYPlot speedPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_graph);

        speedPlot = (XYPlot)findViewById(R.id.SpeedPlot);


        BufferedReader lineReader;
        InputStream in;
        String line;
        int speed;
        List<Integer> speedData = new ArrayList<Integer>();
        //FileInputStream fin;

        try
        {

            //fin = openFileInput(getIntent().getData().toString());

            in = this.getAssets().open(getIntent().getData().toString());
            lineReader = new BufferedReader(new InputStreamReader(in));
            line = lineReader.readLine();  //we don't want the starting line right now, just the data

            line = lineReader.readLine();
            while(line!=null){
                speed = Integer.parseInt(line.substring(line.indexOf(":")+2, line.length())); //takes just speed parameter from each line
                speedData.add(speed);
                line = lineReader.readLine();
            }

        }
        // Catches any error conditions
        catch (IOException e)
        {
            line= "Unable to find file!"; //this doesn't do anything. make it give feedback to user
        }


        XYSeries series = new SimpleXYSeries(speedData,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Speed"); //uses index as x-value, easy fix by multiplying later
        LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
        seriesFormat.setPointLabelFormatter(new PointLabelFormatter());
        seriesFormat.configure(getApplicationContext(), R.xml.line_point_formatter_with_plf1);

        speedPlot.addSeries(series, seriesFormat);
        speedPlot.setTicksPerRangeLabel(10); //I think this will increment speed in 10 mph(or kmh or.....knots. goddammit phil)
        speedPlot.getGraphWidget().setDomainLabelOrientation(-45);


    }


}