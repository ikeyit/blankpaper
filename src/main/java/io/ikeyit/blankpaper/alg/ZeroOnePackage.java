package io.ikeyit.blankpaper.alg;

/**
 * N件物品，一个背包。第i件物品重量为Wi，价值为Vi，背包容量为Z公斤。求背包能装下的最大物品价值之和。1个物品只能装一次。
 * DP求解
 * DP[i][j]表示前i个物品放到容量为j的包里的最高价值
 * 转移方程
 * DP[i][0] = 0
 * 当j < Wi，包的容量j小于第i件物品重量时，根本放不下物品，故最高价值还是为i-1时的值，DP[i][j] = DP[i - 1][j]
 * 当j >= Wi, 包的容量j大于等于第i件物品重量时，可以放下物品，最高价值就是放和不放i之间的最大值。放了i，则包的剩余容量为j-Wi，此容量最大价值加上i的价值就为此时价值
 * DP[i - 1][j - Wi] + Vi。 DP[i][j] = MAX(DP[i - 1][j], DP[i - 1][j - Wi] + Vi)
 */
public class ZeroOnePackage {

    class Item {
        int weight;
        int value;
    }

    public static int calculate(Item[] items, int packageCapability) {
        int[][] dp = new int[items.length + 1][packageCapability + 1];
        int maxValue = 0;
        for(int i = 1; i <= items.length; i++) {
            for(int j = 0; j <= packageCapability; j++) {
                if (j == 0) {
                    dp[i][j] = 0;
                } else if (j < items[i].weight) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - items[i].weight] + items[i].value);
                }
            }
        }
        return dp[items.length][packageCapability];
    }
}
