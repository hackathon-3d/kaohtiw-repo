package com.kaowtiw.partyinmypants;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CreateActivity extends Activity {
	TextView tv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		//tv = (TextView) findViewById(R.id.title);
		//tv.setText("Hello World");
	}

}
