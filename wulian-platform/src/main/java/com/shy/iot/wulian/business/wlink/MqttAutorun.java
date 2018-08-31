package com.shy.iot.wulian.business.wlink;
//项目自启动类，用来订阅设备上下线和报警信息

import com.shy.iot.wulian.business.wlink.mqtt.MqttsClientSubMain;
import com.shy.iot.wulian.common.constant.Constant;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class MqttAutorun implements CommandLineRunner {
    @Autowired
    private MqttsClientSubMain mqttsClientSubMain;
    @Override
    public void run(String... strings) throws Exception {


     // MqttClient client=MqttsClientSubMain.initMqtt();
        MqttClient client=mqttsClientSubMain.initMqtt();
        client.subscribe("wl/partner/"+ Constant.PARTNERID+"/state");
        client.subscribe("wl/partner/"+ Constant.PARTNERID+"/alarm");
        client.subscribe("wl/partner/"+ Constant.PARTNERID+"/data");

    }
}