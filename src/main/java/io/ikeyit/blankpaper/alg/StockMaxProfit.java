package io.ikeyit.blankpaper.alg;

/**
 * Dynamic programming example: Calculate the max profit of stock trade
 * 动态规划例子：计算股票最高收益
 * Transition equation:
 * maxProfit[i] = MAX(maxProfit[i-1], prices[i] - minPrice[0...i-1])
 */
public class StockMaxProfit {

    /**
     *
     *
     * @param prices array contains daily stock prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;

        int maxProfit = 0, minPrice = prices[0];
        for(int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            minPrice = Math.min(prices[i], minPrice);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{6, 1, 2, 3, 19, 6}));
    }
}
