import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclickBarrier Demo
 */
public class CyclicBarrierTask implements Runnable {

    private Executor executor = Executors.newCachedThreadPool();
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    @Override
    public void run() {
        executor.execute(() -> {
            try {
                Thread.sleep(new Random().nextInt(5) * 1000);
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
