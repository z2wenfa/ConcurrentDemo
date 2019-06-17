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
        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(new Random().nextInt(5) * 1000);
                    System.out.println(Thread.currentThread().getName() + " 到达栅栏处!");
                    cyclicBarrier.await();
                    System.out.println("一起走过栅栏!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
