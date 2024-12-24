import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
class Stock {
    private final String name;
    private final Lock lock = new ReentrantLock();
    private double price;
    private int quantity;

    public Stock(String name, double initialPrice, int initialQuantity) {
        this.name = name;
        this.price = initialPrice;
        this.quantity = initialQuantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        lock.lock();
        try {
            return price;
        } finally {
            lock.unlock();
        }
    }

    public void updatePrice(double delta) {
        lock.lock();
        try {
            price = Math.max(1, price + delta); // Минимальная цена = 1
        } finally {
            lock.unlock();
        }
    }

    public boolean trade(int amount) {
        lock.lock();
        try {
            if (quantity >= amount) {
                quantity -= amount;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}
