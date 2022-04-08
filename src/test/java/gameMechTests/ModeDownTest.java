package gameMechTests;

import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования метода перемещения всех цифр на игровом поле вниз.
 */
public class ModeDownTest {

    /**
     * Тест перемещения цифр вниз на пустом поле. При создании класса игры создается пустое поле, поэтому берём его за
     * эталон.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testEmptyFieldMove() throws Exception {
        Game game = new Game();
        int[][] fieldBeforeMoves = game.getField();
        Method moveDown = Game.class.getDeclaredMethod("moveDown");
        moveDown.setAccessible(true);
        moveDown.invoke(game);
        assertArrayEquals(fieldBeforeMoves, game.getField());
    }

    /**
     * Тест перемещения цифр вниз на поле со столбцом, заполненным цифрой 4.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testWithFullColumnOfFours() throws Exception {
        int[][] startArray = new int[][]{
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0}
        };
        Game game = new Game();
        game.setField(startArray);
        Method moveDown = Game.class.getDeclaredMethod("moveDown");
        moveDown.setAccessible(true);
        moveDown.invoke(game);
        int[][] expectedArray = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {8, 0, 0, 0},
                {8, 0, 0, 0}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

    /**
     * Тест перемещения цифр винз на поле, где все цифры находятся на верхней строке.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testFromUpToDownMove() throws Exception {
        int[][] startArray = new int[][]{
                {2, 4, 8, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        Game game = new Game();
        game.setField(startArray);
        Method moveDown = Game.class.getDeclaredMethod("moveDown");
        moveDown.setAccessible(true);
        moveDown.invoke(game);
        int[][] expectedArray = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 4, 8, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

    /**
     * Тест перемещения цифр вниз на полностью заполненном поле.
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
        Method moveDown = Game.class.getDeclaredMethod("moveDown");
        moveDown.setAccessible(true);
        moveDown.invoke(game);
        int[][] expectedArray = new int[][]{
                {2, 0, 0, 4},
                {8, 4, 2, 2},
                {16, 8, 8, 8},
                {8, 4, 2, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
    }

}
