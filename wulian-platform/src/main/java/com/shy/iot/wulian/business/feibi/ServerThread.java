package com.shy.iot.wulian.business.feibi;

import com.shy.iot.wulian.business.websocket.WebSocketServer;
import com.shy.iot.wulian.common.utils.Bytes2Hex;

import java.io.InputStream;
import java.net.Socket;

//服务端多线程
public class ServerThread implements Runnable {
    private Socket client = null;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //服务器端输入流,接受客户端传来的数据
            //         BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //服务器端输出流，向客户端传送数据
            //        PrintStream ps = new PrintStream(client.getOutputStream());
            boolean flag = true;
            InputStream is = client.getInputStream();
            while (flag) {
                Thread.sleep(1000);
                while (is.available() != 0) {
                    byte[] bytes = new byte[60];
                    WebSocketServer web = new WebSocketServer();
                    is.read(bytes);
                    String str = new String(bytes);
                    String str2 = Bytes2Hex.bytesToHex(bytes);
                    if (str == null || str.equals("")) {
                        web.send("没有收到网关数据");
                        Thread.sleep(2000);
                    } else {
                        web.send(str);
                        web.send(str2);

                    }

                }
            }
            //is.close();
            //  client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}