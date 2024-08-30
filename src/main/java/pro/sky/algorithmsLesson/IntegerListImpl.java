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
    private final Integer[] storage;
    private int size;

    public IntegerListImpl() {
        storage = new Integer[10];  //Инициализация пустого массива на 10 элементов (не использован)
    }

    public IntegerListImpl(int initSize) {  //Использовал только этот параметризованный конструктор (в тест-методе)
        storage = new Integer[initSize];
    }

    @Override
    public String add(String item) {
        validateLength();
        validateItemAdd(item);
        Integer numItem = Integer.parseInt(item);  //Переформатирование входной строки в целое число (так везде в других методах)
        storage[size++] = numItem;  //По индексу 0 записываем item и после прибавляем: size=size+1
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateLength();
        validateItemAdd(item);
        validateIndexAdd(index);
        if (index == size) {
            Integer numItem = Integer.parseInt(item);
            storage[size++] = numItem;
            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
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
    public String remove(String item) {  //Не учёл ещё ошибку, если такого значения нет в Сете (учёл её в ветке hw-2.15 в StringListImpl)
        validateItemAdd(item);
        int index = indexOf(item);
        return remove(index);  //По-хорошему, нельзя перебрасывать решение другому методу (учёл и изменил эквивалентный метод в ветке hw-2.15 в StringListImpl)
    }

    @Override
    public String remove(int index) {
        validateIndexSetOrRemoveOrGet(index);
        Integer item = storage[index];
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);  //Удаление это значение item со смещением влево
        size--;
        return String.valueOf(item); //Переформатирование целого числа item в строку для вывода из метода
    }


    @Override
    public boolean contains(String item) { //В отличие от string-реализации в integer-реализации есть сортировка, чтобы обеспечить бинарный поиск этого item
        Integer numItem = Integer.parseInt(item); //Преобразование строки в число
        Integer[] copyArray = Arrays.copyOf(storage, size);  //ПСоздание нового массива через копирование старого и длиной массива size
        sort(copyArray);  //Статическая утилита от класса Arrays
        int count = Arrays.binarySearch(copyArray, numItem); //Бинарный поиск
        if (count >= 0 && count < size) { //Проверка вхождения результата-индекса в заданный предел
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(String item) {  //Снизу вверх
        Integer numItem = Integer.parseInt(item);
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(numItem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {  //Сверху вниз
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
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = String.valueOf(storage[i]);  //Интересное решение преобразования в целочисленном массиве целых чисел в строки от наставника Кости
        }
        return result;  //Выдача строкового массива
    }

    private void validateItemAdd(String item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    private void validateLength() {
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

    //Ряд сортировок для их проверки на скорость выполнения в классе Main:
    public static void sortInsertion(Integer[] arr) {  //Сортировка вставкой (см. шпаргалку 2.16)
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

    public static void sortSelection(Integer[] arr) {  //Сортировка выбором (см. шпаргалку 2.16)
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

    public static void collectionSort(Integer[] integers) {  //Самая быстрая сортировка из реализации Collections
        Integer[] generated2 = Arrays.copyOf(integers, integers.length);
        List<Integer> list = new ArrayList<>(List.of(generated2));
        Collections.sort(list);
        System.out.println("list.get(6800) = " + list.get(6800));  //Для ручного теста в майне
    }
}
