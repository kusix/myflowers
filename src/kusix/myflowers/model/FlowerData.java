package kusix.myflowers.model;

public class FlowerData {
	
	public final static int ERROR = -1;
	
	private int airTemperature;
	private int airHumidity;
	private int soilMoisture;
	private int light;
	
	public int getAirTemperature() {
		return airTemperature;
	}
	public void setAirTemperature(int airTemperature) {
		this.airTemperature = airTemperature;
	}
	public int getAirHumidity() {
		return airHumidity;
	}
	public void setAirHumidity(int airHumidity) {
		this.airHumidity = airHumidity;
	}
	public int getSoilMoisture() {
		return soilMoisture;
	}
	public void setSoilMoisture(int soilMoisture) {
		this.soilMoisture = soilMoisture;
	}
	public int getLight() {
		return light;
	}
	public void setLight(int light) {
		this.light = light;
	}

}
