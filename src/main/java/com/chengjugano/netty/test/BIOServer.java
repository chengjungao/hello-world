package com.chengjugano.netty.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BIOServer {
	public static final Logger logger = LoggerFactory.getLogger(BIOServer.class);
	
	public static void main(String[] args) throws IOException {
		
		ExecutorService pool = new ThreadPoolExecutor(2, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
			
					private final AtomicInteger poolNumber = new AtomicInteger(1);
					SecurityManager s = System.getSecurityManager();
					
					private final ThreadGroup group = (s != null)? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
					
					private final AtomicInteger threadNumber = new AtomicInteger(1);
					private final String prefix =  "myBusiness-" +
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
				
		try(ServerSocket serverSocket = new ServerSocket(8080)){
			while (true) {
				Socket socket = serverSocket.accept();
				pool.submit(new Runnable() {
					
					@Override
					public void run() {
						try {
							InputStream is = socket.getInputStream();
							OutputStream os = socket.getOutputStream();
							BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
							String line = null;
							while((line = br.readLine()) != null) {
								logger.debug("From Client: {}" , line);
								
								//send response
								PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,StandardCharsets.UTF_8)); 
								pw.println("From Server, I revice your message : " + line.toString()); 
								pw.flush();
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				});
			}
		}
	}

}
