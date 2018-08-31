package com.shy.tools.sms;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV2Request;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.shy.tools.redis.RedisManager;
import com.shy.tools.utils.PropertiesUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cxx on 2018/7/9.
 */
public class BaiduSms {

    private static final Logger LOG = LoggerFactory.getLogger(BaiduSms.class);

    private static final String MSGSMS_SERVER_URL = PropertiesUtil.getProperty("sms.url");

    private static final String MSGSMS_ACCESSKEY = PropertiesUtil.getProperty("sms.accesskey");

    private static final String MSGSMS_SECRETKEY = PropertiesUtil.getProperty("sms.secretkey");

    private static final String MSGPUS_INVOKE_ID = PropertiesUtil.getProperty("sms.invokeid");
    /**
     * 短信发送成功
     */
    private static final int SMS_SEND_SUCCESS = 1;
    /**
     * 短信发送失败
     */
    private static final int SMS_SEND_FAILURE = 2;
    /**
     * 短信发送过于频繁
     */
    private static final int SMS_SEND_TOO_FREQUENT = 3;

    /**
     * 发送短信验证码
     * @param phone 接受短信手机号
     * @param mark  验证码类型标记，"login"：登录验证码，"register"：注册验证码，"common"：通用验证码
     * @return 发送结果，1：发送成功，2：发送失败，3：发送过于频繁（1分钟以内）
     */
    public static int sendValidateCode(String phone, String mark) {
        int result = SMS_SEND_SUCCESS;
        //从配置中读取短信失效时间，单位：分钟
        int smsExpireTime = 30;
        //生成随机数
        String code = getCode();
        //构造短信（需要符合短信平台的模板）
        String[] phones = new String[]{phone};
        String[] params = new String[]{code, "30"};
        // redis的key
        String redisKey = mark + phone;
        String smsCodeInfo = RedisManager.get(redisKey);
        if (smsCodeInfo == null) {
            //发送验证码
            if (!sendSms(BaiduSmsCode.COMMON_VALIDATE_TEMPLATE,phones,params)) {
                return SMS_SEND_FAILURE;
            }
            // 存储redis
            JSONObject jsonValue = new JSONObject();
            // 短信验证码
            jsonValue.put("validateCode", code);
            // 发送时间
            jsonValue.put("createTime", System.currentTimeMillis());
            RedisManager.add(redisKey, jsonValue.toString(), smsExpireTime * 60);
        } else {
            //获取原有验证码
            JSONObject jsStr = JSONObject.fromObject(smsCodeInfo);
            Long createTime = jsStr.getLong("createTime");
            Long nowTime = System.currentTimeMillis();
            // 如果时间差超过一分钟可再次发送
            if ((nowTime - createTime) > 60 * 1000) {
                //发送验证码
                if (!sendSms(BaiduSmsCode.COMMON_VALIDATE_TEMPLATE,phones,params)) {
                    return SMS_SEND_FAILURE;
                }
                //删除原有验证码
                RedisManager.delete(redisKey);
                // 存储redis
                JSONObject jsonValue = new JSONObject();
                // 短信验证码
                jsonValue.put("validateCode", code);
                // 发送时间
                jsonValue.put("createTime", System.currentTimeMillis());
                RedisManager.add(redisKey, jsonValue.toString(), smsExpireTime * 60);
            } else {
                result = SMS_SEND_TOO_FREQUENT;
            }
        }
        return result;
    }

    /**
     * 发送短信
     * @param templateCode 短信模板ID
     * @param phoneNumbers  手机号数组
     * @param params        参数列表
     * @return true为成功，false为失败
     */
    public static boolean sendSms(String templateCode, String[] phoneNumbers, String[] params) {
        // ak、sk等config
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setEndpoint(MSGSMS_SERVER_URL);
        config.setCredentials(new DefaultBceCredentials(MSGSMS_ACCESSKEY, MSGSMS_SECRETKEY));
        // 实例化发送客户端
        SmsClient smsClient = new SmsClient(config);
        // 定义请求参数
        Map<String, String> vars = new HashMap<>();
        // 若模板内容为：您的验证码是\${code},在\${time}分钟内输入有效
        for (int i = 0; i < params.length; i++) {
            vars.put("PARM" + (i + 1), params[i]);
        }
        // 发送短信，开启线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        //实例化请求对象
        for (String phoneNumber : phoneNumbers) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    SendMessageV2Request request = new SendMessageV2Request();
                    request.withInvokeId(MSGPUS_INVOKE_ID).withPhoneNumber(phoneNumber).withTemplateCode(templateCode).withContentVar(vars);
                    // 发送请求
                    SendMessageV2Response response = smsClient.sendMessage(request);
                    if (null == response || !response.isSuccess()) {
                        LOG.error("Send Sms to " + phoneNumber + ",response=" + response);
                    } else {
                        LOG.info("Send Sms to " + phoneNumber + ", result=" + (response != null && response.isSuccess()));
                    }
                }
            });
        }
        // 关闭线程池
        threadPoolExecutor.shutdown();
        // 现阶段不关注发送的结果，默认发送成功
        return true;
    }

    /**
     * 生成6位随机数字验证码
     * @return 6位随机数
     */
    private static String getCode() {
        StringBuilder buffer = new StringBuilder("0123456789");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < 6; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    public static boolean checkValidateCode(String phone, String mark, String validateCode) {
        //校验入参不可为空
        if (phone==null || mark ==null || validateCode==null) {
            return false;
        }
        // redis的key
        String redisKey = mark + phone;
        //获取验证码详情
        String smsCodeInfo = RedisManager.get(redisKey);
        if (smsCodeInfo==null) {
            //如果取不到，说明验证码失效或不存在
            return false;
        }
        //获取原有验证码
        JSONObject jsStr = JSONObject.fromObject(smsCodeInfo);
        String code = jsStr.getString("validateCode");

        //返回验证码比较结果
        if (validateCode.equals(code)) {
            //RedisManager.delete(redisKey);
        } else {
            return false;
        }
        return true;
    }
}
