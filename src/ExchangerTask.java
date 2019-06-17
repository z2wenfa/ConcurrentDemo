import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Exchanger Demo
 */
public class ExchangerTask implements Runnable {
    private Exchanger<Integer> integerExchanger = new Exchanger<>();
    private Executor executor = Executors.newCachedThreadPool();

    @Override
    public void run() {
        executor.execute(new SumTask(integerExchanger));
        executor.execute(new EchoTask(integerExchanger));
    }


    private static class SumTask implements Runnable {
        private Exchanger<Integer> integerExchanger;

        public SumTask(Exchanger<Integer> integerExchanger) {
            this.integerExchanger = integerExchanger;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                integerExchanger.exchange(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class EchoTask implements Runnable {
        private Exchanger<Integer> integerExchanger;

        public EchoTask(Exchanger<Integer> integerExchanger) {
            this.integerExchanger = integerExchanger;
        }

        @Override
        public void run() {
            int sumCount = 0;
            try {
                sumCount = integerExchanger.exchange(sumCount);
                System.out.println("SumCount is " + sumCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
