package com.demo.avpush.params;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2019/4/18
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
public class AudioParam {

    // 采样率
    private int sampleRateInHz = 44100;
    // 声道个数
    private int channel = 1;

    public AudioParam() {
    }

    public AudioParam(int sampleRateInHz, int channel) {
        super();
        this.sampleRateInHz = sampleRateInHz;
        this.channel = channel;
    }

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public void setSampleRateInHz(int sampleRateInHz) {
        this.sampleRateInHz = sampleRateInHz;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
