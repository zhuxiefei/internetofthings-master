package com.shy.iot.wulian.common.utils;

import java.util.ArrayList;

/**
 * 字节处理工具
 *
 * @author  wangl
 * @version  [版本号, 2017年3月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ByteGroup
{
    ArrayList<Byte> byteContainer = new ArrayList<Byte>();
    
    public byte[] toBytes()
    {
        byte[] bytes = new byte[byteContainer.size()];
        for (int i = 0; i < byteContainer.size(); i++)
        {
            bytes[i] = byteContainer.get(i);
        }
        return bytes;
    }
    
    public ByteGroup addBytes(byte[] bytes)
    {
        for (byte b : bytes)
        {
            byteContainer.add(b);
        }
        return this;
    }
    
    public int size()
    {
        return byteContainer.size();
    }
}
