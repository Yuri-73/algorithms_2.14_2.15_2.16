package pro.sky.algorithmsLesson;

import java.util.*;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

//import static pro.sky.algorithmsLesson.IntegerListImpl.binarySort;

public class Main {
    public static void main(String[] args) {
        StringListImpl stringList = new StringListImpl(10);

        System.out.println(stringList.add("один"));
        System.out.println(stringList.add("два"));
        System.out.println(stringList.add(1, "один+"));
        System.out.println("stringList.getStorage(): " + stringList.toString());

        System.out.println("stringList.remove(\"один+\"): " +stringList.remove("один+"));
        System.out.println("stringList.getStorage(): " + stringList.toString());

        IntegerListImpl integerList = new IntegerListImpl();

        long start = System.currentTimeMillis();
        integerList.sortInsertion(generateArray());
        System.out.println("Время сортировки по методу sortInsertion(сортировка вставкой): " + (System.currentTimeMillis() - start) + " мс");

        long start2 = System.currentTimeMillis();
        Integer[] integerArray = generateArray();
        integerList.sortSelection(integerArray);
        System.out.println("Время сортировки по методу sortSelection(сортировка выбором): " + (System.currentTimeMillis() - start2) + " мс");

        long start3 = System.currentTimeMillis();
        Integer[] integerArray2 = generateArray();
        integerList.collectionSort(integerArray2);
        System.out.println("Время сортировки по методу collectionSort(утилитная сортировка): " + (System.currentTimeMillis() - start3) + " мс");

        //Блок специально вставлен для ДЗ-2.16:
        long start4 = System.currentTimeMillis();
        Integer[] integerArray3 = generateArray();
        integerList.sortFastRecursion(integerArray3);
        System.out.println("Время сортировки по методу sortFastRecursion(быстрая сортировка с рекурсией): " + (System.currentTimeMillis() - start4) + " мс");
    }

    public static Integer[] generateArray() {
        Integer[] integerArray = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            Random random = new Random();
            integerArray[i] = random.nextInt(1, 10);
        }
        return integerArray;
    }
}
