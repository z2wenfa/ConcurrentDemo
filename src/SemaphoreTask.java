import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore Demo
 */
public class SemaphoreTask implements Runnable {

    private Semaphore semaphore = new Semaphore(10);
    private Executor executor = Executors.newCachedThreadPool();

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("acquire lock, do somethings! " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(new Random().nextInt(5) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                }
            });
        }
    }
}
