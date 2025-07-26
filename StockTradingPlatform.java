package CodeAlpha_StockTradingPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StockTradingPlatform {
	 static Map<String, Stock> stockMarket = new HashMap<>();
	    static Map<String, UserHolding> portfolio = new HashMap<>();
	    static List<Transaction> history = new ArrayList<>();
	    static double userBalance = 10000.00; // Starting balance

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        populateStocks();

	        while (true) {
	            System.out.println("\n=== Stock Trading Platform ===");
	            System.out.println("1. View Available Stocks");
	            System.out.println("2. Buy Stock");
	            System.out.println("3. Sell Stock");
	            System.out.println("4. View Portfolio");
	            System.out.println("5. View Transaction History");
	            System.out.println("6. View Balance");
	            System.out.println("7. Exit");
	            System.out.print("Enter choice: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consume newline

	            switch (choice) {
	                case 1 -> displayStocks();
	                case 2 -> buyStock(scanner);
	                case 3 -> sellStock(scanner);
	                case 4 -> viewPortfolio();
	                case 5 -> viewHistory();
	                case 6 -> System.out.printf("Current Balance: $%.2f%n", userBalance);
	                case 7 -> {
	                    System.out.println("Exiting... Goodbye!");
	                    return;
	                }
	                default -> System.out.println("Invalid choice. Try again.");
	            }
	        }
	    }

	    static void populateStocks() {
	        stockMarket.put("AAPL", new Stock("AAPL", "Apple Inc.", 195.75));
	        stockMarket.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 2750.50));
	        stockMarket.put("TSLA", new Stock("TSLA", "Tesla Inc.", 720.30));
	        stockMarket.put("MSFT", new Stock("MSFT", "Microsoft Corp.", 330.10));
	        stockMarket.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 3500.00));
	    }

	    static void displayStocks() {
	        System.out.println("\nAvailable Stocks:");
	        for (Stock stock : stockMarket.values()) {
	            System.out.printf("%s (%s): $%.2f%n", stock.name, stock.symbol, stock.currentPrice);
	        }
	    }

	    static void buyStock(Scanner scanner) {
	        System.out.print("Enter stock symbol to buy: ");
	        String symbol = scanner.nextLine().toUpperCase();

	        if (!stockMarket.containsKey(symbol)) {
	            System.out.println("Stock not found.");
	            return;
	        }

	        System.out.print("Enter quantity to buy: ");
	        int quantity = scanner.nextInt();
	        scanner.nextLine(); // consume newline

	        Stock stock = stockMarket.get(symbol);
	        double cost = stock.currentPrice * quantity;

	        if (cost > userBalance) {
	            System.out.println("Insufficient funds.");
	            return;
	        }

	        userBalance -= cost;

	        if (portfolio.containsKey(symbol)) {
	            UserHolding holding = portfolio.get(symbol);
	            double totalCost = holding.avgBuyPrice * holding.quantity + cost;
	            int newQuantity = holding.quantity + quantity;
	            holding.avgBuyPrice = totalCost / newQuantity;
	            holding.quantity = newQuantity;
	        } else {
	            portfolio.put(symbol, new UserHolding(stock, quantity, stock.currentPrice));
	        }

	        history.add(new Transaction("BUY", symbol, quantity, stock.currentPrice));
	        System.out.println("Stock purchased successfully.");
	    }

	    static void sellStock(Scanner scanner) {
	        System.out.print("Enter stock symbol to sell: ");
	        String symbol = scanner.nextLine().toUpperCase();

	        if (!portfolio.containsKey(symbol)) {
	            System.out.println("You don't own this stock.");
	            return;
	        }

	        System.out.print("Enter quantity to sell: ");
	        int quantity = scanner.nextInt();
	        scanner.nextLine(); // consume newline

	        UserHolding holding = portfolio.get(symbol);

	        if (quantity > holding.quantity) {
	            System.out.println("Not enough shares to sell.");
	            return;
	        }

	        double revenue = stockMarket.get(symbol).currentPrice * quantity;
	        userBalance += revenue;
	        holding.quantity -= quantity;

	        if (holding.quantity == 0) {
	            portfolio.remove(symbol);
	        }

	        history.add(new Transaction("SELL", symbol, quantity, stockMarket.get(symbol).currentPrice));
	        System.out.println("Stock sold successfully.");
	    }

	    static void viewPortfolio() {
	        if (portfolio.isEmpty()) {
	            System.out.println("Your portfolio is empty.");
	            return;
	        }

	        System.out.println("\n--- Portfolio ---");
	        double totalValue = 0;

	        for (UserHolding holding : portfolio.values()) {
	            double currentValue = holding.quantity * holding.stock.currentPrice;
	            double gainLoss = currentValue - (holding.avgBuyPrice * holding.quantity);
	            totalValue += currentValue;

	            System.out.printf("%s: %d shares | Buy @ %.2f | Current @ %.2f | Value: %.2f | P/L: %.2f%n",
	                    holding.stock.symbol,
	                    holding.quantity,
	                    holding.avgBuyPrice,
	                    holding.stock.currentPrice,
	                    currentValue,
	                    gainLoss);
	        }

	        System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
	    }

	    static void viewHistory() {
	        if (history.isEmpty()) {
	            System.out.println("No transactions yet.");
	            return;
	        }

	        System.out.println("\n--- Transaction History ---");
	        for (Transaction t : history) {
	            System.out.println(t);
	        }
	    }

}
