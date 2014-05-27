package kusix.myflowers.protocol.impl;

import kusix.myflowers.model.FlowerData;
import kusix.myflowers.protocol.ProtocolParser;
import kusix.myflowers.util.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FlowerDataProtocolParser implements ProtocolParser<FlowerData> {

	/**
	 * {"cmd":115,"d":{"ati":"Read fail","at":-1,"ahi":"","ah":-1,"l":549}}
	 * 
	 * @see kusix.myflowers.protocol.ProtocolParser#parse(char[])
	 */
	@Override
	public FlowerData parse(String s) {
		Log.d(Tags.ME, "parse json string : " + s);
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
			Log.e(Tags.ME, "parse json string failed", e);
		}

		return flowerData;
	}

}
