package com.waylau.netty.self.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.Logger;


/**
 * This class is used for TCP IP communications with client. It uses Netty tcp
 * server bootstrap for this.
 * 
 * @author Abraham Menacherry
 * 
 */
public class NettyTCPServer
{
	private static final Logger logger = Logger.getLogger(NettyTCPServer.class.getName());
	private int port;
	public NettyTCPServer(int port) {
		this.port = port;
	}
	public void startServer() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});

			ChannelFuture f = b.bind(this.port).sync();
			logger.info("Socket服务器已启动完成");
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
