package com.waylau.netty.self.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DELL on 2017/9/7.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(
            EchoServerHandler.class.getName());
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg.toString() + "\n");
            } else {
                channel.writeAndFlush("[you]" + msg.toString() + "\n");
            }
        }
        incoming.writeAndFlush(msg);
        ctx.write(msg);
        logger.info("数据内容："+ msg.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
