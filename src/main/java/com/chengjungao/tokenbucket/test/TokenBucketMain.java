package com.chengjungao.tokenbucket.test;

import java.util.List;

public class TokenBucketMain {
	static TokenBucket tockenBucket  = new TockenBucketImpl(10);;
	
	 
	
	public static void main(String[] args) {
		System.err.println(tockenBucket instanceof Object);
		
		//first
		new  Thread(new  Runnable() {
			
			public void run() {
				System.out.println("first runing!");
				List<Object> borrowBuckets = tockenBucket.borrow(20);
				System.out.println("first get 20 tokens!");
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tockenBucket.returnObj(borrowBuckets);
				System.out.println("first return 20 tokens!");
			}
		}).start();
		
		//second
		new Thread(new Runnable() {
			
			public void run() {
				System.out.println("second runing!");
				List<Object> borrowBuckets = tockenBucket.borrow(20);
				System.out.println("second get 20 tokens!");
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tockenBucket.returnObj(borrowBuckets);
				System.out.println("second return 20 tokens!");
			}
		}).start();
		
		
		
	}
}
