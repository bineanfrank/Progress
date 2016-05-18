package com.harlan.progress;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.harlan.progressbar.CircleProgressBar;
import com.harlan.progressbar.LineProgressBar;

public class MainActivity extends AppCompatActivity {

    private LineProgressBar lineProgressBar1, lineProgressBar2;
    private CircleProgressBar circleProgressBar1, circleProgressBar2;

    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineProgressBar1 = (LineProgressBar) findViewById(R.id.line1);
        lineProgressBar2 = (LineProgressBar) findViewById(R.id.line2);

        circleProgressBar1 = (CircleProgressBar) findViewById(R.id.circle1);
        circleProgressBar2 = (CircleProgressBar) findViewById(R.id.circle2);

        mHandler.sendEmptyMessageDelayed(1, 200);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            lineProgressBar1.setProgress(progress);
            lineProgressBar2.setProgress(progress);
            circleProgressBar1.setProgress(progress);
            circleProgressBar2.setProgress(progress);
            mHandler.sendEmptyMessageDelayed(1, 200);
        }
    };
}
