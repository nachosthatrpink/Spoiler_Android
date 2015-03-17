package com.spoiler.spoilerandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = (ImageView) findViewById(R.id.spoilerImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Removed this method because of the changed main menu files
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
    
    //called on logger button click
    public void moveLog(View view){
    	Intent intent = new Intent(view.getContext(), Logger.class);
    	startActivity(intent);
    }
    
  //called on settings button click
    public void moveSettings(View view){
    	Intent intent = new Intent(view.getContext(), Settings.class);
    	startActivity(intent);
    }

    //called on log library button click
    public void moveStore(View view){
        Intent intent = new Intent(view.getContext(), LogStore.class);
        startActivity(intent);
    }
}
