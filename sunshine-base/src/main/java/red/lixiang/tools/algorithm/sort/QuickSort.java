package red.lixiang.tools.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 * array = [6,10,3,8,9,2],[6,7,8,9,10]
 * 先选定一个基准数，6， 然后比6大的，放6右边，比6小的放6左边
 * 两个哨兵，一个从第一个出发， 一个从最后一个出发。 相遇时则一轮结束
 * 要注意，一定要从基准数对面先走
 *
 */
public class QuickSort {
    /**
     * 这里注意，传入的left/right 为坐标值， 如0，6
     * @param array
     * @param left
     * @param right
     */
    public static void quick(int[] array,int left,int right){
        // 因为是递归，所以先写退出条件
        if(left>=right){ return; }
        // 选定第一个当基准
        int base = array[left];
        // i 从左边出发， j 从右边出发
        int i = left;int j = right;
        while (i!=j){
            // j先走，找到比基准数要小的
            while (array[j]>=base && j>i){ j--; }
            // i开始走
            while (array[i]<=base && i<j){ i++; }
            // 各自找到了对应的数据，开始交换,这时候，要么i=j , 要么i<j
            if(i<j){
                int temp = array[i];array[i] = array[j];array[j] = temp;
            }
        }
        // 相等的时候，就可以结束一轮了
        // 交换基准数和i/j
        int temp = array[left];array[left] = array[i];array[i] = temp;
        System.out.println(Arrays.toString(array));
        // 递归去排序
        quick(array,left,i-1);
        quick(array,i+1,right);
    }

    public static void main(String[] args) {
        int[] array = new int[]{6,3,10,7,2,1,9};
        quick(array,0,array.length-1);
    }
}
