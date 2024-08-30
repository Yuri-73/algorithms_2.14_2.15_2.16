package pro.sky.algorithmsLesson;

import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.Arrays;

public class StringListImpl implements StringList {
    private  String[] storage;
    private int size; //Фактическое заполнение (не путать с длиной массива)

    public StringListImpl() {
        storage = new String[10];  //Инициализация пустого массива на 10 элементов
    }

    public StringListImpl(int initSize) {  //Использовал только этот параметризованный конструктор, где initSize это длина массива
        storage = new String[initSize];
    }

    @Override
    public String toString() {
        return "StringListImpl{" +
                "storage=" + Arrays.toString(storage) +
                '}';
    }

    @Override
    public String add(String item) {
        validateItemAdd(item); //if (item == null) -> throw new NullItemException();
        validateLength();  //При выходе за предел массива: if (size == storage.length) -> throw new StorageIsFullException();
        storage[size++] = item;  //По индексу 0 записываем item и после прибавляем: size=size+1, пока не достигнем предела массива

        return item;
    }

    @Override
    public String add(int index, String item) {
        validateItemAdd(item); //if (item == null) -> throw new NullItemException();
        validateIndexAdd(index); // if (index < 0 || index > size) -> throw new InvalidIndexException()
        // (здесь size соответствует в массиве месту ожидания для внесения следующего значения и через него перескакивать нельзя)
        validateLength();  //При выходе за предел массива: if (size == storage.length) -> throw new StorageIsFullException();
        if (index == size) { //Если индекс равен крайнему значению фактического заполнения, то просто вносим новое значение без смещения вправо

            storage[size++] = item;

            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index); //Смещение вправо, потому что добавили значение в середину массива
        size++;
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndexSetOrRemoveOrGet(index); //if (index < 0 || index >= size) -> throw new InvalidIndexException()
        // index=size нельзя выбирать, т.к. это место для нового значения массива, а set() лишь изменяет имеющееся значение в массиве
        validateItemAdd(item);  //if (item == null) -> throw new NullItemException();
        storage[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        validateItemAdd(item); //if (item == null) -> throw new NullItemException();
        int index = indexOf(item);
        validateIndexSetOrRemoveOrGet(index); //if (index < 0 || index >= size) -> throw new InvalidIndexException() (т.е., если индекс не найден, а значит, равен -1)
        return remove(index);
    }

    @Override
    public String remove(int index) {
        validateIndexSetOrRemoveOrGet(index); //if (index < 0 || index >= size) -> throw new InvalidIndexException()
        String item = storage[index];
        System.arraycopy(storage, index + 1, storage, index, storage.length - 1 - index);  //Удаление со смещением влево
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) { //Если принимаемый аргумент содержится в массиве -> true, отсутствует -> false
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
    public String get(int index) {  //Получение значения массива по индексу
        validateIndexSetOrRemoveOrGet(index);  //if (index < 0 || index >= size) -> throw new InvalidIndexException()
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) { //Проверка массивов на равенство
        validateItemArrays(otherList); //if (item == null) -> throw new NullItemException();
        return Arrays.equals(this.toArray(), otherList.toArray()); //Текущий объект и входной объект приведены к массивам с длиной, равной size!
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
