package com.example.harry.mytoolkit.TimerTaskToolKit;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Harry on 2018/4/14.
 *
 */

public class MyTimerTaskHelper implements Serializable{

    private IMyTimerTaskHelper mIMyTimerTaskHelper;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean everyTimeNeedRestart = false;  //標記是否每次 start Timer 都 Restart

    private int delay , period , runCount;
    private int checkRunCount = 0;

    public MyTimerTaskHelper(IMyTimerTaskHelper mIMyTimerTaskHelper){
        this.mIMyTimerTaskHelper = mIMyTimerTaskHelper;
        initTimer();
    }

    public MyTimerTaskHelper(int delay , int period , int runCount , IMyTimerTaskHelper mIMyTimerTaskHelper){
        this(mIMyTimerTaskHelper);
        this.delay = delay;
        this.period = period;
        this.runCount = runCount;
    }

    private void initTimer(){
        this.mTimer = null;
        this.mTimer = new Timer();
    }

    public void setEveryTimeNeedRestart(boolean everyTimeNeedRestart){
        this.everyTimeNeedRestart = everyTimeNeedRestart;
    }

    protected void checkNeedCancel(){
        if(everyTimeNeedRestart && this.mTimer != null){
            Log.d("MainActivity "," Timer cancel ");
            onCancel();
            initTimer();
        }
    }

    public void startTask(final String tag){
        startTask(delay , period , tag);
    }

    public void startTask(Date whenAfterStart , int period){
        checkNeedCancel();
    }

    public void startTask(int delay , int period , final String tag){

        initTimer();

        checkNeedCancel();

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                checkRunCount++;
                mIMyTimerTaskHelper.executeTask(tag);
                if(checkRunCount >= runCount){
                    mIMyTimerTaskHelper.executeFinishTask(tag);
                    onCancel();
                }
            }
        };

        this.mTimer.schedule( mTimerTask, delay, period);
    }

    public void onCancel(){
        if(mTimer != null)
            this.mTimer.cancel();
        if(this.mTimerTask != null)
            this.mTimerTask.cancel();
    }

    public interface IMyTimerTaskHelper {
        public void executeTask(String tag); //execute In Runnable
        public void executeFinishTask(String tag); //run finish
    }

}
