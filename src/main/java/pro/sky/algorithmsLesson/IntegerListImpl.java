package pro.sky.algorithmsLesson;

import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.sort;

public class IntegerListImpl implements StringList {
    //Вторая реализация StringList, но с целочисленным массивом. Для этого важно сохранить сигнатуру и тип выходного параметра в каждом методе
    private Integer[] storage;
    private int size;

    public IntegerListImpl() {
        storage = new Integer[10];  //Инициализация пустого массива на 10 элементов
    }

    public IntegerListImpl(int initSize) {  //Использовал только этот параметризованный конструктор
        storage = new Integer[initSize];
    }

    @Override
    public String add(String item) {
//        validateLength();  //Удален, т.к массив теперь в ДЗ-2.16 расширяемый
        growIfNeeded();  //Увеличение массива в 1,5 раза при необходимости
        validateItemAdd(item);
        Integer numItem = Integer.parseInt(item);
        storage[size++] = numItem;  //По индексу 0 записываем item и после прибавляем: size=size+1

        return item;
    }

    @Override
    public String add(int index, String item) {
        growIfNeeded();
//        validateLength();  //Удален, т.к массив теперь в ДЗ-2.16 расширяемый
        validateItemAdd(item);
        validateIndexAdd(index);
        if (index == size) {
            Integer numItem = Integer.parseInt(item);
            storage[size++] = numItem;

            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index); //Сдвиг массива вправо
        size++;
        Integer numItem = Integer.parseInt(item);
        storage[index] = numItem;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndexSetOrRemoveOrGet(index);
        validateItemAdd(item);
        Integer numItem = Integer.parseInt(item);
        storage[index] = numItem;
        return item;
    }

    @Override
    public String remove(String item) {
        validateItemAdd(item);
        int index = indexOf(item);
        return remove(index);
    }

    @Override
    public String remove(int index) {
        validateIndexSetOrRemoveOrGet(index);
        Integer item = storage[index];
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);  //Удаление со смещением
        size--;
        return String.valueOf(item);
    }


    @Override
    public boolean contains(String item) { //В отличие от string-реализации в integer-реализации есть сортировка, а потом через бинарный поиск
        Integer numItem = Integer.parseInt(item);
        Integer[] copyArray = Arrays.copyOf(storage, size);
//        sort(copyArray); //для ДЗ-2.15 утилита
        sortFastRecursion(copyArray); //Быстрая сортировка рекурсивная согласно условия ДЗ-2.16. Код скопирован со шпаргалки 2.16
        int count = Arrays.binarySearch(copyArray, numItem); //Утилита бинарного поиска
        if (count >= 0 && count < size) { //Проверка вхождения результата-индекса в заданный предел
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(String item) {
        Integer numItem = Integer.parseInt(item);
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(numItem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        Integer numItem = Integer.parseInt(item);
        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(numItem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndexSetOrRemoveOrGet(index);
        return String.valueOf(storage[index]);
    }

    @Override
    public boolean equals(StringList otherList) {
        validateItemArrays(otherList);
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String[] toArray() {
        String[] result = new String[storage.length];
        for (int i = 0; i < storage.length; i++) {
            result[i] = String.valueOf(storage[i]);
        }
        return result;
    }

    private void validateItemAdd(String item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    private void validateLength() {  //В ДЗ-2.16 не актуален, поэтому в тест-методы выхода за длину массива удалил
        if (size == storage.length) {
            throw new StorageIsFullException();
        }
    }

    private void validateIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new InvalidIndexException();
        }
    }

    private void validateIndexSetOrRemoveOrGet(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException();
        }
    }

    private void validateItemArrays(StringList item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    public static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static void sortSelection(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public static void collectionSort(Integer[] integers) {  //Самая быстрая сортировка!!!
        Integer[] generated2 = Arrays.copyOf(integers, integers.length);
        List<Integer> list = new ArrayList<>(List.of(generated2));
        Collections.sort(list);
        System.out.println("list.get(8800) = " + list.get(8800));
    }

    private void growIfNeeded() {  //Для расширения массива
        if (size == storage.length) {
            grow();
        }
    }

    private void grow() {  //Для расширения массива в 1,5 раза
        storage = Arrays.copyOf(storage, size + size / 2);
        System.out.println(storage.length);
    }


    public static void sortFastRecursion(Integer[] arr) {  //Для ДЗ-2.16 - применение быстрой сортировки (она с рекурсией)
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    public static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }


}
