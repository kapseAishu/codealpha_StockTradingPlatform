package CodeAlpha_StockTradingPlatform;

public class UserHolding {
	Stock stock;
    int quantity;
    double avgBuyPrice;

    UserHolding(Stock stock, int quantity, double avgBuyPrice) {
        this.stock = stock;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
    }

}
