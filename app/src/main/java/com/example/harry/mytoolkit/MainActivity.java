package com.example.harry.mytoolkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.harry.mytoolkit.TimerTaskToolKit.MyTimerService;
import com.example.harry.mytoolkit.TimerTaskToolKit.MyTimerTaskHelper;

public class MainActivity extends AppCompatActivity {

    private MyTimerTaskHelper mMyTimerTaskHelper;

    private Button timer_switch_btn;
    int tagInt = 1;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initTool();
//
//        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyTimerTaskHelper != null)
            mMyTimerTaskHelper.onCancel();
    }

    private void initTool(){
        mMyTimerTaskHelper = new MyTimerTaskHelper(
                2000,
                1000,
                5,
                new MyTimerTaskHelper.IMyTimerTaskHelper(){
                    @Override
                    public void executeTask(final String tag) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("MainActivity ","executeTask Task : "+tag);
                            }
                        });
                    }

                    @Override
                    public void executeFinishTask(String tag) {
                        Log.d("MainActivity "," executeFinishTask Task : "+tag);
                    }
                });
        mMyTimerTaskHelper.setEveryTimeNeedRestart(true);
    }

    private void init(){
        timer_switch_btn = ((Button) findViewById(R.id.timer_switch_btn));

        timer_switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerBtnEvent(1000);
            }
        });

        textView = ((TextView) findViewById(R.id.textView));
    }

    private void setMessageToUI(String msg){
        textView.setText(msg);
    }

    private boolean isStart = false;

    private void startTimerBtnEvent(int period){
        isStart = !isStart;
        if(isStart){
            timer_switch_btn.setText(getResources().getString(R.string.timer_task_cancel));
            tagInt += 1;
            Log.d("MainActivity ","==================Task : "+tagInt + " Start period = "+period);
//            mMyTimerTaskHelper.startTask(2000 , period , ""+tagInt);
            setartMyTimerService("Task "+tagInt);
        }else{
            timer_switch_btn.setText(getResources().getString(R.string.timer_task_start));
//            mMyTimerTaskHelper.onCancel();
        }

    }

    private void setartMyTimerService(String tag){
        Intent mIntent = new Intent(MainActivity.this, MyTimerService.class);
        Bundle bd = new Bundle();
        bd.putString(MyTimerService.BD_DATA_TAG , tag);
        mIntent.putExtras(bd);
        startService(mIntent);
    }
}
