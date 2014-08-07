package kusix.myflowers;

import java.io.IOException;

import kusix.myflowers.bluetooth.BluetoothClient;
import kusix.myflowers.chart.ChartViewBuilder;
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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
	private RadioGroup tabBarGroup;
	private Flower inActiveFlower;
	private BluetoothClient client;
	private Thread refreshDataThread;
	private boolean canRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		Log.d(Tags.ME, "---onCreate---");
		setContentView(R.layout.activity_main);
		this.registerReceiver(dataReceiver, dataReceiverFilter);
		fragmentManager = getSupportFragmentManager();
        tabBarGroup = (RadioGroup) findViewById(R.id.tab_bar);
        tabBarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                changeFragmentByID(checkedId); 
            }

			
        });
        tabBarGroup.check(R.id.btn_me);
	}
	
	private void changeFragmentByID(int checkedId) {

        Fragment fragment = FragmentFactory.getInstance().getFragmentByID(checkedId);
		changeFragment(fragment);
	}

	public void changeFragment(Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();    
        transaction.replace(R.id.content, fragment);  
        transaction.commit();
	}  

	
	@Override
	public void onActivityResult(int arg0,int arg1,Intent arg2){
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(Tags.ME, "---onResume---");
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
		Log.d(Tags.ME, "---onPause---");
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d(Tags.ME, "---onDestroy---");
		this.unregisterReceiver(dataReceiver);
		super.onDestroy();
	}


	//display data
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
//				TextView textView = (TextView) getView(R.id.temp);
//				if(null == textView){
//					break;
//				}
//				textView.setText(String
//						.valueOf(((FlowerData) msg.obj).getAirTemperature()));
//				((TextView) getView(R.id.light)).setText(String
//						.valueOf(((FlowerData) msg.obj).getLight()));
			}
		}

	};


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
