package com.kaowtiw.partyinmypants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	EditText userName, password;
	Button submit, newUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userName = (EditText)findViewById(R.id.userName);
		password = (EditText)findViewById(R.id.password);
		submit = (Button)findViewById(R.id.btnLoginSubmit);
		newUser = (Button)findViewById(R.id.btnLoginNew);
		
		submit.setOnClickListener(submitClick);
		newUser.setOnClickListener(newUserClick);
	}
	
	OnClickListener submitClick = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
		}
	};
	
	OnClickListener newUserClick = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
		}
	};
}
