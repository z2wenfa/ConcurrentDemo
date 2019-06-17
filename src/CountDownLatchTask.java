import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CountDownLatch demo
 */
public class CountDownLatchTask implements Runnable {

    private Executor executor = Executors.newCachedThreadPool();
    private CountDownLatch countDownLatch = new CountDownLatch(3);

    @Override
    public void run() {

        for (int i = 0; i < countDownLatch.getCount(); i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(new Random().nextInt(3) * 1000);
                    countDownLatch.countDown();
                    System.out.println("CountDown!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(new Random().nextInt(3) * 1000);
                    System.out.println("do something after countdown!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            countDownLatch.await();
            System.out.println("CountDown is all ready!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
