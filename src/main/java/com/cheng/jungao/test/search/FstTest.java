package com.cheng.jungao.test.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

public class FstTest {

  public static void main(String[] args) {
	String[] inputValues = {"abc", "abs", "absef", "solr", "sonar"};
    long[] outputValues = {4, 7, 15, 45, 66};
    try {
        PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
        Builder<Long> builder = new Builder<Long>(FST.INPUT_TYPE.BYTE4, outputs);
        IntsRefBuilder intsRefBuilder = new IntsRefBuilder();
        for (int i = 0; i < inputValues.length; i++) {
            builder.add(Util.toIntsRef(new BytesRef(inputValues[i]), intsRefBuilder), outputValues[i]);
        }
        FST<Long> fst = builder.finish();
        long start = System.currentTimeMillis();
        Long value = Util.get(fst, new BytesRef("solr") );
        for (int i = 0; i < 10000;i++ ) {
        	 Util.get(fst, new BytesRef("solr") );
		}
        System.out.println(value + " 耗时：" + (System.currentTimeMillis() - start) ); // 45
       } catch (Exception e) {
    	   e.printStackTrace();
       }
    
    

	    Map<String, Long> map = new HashMap<>();
	    for(int i = 0; i < inputValues.length;i++ ) {
	    	map.put(inputValues[i], outputValues[i]);
	    }
	    long start = System.currentTimeMillis();
	    Long value =  map.get("solr"); // 45
	    for (int i = 0; i < 10000;i++ ) {
	    	map.get("solr");
		}
	    System.out.println(value + " 耗时：" + (System.currentTimeMillis() - start) ); // 45
     }
}
