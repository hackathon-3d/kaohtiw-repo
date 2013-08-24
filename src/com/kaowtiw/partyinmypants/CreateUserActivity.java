package com.kaowtiw.partyinmypants;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends Activity implements OnClickListener {
	private EditText user, pass;
	private Button submit;
	private ProgressDialog pDialog;
	
	
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.joelv.net/partyapp/register.php";

	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
		
		user = (EditText)findViewById(R.id.CreateUserName);
		pass = (EditText)findViewById(R.id.createPassword);
		
		submit = (Button)findViewById(R.id.btnCreateUser);
		submit.setOnClickListener(this);

	}
	
	public void onClick(View v) {
		new CreateUser().execute();
	}
	
	class CreateUser extends AsyncTask<String, String, String> {
		boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateUserActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("User Created!", json.toString());
                	finish();
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
            	Toast.makeText(CreateUserActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_user, menu);
		return true;
	}

}
