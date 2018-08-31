package com.shy.tools.push;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.shy.tools.utils.GsonUtil;
import com.shy.tools.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by cxx on 2018/7/9.
 */
public class MsgPush {

    private static final Logger LOG = LoggerFactory.getLogger(MsgPush.class);
    private static JPushClient jpushClient;
    private static boolean flag = false;
    private String appKey = PropertiesUtil.getProperty("jpush.appkey");
    private String masterSecret =PropertiesUtil.getProperty("jpush.master.secret");
    private String jpushEnvironment=PropertiesUtil.getProperty("jpush.environment");
    
    /**
     * 初始化极光服务环境
     */
    private MsgPush(){
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang init start...=========");
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang -- appKey========="+appKey);
            LOG.info("============jiguang -- masterSecret========="+masterSecret);
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("======read config...");
            LOG.debug("masterSecret:" + masterSecret);
            LOG.debug("appKey:" + appKey);
        }
        ClientConfig clientConfig = ClientConfig.getInstance();
        jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang environment========="+jpushEnvironment);
        }
        if (jpushEnvironment.equals("true")){
            flag = true;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang init end...=========");
        }
    }

    /**
     * 单例对象，在系统启动时创建
     */
    private static MsgPush instance = new MsgPush();

    /**
     * 单例构造函数(饿汉)
     * @return 单例对象
     */
    public static MsgPush getInstance(){
        return instance;
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObjectAndroidIosAliasAlert(String alias, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(alert)
                        .build()
                )
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }


    /**
     * 指定对象推送 ios
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosAliasAlert(String alias, String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * 群推
     *return response
     */
    public static PushPayload buildPushObjectAndroidIosListaliasAlert(List<String> alias, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(alert)
                        .build()
                )
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * 群推 ios
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosListaliasAlert(List<String> alias, String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * 群推 ios
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosAll(String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param alias 对象别名
     * @param alert  推送内容
     * @return PushResult
     */
    public static void push(String alias, String alert) {
        LOG.info("----------jiguang--obect--------"+alias);
        PushPayload payload = buildPushObjectAndroidIosAliasAlert(alias, alert);
        PushPayload payloadIos = buildPushObjectIosAliasAlert(alias, alert);
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang--------");
        }
        try{
            jpushClient.sendPush(payloadIos);
        }catch (Exception e) {
            LOG.error("-----jiguang--------");
        }
    }

    /**
     * 推送给所有人
     *return response
     */
    public static void pushAll(String alert) {
        PushPayload payload =  PushPayload.messageAll(alert);
        PushPayload payloadIos =  buildPushObjectIosAll(alert);
        try {
            jpushClient.sendPush(payloadIos);
        } catch (Exception e) {
            LOG.error("-----jiguang------pushAll--");
        }
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang------pushAll--");
        }
    }

    /**
     * @param alias 对象别名集合
     * @param alert  推送内容
     * Author: zhouye <br/>
     * Date: 2017/8/2 16:27
     */
    public static void pushList(List<String> alias, String alert){
        PushPayload payload = buildPushObjectAndroidIosListaliasAlert(alias,alert);
        PushPayload payloadIos = buildPushObjectIosListaliasAlert(alias,alert);
        try {
            jpushClient.sendPush(payloadIos);
        } catch (Exception e) {
            LOG.error("-----jiguang-----pushList---");
        }
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang-----pushList---");
        }
    }
}
