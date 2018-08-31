package com.shy.tools.sms;

/**
 * Created by cxx on 2018/4/19/019.
 */
public interface BaiduSmsCode {

    /**
     * 房屋成员注册成功:您已成为${house}房屋成员，登录账号：${account}，密码：${password}。
     */
    String HOUSE_MEMBER_JOIN_TEMPLATE = "smsTpl:5e5f29de-032f-4faa-8a99-3ec90460145b";

    /**
     * 维修通知:尊敬的${name1}，您对${type}的报修：${content}，我们已指派维修人员前来维修，有问题和师傅联系:${phone},${name2}。
     */
    String REPAIR_NOTICE_TEMPLATE = "smsTpl:49cc7262-7c95-4e92-ac2f-0e5b03f13911";

    /**
     * 缴费提醒:尊敬的${house}房屋业主:${name}，您${cycle}周期的${type}共计人民币${num}元，请尽快缴费。
     */
    String PAY_REMIND_TEMPLATE = "smsTpl:bbd40970-3d41-4ffa-b04d-4c6987cdc54d";

    /**
     * 注册通过通知:您已成功注册账号，登录账号：${account}，密码：${password}。
     */
    String REGISTER_PASS_TEMPLATE = "smsTpl:68b6c5fd-8a7b-406e-902c-4ced2c166570";

    /**
     * 缴费完成通知:尊敬的业主${name}，您于${time}缴纳${type}费用共合计人民币${num}元，感谢您的配合。
     */
    String PAY_SUCCESS_TEMPLATE = "smsTpl:30cae241-0dbc-448d-9142-6637c90d488d";

    /**
     * 通用验证码短信:尊敬的用户，您的验证码是：${code}，${min}分钟内有效。请勿向任何人提供您收到的短信验证码！
     */
    String COMMON_VALIDATE_TEMPLATE = "smsTpl:c9f94380-52af-43b3-a993-2d8bdadf6532";

    /**
     * 欠缴提醒:尊敬的xx房屋业主${name}，截止${time}，您欠缴的${type}共计人民币${num}元（未包含滞纳金），请尽快缴费，感谢您的支持。
     */
    String PAY_REMIND_TEMPLATE_TWO = "smsTpl:ebabf48a-76e0-47eb-acbd-ab8a620db837";

    /**
     * 传感检测异常提醒:尊敬的用户，您${house}房屋的${type}传感器检测到异常，请立即处理。
     */
    String DEVICE_REMIND_TEMPLATE = "smsTpl:1c254501-3a1f-45c2-af82-d5cbe668abb2";
}
