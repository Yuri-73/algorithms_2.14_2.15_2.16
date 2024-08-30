package pro.sky.algorithmsLesson;

import java.util.*;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

//import static pro.sky.algorithmsLesson.IntegerListImpl.binarySort;

public class Main {
    public static void main(String[] args) {
        IntegerListImpl integerList = new IntegerListImpl();

        long start = System.currentTimeMillis();
        integerList.sortInsertion(generateArray()); //Сортировка случайного массива
        System.out.println("Время сортировки по методу sortInsertion(сортировка вставкой): " + (System.currentTimeMillis() - start) + " мс");

        long start2 = System.currentTimeMillis();
        Integer[] integerArray = generateArray();
        integerList.sortSelection(integerArray); //Сортировка случайного массива
        System.out.println("Время сортировки по методу sortSelection(сортировка выбором): " + (System.currentTimeMillis() - start2) + " мс");

        long start3 = System.currentTimeMillis();
        Integer[] integerArray2 = generateArray(); //Сортировка случайного массива
        integerList.collectionSort(integerArray2);  //Очень быстрая сортировка!!!
        System.out.println("Время сортировки по методу collectionSort(): " + (System.currentTimeMillis() - start3) + " мс");
    }

    public static Integer[] generateArray() {  //Генератор случайных чисел от 1 до 10 в заданном массиве
        Integer[] integerArray = new Integer[100000];
        for (int i = 0; i < 100000; i++) {
            Random random = new Random();
            integerArray[i] = random.nextInt(1, 10);
        }
        return integerArray;
    }
}
