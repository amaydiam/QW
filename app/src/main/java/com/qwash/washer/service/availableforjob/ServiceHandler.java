package com.qwash.washer.service.availableforjob;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("NewApi")
public class ServiceHandler extends Activity {

	Messenger mService = null;
	boolean mIsBound;
	private Context activity;

	
	


	
	public ServiceHandler(Context a) {
		// TODO Auto-generated constructor stub
		activity=a;
	}

	public void logText(String text, String date) {
		
		if(date==null){
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		
		if(text.contains("service stopped")){
		//	Prefs.putUpdateCalonPenumpang(activity, "0");
			activity.stopService(new Intent(activity,
				LocationUpdateService.class));
			doUnbindService();
		}
	     
		
				
	}

	

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
		IBinder service) {}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
			logText("<font color='#F64747'>disconnected from service :("+"</font>",null);
		}
	};
	

	
	public void doBindService() {
		activity.bindService(new Intent(activity, LocationUpdateService.class), mConnection,
			Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	public void doUnbindService() {
		if (!mIsBound)
			return;

		activity.unbindService(mConnection);
		mIsBound = false;
	}


	public boolean isRunning() {
		ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (LocationUpdateService.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	  	
	
}
