import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static final int THREAD_COUNT = 5;
    private static final double EPS = 1E-9;
    private static final int STEP = THREAD_COUNT;
    private static Double Sum = 0.0;
    private static final int MAX_AVAILABLE = THREAD_COUNT;
    private static final CountDownLatch START = new CountDownLatch(MAX_AVAILABLE);
    private static long startTime;

    public static void main(String[] args) throws InterruptedException {
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_COUNT; i++) {
            int temp = i;
            new Thread(() -> {
                calc(temp);
                START.countDown();
            }).start();
        }
        new Thread(() -> {
            try {
                START.await();
                output();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    synchronized private static void calc(int j) {
        int i = j;
        double a = 1000;
        while (Math.abs(a) > EPS) {
            a = 1.0 / ((4 * i + 1) * (4 * i - 1));
            Sum += a;
            i += STEP;
        }
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    synchronized private static void output() {
        System.out.println("Sum=" + Sum);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));// - 500*MAX_AVAILABLE));

    }
}


