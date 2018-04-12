import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ParallelSort {
    private static final int THREAD_COUNT = 7;
    private static final int STEP = THREAD_COUNT;
    private static Double Sum = 0.0;
    private static final int MAX_AVAILABLE = THREAD_COUNT;
    private static final CountDownLatch START = new CountDownLatch(MAX_AVAILABLE);
    private static long startTime;
    private static ArrayList<Integer> mainArray;
    private static ArrayList<Integer> result = new ArrayList<>();
    private static ArrayList<Integer> tmp = new ArrayList<>();


    public static void main(String[] args) throws InterruptedException {
        startTime = System.currentTimeMillis();
        Random random = new Random();
        mainArray = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            mainArray.add(random.nextInt(1000));
        }

        int step = Math.round(mainArray.size() / THREAD_COUNT);
        ArrayList<List<Integer>> subArrays = new ArrayList<>();
        int number = 0;

        for (int i = 1; i <= THREAD_COUNT; i++) {
            List<Integer> tmp;
            if (i == THREAD_COUNT) {
                subArrays.add(mainArray.subList(number, mainArray.size()));
            } else {
                subArrays.add(mainArray.subList(number, number + step));
            }
//            System.out.println(i + ":" + subArrays.get(i - 1).size());
            int temp = i - 1;
            new Thread(() -> {
                Collections.sort(subArrays.get(temp));
//                System.out.println(subArrays.get(temp));
                START.countDown();
            }).start();
            number += step;
        }

        new Thread(() -> {
            try {
                START.await();
                merge(subArrays);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void merge(ArrayList<List<Integer>> subArrays) {

        ArrayList<Integer> indexes = new ArrayList<>();
        for (List<Integer> i : subArrays) {
            indexes.add(0);
        }

        while (validate(subArrays,indexes)) {

            int cindex, currentMinValue, minIndex=-999;
            int min = Integer.MAX_VALUE;

            for(int i=0;i<subArrays.size();i++){
                index = indexes.get(i);
                currentMinValue = subArrays.get(i).get(index);
                if(index>subArrays.get(i).size()) continue;
                if(currentMinValue<min) {min=currentMinValue;minIndex=index;}
                tmp.add(subArrays.get(i).get(indexes.get(i)));//достаем текущее значение для сравнения на минимум, кладем в массив
            }

            result.add(min);
            int tempIndex = indexes.get(minIndex);
            indexes.set(minIndex,tempIndex+1);//minIndex это индекс подмасива, где был новый мин, а tempIndex это индекс текущего элемента, т.е. нашего минимума в данном подмассиве








//            for (List<Integer> subArray : subArrays) {
//                tmp.add(subArray.get());
//            }
//            List<Integer> minArrayUtil = indexMinAndValueMin(tmp);
//            result.add(minArrayUtil.get(1));
//            int index = minArrayUtil.get(0);
//            int currentIndexInArray = indexes.get(index);
//            indexes.set(index, currentIndexInArray + 1);
//        }
            System.out.println(result);
        }
        System.out.println(result);
        System.out.println(result.size());
    }


    private static boolean validate(ArrayList<List<Integer>> subArrays, ArrayList<Integer> indexes) {
        int count=0;
        for (int i=0;i<indexes.size();i++){
            if(indexes.get(i)>subArrays.get(i).size()){count++;}
        }
        if(count==subArrays.size()){return false;}
        return true;
    }

    private static List<Integer> indexMinAndValueMin(ArrayList<Integer> tmp) {
        List<Integer> res = Arrays.asList(0, Integer.MAX_VALUE);
        for (int i = 0; i < tmp.size(); i++) {
            if (tmp.get(i) < res.get(1)) {
                res.set(0, i);
                res.set(1, tmp.get(i));
            }
        }
        return res;
    }
}


//        System.out.println("Time: " + (System.currentTimeMillis() - startTime));

//        START.countDown();


//        new Thread(() -> {
//            try {
//                START.await();
//                output();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//    synchronized private static void calc(int j) {
//        int i = j;
//        double a = 1000;
//        while (Math.abs(a) > EPS) {
//            a = 1.0 / ((4 * i + 1) * (4 * i - 1));
//            Sum += a;
//            i += STEP;
//        }
//    }
//
//    synchronized private static void output() {
//        System.out.println("Sum=" + Sum);
//        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
//



