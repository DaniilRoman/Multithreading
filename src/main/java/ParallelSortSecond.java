import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ParallelSortSecond {
    private static final int THREAD_COUNT = 4;
    private static final int ARRAY_LENGHT = 10000;
    private static final int MAX_AVAILABLE = THREAD_COUNT;
    private static final CountDownLatch START = new CountDownLatch(MAX_AVAILABLE);
    private static long startTime;
    private static ArrayList<Integer> result = new ArrayList<>();
    private static final ArrayList<List<Integer>> subArrays = new ArrayList<>();


    public static void main(String[] args) throws InterruptedException {
        List<Integer> tmp = new ArrayList<>();
        startTime = System.currentTimeMillis();
        Random random = new Random();
        ArrayList<Integer> mainArray = new ArrayList<>();

        for (int i = 0; i < ARRAY_LENGHT; i++) {
            mainArray.add(random.nextInt(1000));
        }

        //        for (int i = 0; i < ARRAY_LENGHT/2; i++) {
//            mainArray.add(random.nextInt(50));
//        }
//        for (int i = 0; i < ARRAY_LENGHT/2; i++) {
//            mainArray.add(random.nextInt(1000)+200);
//        }

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < ARRAY_LENGHT; i++) {
            int currentValue = mainArray.get(i);
            if (currentValue < min) {
                min = currentValue;
            }
            if (currentValue > max) {
                max = currentValue;
            }
        }
        int A = Math.round((min + max) / THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            int mini = min + i * A, maxi = min + (i + 1) * A;
            for (int x : mainArray) {if (x > mini && x <= maxi) {tmp.add(x);}}
            List<Integer> buffer = new ArrayList<>();
            buffer.addAll(tmp);
            subArrays.add(buffer);
            tmp.clear();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            int finalI = i;
            new Thread(() -> {
                synchronized (subArrays) {
                    Collections.sort(subArrays.get(finalI));
                }
                START.countDown();
            }).start();
        }

        new Thread(() -> {
            try {
                START.await();
                for (int i = 0; i < THREAD_COUNT; i++) result.addAll(subArrays.get(i));
                System.out.println(result);
                System.out.println("Time: " + (System.currentTimeMillis() - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


//
//        for (int i = 0; i < ARRAY_LENGHT/2; i++) {
//            mainArray.add(random.nextInt(50));
//        }
//        for (int i = 0; i < ARRAY_LENGHT/2; i++) {
//            mainArray.add(random.nextInt(1000)+200);
//        }


//        int step = Math.round(mainArray.size() / THREAD_COUNT);
//        ArrayList<List<Integer>> subArrays = new ArrayList<>();
//        int number = 0;
//
//        for (int i = 1; i <= THREAD_COUNT; i++) {
//            List<Integer> tmp;
//            if (i == THREAD_COUNT) {
//                subArrays.add(mainArray.subList(number, mainArray.size()));
//            } else {
//                subArrays.add(mainArray.subList(number, number + step));
//            }
//            int temp = i - 1;
//            new Thread(() -> {
//                Collections.sort(subArrays.get(temp));
//                START.countDown();
//            }).start();
//            number += step;
//        }
//
//        new Thread(() -> {
//            try {
//                START.await();
//                merge(subArrays);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//    private static void merge(ArrayList<List<Integer>> subArrays) {
//
//        ArrayList<Integer> indexes = new ArrayList<>();
//        for (List<Integer> i : subArrays) {
//            indexes.add(0);
//        }
//
//        while (validate(subArrays, indexes)) {
//
//            int currentIndex, currentMinValue, minIndex = -999;
//            int min = Integer.MAX_VALUE;
//
//            for (int i = 0; i < subArrays.size(); i++) {
//                currentIndex = indexes.get(i);
//                if (currentIndex >= subArrays.get(i).size()) continue;
//                currentMinValue = subArrays.get(i).get(currentIndex);
//                if (currentMinValue < min) {
//                    min = currentMinValue;
//                    minIndex = i;
//                }
//            }
//            if (minIndex >= 0) {
//                result.add(min);
//                int tempIndex = indexes.get(minIndex);
//                indexes.set(minIndex, tempIndex + 1);//minIndex это индекс подмасива, где был новый мин, а tempIndex это индекс текущего элемента, т.е. нашего минимума в данном подмассиве
//            }
//        }
//        System.out.println(result);
//        System.out.println(result.size());
//
//        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
//    }
//
//
//    private static boolean validate(ArrayList<List<Integer>> subArrays, ArrayList<Integer> indexes) {
//        int count = 0;
//        for (int i = 0; i < indexes.size(); i++) {
//            if (indexes.get(i) >= subArrays.get(i).size()) {
//                count++;
//            }
//        }
//        if (count == subArrays.size()) {
//            return false;
//        }
//        return true;
    }
}


