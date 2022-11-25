package com.chengjugano.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NIOClient {

	public static void main(String[] args) throws IOException {
		SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8080));
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
			String input = scanner.nextLine();
			if ("exit".equals(input)) {
				scanner.close();
				sc.close();
				break;
			}
			sc.write(ByteBuffer.wrap(input.getBytes(StandardCharsets.UTF_8)));
		}

	}

}
