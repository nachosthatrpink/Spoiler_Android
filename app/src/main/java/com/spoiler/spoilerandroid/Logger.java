package com.spoiler.spoilerandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Logger extends ActionBarActivity {

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
    
    
    public void logStart(View view){
    	TextView t = (TextView)findViewById(R.id.textView1);
    	t.setText("Logging...");
    }
    
    public void logEnd(View view){
    	TextView t = (TextView)findViewById(R.id.textView1);
    	t.setText("Stopped Logging");
    }
}
