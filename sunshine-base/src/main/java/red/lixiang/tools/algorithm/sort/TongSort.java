package red.lixiang.tools.algorithm.sort;

/**
 * 桶排序就是，先有桶（数组）
 * 中间的框框为桶
 * array[]={[],[],[],[],[]}
 * 进来一个值，对应index的数据+1
 * 然后从第一个进行遍历输出
 *
 */
public class TongSort {


    public static void tong(int[] array){
        // 先建一个桶
        int[] tong = new int[11];
        for (int i : array) {
            tong[i]=tong[i]+1;
        }
        // 输出桶里面的元素
        for (int i = 0; i < tong.length; i++) {
            if(tong[i]>0){
                for (int i1 = 0; i1 < tong[i]; i1++) {
                    System.out.print(i+" ");
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{10,8,9,5,3,8};
        tong(array);
    }
}
