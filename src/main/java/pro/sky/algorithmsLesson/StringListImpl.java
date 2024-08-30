package pro.sky.algorithmsLesson;

import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.Arrays;

//Подробные комментарии для каждого метода этого класса даны и в задании 2.16 (в 2.14 почти не даны)
public class StringListImpl implements StringList {
    private final String[] storage;
    private int size;  //Не путать с длиной массива. Это количество заполненных значений массива.

    public StringListImpl() {
        storage = new String[10];  //Инициализация пустого массива на 10 элементов
    }

    public StringListImpl(int initSize) {  //Использовал только этот параметризованный конструктор
        storage = new String[initSize];
    }

    @Override
    public String add(String item) {
        validateLength(); //if (size == storage.length) -> throw new StorageIsFullException();
        validateItemAdd(item); // if (item == null) -> throw new NullItemException();
        storage[size++] = item;  //По индексу 0 записываем item и после прибавляем: size=size+1
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateLength();
        validateItemAdd(item);
        validateIndexAdd(index); //if (index < 0 || index > size) -> throw new InvalidIndexException();
        if (index == size) { //Внесение в правый край массива (смещение не требуется)
            storage[size++] = item;
            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);  //Смещение вправо для внесения значения в середину
        size++;
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) { // Обновление с затиранием предыдущего значения, поэтому index должен быть меньше текущего size++
        validateIndexSetOrRemoveOrGet(index); // if (index < 0 || index >= size) -> throw new InvalidIndexException()
        validateItemAdd(item);
        storage[index] = item;
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
        String item = storage[index];
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);  //Удаление со смещением влево
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1; //Переход в следующий метод
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndexSetOrRemoveOrGet(index);
        return storage[index];
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
        return Arrays.copyOf(storage, size);
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

    private void validateIndexAdd(int index) { //Надо учитывать в понимании логики, что это size++.
        // Исключение возникнет, если выбрать параметром индекс на один больше, чем size++.
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
}
