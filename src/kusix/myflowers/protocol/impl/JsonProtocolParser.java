package kusix.myflowers.protocol.impl;

import kusix.myflowers.protocol.ProtocolParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class JsonProtocolParser implements ProtocolParser<JSONObject> {

	@Override
	public JSONObject parse(String json) {
		Log.d(getClass().getSimpleName(), "parse json string: "+json);
		JSONObject obj = new JSONObject();
		JSONTokener tokener = new JSONTokener(json);
		try {
			obj = (JSONObject)tokener.nextValue();
		} catch (JSONException e) {
			Log.e(getClass().getSimpleName(),"parse json string failed",e);
		}
		return obj;
	}



}
