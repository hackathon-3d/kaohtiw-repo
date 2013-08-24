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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
            	  
            	  String partyId = c.getString("partyid");
            	  String partyName = c.getString("partyname");
            	  String startTime = c.getString("starttime");
            	  String endTime = c.getString("endtime");
            	  String street = c.getString("street");
            	  String city = c.getString("city");
            	  String state = c.getString("state");
            	  String zip = c.getString("zip");
            	  String description = c.getString("description");
            	  String date = c.getString("date");
            	  System.out.println("party name = " + partyName);
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
		ListAdapter la = new SimpleAdapter(this,theList,R.layout.activity_list,new String[] {"partyname", "street"},new int[] {R.id.partyName});
        setListAdapter(la);
	}

}

