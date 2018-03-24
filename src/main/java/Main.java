import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    static Object obj = new Object();

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Random random = new Random();


        for (int i = 0; i < 10; i++) {
            System.out.println("1----" + i);
            array.add(random.nextInt(50));
        }

        new Thread(() -> {
            asc(array);
        }).start();
        new Thread(() -> {
            desc(array);
        }).start();


    }
    static void asc(ArrayList<Integer> array) {
        synchronized (obj) {
            Collections.sort(array);
            for (Integer i : array) {
                System.out.println("2----" + i);
            }
        }
    }

    static void desc(ArrayList<Integer> array) {

        synchronized (obj) {
            Collections.sort(array);
            Collections.reverse(array);
            for (Integer i : array) {
                System.out.println("3----" + i);
            }
        }
    }
}
