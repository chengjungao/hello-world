package com.cheng.jungao.test.sort;

import java.util.Arrays;

/**
 * 单边循环快排
 * @author wolf
 *
 */
public class QuickSort {
	public static void main(String[] args) {
		int[] array = new int[] {2,8,9,0,3,1,5,7};
		sort(array,0,array.length -1);
		System.out.println(Arrays.toString(array));
	}

	private static void sort(int[] array, int start,int end) {
		System.out.println("排序区域：" + start + "-" + end + " : " + Arrays.toString(array));
		if (start >= end) {
			return;
		} 
		int pivotIndex = partition(array,start,end);
		sort(array, start, pivotIndex -1);
		sort(array, pivotIndex +1, end);
	}

	private static int partition(int[] array, int start, int end) {
		int pivot = array[start];
		int boundary = start; //小值元素的边界
		for (int i = start + 1; i <= end; i++) {
			if (array[i] < pivot) { //比基准值小，则需要将小值元素边界扩大一个，并此处元素交换
				boundary ++; //将边界扩大一个位置
				int temp = array[boundary];
				array[boundary] = array[i];
				array[i] = temp;
			}
		}
		array[start] = array[boundary];
		array[boundary] = pivot;		
		return boundary;
	}
}
