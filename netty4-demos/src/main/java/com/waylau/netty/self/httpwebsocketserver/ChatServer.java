package com.waylau.netty.self.httpwebsocketserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by DELL on 2018/1/3.
 */
public class ChatServer {

    public void start(InetSocketAddress address) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap  = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(createInitializer());
            ChannelFuture future = bootstrap.bind(address).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    protected ChannelInitializer<SocketChannel> createInitializer() {
        return new ChatServerInitializer();
    }

    public static void main(String[] args) throws Exception{
        int port = 8084;

        final ChatServer endpoint = new ChatServer();
        endpoint.start(new InetSocketAddress(port));
    }
}
