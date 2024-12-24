import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
class Broker implements Runnable {
    private final Exchange exchange;
    private final Random random = new Random();

    public Broker(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void run() {
        while (exchange.isTradingActive()) {
            List<Stock> stocks = exchange.getStocks();
            Stock stock = stocks.get(random.nextInt(stocks.size()));
            int action = random.nextInt(2); // 0 - покупка, 1 - продажа
            int amount = random.nextInt(10) + 1;

            if (action == 0) {
                if (stock.trade(amount)) {
                    stock.updatePrice(amount * 0.1);
                }
            } else {
                stock.updatePrice(-amount * 0.1);
            }


            if (exchange.calculateIndex() < 50) {
                exchange.stopTrading();
                System.out.println("Торги приостановлены из-за падения индекса!");
            }

            try {
                Thread.sleep(500); // Задержка для имитации времени торговли
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}