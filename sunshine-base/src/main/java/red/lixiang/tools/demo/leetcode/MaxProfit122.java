package red.lixiang.tools.demo.leetcode;

public class MaxProfit122 {

    public static void main(String[] args) {
        int[] prices = new int[]{1, 2, 3, 4, 5};
        int profit = maxProfit(prices);
        System.out.println(profit);

    }

    /**
     * 用贪心求解
     * 卖出的那一刻，sample 就是这一天的价格
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int totalProfit = 0;
        int maxProfit = 0;
        // 0 :未持有，1:持有，2:未知
        int hold = 2;
        int[] memo = new int[prices.length];
        memo[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            int todayProfit = 0;
            int todayPrice = prices[i];
            if(hold == 0){
                hold =2;
            }
            if(hold == 1){
                // 可以卖出
//                todayProfit = todayPrice -
            }


        }
        return totalProfit;
    }


}
