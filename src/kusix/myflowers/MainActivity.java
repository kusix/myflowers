package kusix.myflowers;

import java.io.IOException;

import kusix.myflowers.bluetooth.BluetoothActivity;
import kusix.myflowers.bluetooth.BluetoothClient;
import kusix.myflowers.model.Flower;
import kusix.myflowers.model.FlowerData;
import kusix.myflowers.protocol.impl.FlowerDataProtocolParser;
import kusix.myflowers.util.ShareApplication;
import kusix.myflowers.util.Tags;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author cdjiale
 * 
 */
public class MainActivity extends ActionBarActivity {

	private IntentFilter dataReceiverFilter = new IntentFilter(
			addDeviceFinishedReceiver.ADD_DEVICE_FINISHED);
	private BroadcastReceiver dataReceiver = new addDeviceFinishedReceiver();
	private FragmentManager fragmentManager;
	private Flower inActiveFlower;
	private BluetoothClient client;
	private Thread refreshDataThread;
	private boolean canRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.registerReceiver(dataReceiver, dataReceiverFilter);
		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
			fragmentManager
					.beginTransaction()
					.add(R.id.container, new PlaceholderFragment(),
							"placeholderFragment").commit();
		}
		// if no device,go to device search page
		if (null == inActiveFlower || !inActiveFlower.isEnabe()) {
			Intent btIntent = new Intent(this, BluetoothActivity.class);
			startActivity(btIntent);
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		canRefresh = true;
		if (null != refreshDataThread && !refreshDataThread.isAlive()) {
			refreshData();
			Log.d(Tags.ME, "refresh thread has resume...");
		}
		this.registerReceiver(dataReceiver, dataReceiverFilter);
	}

	@Override
	public void onPause() {
		canRefresh = false;
		super.onPause();
	}

	@Override
	public void onDestroy() {
		this.unregisterReceiver(dataReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				TextView textView = (TextView) getView(R.id.temp);
				if(null == textView){
					break;
				}
				textView.setText(String
						.valueOf(((FlowerData) msg.obj).getAirTemperature()));
				((TextView) getView(R.id.light)).setText(String
						.valueOf(((FlowerData) msg.obj).getLight()));
			}
		}

	};

	private View getView(int id) {
		return fragmentManager.findFragmentByTag("placeholderFragment")
				.getView().findViewById(id);
	}

	class addDeviceFinishedReceiver extends BroadcastReceiver {

		public static final String ADD_DEVICE_FINISHED = "addDeviceFinished";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(Tags.ME, "reveive <addDeviceFinished> intent");
			client = ((ShareApplication) getApplicationContext()).getClient();
			refreshData();
			inActiveFlower = new Flower();
		}

	}

	private void refreshData() {
		canRefresh = true;
		refreshDataThread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (!client.hasConnected()) {
					client.connect();
				}
				while (client.hasConnected() && canRefresh) {
					try {
						client.write("D");
						FlowerData data = (FlowerData) client
								.read(new FlowerDataProtocolParser());
						handler.sendMessage(handler.obtainMessage(1, data));
						Thread.sleep(1000);
					} catch (IOException e) {
						Log.e(Tags.ME, e.toString());
						client.connect();
					} catch (Exception e) {
						Log.e(Tags.ME, e.toString());

					}
				}
				Log.d(Tags.ME, "refresh thread will be stop...");

			}

		});
		refreshDataThread.start();
	}

}
