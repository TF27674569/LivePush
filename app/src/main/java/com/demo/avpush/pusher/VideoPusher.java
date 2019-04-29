package com.demo.avpush.pusher;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.hardware.Camera.CameraInfo;

import com.demo.avpush.jni.PushNative;
import com.demo.avpush.params.VideoParam;

import java.io.IOException;
import java.util.List;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2019/4/18
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
public class VideoPusher implements IPusher, SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "VideoPusher";

    private SurfaceHolder surfaceHolder;
    private Camera mCamera;
    private VideoParam videoParams;
    private boolean isPushing = false;
    private PushNative pushNative;
    private byte[] buffers;

    public VideoPusher(SurfaceHolder surfaceHolder, VideoParam videoParams, PushNative pushNative) {
        this.surfaceHolder = surfaceHolder;
        this.videoParams = videoParams;
        this.pushNative = pushNative;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void startPush() {
        //设置视频参数
        pushNative.setVideoOptions(videoParams.getWidth(),
                videoParams.getHeight(), videoParams.getBitrate(), videoParams.getFps());
        isPushing = true;
    }

    @Override
    public void stopPush() {
        isPushing = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void release() {
        stopPreview();
    }


    /**
     * 切换摄像头
     */
    public void switchCamera() {
        if(videoParams.getCameraId() == CameraInfo.CAMERA_FACING_BACK){
            videoParams.setCameraId(CameraInfo.CAMERA_FACING_FRONT);
        }else{
            videoParams.setCameraId(CameraInfo.CAMERA_FACING_BACK);
        }
        //重新预览
        stopPreview();
        startPreview();
    }

    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            //SurfaceView初始化完成，开始相机预览
            mCamera = Camera.open(videoParams.getCameraId());
            Camera.Parameters parameters = mCamera.getParameters();

            // 查看摄像头支持的分辨率
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
                Log.e(TAG, supportedPreviewSize.width+"-----------------------"+supportedPreviewSize.height);
            }
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPreviewSize(videoParams.getWidth(),videoParams.getHeight());
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(surfaceHolder);
            //获取预览图像数据
            buffers = new byte[(int) (videoParams.getWidth() * videoParams.getHeight() * 1.5)];
            mCamera.addCallbackBuffer(buffers);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止预览
     */
    private void stopPreview() {
        if(mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (mCamera!=null){
            mCamera.addCallbackBuffer(buffers);
        }
        if(isPushing){
            //回调函数中获取图像数据，然后给Native代码编码
            pushNative.fireVideo(data);
        }
    }
}
