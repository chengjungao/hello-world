package com.chengjugano.netty.test;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {
	
	public static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
	public static void main(String[] args) {
		new ServerBootstrap().group(new NioEventLoopGroup())
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<NioSocketChannel>() {
			
			@Override
			protected void initChannel(final NioSocketChannel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						logger.debug((String) msg);
						super.channelRead(ctx, msg);
					}
					
				});
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						logger.debug("send to client!");
						ch.writeAndFlush(("Hello Client! " + msg));
					}
					
				});
				ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
			}
			
		})
		.bind(8080);
	}

}
