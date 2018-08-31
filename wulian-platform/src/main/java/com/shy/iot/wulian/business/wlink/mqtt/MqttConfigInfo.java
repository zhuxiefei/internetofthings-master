/*
 * 文 件 名:  MqqtConfig.java
 * 版    权:  Wulian Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  姓名 工号
 * 修改时间:  2015年11月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.shy.iot.wulian.business.wlink.mqtt;



/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2015年11月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MqttConfigInfo
{
    // 连接实例基础名称
    private String baseClientId;
    
    // 协议
    private String protocal;
    
    // RabbiMQ服务主机
    private String host;
    
    // RabbiMQ服务端口
    private int port;
    
    // 连接用户名
    private String username;
    
    // 连接密码
    private String password;
    
    // 消息是否持久化
    private boolean clean;
    
    // 心跳交互间隔
    private int keepAliveInterval;
    
    // 消息容错参数
    private int qos;

    /**
     * 获取 baseClientId
     * 
     * @return 返回 baseClientId
     */
    public String getBaseClientId()
    {
        return baseClientId;
    }
    
    /**
     * 设置 baseClientId
     *
     */
    public void setBaseClientId(String baseClientId)
    {
        this.baseClientId = baseClientId;
    }
    
    /**
     * 获取 protocal
     * 
     * @return 返回 protocal
     */
    public String getProtocal()
    {
        return protocal;
    }
    
    /**
     * 设置 protocal
     *
     */
    public void setProtocal(String protocal)
    {
        this.protocal = protocal;
    }
    
    /**
     * 获取 host
     * 
     * @return 返回 host
     */
    public String getHost()
    {
        return host;
    }
    
    /**
     * 设置 host
     *
     */
    public void setHost(String host)
    {
        this.host = host;
    }
    
    /**
     * 获取 port
     * 
     * @return 返回 port
     */
    public int getPort()
    {
        return port;
    }
    
    /**
     * 设置 port
     *
     */
    public void setPort(int port)
    {
        this.port = port;
    }
    
    /**
     * 获取 username
     * 
     * @return 返回 username
     */
    public String getUsername()
    {
        return username;
    }
    
    /**
     * 设置 username
     *
     */
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 获取 password
     * 
     * @return 返回 password
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * 设置 password
     *
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    /**
     * 获取 clean
     * 
     * @return 返回 clean
     */
    public boolean isClean()
    {
        return clean;
    }
    
    /**
     * 设置 clean
     *
     */
    public void setClean(boolean clean)
    {
        this.clean = clean;
    }
    
    /**
     * 获取 keepAliveInterval
     * 
     * @return 返回 keepAliveInterval
     */
    public int getKeepAliveInterval()
    {
        return keepAliveInterval;
    }
    
    /**
     * 设置 keepAliveInterval
     *
     */
    public void setKeepAliveInterval(int keepAliveInterval)
    {
        this.keepAliveInterval = keepAliveInterval;
    }
    
    /**
     * 获取 qos
     * 
     * @return 返回 qos
     */
    public int getQos()
    {
        return qos;
    }
    
    /**
     * 设置 qos
     *
     */
    public void setQos(int qos)
    {
        this.qos = qos;
    }
    
    /**
     * <pre>
     * <一句话功能描述> 
     *  重载方法
     * </pre>
     * 
     * @author 彭海明
     * @return
     */
    @Override
    public String toString()
    {
        return "MqttConfig [clientId=" + baseClientId + ", protocal=" + protocal + ", host=" + host + ", port="
            + port + ", username=" + username + ", password=" + password + ", clean=" + clean + ", keepAliveInterval="
            + keepAliveInterval + ", qos=" + qos + "]";
    }
}
