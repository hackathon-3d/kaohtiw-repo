package com.kaowtiw.partyinmypants;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText userName, password;
	Button submit, newUser;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.joelv.net/partyapp/login.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
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
			new AttemptLogin().execute();
		}
	};
	
	OnClickListener newUserClick = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
		}
	};
	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
        * Before starting background thread Show Progress Dialog
        * */
		boolean failure = false;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(LoginActivity.this);
           pDialog.setMessage("Attempting login...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
           int success;
           String username = userName.getText().toString();
           String passwordStr = password.getText().toString();
           try {
               // Building Parameters
               List<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("username", username));
               params.add(new BasicNameValuePair("password", passwordStr));

               Log.d("request!", "starting");
               // getting product details by making HTTP request
               JSONObject json = jsonParser.makeHttpRequest(
                      LOGIN_URL, "POST", params);

               // check your log for json response
               Log.d("Login attempt", json.toString());

               // json success tag
               success = json.getInt(TAG_SUCCESS);
               if (success == 1) {
               	Log.d("Login Successful!", json.toString());
               	Intent i = new Intent(LoginActivity.this, MainActivity.class);
               	finish();
   				startActivity(i);
               	return json.getString(TAG_MESSAGE);
               }else{
               	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
               	return json.getString(TAG_MESSAGE);

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

		}
		/**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once product deleted
           pDialog.dismiss();
           if (file_url != null){
           	Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
           }

       }

	}
}
