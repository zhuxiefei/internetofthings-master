package com.shy.iot.wulian.business.wlink.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServlet;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Component
public class MqttsClientSubMain extends HttpServlet {
    @Autowired
    private MqttsClientCallback callback;

    public  MqttClient initMqtt() {
        MqttClient client=null;
        try {
            System.out.println("*********************************init初始化***********************************************");
            MqttConfigInfo config = tidyMqttConfig();

            MqttConnectOptions options = tidyMqttOptions(config);

         //  MqttsClientCallback callback = new MqttsClientCallback();

            client = createMqttClient(MqttClient.generateClientId(), callback, config, options);


            System.out.println("mqtts client creating!");


            //   client.subscribe("wl/partner/ef46c2264838dac6/alarm");

        } catch (Exception e) {
            System.out.println("mqtts error  0!");
        }
         return client;
    }


    private static MqttConfigInfo tidyMqttConfig() {
        MqttConfigInfo config = new MqttConfigInfo();
        config.setHost("umqttcn.wuliancloud.com");//地址
        config.setPort(18884);//端口
        config.setProtocal("ssl://");
        config.setClean(true);
        config.setKeepAliveInterval(50);
        config.setUsername("ef46c2264838dac6");
        config.setPassword("6504bc18a7bbe63bb1fe6ad65569ed2f");
        config.setQos(1);
        return config;
    }

    public static MqttConnectOptions tidyMqttOptions(MqttConfigInfo mqttConfig)
            throws MqttSecurityException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(mqttConfig.isClean());
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttConfig.getPassword().toCharArray());
        options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());

        // 创建X509信任管理器
        X509TrustManager x509TM = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[]{x509TM}, null);

        SSLSocketFactory sslFactory = ctx.getSocketFactory();
        options.setSocketFactory(sslFactory);

        return options;
    }

    public static MqttClient createMqttClient(String clientId, MqttCallback callBack, MqttConfigInfo mqttConfig,
                                              MqttConnectOptions options)
            throws MqttException, MqttSecurityException {
        String serverURI = mqttConfig.getProtocal() + mqttConfig.getHost() + ":" + mqttConfig.getPort();
        MqttClient client = new MqttClient(serverURI, clientId);
        client.setCallback(callBack);
        client.connect(options);

        return client;
    }
}
