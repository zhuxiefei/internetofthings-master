package com.shy.iot.wulian.business.wlink.http;

import com.alibaba.fastjson.JSONObject;
import com.shy.iot.wulian.common.constant.Constant;
import com.shy.iot.wulian.common.exception.GlobalException;
import com.shy.iot.wulian.common.utils.GsonUtil;
import com.shy.iot.wulian.common.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

//用于获取accessToken
public class AccessTokenUtil {
    public static String getAccessToken(){
        //获取accessToken,用于绑定网关
        Map hashmap = new HashMap<>();
        hashmap.put("partnerId", Constant.PARTNERID);
        hashmap.put("partnerKey", Constant.PARTNERKEY);

        String msge = GsonUtil.object2Gson(hashmap);
        String resp = HttpClientUtil.post(
                "https://iotsh.wuliancloud.com/sso/partner/tokens",
                msge
        );
        JSONObject gson = JSONObject.parseObject(resp);
        JSONObject jo = (JSONObject) gson.get("data");
        String accessToken = jo.get("accessToken") == null ? "" : jo.get("accessToken").toString();
        if (accessToken.equals("")) {
            throw new GlobalException("GW0007");
        }
        return accessToken;
    }
}
