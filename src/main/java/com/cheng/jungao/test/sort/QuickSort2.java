package com.cheng.jungao.test.sort;

import java.util.Arrays;

/**
 * 双边循环快排
 * @author wolf
 *
 */
public class QuickSort2 {
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
		int smallBoundary = start; //小值元素的边界
		int bigBoundary = end; //大值元素的边界
		while (smallBoundary != bigBoundary) {
			//如果右边界元素比基准值大，则需要将右边界左移动，扩大区域
			while(smallBoundary < bigBoundary && array[bigBoundary] > pivot) {
				bigBoundary --;
			}
			
			//如果左边界元素比基准值小或等于，则需要将左边界右移动，扩大区域
			while(smallBoundary < bigBoundary && array[smallBoundary] <= pivot) {
				smallBoundary ++;
			}
			
			//此时双方边界的值处于逆位置，需要交换
			if (smallBoundary < bigBoundary) {
				int temp = array[smallBoundary];
				array[smallBoundary] = array[bigBoundary];
				array[bigBoundary] = temp;
			}
			
		}
		
		array[start] = array[smallBoundary];
		array[smallBoundary] = pivot;		
		return smallBoundary;
	}
}
