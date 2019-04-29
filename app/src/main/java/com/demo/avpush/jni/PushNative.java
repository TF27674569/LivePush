package com.demo.avpush.jni;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2019/4/19
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
public class PushNative {

    static {
        System.loadLibrary("ndk_push");
        // 静态库不用加载
        // System.loadLibrary("faac");
        System.loadLibrary("rtmp");
        System.loadLibrary("x264");
    }

    public static final int CONNECT_FAILED = 101;
    public static final int INIT_FAILED = 102;

    private LiveStateChangeListener liveStateChangeListener;


    public void setLiveStateChangeListener(LiveStateChangeListener liveStateChangeListener) {
        this.liveStateChangeListener = liveStateChangeListener;
    }

    public void removeLiveStateChangeListener(){
        this.liveStateChangeListener = null;
    }

    /**
     * 接收Native层抛出的错误
     * @param code
     */
    public void throwNativeError(int code){
        if(liveStateChangeListener != null){
            liveStateChangeListener.onError(code);
        }
    }

    public native void startPush(String url);

    public native void stopPush();

    public native void release();

    /**
     * 设置视频参数
     * @param width 宽
     * @param height  高
     * @param bitrate 比特率
     * @param fps 帧率
     */
    public native void setVideoOptions(int width, int height, int bitrate, int fps);

    /**
     * 设置音频参数
     * @param sampleRateInHz 采样率
     * @param channel 声道个数
     */
    public native void setAudioOptions(int sampleRateInHz, int channel);


    /**
     * 发送视频数据
     * @param data 视频数据
     */
    public native void fireVideo(byte[] data);

    /**
     * 发送音频数据
     * @param data 音频数据
     * @param len 有效长度
     */
    public native void fireAudio(byte[] data, int len);

}
