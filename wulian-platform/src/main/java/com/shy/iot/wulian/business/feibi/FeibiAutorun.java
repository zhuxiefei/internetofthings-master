package com.shy.iot.wulian.business.feibi;

import com.shy.iot.wulian.business.websocket.WebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;

@Component
@Order(value = 2)
public class FeibiAutorun implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        ServerSocket server =new ServerSocket(8090);
        Socket client=null;
        boolean flag=true;
        System.out.println("服务端已开启！");
        Thread.sleep(1000*30);
        System.out.println("============");
        WebSocketServer web=new WebSocketServer();
        web.send("TCP服务端已启动");
        while(flag){
            //等待与客户端的链接，没有则获取连接
            client=server.accept();
            System.out.println("与客户端连接成功！");
            web.send("有网关接入");
            new Thread(new ServerThread(client)).start();
        }
        server.close();
    }
}