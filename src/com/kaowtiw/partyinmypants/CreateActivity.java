package com.kaowtiw.partyinmypants;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateActivity extends Activity {
	EditText partyName, street, city, state, zip, startTime, endTime;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		partyName = (EditText) findViewById(R.id.partyNameET);
		street = (EditText) findViewById(R.id.streetAddressET);
		city = (EditText) findViewById(R.id.cityET);
		state = (EditText) findViewById(R.id.stateET);
		zip = (EditText) findViewById(R.id.zipET);
		startTime = (EditText) findViewById(R.id.startTimeET);
		endTime = (EditText) findViewById(R.id.endTimeET);
		
		String partyNameStr = partyName.getText().toString();
		String streetStr = street.getText().toString();
		String cityStr = city.getText().toString();
		String stateStr = state.getText().toString();
		String zipStr = zip.getText().toString();
		String startTimeStr = startTime.getText().toString();
		String endTimeStr = endTime .getText().toString();
		
	}

}
