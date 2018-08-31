package com.shy.iot.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhuxiefei
 * @date 2018/8/30 10:38
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    public static String message;

    public ClientHandler(String message){
        this.message = message;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx);

        ByteBuf buf = Unpooled.copiedBuffer(message.getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte[] data=new byte[buf.readableBytes()];
        buf.readBytes(data);
        System.out.print(new String(data));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }



}