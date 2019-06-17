import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    private static Executor executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
//        executor.execute(new CountDownLatchTask());
//        executor.execute(new CyclicBarrierTask());
//        executor.execute(new SemaphoreTask());
        executor.execute(new ExchangerTask());
    }
}
