package kusix.myflowers.protocol.impl;

import kusix.myflowers.model.FlowerData;
import kusix.myflowers.protocol.ProtocolParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class FlowerDataProtocolParser implements ProtocolParser<FlowerData> {

	/**
	 * {"d": { "at": 26, "i": "Read fail", "ah": 60, "sm": 40, "l": 675 } }
	 * 
	 * @see kusix.myflowers.protocol.ProtocolParser#parse(char[])
	 */
	@Override
	public FlowerData parse(String s) {
		FlowerData flowerData = new FlowerData();
		try {
			JSONObject obj = new JSONObject(String.valueOf(s));
			JSONObject data = (JSONObject) obj.get("d");
			if (null != data) {
				flowerData.setAirHumidity(data.getInt("ah"));
				flowerData.setSoilMoisture(data.getInt("sm"));
				flowerData.setAirTemperature(data.getInt("at"));
				flowerData.setLight(data.getInt("l"));
			}
		} catch (JSONException e) {
			Log.e(getClass().getSimpleName(), "parse json string failed", e);
		}

		return flowerData;
	}

}
