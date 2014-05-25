package kusix.myflowers.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DataReceiveService extends Service {
	
	private DataReceiveService me;
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		Log.d(getClass().getSimpleName(),"DataReceiveService has started");
		me = this;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {

				for(int i=0;i<10;i++){
					Intent dataIntent = new Intent("refresh_data");
					dataIntent.putExtra("temp", i);
					me.sendBroadcast(dataIntent);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.interrupted();
					}
				}				
			}
		});
		
		t.start();
		
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
