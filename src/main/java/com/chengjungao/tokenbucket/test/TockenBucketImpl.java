package com.chengjungao.tokenbucket.test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TockenBucketImpl implements TokenBucket{
	private BlockingQueue<Object> blockingQueue;
	
	
	public TockenBucketImpl(Integer size) {
		super();
		blockingQueue = new ArrayBlockingQueue<Object>(size);
		for(int i=0;i<size;i++) {
			Object token = new Object();
			try {
				blockingQueue.put(token);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public List<Object> borrow(Integer number) {
		List<Object> listToken = new ArrayList<Object>();
		try {
			for(int i = 0;i < number;i++) {
				listToken.add(blockingQueue.take());
			}
			
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		return listToken;
	}

	public void returnObj(Object obj) {
		try {
			blockingQueue.put(obj);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

	public Integer remain() {
		return blockingQueue.size();
	}

	public void returnObj(List<Object> objs) {
		for (Object object : objs) {
			try {
				blockingQueue.put(object);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
