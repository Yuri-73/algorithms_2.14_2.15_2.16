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
    void setUp() {  //В каждом тест-методе будут в самом начале
        integerList.add("3");
        integerList.add("2");
        integerList.add("1");
        integerList.add("5");
        integerList.add("4");
    }

    @AfterEach
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
    void shouldAdd_WhenSizeEqualsLength_ThenGrow() {  //ДЗ-2.16 Расширение length на 50%
        int size = integerList.size();
        integerList.add("6");
        integerList.add("7");
        integerList.add("8");
        integerList.add("9");
        integerList.add("10");
        integerList.add("11"); //Здесь size становится равной length массива storoge[10]

        Assertions.assertEquals("11", integerList.toArray()[integerList.size() - 1]);  //Убеждаемся в прибавке нового элемента в методе add(item)
        Assertions.assertEquals(integerList.size() + integerList.size()/2 - 1, integerList.toArray().length);  //Убеждаемся в прибавке нового элемента в методе add(item)
    }

    @Test
    void shouldAdd_WhenLastCorrectParams_ThenAdd() {
        int size = integerList.size();
        Assertions.assertEquals("6", integerList.add(integerList.size(),"6"));  //Добавляем новый элемент в коней массива
        Assertions.assertEquals(size + 1, integerList.size());  //Убеждаемся в прибавке нового элемента
        Assertions.assertEquals("6", integerList.toArray()[integerList.size() - 1]); //Добавленный элемент в самом конце списка
    }

    @Test
    void shouldAdd_WhenInsideCorrectParams_ThenAdd() {
        int size = integerList.size();
        Assertions.assertEquals("6", integerList.add(3, "6"));
        Assertions.assertEquals(size + 1, integerList.size()); //Убеждаемся в прибавке нового элемента в массиве
        Assertions.assertEquals("6", integerList.toArray()[3]); //Убеждаемся, что по третьему индексу записана новая строка и произошло смещение массива вправо
        Assertions.assertEquals("5", integerList.toArray()[4]); //Убеждаемся, что name4 смещена вправо с индекса 3 на индекс 4
    }

    @Test
    void shouldAdd_WhenWith2ParamsSizeEqualsLength_ThenGrow() {  //ДЗ-2.16 Расширение length на 50%
        int size = integerList.size();
        integerList.add(5,"6");
        integerList.add(6,"7");
        integerList.add(7,"8");
        integerList.add(8,"9");
        integerList.add(9,"10");
        integerList.add(10,"11"); //Здесь size становится равной length массива storoge[10]

        Assertions.assertEquals(integerList.size() + integerList.size()/2 - 1, integerList.toArray().length);  //Убеждаемся в прибавке нового элемента в методе add(item)
        Assertions.assertEquals("11", integerList.toArray()[integerList.size() - 1]);  //Убеждаемся в прибавке нового элемента в методе add(item)
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
        Assertions.assertThrows(InvalidIndexException.class, () -> integerList.set(integerList.size(), "11")); //Выход за предел значений массива size
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
        //Для ДЗ-2.16 этот тест поменял, т.к. исходный метод equals() из-за корректировки метода toArray() (тот стал работать не по size, а по length. В ДЗ-2.15 остался по size)
        integerList.add("6");
        integerList.add("7");
        integerList.add("8");
        integerList.add("9");
        integerList.add("10");

        StringList testList = new StringListImpl(10);
        testList.add("3");
        testList.add("2");
        testList.add("1");
        testList.add("5");
        testList.add("4");
        testList.add("6");
        testList.add("7");
        testList.add("8");
        testList.add("9");
        testList.add("10");
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList.toArray()[i], integerList.toArray()[i]);
        }
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
        assertTrue(!integerList.isEmpty());
        assertFalse(integerList.isEmpty());
    }

    @Test
    void shouldClear() {
        integerList.clear();
        Assertions.assertEquals(0, integerList.size());
    }

    @Test
    void shouldCopiToArray() {
        String[] testList = integerList.toArray();  //Создание копии массива storoge[]
        assertEquals("1",testList[2]);
        assertEquals(5, integerList.size());  //Количество действующих элементов нового массива равно size
        assertEquals(10, testList.length);  //Длина нового массива равна длине storoge[]
    }

    @Test
    void shouldSortSelection_WhenWhenUnsortedArray_ThenSortedArrays() {  //Для ДЗ-2.15
        Integer[] testList = {3, 2, 1, 4, 5, 7, 6, 8, 9, 10};
        Integer[] testList2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntegerListImpl.sortSelection(testList);
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList[i], testList2[i]);
        }
    }

    @Test
    void shouldSortInsert_WhenUnsortedArray_ThenSortedArrays() {  //Для ДЗ-2.15
        Integer[] testList = {3, 2, 1, 4, 5, 7, 6, 8, 9, 10};
        Integer[] testList2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntegerListImpl.sortInsertion(testList);
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList[i], testList2[i]);
        }
    }
    //Блок специально вставлен для ДЗ-2.16:
    @Test
    void shouldSortFastRecursion_WhenUnsortedArray_ThenSortedArrays() {
        Integer[] testList = {3, 2, 1, 4, 5, 7, 6, 8, 9, 10};
        Integer[] testList2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntegerListImpl.sortFastRecursion(testList);
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testList[i], testList2[i]);
        }
    }
}