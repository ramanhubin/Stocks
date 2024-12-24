import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
class Exchange {
    private final List<Stock> stocks = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private boolean tradingActive = true;

    public Exchange() {
        // Инициализация акций
        stocks.add(new Stock("AAPL", 150, 1000));
        stocks.add(new Stock("GOOGL", 2800, 500));
        stocks.add(new Stock("AMZN", 3300, 200));
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public boolean isTradingActive() {
        lock.lock();
        try {
            return tradingActive;
        } finally {
            lock.unlock();
        }
    }

    public void stopTrading() {
        lock.lock();
        try {
            tradingActive = false;
        } finally {
            lock.unlock();
        }
    }

    public double calculateIndex() {
        lock.lock();
        try {
            return stocks.stream()
                    .mapToDouble(stock -> stock.getPrice())
                    .average()
                    .orElse(0);
        } finally {
            lock.unlock();
        }
    }
}
