package com.waylau.netty.self.httpwebsocketserver;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bson.Document;

import java.net.InetSocketAddress;

/**
 * Created by DELL on 2018/1/3.
 */
public class ChatServer {
    public static final MongoClient MONGO_CLIENT;
    static {
        MONGO_CLIENT = new MongoClient(new MongoClientURI("mongodb://root:685846@115.28.160.104:27017/?authSource=admin"));
        // Select the MongoDB database.
        MongoDatabase database = MONGO_CLIENT.getDatabase("blog");

        // Select the collection to query.
        MongoCollection<Document> collection = database.getCollection("protectType");
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document d = iterator.next();
            System.out.println(d);
        }
    }
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
