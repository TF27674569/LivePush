package com.demo.avpush.pusher;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.demo.avpush.jni.LiveStateChangeListener;
import com.demo.avpush.jni.PushNative;
import com.demo.avpush.params.AudioParam;
import com.demo.avpush.params.VideoParam;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2019/4/18
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
public class LivePusher implements SurfaceHolder.Callback {


    private SurfaceHolder surfaceHolder;
    private VideoPusher videoPusher;
    private AudioPusher audioPusher;
    private PushNative pushNative;

    public LivePusher(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
        prepare();
    }

    /**
     * 预览准备
     */
    private void prepare() {
        pushNative = new PushNative();

        //实例化视频推流器
        VideoParam videoParam = new VideoParam(320, 240, Camera.CameraInfo.CAMERA_FACING_FRONT);
        videoPusher = new VideoPusher(surfaceHolder,videoParam,pushNative);

        //实例化音频推流器
        AudioParam audioParam = new AudioParam();
        audioPusher = new AudioPusher(audioParam,pushNative);
    }

    public void setLiveStateListener(LiveStateChangeListener liveStateChangeListener){
        pushNative.setLiveStateChangeListener(liveStateChangeListener);
    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {
        videoPusher.switchCamera();
    }

    /**
     * 开始推流
     */
    public void startPush(String url) {
        videoPusher.startPush();
        audioPusher.startPush();
        pushNative.startPush(url);
    }


    /**
     * 停止推流
     */
    public void stopPush() {
        videoPusher.stopPush();
        audioPusher.stopPush();
        pushNative.stopPush();
    }

    /**
     * 释放资源
     */
    public void release() {
        videoPusher.release();
        audioPusher.release();
        pushNative.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPush();
        release();
    }
}
