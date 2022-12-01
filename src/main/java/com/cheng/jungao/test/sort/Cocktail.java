package com.cheng.jungao.test.sort;

import java.util.Arrays;

/**
 * 所谓鸡尾酒排序，是冒泡的一种优化，来回摇摆排序，选择最大和最小值
 * @author wolf
 */
public class Cocktail {
	
	public static void main(String[] args) {
		int[] array = new int[] {2,8,9,0,3,1,5,7};
		sort(array);
		System.out.println(Arrays.toString(array));
	}
	
	public static void sort(int array[]) {
		int temp = 0;
		for (int i = 0; i < array.length / 2; i++) {
			System.out.println(Arrays.toString(array));
			System.out.println("第" + (i + 1) + "轮循环");
			boolean isSorted = true;
			//从左向右冒泡，起始位置需要考虑向左已经冒泡的位置
			for (int j = i; j < array.length -i -1; j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					isSorted = false;
				}
			}
			//如果全部是有序则结束循环
			if (isSorted) {
				break;
			}
			
			//从右向左冒泡
			isSorted = true;
			for (int j = array.length -i -1; j > i; j--) {
				if (array[j] < array[j - 1]) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
					isSorted = false;
				}
			}
			if (isSorted) {
				break;
			}
		}
	}
}
