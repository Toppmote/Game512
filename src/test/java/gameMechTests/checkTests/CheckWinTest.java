package gameMechTests.checkTests;

import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования проверки игрового поля на выигрыш
 */
public class CheckWinTest {

    /**
     * Тест метода проверки игрового поля с выигрышом на выигрыш.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void checkWinPosition() throws Exception {
        int[][] winArray = new int[][]{
                {512, 0, 8, 0},
                {4, 8, 0, 0},
                {4, 0, 8, 0},
                {4, 8, 0, 0}
        };
        Game game = new Game();
        game.setField(winArray);
        Method checkWin = Game.class.getDeclaredMethod("checkWin");
        checkWin.setAccessible(true);
        boolean methodResult = (boolean) checkWin.invoke(game);
        assertTrue(methodResult);
    }

    /**
     * Тест метода проверки игрового поля без выигрыша на выигрыш.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void checkPositionWithoutWin() throws Exception {
        int[][] arrayWithoutWin = new int[][]{
                {8, 2, 8, 0},
                {4, 8, 4, 0},
                {4, 2, 8, 0},
                {4, 8, 0, 0}
        };
        Game game = new Game();
        game.setField(arrayWithoutWin);
        Method checkWin = Game.class.getDeclaredMethod("checkWin");
        checkWin.setAccessible(true);
        boolean methodResult = (boolean) checkWin.invoke(game);
        assertFalse(methodResult);
    }

}
