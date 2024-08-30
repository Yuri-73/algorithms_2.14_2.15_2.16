package pro.sky.algorithmsLesson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.algorithmsLesson.exceptions.InvalidIndexException;
import pro.sky.algorithmsLesson.exceptions.NullItemException;
import pro.sky.algorithmsLesson.exceptions.StorageIsFullException;

import static org.junit.jupiter.api.Assertions.*;

class IntegerListImplTest {

    private StringList integerList = new IntegerListImpl(10); //Будем использовать параметризованный конструктор

    @BeforeEach
    void setUp() {  //В каждом тест-методе будут в самом начале, а манипуляции в тестах стираются,
        // не смотря даже на наличие @AfterEach и обнулении в нём size (можно его вообще убрать!)
        integerList.add("3");
        integerList.add("2");
        integerList.add("1");
        integerList.add("5");
        integerList.add("4");
    }

    @AfterEach //Не обязательно
    public void clearList() {  //В конце каждого тест-метода будет удаление всех элементов массива
        integerList.clear();
    }

    @Test
    void shouldAdd_WhenCorrectParam_ThenAdd() {
        int size = integerList.size();
        Assertions.assertEquals("6", integerList.add("6")); //Добаляем в массив элемент "6"
        Assertions.assertEquals(size + 1, integerList.size());  //Убеждаемся в прибавке нового элемента в методе add(item)
        Assertions.assertEquals("6", integerList.toArray()[integerList.size() - 1]);
    }

    @Test
    void shouldAdd_WhenNotCorrectParam_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> integerList.add(null));
    }

    @Test
    void shouldAdd_WhenNotCorrectParam_ThenStorageIsFullException() {
        integerList.add("6");
        integerList.add("7");
        integerList.add("8");
        integerList.add("9");
        integerList.add("10");
        Assertions.assertThrows(StorageIsFullException.class, () -> integerList.add("11"));  //У нас length=10, в этом методе быстрее сработает исключение из-за size++ = length
    }

    @Test
    void shouldAdd_WhenLastCorrectParams_ThenAdd() {
        int size = integerList.size();
        Assertions.assertEquals("6", integerList.add(integerList.size(),"6"));  //Добавляем новый элемент в конец массива
        Assertions.assertEquals(size + 1, integerList.size());  //Убеждаемся в прибавке нового элемента
        Assertions.assertEquals("6", integerList.toArray()[integerList.size() - 1]); //Добавленный элемент в самом конце списка
    }

    @Test
    void shouldAdd_WhenInsideCorrectParams_ThenAdd() { //Проверка внесения параметра в массив в его середину (перегруженный метод  - с 2 параметрами)
        int size = integerList.size();
        Assertions.assertEquals("6", integerList.add(3, "6"));
        Assertions.assertEquals(size + 1, integerList.size()); //Убеждаемся в прибавке нового элемента в массиве
        Assertions.assertEquals("6", integerList.toArray()[3]); //Убеждаемся, что по третьему индексу записана новая строка и произошло смещение массива вправо
        Assertions.assertEquals("5", integerList.toArray()[4]); //Убеждаемся, что name4 смещена вправо с индекса 3 на индекс 4
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenStorageIsFullException() { //Проверка выхода на предел массива
        integerList.add(5, "6");
        integerList.add(6, "7");
        integerList.add(7, "8");
        integerList.add(8, "9");
        integerList.add(9, "10");
        Assertions.assertThrows(StorageIsFullException.class, () -> integerList.add(10, "11"));  //У нас length=10, поэтому выход за пределы массива
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> integerList.add(1, null)); //Нулевая строка не пройдёт
    }

    @Test
    void shouldAdd_WhenNotCorrectParams_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.add(-1, "11")); //Отрицательный индекс выдаст ошибку
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.add(integerList.size() + 1, "11")); //Ошибка, если индекс выбран не текущий новый, а более
    }


    @Test
    void shouldSet_WhenCorrectParams_ThenTSet() {
        Assertions.assertEquals("12", integerList.set(2, "12"));  //Произойдёт затирование по указанному индексу
        Assertions.assertEquals("12", integerList.toArray()[2]);
    }

    @Test
    void shouldSet_WhenNotCorrectParams_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.set(-1, "11"));
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.set(integerList.size(), "11")); //Выход за предел значений массива size (обновлять нечего)
    }

    @Test
    void shouldSet_WhenNotCorrectParams_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> integerList.set(2, null));
    }

    @Test
    void shouldRemove_WhenLastCorrectItem_ThenRemove() {
        int size = integerList.size();
        Assertions.assertEquals("5", integerList.remove("5"));
        Assertions.assertEquals(size - 1, integerList.size());
        Assertions.assertFalse(integerList.contains("5"));  //Такого элемента нет
    }

    @Test
    void shouldRemove_WhenInsideCorrectItem_ThenRemove() {
        int size = integerList.size();
        Assertions.assertEquals("2", integerList.remove("2"));
        Assertions.assertEquals(size - 1, integerList.size());
        Assertions.assertEquals("1", integerList.toArray()[1]);  //Убеждаемся, что произошло смещение массива влево, где был ранее удалённый элемент
        Assertions.assertFalse(integerList.contains("2"));  //Такого элемента нет
    }

    @Test
    void shouldRemove_WhenNotCorrectItem_ThenNullItemException() {
        Assertions.assertThrows(NullItemException.class, () -> integerList.remove(null));
    }

    @Test
    void shouldRemove_WhenLastCorrectIndex_ThenRemove() {
        int size = integerList.size();
        Assertions.assertEquals("4", integerList.remove(4));
        Assertions.assertEquals(size - 1, integerList.size());
        Assertions.assertFalse(integerList.contains("4"));  //Такого элемента нет
    }

    @Test
    void shouldRemove_WhenInsideCorrectIndex_ThenRemove() {
        int size = integerList.size();
        Assertions.assertEquals("2", integerList.remove(1));
        Assertions.assertEquals(size - 1, integerList.size());
        Assertions.assertEquals("1", integerList.toArray()[1]);  //Убеждаемся, что произошло смещение массива влево, где был ранее удалённый элемент
        Assertions.assertFalse(integerList.contains("2"));  //Такого элемента нет
    }

    @Test
    void shouldRemove_WhenNotNotCorrectIndex_ThenInvalidIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.remove(integerList.size()));  //Убеждаемся, что штатно выводится ошибка при выходе индекса на size
    }

    @Test
    void shouldСontains_WhenCorrectItem_ThenTrue() {
        Assertions.assertEquals(true, integerList.contains("2"));
        Assertions.assertEquals(false, integerList.contains("8")); //Такого элемента нет
        Assertions.assertFalse(integerList.contains("8"));  //Такого элемента нет
    }

    @Test
    void shouldIndexOf_WhenCorrectItem_ThenIndex() {
        Assertions.assertEquals(0, integerList.indexOf("3"));
    }

    @Test
    void shouldIndexOf_WhenNotCorrectItem_ThenNegativeNumber() {
        Assertions.assertEquals(-1, integerList.indexOf("6"));
    }

    @Test
    void shouldLastIndexOf_WhenCorrectItem_ThenIndex() {
        Assertions.assertEquals(0, integerList.lastIndexOf("3"));
    }

    @Test
    void shouldLastIndexOf_WhenNotCorrectItem_NegativeNumber() {
        Assertions.assertEquals(-1, integerList.lastIndexOf("6"));
    }

    @Test
    void shouldGet_WhenCorrectIndex_ThenItem() {
        Assertions.assertEquals("1", integerList.get(2));
    }

    @Test
    void shouldGet_WhenNotCorrectIndex_ThenInvalidNegativeIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.get(-1));
    }

    @Test
    void shouldGet_WhenNotCorrectIndex_ThenInvalidLimitIndexException() {
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.get(integerList.size()));  //Выход индекса за предел значений массива size
    }

    @Test
    void shouldEquals_WhenCorrectTestList_ThenTrue() {
        StringList testList = new StringListImpl(11);
        testList.add("3");
        testList.add("2");
        testList.add("1");
        testList.add("5");
        testList.add("4");

        assertTrue(integerList.equals(testList));  //Сравнение идёт не по предельному количеству элементов в массиве (они не равны), а по реальному заполнению, т.е. по size
    }

    @Test
    void shouldEquals_WhenNotCorrectTestList_ThenFalse() {
        Assertions.assertThrows(NullItemException.class, () -> integerList.equals(null)); //null в параметр-массив подавать нельзя
    }

    @Test
    void shouldSize() {
        Assertions.assertEquals(5, integerList.size()); //Изначально через @BiforeEach задано 5 значений массива
    }

    @Test
    void shouldNotIsEmpty() {
        assertTrue(!integerList.isEmpty()); //Начальный массив имеет 5 значений
        assertFalse(integerList.isEmpty()); //Начальный массив имеет 5 значений
    }

    @Test
    void shouldClear() {
        integerList.clear();
        Assertions.assertEquals(0, integerList.size()); // Обнуление
    }

    @Test
    void shouldCopiToArray() {
        String[] testList = integerList.toArray();
        assertEquals("1",testList[2]);
        assertEquals(5, testList.length);  //Длина нового массива высчитывается по size!
    }

    @Test
    void shouldSortSelection_WhenCorrectParams_ThenSortedArrays() {
        Integer[] testList = {3, 2, 1, 4, 5, 7, 6, 8, 9, 10};
        Integer[] testList2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntegerListImpl.sortSelection(testList);
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList[i], testList2[i]);
        }
    }

    @Test
    void shouldSortInsert_WhenCorrectParams_ThenSortedArrays() {
        Integer[] testList = {3, 2, 1, 4, 5, 7, 6, 8, 9, 10};
        Integer[] testList2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntegerListImpl.sortInsertion(testList);
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList[i], testList2[i]);
        }
    }
}