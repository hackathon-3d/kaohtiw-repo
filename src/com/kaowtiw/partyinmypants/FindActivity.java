package com.kaowtiw.partyinmypants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FindActivity extends ListActivity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String LIST_URL = "http://www.joelv.net/partyapp/list_parties.php";
	
	private JSONArray jArray = null;
	private ArrayList<HashMap<String,String>> theList;
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PARTY_ID = "party";
    
    String partyId;
	String partyName;
	String startTime;
	String endTime;
	String street;
	String city;
	String state;
	String zip;
	String description;
	String date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		new AttemptLogin().execute();
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
	
	class AttemptLogin extends AsyncTask<Void, Void, Boolean> {

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
		protected Boolean doInBackground(Void... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
          int success;
          try {
              theList = new ArrayList<HashMap<String,String>>();
              JSONParser jParser = new JSONParser();
              JSONObject json = jParser.getJSONFromUrl(LIST_URL);
              jArray = json.getJSONArray(TAG_PARTY_ID);
              
              for (int i = 0; i < jArray.length(); i++) {
            	  
            	  JSONObject c = jArray.getJSONObject(i);
            	  
            	  partyId = c.getString("partyid");
            	  partyName = c.getString("partyname");
            	  startTime = c.getString("starttime");
            	  endTime = c.getString("endtime");
            	  street = c.getString("street");
            	  city = c.getString("city");
            	  state = c.getString("state");
            	  zip = c.getString("zip");
            	  description = c.getString("description");
            	  date = c.getString("date");
            	  String fullAddress = street+" "+city+" "+state+" "+zip;

            	  HashMap<String, String> map = new HashMap<String, String>();
            	  
            	  map.put("partyid", partyId);
            	  map.put("partyname", partyName);
            	  map.put("starttime", startTime);
            	  map.put("endtime", endTime);
            	  map.put("street", street);
            	  map.put("city", city);
            	  map.put("state", state);
            	  map.put("zip", zip);
            	  map.put("description", description);
            	  map.put("date", date);
            	  map.put("fullAddress", fullAddress);
            	  
            	  theList.add(map);
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }

          return null;

		}
		/**
       * After completing background task Dismiss the progress dialog
       * **/
      protected void onPostExecute(Boolean result) {
          super.onPostExecute(result);
          pDialog.dismiss();
          
          updateList();
          }

      }
	
	private void updateList() {
		ListAdapter la = new SimpleAdapter(this,theList,R.layout.activity_list,new String[] { "partyname", "fullAddress" },new int[] { R.id.partyName, R.id.partyAddress });
        setListAdapter(la);
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(FindActivity.this, DetailsActivity.class));
			}
        });
	}

}
