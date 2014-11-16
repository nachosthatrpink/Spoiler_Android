package com.spoiler.spoilerandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class Settings extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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

    //will change log time separation to five seconds
    // issue instantly goes to logger page due to startActivity(intent);
    public void fiveSec(View view){
        Intent intent = new Intent(Settings.this, Logger.class);
        Bundle b = new Bundle();
        b.putInt("value",5);
        intent.putExtras(b);
        startActivity(intent);
    }

    //will change log time separation to five seconds
    // issue instantly goes to logger page due to startActivity(intent);
    public void tenSec(View view){
        Intent intent = new Intent(Settings.this, Logger.class);
        Bundle b = new Bundle();
        b.putInt("value",10);
        intent.putExtras(b);
        startActivity(intent);
    }

    //will change log time separation to five seconds
    // issue instantly goes to logger page due to startActivity(intent);
    public void thirtySec(View view){
        Intent intent = new Intent(Settings.this, Logger.class);
        Bundle b = new Bundle();
        b.putInt("value",30);
        intent.putExtras(b);
    }


}
