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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends Activity implements OnClickListener {
	EditText partyName, street, city, state, zip, startTime, endTime, description, date;
	Button submit;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.joelv.net/partyapp/add_party.php";

	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
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
		description = (EditText) findViewById(R.id.descriptionET);
		date = (EditText) findViewById(R.id.dateET);
		submit = (Button) findViewById(R.id.btnCreateParty);
		
		submit.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		new CreateParty().execute();
	}
	
	class CreateParty extends AsyncTask<String, String, String> {
		boolean failure = false;
		
		public void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateActivity.this);
            pDialog.setMessage("Creating Party...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
		}
		
		protected String doInBackground(String... args) {
			int success;
			String partyNameStr = partyName.getText().toString();
			String streetStr = street.getText().toString();
			String cityStr = city.getText().toString();
			String stateStr = state.getText().toString();
			String zipStr = zip.getText().toString();
			String startTimeStr = startTime.getText().toString();
			String endTimeStr = endTime .getText().toString();
			String descriptionStr = description.getText().toString();
			String dateStr = date.getText().toString();
			
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("party_name", partyNameStr));
				params.add(new BasicNameValuePair("start_time", startTimeStr));
				params.add(new BasicNameValuePair("end_time", endTimeStr));
				params.add(new BasicNameValuePair("street", streetStr));
				params.add(new BasicNameValuePair("city", cityStr));
				params.add(new BasicNameValuePair("state", stateStr));
				params.add(new BasicNameValuePair("zip", zipStr)); 
				params.add(new BasicNameValuePair("description", descriptionStr));
				params.add(new BasicNameValuePair("date", dateStr));
				
				Log.d("Creating..","starting");
				JSONObject json = jsonParser.makeHttpRequest(
	                       LOGIN_URL, "POST", params);
				Log.d("Creating attempt", json.toString());
				
				success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("User Created!", json.toString());
                	finish();
                	return json.getString(TAG_MESSAGE);
                }
                else{
                	Log.d("Creation Failed!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                }
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
            return null;  
		}
		
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(CreateActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }
	}
}
