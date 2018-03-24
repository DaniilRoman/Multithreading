import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    private static final int MAX_AVAILABLE = 2;
    private static int part1Result, part2Result, part3Result;
    private static double part4Result, part5Result;
    private static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        final Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

        System.out.println("Введите a: ");
        int a = scanner.nextInt();
        System.out.println("Введите b: ");
        int b = scanner.nextInt();
        System.out.println("Введите c: ");
        int c = scanner.nextInt();

    //    int a = 3, b = -18, c = 27;

        new Thread(() -> {
            try {
                semaphore.acquire();
                part1Result = part1(b);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                part2Result = part2(a, c);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire(MAX_AVAILABLE);
                part3Result = part3(part1Result, part2Result);
                semaphore.release(MAX_AVAILABLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire(MAX_AVAILABLE);
                part4Result = part4(b, part3Result);
                semaphore.release(MAX_AVAILABLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire(MAX_AVAILABLE);
                part5Result = part5(b, part3Result);
                semaphore.release(MAX_AVAILABLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                part6(a, part4Result);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                part7(a, part5Result);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        semaphore.acquire(MAX_AVAILABLE);
//        synchronized (object) {
//            System.out.println("X1 = " + x1);
//            System.out.println("X2 = " + x2);
//        }
//        semaphore.release(MAX_AVAILABLE);
    }

    private synchronized static int part1(int b) throws InterruptedException {
        Thread.sleep(1000);
        return b * b;

    }

    private synchronized static int part2(int a, int c) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return 4 * a * c;
    }

    private synchronized static int part3(int part1Result, int part2Result) throws InterruptedException {
            Thread.sleep(1000);
        return part1Result - part2Result;
    }

    private synchronized static double part4(int b, int part3Result) throws InterruptedException {
        Thread.sleep(1000);
        return -b + Math.sqrt(part3Result);
    }

    private synchronized static double part5(int b, double part3Result) throws InterruptedException {
        Thread.sleep(1000);
        return -b - Math.sqrt(part3Result);
    }

    private synchronized static void part6(int a, double part4Result) {
        double result = part4Result / (2 * a);
        System.out.println("X1 = " + result);
    }

    private synchronized static void part7(int a, double part5Result) {
        double result = part5Result / (2 * a);
        System.out.println("X2 = " + result);
    }
}
