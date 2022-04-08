package gameMechTests;

import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования метода перемещения всех цифр на игровом поле вправо.
 */
public class MoveRightTest {

    /**
     * Тест перемещения цифр вправо на пустом поле. При создании класса игры создается пустое поле, поэтому берём его за
     * эталон.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testEmptyFieldMove() throws Exception {
        Game game = new Game();
        int[][] fieldBeforeMoves = game.getField();
        Method moveRight = Game.class.getDeclaredMethod("moveRight");
        moveRight.setAccessible(true);
        moveRight.invoke(game);
        assertArrayEquals(fieldBeforeMoves, game.getField());
    }

    /**
     * Тест перемещения цифр вправо на поле со строкой, заполненной цифрой 4.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testWithFullRowOfFours() throws Exception {
        int[][] startArray = new int[][]{
                {4, 4, 4, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        Game game = new Game();
        game.setField(startArray);
        Method moveRight = Game.class.getDeclaredMethod("moveRight");
        moveRight.setAccessible(true);
        moveRight.invoke(game);
        int[][] expectedArray = new int[][]{
                {0, 0, 8, 8},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

    /**
     * Тест перемещения цифр вправо на поле, где все цифры находятся в крайнем столбце.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testFromLeftToRight() throws Exception {
        int[][] startArray = new int[][]{
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {8, 0, 0, 0},
                {4, 0, 0, 0}
        };
        Game game = new Game();
        game.setField(startArray);
        Method moveRight = Game.class.getDeclaredMethod("moveRight");
        moveRight.setAccessible(true);
        moveRight.invoke(game);
        int[][] expectedArray = new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 2},
                {0, 0, 0, 8},
                {0, 0, 0, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

    /**
     * Тест перемещения цифр вправо на полностью заполненном поле.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testFullFieldMove() throws Exception {
        int[][] startArray = new int[][]{
                {2, 4, 2, 4},
                {8, 8, 4, 2},
                {16, 2, 4, 8},
                {8, 2, 2, 4}
        };
        Game game = new Game();
        game.setField(startArray);
        Method moveRight = Game.class.getDeclaredMethod("moveRight");
        moveRight.setAccessible(true);
        moveRight.invoke(game);
        int[][] expectedArray = new int[][]{
                {2, 4, 2, 4},
                {0, 16, 4, 2},
                {16, 2, 4, 8},
                {0, 8, 4, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

}
