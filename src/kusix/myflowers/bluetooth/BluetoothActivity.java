package kusix.myflowers.bluetooth;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import kusix.myflowers.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothActivity extends ActionBarActivity {

	private BluetoothReceiver receiver;
	private BluetoothAdapter bluetoothAdapter;
	private List<String> deviceItems;
	private List<BluetoothDevice> deviceList;
	private IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	private BluetoothClient client;
	private final String lockName = "BOLUTEK";
	private String message = "D";
	private ListView listView;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		context = getApplicationContext();

		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isEnabled()) {
			adapter.enable();
		}

		listView = (ListView) this.findViewById(R.id.listView1);
		deviceList = new ArrayList<BluetoothDevice>();
		deviceItems = new ArrayList<String>();
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, deviceItems);
		listView.setAdapter(listAdapter);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		receiver = new BluetoothReceiver();
		registerReceiver(receiver, filter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BluetoothDevice device = deviceList.get(position);
				Toast.makeText(context, "正在连接...", Toast.LENGTH_LONG)
						.show();
				client = new BluetoothClient(device, handler);
				try {
					client.connect(message);
				} catch (Exception e) {
					Log.e("TAG", e.toString());
				}
			}
		});
		// TODO 定时扫描
		bluetoothAdapter.startDiscovery();
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BluetoothClient.CONNECT_FAILED:
				Toast.makeText(context, "连接失败", Toast.LENGTH_LONG).show();
				try {
					client.connect(message);
				} catch (Exception e) {
					Log.e("TAG", e.toString());
				}
				break;
			case BluetoothClient.CONNECT_SUCCESS:
				Toast.makeText(context, "连接成功", Toast.LENGTH_LONG).show();
				bluetoothAdapter.cancelDiscovery();
				break;
			case BluetoothClient.READ_FAILED:
				Toast.makeText(context, "读取失败", Toast.LENGTH_LONG).show();
				break;
			case BluetoothClient.WRITE_FAILED:
				Toast.makeText(context, "写入失败", Toast.LENGTH_LONG).show();
				break;
			case BluetoothClient.DATA:
				Toast.makeText(context, ((JSONObject)msg.obj) + "", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	private class BluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (deviceItems.indexOf(device.getName()) == -1) {
					String item = device.getName() + "\n" + device.getAddress();
					deviceItems.add(item);
					Log.d(getClass().getSimpleName(), "add device:" + item);
				}
				deviceList.add(device);
			}
		}
	}

	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		bluetoothAdapter.cancelDiscovery();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReceiver(receiver, filter);
		bluetoothAdapter.startDiscovery();
	}

}
