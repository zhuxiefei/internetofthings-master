package com.shy.iot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhuxiefei
 * @date 2018/8/30 10:38
 */
public class Server {

        public void start(int port) throws Exception {

            /**
             * 专门用于网络事件的处理，一个用于服务端接受客户端的连接，另一个用于SocketChannel的网络读写
            */
            EventLoopGroup boosGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            /*
            * Netty用于启动NIO服务端的辅助启动类。配置Netty的一系列参数，例如将两个NIO线程组当作入参
            * 传递到ServerBootStrap中，蛇者创建的Channel.接受传出数据的缓存大小等
            * */
            ServerBootstrap strap = new ServerBootstrap();
            try {
                strap.group(boosGroup, workerGroup).
                        channel(NioServerSocketChannel.class).
                        option(ChannelOption.SO_BACKLOG, 1024).
                        /*
                        * 创建一个实际处理数据的类ChannelInitializer,绑定IO事件的处理类ChannelHandler，进行
                        * 初始化的准备工作，比如设置接受传出数据的字符、格式以及实际处理数据的接口
                        * */
                        childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                /*
                                * 绑定接口,执行同步阻塞方法
                                * */
                                ch.pipeline().addLast(new ServerHandler());
                            }
                        });
                ChannelFuture future=strap.bind(port).sync();
                future.channel().closeFuture().sync();
            }finally {

                /*
                * 调用NIO线程组的shutdownGracefully进行退出,
                * 它会释放跟shutdownGracefully相关联的资源。
                *
                * */
                boosGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        }

        public static void main(String[] args) throws Exception {
            System.out.println("start server");
            new Server().start(8080);
        }
    }

