package com.chengjugano.netty.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BIOClient {
	public static final Logger logger = LoggerFactory.getLogger(BIOClient.class);
	
	public static void main(String[] args) throws IOException {
		try (Socket socket = new Socket("127.0.0.1", 8080)){
				socket.setKeepAlive(true);
				socket.setSoTimeout(3600);
				Scanner scanner = new Scanner(System.in);
				OutputStream os = socket.getOutputStream();
				while (true) {
					String input = scanner.nextLine();
					if ("exit".equals(input)) {
						scanner.close();
						os.close();
						break;
					}
					
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
					pw.print(input);
					pw.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
	
}
