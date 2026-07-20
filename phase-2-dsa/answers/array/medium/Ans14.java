class Solution {
    public int maxProfit(int[] prices) {
        int bestBuy = prices[0];
        int maxProfit = 0;

        for (int price : prices) {
            bestBuy = Math.min(price, bestBuy);
            int profit = price - bestBuy;
            maxProfit = Math.max(profit, maxProfit);
        }

        return maxProfit;
    }
}