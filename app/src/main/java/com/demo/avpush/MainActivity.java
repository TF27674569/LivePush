package com.demo.avpush;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.avpush.jni.LiveStateChangeListener;
import com.demo.avpush.pusher.LivePusher;

public class MainActivity extends AppCompatActivity implements LiveStateChangeListener {
    private static final String PUSH_URL = "rtmp://192.168.1.2/live/tianfeng";
//    private static final String PUSH_URL = "rtmp://124.156.112.151:1935";
//    private static final String PUSH_URL = "rtmp://www.velab.com.cn/live/test";

    private SurfaceView surfaceView;
    private LivePusher mLivePusher;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.suface);
        mLivePusher = new LivePusher(surfaceView.getHolder());

        mLivePusher.setLiveStateListener(this);
    }

    public void start(View v) {
        Button view = (Button) v;
        if ("开始".equals(view.getText().toString())){
            view.setText("停止");
            mLivePusher.startPush(PUSH_URL);
        }else{
            view.setText("开始");
            mLivePusher.stopPush();
        }
    }

    public void switchCamera(View view) {
        mLivePusher.switchCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePusher.release();
    }

    @Override
    public void onError(final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "code = "+code, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
