package red.lixiang.tools.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *  array = [10,3,4,9,8,6];
 *  所谓的冒泡排序，就是从第一个开始，和右边的比较，比右边的大，就交换，一直到最后
 *
 */
public class BubbleSort {

    public static void bubble(int[] array){
        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array.length-1; j++) {
                int value1 = array[j];
                int value2 = array[j+1];
                if(value1<value2){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1]=temp;
                    System.out.println(Arrays.toString(array));
                }
            }

        }
        System.out.println(Arrays.toString(array));
    }

    public static void bubble1(int[] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                int value1 = array[j];
                int value2 = array[j+1];
                if(value1<value2){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1]=temp;
                    System.out.println(Arrays.toString(array));
                }
            }

        }
        System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        int[] array = new int[]{10,4,6,8,3,2,9};
        bubble1(array);
    }
}
