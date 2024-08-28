package pro.sky.algorithmsLesson;

import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.Arrays;

public class StringListImpl implements StringList {
    private final String[] storage;
    private int size;

    public StringListImpl() {
        storage = new String[10];  //Инициализация пустого массива на 10 элементов
    }

    public StringListImpl(int initSize) {  //Использовал только этот параметризованный конструктор
        storage = new String[initSize];
    }

    @Override
    public String add(String item) {
        validateLength();
        validateItemAdd(item);
        storage[size++] = item;  //По индексу 0 записываем item и после прибавляем: size=size+1
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateLength();
        validateItemAdd(item);
        validateIndexAdd(index);
        if (index == size) {
            storage[size++] = item;
            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        size++;
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndexSetOrRemoveOrGet(index);
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
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);  //Удаление со смещением
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
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
}
