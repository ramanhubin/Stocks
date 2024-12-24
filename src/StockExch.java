import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
public class StockExch {
    public static void main(String[] args) {
        Exchange exchange = new Exchange();
        ExecutorService brokerPool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            brokerPool.submit(new Broker(exchange));
        }

        // Основной поток для управления с консоли
        Scanner scanner = new Scanner(System.in);
        while (exchange.isTradingActive()) {
            System.out.println("Введите команду (status, index, stop): ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {//добавить
                case "status":
                    // Показать состояние акций
                    System.out.println("Текущие акции:");
                    for (Stock stock : exchange.getStocks()) {
                        System.out.printf("Акция %s: Цена = %.2f",
                                stock.getName(), stock.getPrice());
                    }
                    break;

                case "index":
                    // Показать текущий индекс биржи
                    System.out.printf("Индекс биржи: %.2f%n", exchange.calculateIndex());
                    break;

                case "stop":
                    // Остановить торги вручную
                    exchange.stopTrading();
                    System.out.println("Торги остановлены вручную!");
                    break;

                default:
                    System.out.println("Неизвестная команда. Попробуйте снова.");
                    break;
            }
        }

        brokerPool.shutdown();
        try {
            brokerPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Торги завершены.");
    }
}
