package com.cheng.jungao.test.sort;

import java.util.Arrays;

/**
 * 插入排序，每一轮循环扩大一个排序元素，并将扩大的元素，插入合适的位置
 * @author wolf
 *
 */
public class InsertSort {

	public static void main(String[] args) {
		int[] array = new int[] {2,8,9,0,3,1,5,7};
		sort(array);
		System.out.println(Arrays.toString(array));

	}

	private static void sort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			System.out.println(Arrays.toString(array));
			System.out.println("第" + (i) + "轮循环");
			
			int temp = array[i];
			int j = i - 1;
			while (j >= 0 && temp < array[j]) { //如果对于取出的值比前一个位置小
				array[j + 1] =  array[j]; //则将当前位置的元素后移一位
				j--;
			}
			array[j + 1] = temp;
			
		}
	}

}
