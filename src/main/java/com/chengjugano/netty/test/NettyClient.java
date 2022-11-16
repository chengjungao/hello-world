package com.chengjugano.netty.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.internal.SocketUtils;

public class NettyClient {
	public static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

	public static void main(String[] args) throws InterruptedException, IOException {
		final EventLoopGroup bussinessGroup = new DefaultEventLoopGroup(2); 
		
		final EventLoopGroup nioGroup = new NioEventLoopGroup();
		
		ChannelFuture channelFuture = new Bootstrap()
		.channel(NioSocketChannel.class)
		.group(nioGroup)
		.handler(new ChannelInitializer<NioSocketChannel>() {
			
			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						logger.debug((String) msg);
						super.channelRead(ctx, msg);
					}
					
				});
				ch.pipeline().addLast(new StringEncoder());
			}
			
		})
		.connect(SocketUtils.socketAddress("127.0.0.1", 8080))
		.sync();
		
		final Channel channel =  channelFuture.channel();
		
		bussinessGroup.execute(new Runnable() {
			
			public void run() {
				try(Scanner scanner = new Scanner(System.in)){
				while (true) {
					String line = scanner.nextLine();
					if ("exit".equals(line)) {
						channel.close();
						break;
					}
					channel.writeAndFlush(line);
				}
				}
				
			}
		});
		
		channel.closeFuture().addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				bussinessGroup.shutdownGracefully();
				nioGroup.shutdownGracefully();
			}
		});
	}

}
