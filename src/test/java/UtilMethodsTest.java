import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования служебных методов
 */
public class UtilMethodsTest {

    /**
     * Тест метода копирования столбца игрового поля.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testCopyColumnMethod() throws Exception {
        int[][] startArray = new int[][]{
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0}
        };
        Game game = new Game();
        game.setField(startArray);
        Method copyColumn = Game.class.getDeclaredMethod("copyColumn", int.class);
        copyColumn.setAccessible(true);
        int[] result = (int[]) copyColumn.invoke(game, 0);
        int[] expectedArray = new int[]{4, 4, 4, 4};
        assertArrayEquals(expectedArray, result);
    }

    /**
     * Тест метода вставки столбца в игровое поле.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testInsertColumnMethod() throws Exception {
        Game game = new Game();
        int[] insertableColumn = new int[]{8, 8, 8, 8};
        Method insertColumn = Game.class.getDeclaredMethod("insertColumn", int[].class, int.class);
        insertColumn.setAccessible(true);
        insertColumn.invoke(game, insertableColumn, 0);
        int[][] expectedArray = new int[][]{
                {8, 0, 0, 0},
                {8, 0, 0, 0},
                {8, 0, 0, 0},
                {8, 0, 0, 0}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

    /**
     * Тест метода перемещения чисел в пустом одномерном массиве влево.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testSlideLeftWithEmptyArray() throws Exception {
        Game game = new Game();
        int[] emptyArray = new int[]{0, 0, 0, 0};
        Method slideLeft = Game.class.getDeclaredMethod("slideLeft", int[].class);
        int[] expectedArray = new int[]{0, 0, 0, 0};
        slideLeft.setAccessible(true);
        boolean methodResult = (boolean) slideLeft.invoke(game, emptyArray);
        assertArrayEquals(emptyArray, expectedArray);
        assertFalse(methodResult);
    }

    /**
     * Тест метода перемещения чисел в одномерном массиве влево.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testSlideLeftWithSlide() throws Exception {
        Game game = new Game();
        int[] arrayForSlide = new int[]{4, 4, 4, 4};
        Method slideLeft = Game.class.getDeclaredMethod("slideLeft", int[].class);
        int[] expectedArray = new int[]{8, 8, 0, 0};
        slideLeft.setAccessible(true);
        boolean methodResult = (boolean) slideLeft.invoke(game, arrayForSlide);
        assertArrayEquals(arrayForSlide, expectedArray);
        assertTrue(methodResult);
    }

    /**
     * Тест метода перемещения чисел в пустом одномерном массиве вправо.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testSlideRightWithEmptyArray() throws Exception {
        Game game = new Game();
        int[] emptyArray = new int[]{0, 0, 0, 0};
        Method slideRight = Game.class.getDeclaredMethod("slideRight", int[].class);
        int[] expectedArray = new int[]{0, 0, 0, 0};
        slideRight.setAccessible(true);
        boolean methodResult = (boolean) slideRight.invoke(game, emptyArray);
        assertArrayEquals(emptyArray, expectedArray);
        assertFalse(methodResult);
    }

    /**
     * Тест метода перемещения чисел в одномерном массиве вправо.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testSlideRightWithSlide() throws Exception {
        Game game = new Game();
        int[] arrayForSlide = new int[]{4, 4, 4, 4};
        Method slideRight = Game.class.getDeclaredMethod("slideRight", int[].class);
        int[] expectedArray = new int[]{0, 0, 8, 8};
        slideRight.setAccessible(true);
        boolean methodResult = (boolean) slideRight.invoke(game, arrayForSlide);
        assertArrayEquals(arrayForSlide, expectedArray);
        assertTrue(methodResult);
    }

}
