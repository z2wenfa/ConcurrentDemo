import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock Demo
 */
public class ReentrantLockTask implements Runnable {

    private Executor executor = Executors.newCachedThreadPool();

    private ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public void run() {
        Condition productCondition = reentrantLock.newCondition();
        Condition consumerCondition = reentrantLock.newCondition();


        executor.execute(new Producer(productCondition, consumerCondition, reentrantLock));
        executor.execute(new Consumer(productCondition, consumerCondition, reentrantLock));

    }

    private List<Integer> wareHouse = new ArrayList<>();

    private class Producer implements Runnable {

        Condition productCondition;
        Condition consumerCondition;
        ReentrantLock reentrantLock;

        public Producer(Condition productCondition, Condition consumerCondition, ReentrantLock reentrantLock) {
            this.productCondition = productCondition;
            this.consumerCondition = consumerCondition;
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    reentrantLock.lock();
                    while (wareHouse.size() >= 10) {
                        System.out.println("warehouse is full!");
                        productCondition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int i = new Random().nextInt(10);
                    wareHouse.add(i);
                    System.out.println("product " + i);
                    consumerCondition.signal();
                    try {
                        productCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reentrantLock.unlock();
                }
            }
        }
    }

    private class Consumer implements Runnable {

        Condition productCondition;
        Condition consumerCondition;
        ReentrantLock reentrantLock;

        public Consumer(Condition productCondition, Condition consumerCondition, ReentrantLock reentrantLock) {
            this.productCondition = productCondition;
            this.consumerCondition = consumerCondition;
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    reentrantLock.lock();
                    while (wareHouse.size() <= 0) {
                        System.out.println("warehouse is empty!");
                        consumerCondition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("consume " + wareHouse.get(0));
                    wareHouse.remove(0);
                    productCondition.signal();
                    try {
                        consumerCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reentrantLock.unlock();
                }
            }
        }
    }

}
