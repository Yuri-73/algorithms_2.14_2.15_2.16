package pro.sky.algorithmsLesson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringListImplTest {
    private StringListImpl stringList = new StringListImpl(10); //Будем использовать параметризованный конструктор

    @BeforeEach
    void setUp() {  //В каждом тест-методе заново будут в самом начале.
        // Очевидно, что этот метод с аннотацией @BeforeEach ещё и сбрасывает массив в налы после каждого теста.
        stringList.add("name1");
        stringList.add("name2");
        stringList.add("name3");
        stringList.add("name4");
        stringList.add("name5");
    }

    @AfterEach
    public void clearList() {  //Этот метод необязателен. Всё-равно size после каждого теста обнуляется автоматом.
        stringList.clear();
    }  //Сбрасывание size после каждого теста

    @Test
    void shouldAdd_WhenCorrectParam_ThenAdd() {
        int size = stringList.size();
        Assertions.assertEquals("name6", stringList.add("name6")); //Добаляем в массив шестой элемент "name6"
        Assertions.assertEquals(size + 1, stringList.size());  //Убеждаемся в прибавке нового элемента в методе add(item)
        Assertions.assertEquals("name6", stringList.toArray()[stringList.size() - 1]);
        System.out.println(stringList.toString());
    }

    @Test
    void shouldAdd_WhenNotCorrectParam_ThenNullItemException() {
        System.out.println(stringList.size());
        System.out.println(stringList.toString());
        Assertions.assertThrows(NullItemException.class, () -> stringList.add(null));
    }

    @Test
    void shouldAdd_WhenNotCorrectParam_ThenStorageIsFullException() {
        stringList.add("name6");
        stringList.add("name7");
        stringList.add("name8");
        System.out.println(stringList.toString());
        stringList.add("name9");
        System.out.println(stringList.toString());
        System.out.println(stringList.size());
        stringList.add("name10");
        System.out.println(stringList.toString());
        System.out.println(stringList.size());
        Assertions.assertThrows(StorageIsFullException.class, () -> stringList.add("name11"));  //У нас length=10, поэтому выход за пределы массива
    }
    @Test
    void shouldAdd_WhenLastCorrectParams_ThenAdd() {
        int size = stringList.size();
        Assertions.assertEquals("name6", stringList.add(stringList.size(),"name6"));  //Добавляем новый элемент в конец массива
        Assertions.assertEquals(size + 1, stringList.size());  //Убеждаемся в прибавке нового элемента
        Assertions.assertEquals("name6", stringList.toArray()[stringList.size() - 1]); //Добавленный элемент в самом конце списка
    }
    @Test
    void shouldAdd_WhenInsideCorrectParams_ThenAdd() {
        int size = stringList.size();
        System.out.println(stringList.toString());
        Assertions.assertEquals("name6", stringList.add(3, "name6")); //Добавляем name6 по индексу 3, предыдущее значение должно сместиться вправо
        Assertions.assertEquals(size + 1, stringList.size()); //Убеждаемся в прибавке нового элемента в массиве
        Assertions.assertEquals("name6", stringList.toArray()[3]); //Убеждаемся, что по третьему индексу записана новая строка и произошло смещение массива вправо
        Assertions.assertEquals("name4", stringList.toArray()[4]); //Убеждаемся, что name4 смещена вправо с индекса 3 на индекс 4
        System.out.println(stringList.toString());
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenStorageIsFullException() {
        stringList.add(5, "name6");
        stringList.add(6, "name7");
        stringList.add(7, "name8");
        stringList.add(8, "name9");
        stringList.add(9, "name10");
        Assertions.assertThrows(StorageIsFullException.class, () -> stringList.add(10, "name11"));  //У нас size++ = length=10, поэтому выход за пределы массива
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> stringList.add(1, null)); //Нулевая строка не пройдёт
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.add(-1, "name11")); //Отрицательный индекс выдаст ошибку, т.к. name11 отсутствует в массиве
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.add(stringList.size() + 1, "name11")); //Ошибка, если индекс выбран не текущий новый, а более
    }

    @Test
    void shouldSet_WhenCorrectParams_ThenTSet() {
        System.out.println(stringList.toString());
        Assertions.assertEquals("name12", stringList.set(2, "name12"));  //Произойдёт затирание по указанному индексу
        Assertions.assertEquals("name12", stringList.toArray()[2]);
        System.out.println(stringList.toString());
    }

    @Test
    void shouldSet_WhenNotCorrectParams_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.set(-1, "name11"));
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.set(stringList.size(), "name11")); //Выход за предел значений массива size
    }

    @Test
    void shouldSet_WhenNotCorrectParams_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> stringList.set(2, null));
    }

    @Test
    void shouldRemove_WhenLastCorrectItem_ThenRemove() {
        int size = stringList.size();
        System.out.println(stringList.toString());
        Assertions.assertEquals("name5", stringList.remove("name5"));
        Assertions.assertEquals(size - 1, stringList.size()); //size уменьшилось на 1
        Assertions.assertFalse(stringList.contains("name5"));  //Такого элемента уже нет
        System.out.println(stringList.toString()); //Убеждаемся в удалении элемента
    }

    @Test
    void shouldRemove_WhenInsideCorrectItem_ThenRemove() {
        int size = stringList.size();
        System.out.println(stringList.toString()); //В начале
        Assertions.assertEquals("name2", stringList.remove("name2"));
        Assertions.assertEquals(size - 1, stringList.size());
        Assertions.assertEquals("name3", stringList.toArray()[1]);  //Убеждаемся, что произошло смещение массива влево, где был ранее удалённый элемент
        Assertions.assertFalse(stringList.contains("name2"));  //Такого элемента нет
        System.out.println(stringList.toString()); //Убеждаемся в удалении элемента из середины
    }

    @Test
    void shouldRemove_WhenNotCorrectItem_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> stringList.remove(null));
    }

    @Test
    void shouldRemove_WhenLastCorrectIndex_ThenRemove() {
        int size = stringList.size();
        System.out.println(stringList.toString()); //Сначала
        Assertions.assertEquals("name5", stringList.remove(4));
        Assertions.assertEquals(size - 1, stringList.size());
        Assertions.assertFalse(stringList.contains("name5"));  //Такого элемента нет
        System.out.println(stringList.toString()); //Убеждаемся в удалении элемента справа
    }

    @Test
    void shouldRemove_WhenInsideCorrectIndex_ThenRemove() {
        int size = stringList.size();
        System.out.println(stringList.toString()); //Сначала
        Assertions.assertEquals("name2", stringList.remove(1));
        Assertions.assertEquals(size - 1, stringList.size());
        Assertions.assertEquals("name3", stringList.toArray()[1]);  //Убеждаемся, что произошло смещение массива влево, где был ранее удалённый элемент
        Assertions.assertFalse(stringList.contains("name2"));  //Такого элемента нет
        System.out.println(stringList.toString()); //Убеждаемся в удалении элемента
    }

    @Test
    void shouldRemove_WhenNotNotCorrectIndex_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.remove(stringList.size()));  //Убеждаемся, что штатно выводится ошибка при выходе индекса на size
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.remove(-1));
    }

    @Test
    void shouldСontains_WhenCorrectItem_ThenTrue() {
        Assertions.assertEquals(true, stringList.contains("name2")); //Такой элемент есть
        Assertions.assertFalse(stringList.contains("name8"));  //Такого элемента нет
    }

    @Test
    void shouldIndexOf_WhenCorrectItem_ThenIndex() {
        Assertions.assertEquals(2, stringList.indexOf("name3"));
    } //Такой элемент есть

    @Test
    void shouldIndexOf_WhenNotCorrectItem_ThenNegativeNumber() { //Такого элемента нет
        Assertions.assertEquals(-1, stringList.indexOf("name6"));
    }

    @Test
    void shouldLastIndexOf_WhenCorrectItem_ThenIndex() {
        Assertions.assertEquals(2, stringList.lastIndexOf("name3"));
    } //Такой элемент есть (поиск справа налево)

    @Test
    void shouldLastIndexOf_WhenNotCorrectItem_NegativeNumber() {
        Assertions.assertEquals(-1, stringList.lastIndexOf("name6")); //Такой элемент отсутствует (поиск справа налево)
    }

    @Test
    void shouldGet_WhenCorrectIndex_ThenItem() {
        Assertions.assertEquals("name3", stringList.get(2));
    }

    @Test
    void shouldGet_WhenNotCorrectIndex_ThenInvalidNegativeIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.get(-1));
    }

    @Test
    void shouldGet_WhenNotCorrectIndex_ThenInvalidLimitIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> stringList.get(stringList.size()));  //Выход индекса за предел значений массива size
    }

    @Test
    void shouldEquals_WhenCorrectTestList_ThenTrue() {
        StringList testList = new StringListImpl(11);
        testList.add("name1");
        testList.add("name2");
        testList.add("name3");
        testList.add("name4");
        testList.add("name5");

        assertTrue(stringList.equals(testList));  //Сравнение идёт не по предельному количеству элементов в массиве (они не равны), а по реальному заполнению, т.е. по size
    }

    @Test
    void shouldEquals_WhenNotCorrectTestList_ThenFalse() {
        Assertions.assertThrows(NullItemException.class, () -> stringList.equals(null)); //null в параметр-массив подавать нельзя
    }

    @Test
    void shouldSize() {
        Assertions.assertEquals(5, stringList.size()); //Изначально через @BiforeEach задано 5 значений массива
    }

    @Test
    void shouldNotIsEmpty() {
        assertTrue(!stringList.isEmpty()); //Не пустой массив: изначально через @BiforeEach задано 5 значений массива
        assertFalse(stringList.isEmpty());  //Не пустой массив: изначально через @BiforeEach задано 5 значений массива
    }

    @Test
    void shouldClear() {
        stringList.clear();
        Assertions.assertEquals(0, stringList.size());
    }

    @Test
    void shouldCopiToArray() {
        String[] testList = stringList.toArray(); //Копирование нашего массива в новый, длина его size!
        assertEquals("name1",testList[0]);
        assertEquals(5, testList.length);  //Длина нового массива высчитывается по size!
    }
}