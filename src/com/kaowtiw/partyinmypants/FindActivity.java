Ovpackage com.kaowtiw.partyinmypants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FindActivity extends Activity {
	
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
		
	}
	
	private class StableArrayAdapter extends ArrayAdapter<String>{
		HashMap<String, Integer> idMap = new HashMap<String, Integer>();
		
		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				idMap.put(objects.get(i), i);
			}
		}
	}
	
	@
}
