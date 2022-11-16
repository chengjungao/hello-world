package com.chengjungao.tokenbucket.test;

import java.util.List;

public interface TokenBucket {
	/**
	 * 借
	 * @param number
	 * @return
	 */
	 public List<Object> borrow(Integer number);
	 /**
	  * 还
	  * @param obj
	  */
	 public void returnObj(Object obj);
	 public void returnObj(List<Object> objs);
	 /**
	  * 返回剩余
	  * @return
	  */
	 public Integer remain();
	 

}
