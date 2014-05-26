package kusix.myflowers;

import kusix.myflowers.bluetooth.BluetoothActivity;
import kusix.myflowers.model.Flower;
import kusix.myflowers.service.DataReceiveService;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
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
	
	private IntentFilter dataReceiverFilter = new IntentFilter(DataReceiver.REFRESH_DATA);	
	private BroadcastReceiver dataReceiver = new DataReceiver();
	private TextView tempView;
	private FragmentManager fragmentManager;
	private Flower flower;
	
	private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if no device,go to device search page
        if(null == flower || !flower.isEnabe()){
        	Intent btIntent = new Intent(this, BluetoothActivity.class);
            startActivity(btIntent);
        }else{        
	        fragmentManager = getSupportFragmentManager();
	        if (savedInstanceState == null) {
	        	fragmentManager.beginTransaction()
	                    .add(R.id.container,new PlaceholderFragment(),"placeholderFragment")
	                    .commit();
	        }
	        this.registerReceiver(dataReceiver, dataReceiverFilter);
	        this.startService(new Intent(this,DataReceiveService.class));
        }
    }
    
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.registerReceiver(dataReceiver, dataReceiverFilter);
    }
    
    @Override
    public void onPause(){
    	this.unregisterReceiver(dataReceiver);
    	super.onPause();
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    class DataReceiver extends BroadcastReceiver{
    	
    	public static final String REFRESH_DATA = "refresh_data";

    	@Override
    	public void onReceive(Context context, Intent intent) {
    		// TODO Auto-generated method stub
    		int temp = intent.getIntExtra("temp", 0);
    		tempView = (TextView)fragmentManager.findFragmentByTag("placeholderFragment").getView().findViewById(R.id.temp);
    		tempView.setText(String.valueOf(temp));
    	}
    	
    }

}

