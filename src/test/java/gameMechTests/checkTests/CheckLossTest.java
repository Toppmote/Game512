package gameMechTests.checkTests;

import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class CheckLossTest {

    /**
     * Тест метода проверки игрового поля с проигрышом на проигрыш.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void checkLossPosition() throws Exception {
        int[][] lossArray = new int[][]{
                {2, 4, 8, 2},
                {4, 8, 2, 8},
                {8, 2, 8, 2},
                {4, 8, 2, 4}
        };
        Game game = new Game();
        game.setField(lossArray);
        Method checkLoss = Game.class.getDeclaredMethod("checkLoss");
        checkLoss.setAccessible(true);
        boolean methodResult = (boolean) checkLoss.invoke(game);
        assertTrue(methodResult);
    }

    /**
     * Тест метода проверки игрового поля без проигрыша на проигрыш.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void checkPositionWithoutLoss() throws Exception {
        int[][] arrayWithoutLoss = new int[][]{
                {8, 4, 8, 8},
                {4, 8, 4, 16},
                {8, 2, 8, 2},
                {4, 8, 2, 4}
        };
        Game game = new Game();
        game.setField(arrayWithoutLoss);
        Method checkLoss = Game.class.getDeclaredMethod("checkLoss");
        checkLoss.setAccessible(true);
        boolean methodResult = (boolean) checkLoss.invoke(game);
        assertFalse(methodResult);
    }

}
