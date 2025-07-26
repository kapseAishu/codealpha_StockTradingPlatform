package CodeAlpha_StockTradingPlatform;

import java.util.Date;

public class Transaction {
	String type; // Buy or Sell
    String symbol;
    int quantity;
    double price;
    Date date;

    Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.date = new Date();
    }

    public String toString() {
        return date + " - " + type + " " + quantity + " shares of " + symbol + " @ " + price;
    }

}
