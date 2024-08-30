package pro.sky.algorithmsLesson;

import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.Arrays;

public class StringListImpl implements StringList {
    private final String[] storage;
    private int size;

    public StringListImpl() {  //В ДЗ-2.15 и других ДЗ не пригодился
        storage = new String[10];
    }

    public StringListImpl(int initSize) {  //Использовал только этот конструктор (связь с тест-классом)
        storage = new String[initSize];
    }

    @Override
    public String add(String item) {
        validateLength();  //Внесение item за предел Сета
        validateItemString(item);  //Если item = null
        storage[size++] = item;  //Теперь можно ввести новый элемент (строку) и прибавить к size 1
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateLength();
        validateItemString(item); //Если item = null
        validateIndexAdd(index); //Если index невалидный
        if (index == size) {  //Если index равен новому правому значению
            storage[size++] = item;  //Добавляем справа в массив
            return item;
        }
        //Если index где-то в середине массива, то его надо смещать вправо, освобождая место для нового item'a:
        System.arraycopy(storage, index, storage, index + 1, size - index);
        size++;
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndexSetOrRemoveOrGet(index);  //Если index не содержит никаких значений Сета, т.е. равен size
        validateItemString(item); //Если item = null
        storage[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        validateItemString(item); //Если item = null
        int index = indexOf(item);
        validateIndexSetOrRemoveOrGet(index); //Если индекс равен -1 (такого значения item нет в Сете)
        return remove(index);
    }

    @Override
    public String remove(int index) {
        validateIndexSetOrRemoveOrGet(index); //Если индекс не в пределах Сета (такого значения по индексу нет в Сете)
        String item = storage[index];
        if (index != size) {  //Значит, индекс взят в пределах массива и надо смещать массив влево, удаляя это значение (это условие излишнее):
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);  //Удаление со смещением
        }
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {  //Итерация вверх
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = size - 1; i >= 0; i--) {  //Итерация вниз
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndexSetOrRemoveOrGet(index);  //Если индекс не валидный
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        validateItemList(otherList);  //Если объект Массив равен null
        return Arrays.equals(this.toArray(), otherList.toArray());  //Массивы равны, если до size-1 включительно их значения равны
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
    }  //Выдаётся копия массива storage[] с длиной length=size

    private void validateItemString(String item) {
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

    private void validateItemList(StringList item) {
        if (item == null) {
            throw new NullItemException();
        }
    }
}
