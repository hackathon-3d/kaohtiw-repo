package com.kaowtiw.partyinmypants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button findButton, createButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findButton = (Button) findViewById(R.id.findButton);
		createButton = (Button) findViewById(R.id.createButton);
		
		findButton.setOnClickListener(btnClickListener);
		createButton.setOnClickListener(btnClickListener);
		
	}
	
	private OnClickListener btnClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(v.getId() == R.id.findButton) { 
				Intent startFind = new Intent(MainActivity.this, FindActivity.class);
				startActivity(startFind);
			}
			else if(v.getId() == R.id.createButton) {
				Intent startCreate = new Intent(MainActivity.this, CreateActivity.class);
				startActivity(startCreate);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
