package com.demo.avpush;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2019/4/24
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
public class Demo {

    public static void main(String[] args){
        Socket socket = null;
        try {
            socket = new Socket("124.156.112.151",1935);
            InputStream inputStream = socket.getInputStream();
            int len;
            System.out.println("in");
            byte[] buff = new byte[1024];

            while (-1!=(len=(inputStream.read(buff)))){
                System.out.println(""+len);
                System.out.println(new String(buff,0,len));
            }
            System.out.println("1935");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
