package com.chengjugano.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOServer {
	public static final Logger logger = LoggerFactory.getLogger(NIOServer.class);

	public static void main(String[] args) throws IOException {
		Selector boss = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		//注册serverChannel，仅注册连接事件
		serverChannel.register(boss, SelectionKey.OP_ACCEPT);
		serverChannel.bind(new InetSocketAddress(8080));
		
		ExecutorService workerPool = initWorks();
		
		 while(true) {
	            boss.select();
	            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
	            while (iter.hasNext()) {
	                SelectionKey key = iter.next();
	                iter.remove();
	                if (key.isAcceptable()) {
	                    SocketChannel sc = serverChannel.accept();
	                    sc.configureBlocking(false);
	                    logger.debug("connected...{}", sc.getRemoteAddress());
	                    workerPool.submit(new Worker(sc));
	                    logger.debug("submit worker!");
	                }
	            }
	        }
	}
	
	
	
	public static ExecutorService initWorks() {
		
		return new ThreadPoolExecutor(2, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
			
					private final AtomicInteger poolNumber = new AtomicInteger(1);
					SecurityManager s = System.getSecurityManager();
					
					private final ThreadGroup group = (s != null)? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
					
					private final AtomicInteger threadNumber = new AtomicInteger(1);
					private final String prefix =  "NIO-Worker" +
		                    poolNumber.getAndIncrement() +
		                   "-thread-";
					
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(group, r,
	                            prefix + threadNumber.getAndIncrement(),
	                            0);

						t.setDaemon(false);
							      
						if (t.getPriority() != Thread.NORM_PRIORITY)
							          t.setPriority(Thread.NORM_PRIORITY);{
							        	  return t;  
							          }
					}
				});
	}
	
	static class Worker implements Runnable{
        private Selector selector;
        
        public Worker(SocketChannel channel) throws IOException {
            this.selector = Selector.open();
            //注册SocketChannel
            channel.register(selector,SelectionKey.OP_READ);
        }

        @Override
        public void run() {
        	logger.debug("worker start!");
            
				while(true) {
					try {
						selector.select(1000 * 10L);
						Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
						while (iter.hasNext()) {
						    SelectionKey key = iter.next();
						    iter.remove();
						    if (key.isReadable()) {
						        ByteBuffer buffer = ByteBuffer.allocate(1024);
						        SocketChannel channel = (SocketChannel) key.channel();
						        channel.read(buffer);
						        buffer.flip();
						        String temp = new String(buffer.array(),StandardCharsets.UTF_8);
						        logger.debug("FROM Client: {}" , temp);
						        
						        buffer.clear();
						        buffer.put(("Hi Client, From Server, " + temp).getBytes(StandardCharsets.UTF_8) );
						        channel.write(buffer);  
						        key.interestOps(SelectionKey.OP_WRITE);
						    }
						    if (key.isWritable()) {
						    	 ByteBuffer buffer = (ByteBuffer) key.attachment();
						         SocketChannel sc = (SocketChannel) key.channel();
						         if( buffer != null) {
							         sc.write(buffer);
						         }
								}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
        }
    }

}
