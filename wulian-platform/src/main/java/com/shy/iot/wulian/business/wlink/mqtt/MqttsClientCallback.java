/*
 * 文 件 名:  CcpMqqtCallback.java
 * 版    权:  Wulian Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  姓名 工号
 * 修改时间:  2015年11月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.shy.iot.wulian.business.wlink.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.shy.iot.netty.Client;
import com.shy.iot.netty.ClientHandler;
/*import com.shy.iot.wulian.business.gateway.dao.GatewayDao;
import com.shy.iot.wulian.business.gateway.model.Count;
import com.shy.iot.wulian.business.gateway.model.Gateway;*/
import com.shy.iot.wulian.business.websocket.WebSocketServer;
import com.shy.iot.wulian.common.constant.Constant;
import com.shy.iot.wulian.common.exception.GlobalException;
import com.shy.iot.wulian.common.utils.SecretUtil;
import io.netty.channel.ChannelHandlerContext;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author 姓名 工号
 * @version [版本号, 2015年11月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class MqttsClientCallback implements MqttCallback {
   /* @Autowired
    private GatewayDao gatewayDao;
    @Autowired
    private Count count;
*/
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connectionLost");

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttmessage)
            throws Exception {
        if (mqttmessage != null && mqttmessage.toString() != null && !("".equals(mqttmessage))) {
            System.out.println("this is test." + topic + " " + mqttmessage.toString());
            String returnMeg = mqttmessage.toString();
            String decryptMsg = SecretUtil.decrypt(returnMeg);
            System.out.println("解码后：" + decryptMsg);

            new Client().connect(8080,"127.0.0.1",decryptMsg);

 /*           JSONObject json = JSONObject.parseObject(decryptMsg);

            String cmd = json.get("cmd") == null ? "" : json.get("cmd").toString();
            String messageCode = json.get("messageCode") == null ? "" : json.get("messageCode").toString();
            String gwId = json.get("gwID") == null ? "" : json.get("gwID").toString();
            String gwPassword = json.get("gwPwd") == null ? "" : json.get("gwPwd").toString();
            String data = json.get("data") == null ? "" : json.get("data").toString();

            //订阅消息为设备的状态信息，包括网关和设备的上下线；
            if (topic.equals("wl/partner/" + Constant.PARTNERID + "/state")) {
                //网关上线的判断
                System.out.println("==========");
                if (cmd.equals("01") && messageCode.equals("0101012")) {
                    Gateway gateway = new Gateway();
                    gateway.setGwId(gwId);
                    gateway.setMessageCode("在线");
                    int i = 0;
                    i = gatewayDao.updateMessagecodeByGwId(gateway);
                    if (i > 0) {
                        System.out.println("上线成功");
                    }
                    if (i == 0) {
                        System.out.println("上线失败");
                        throw new GlobalException("GW0008");

                    }
                }
                //网关下线的判断
                if (cmd.equals("15") && messageCode.equals("0101022")) {
                    Gateway gateway = new Gateway();
                    gateway.setGwId(gwId);
                    gateway.setMessageCode("下线");
                    int i = gatewayDao.updateMessagecodeByGwId(gateway);
                    if (i > 0) {
                        System.out.println("更改下线状态成功");
                    }
                    if (i == 0) {
                        throw new GlobalException("GW0009");
                    }
                }
            }
            //订阅消息为设备主动上报的设备数据，如温度等；设备被动上报的用户数据如控制后上报设备数据。
            if (topic.equals("wl/partner/" + Constant.PARTNERID + "/data")) {
                //修改网关密码的返回值
                if (cmd.equals("509") && data.equals("-1")) {

                    count.setI(1);
                }
                if (cmd.equals("509") && data.equals("0")) {
                    //说明修改密码成功，do nothing
                }
                //修改设备名称的返回值
                if(cmd.equals("512")){
                    count.setI(2);
                }

            }
            //如果是报警信息，通过websocket发送至前端
            if (topic.equals("wl/partner/" + Constant.PARTNERID + "/alarm")){
                WebSocketServer web=new WebSocketServer();
                web.send(decryptMsg);
            }*/

            //下面用于演示 后期要更改
//            String devType ="";
//            String level = "";
//            JSONObject  json = JSONObject.parseObject(rightMsg);
//            String devID  = json.get("devID").toString();
//            if(devID.equals("D262C403004B1200")){ //门窗
//                devType ="001";
//                level = "1";
//            }else if(devID.equals("14F5CB0A004B1200")){//燃气
//                devType ="002";
//                level = "1";
//            }else if(devID.equals("0ABC6B0F004B1200")){//水浸
//                devType ="003";
//                level = "2";
//            }else if(devID.equals("323C4E09004B1200")){//烟雾
//                devType ="004";
//                level = "1";
//            }else {
//
//            }
//            if(devType!=null&&!"".equals(devType)){
//                List<PushResp> list = new ArrayList<>();
//                PushResp pushResp = new PushResp();
//                pushResp.setDevType(devType);
//                pushResp.setLevel(level);
//                list.add(pushResp);
//                System.out.println("********************************************************************");
//                System.out.println(pushResp);
//                WebSocket web = new WebSocket();
//                web.sendMessageToUser(1L, GsonUtil.object2Gson(list));     //演示用户id为1
//                System.out.println("webSocket  end successful");
//            }
        }


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete");
    }


}
