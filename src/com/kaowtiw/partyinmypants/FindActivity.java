package com.kaowtiw.partyinmypants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FindActivity extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.joelv.net/partyapp/listparties.php";
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		
		ListView listView = (ListView)findViewById(R.id.partyList);
		
		String[] items = new String[] {
				"Fuckin Party",
				"Another Party",
				"The Party"
		};
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (String item : items) {
			list.add(item);
		}
		
		StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private class StableArrayAdapter extends ArrayAdapter<String> {
		HashMap<String, Integer> idMap = new HashMap<String, Integer>();
		
		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				idMap.put(objects.get(i), i);
			}
		}
	
		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return idMap.get(item);
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
	
	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
       * Before starting background thread Show Progress Dialog
       * */
		boolean failure = false;

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          pDialog = new ProgressDialog(FindActivity.this);
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
          try {
              // Building Parameters
              List<NameValuePair> params = new ArrayList<NameValuePair>();
              params.add(new BasicNameValuePair("partyname", "partyname"));

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
              	Intent i = new Intent(FindActivity.this, MainActivity.class);
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
          	Toast.makeText(FindActivity.this, file_url, Toast.LENGTH_LONG).show();
          }

      }

	}
}
