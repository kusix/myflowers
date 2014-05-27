package kusix.myflowers.util;

import kusix.myflowers.bluetooth.BluetoothClient;
import android.app.Application;

public class ShareApplication extends Application{
	
	private BluetoothClient client;

	public BluetoothClient getClient() {
		return client;
	}

	public void setClient(BluetoothClient client) {
		this.client = client;
	}

}
