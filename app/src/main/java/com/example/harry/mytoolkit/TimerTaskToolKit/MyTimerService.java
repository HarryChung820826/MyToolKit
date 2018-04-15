package com.example.harry.mytoolkit.TimerTaskToolKit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.harry.mytoolkit.TimerTaskToolKit.MyTimerTaskHelper.IMyTimerTaskHelper;

import java.util.HashMap;

/**
 * Created by Harry on 2018/4/15.
 */

public class MyTimerService extends Service {

    private String TAG = "MyTimerService";

    public static final String BD_DATA_TAG = "bundle_data_tag";
    public static final String BD_EVENT_TAG = "bundle_event_tag";

    private HashMap<String , MyTimerTaskHelper> mMyTimerTaskHelperHS = new HashMap<String , MyTimerTaskHelper>();

    private IMyTimerTaskHelper mIMyTimerTaskHelper;

    public MyTimerService(){}

    public MyTimerService(IMyTimerTaskHelper mIMyTimerTaskHelper){
        this.mIMyTimerTaskHelper = mIMyTimerTaskHelper;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        Log.d(TAG," onStartCommand ");
        if(intent != null && intent.getExtras()!=null){
            MyTimerTaskHelper mMyTimerTaskHelper = initTool();
            String key = intent.getExtras().getString(BD_DATA_TAG,"default");
            mMyTimerTaskHelperHS.put(key,mMyTimerTaskHelper);
            mMyTimerTaskHelper.startTask(key);

        }
//        return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private MyTimerTaskHelper initTool(){
        MyTimerTaskHelper mMyTimerTaskHelper = new MyTimerTaskHelper(
                2000,
                1000,
                5,
                new MyTimerTaskHelper.IMyTimerTaskHelper(){
                    @Override
                    public void executeTask(final String tag) {
                        Log.d( TAG," executeTask Task : "+tag);
                    }

                    @Override
                    public void executeFinishTask(String tag) {
                        Log.d( TAG," executeFinishTask Task : "+tag);
                        mMyTimerTaskHelperHS.remove(tag);
                        allFinishStopService();
                    }
                });
        mMyTimerTaskHelper.setEveryTimeNeedRestart(true);
        return mMyTimerTaskHelper;
    }

    private void allFinishStopService(){
        if(mMyTimerTaskHelperHS == null || (mMyTimerTaskHelperHS != null && mMyTimerTaskHelperHS.size() == 0)){
            stopSelf();
        }
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d(TAG," stopService ");
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        if(mMyTimerTaskHelperHS != null && mMyTimerTaskHelperHS.size() > 0 ){
            for(String key : mMyTimerTaskHelperHS.keySet()){
                mMyTimerTaskHelperHS.get(key).onCancel();
            }
        }
        super.onDestroy();
        Log.d(TAG," onDestroy ");
    }
}
