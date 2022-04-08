import game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    /**
     * Тест инициализации класса игры.
     * Проверяем, что сам класс не null, игровое поле не null и количество очков == 0
     */
    @Test
    void initGameClassTest() {
        Game game = new Game();
        assertNotNull(game);
        assertNotNull(game.getField());
        assertEquals(0, game.getPoints());
    }

    /**
     * Тест печати правил игры перед её запуском.
     * Проверяем, что печатается в консоль.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void printRulesTest() throws Exception {
        final String RULES_STRING = "game.Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." +
                "Как только значение одной клетки достигнет 512 - вы победили. Если вы не сможете больше двигать цифры - " +
                "вы проиграли.\n";
        Method printRules = Game.class.getDeclaredMethod("printRules");
        printRules.setAccessible(true);
        printRules.invoke(new Game());
        assertEquals(outputStreamCaptor.toString(), RULES_STRING);
    }

    /**
     * Тест стартовой инициализации поля. При старте игры на поле в случайном месте генерируются две цифры 2.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void initGameFieldTest() throws Exception {
        Game game = new Game();
        Method initGameField = Game.class.getDeclaredMethod("initField");
        initGameField.setAccessible(true);
        initGameField.invoke(game);
        int notNullValuesCount = (int) Arrays.stream(game.getField())
                .flatMapToInt(Arrays::stream)
                .filter(x -> x != 0)
                .count();
        assertEquals(2, notNullValuesCount);
    }

    /**
     * Тест печати игрового поля в консоль.
     * Т.к. метод приватный, тестируем его через рефлексию.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void testPrintArrayMethod() throws Exception {
        String lineSeparator = System.getProperty("line.separator");
        final String PRINTED_ARRAY_STRING = lineSeparator + "4\t4\t8\t0\t" + lineSeparator +
                "4\t8\t0\t8\t" + lineSeparator +
                "4\t8\t8\t8\t" + lineSeparator +
                "4\t8\t8\t0\t" + lineSeparator;
        Game game = new Game();
        int[][] startArray = new int[][]{
                {4, 4, 8, 0},
                {4, 8, 0, 8},
                {4, 8, 8, 8},
                {4, 8, 8, 0}
        };
        game.setField(startArray);
        Method initGameField = Game.class.getDeclaredMethod("printField");
        initGameField.setAccessible(true);
        initGameField.invoke(game);
        assertEquals(PRINTED_ARRAY_STRING, outputStreamCaptor.toString());
    }

    /**
     * Тест выхода из игрового цикла. Для выхода в консоль вводится буква "q".
     * Проверяется сообщение, выводимое при выходе из игры.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void gameCycleQuitTest() throws Exception {
        String playerInput = "q";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(playerInput.getBytes());
        System.setIn(byteArrayInputStream);
        Game game = new Game();
        Method runCycle = Game.class.getDeclaredMethod("gameCycle");
        runCycle.setAccessible(true);
        runCycle.invoke(game);
        String[] lines = outputStreamCaptor.toString().split(System.lineSeparator());
        String result = lines[lines.length - 1];
        String expected = "Спасибо за игру. Ваши очки: 0";
        assertEquals(expected, result);
    }

    /**
     * Тест перемещения чисел на игровом поле вверх в игровом цикле.
     * Для выхода из игры дополнительно в консоль вводится буква "q".
     * Проверяется игровое поле и количество очков.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void gameCycleUpMoveTest() throws Exception {
        String playerInput = "w" + System.lineSeparator() + "q";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(playerInput.getBytes());
        System.setIn(byteArrayInputStream);
        int[][] startArray = new int[][]{
                {2, 4, 2, 4},
                {8, 8, 4, 2},
                {16, 2, 16, 8},
                {8, 2, 2, 4}
        };
        Game game = new Game();
        game.setField(startArray);
        Method runCycle = Game.class.getDeclaredMethod("gameCycle");
        runCycle.setAccessible(true);
        runCycle.invoke(game);
        int[][] expectedArray = new int[][]{
                {2, 4, 2, 4},
                {8, 8, 4, 2},
                {16, 4, 16, 8},
                {8, 2, 2, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
        assertEquals(4, game.getPoints());
    }

    /**
     * Тест перемещения чисел на игровом поле вниз в игровом цикле.
     * Для выхода из игры дополнительно в консоль вводится буква "q".
     * Проверяется игровое поле и количество очков.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void gameCycleDownMoveTest() throws Exception {
        String playerInput = "s" + System.lineSeparator() + "q";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(playerInput.getBytes());
        System.setIn(byteArrayInputStream);
        int[][] startArray = new int[][]{
                {2, 4, 2, 4},
                {8, 8, 4, 2},
                {16, 2, 16, 8},
                {8, 2, 2, 4}
        };
        Game game = new Game();
        game.setField(startArray);
        Method runCycle = Game.class.getDeclaredMethod("gameCycle");
        runCycle.setAccessible(true);
        runCycle.invoke(game);
        int[][] expectedArray = new int[][]{
                {2, 2, 2, 4},
                {8, 4, 4, 2},
                {16, 8, 16, 8},
                {8, 4, 2, 4}
        };
        assertArrayEquals(expectedArray, game.getField());
        assertEquals(4, game.getPoints());
    }

    /**
     * Тест проигрышной ситуации в игровом цикле.
     * Проигрышная ситуация достигается перемещением чисел на игровом поле влево.
     * Проверяется игровое поле, количество очков и выводимое в консоль сообщение.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void gameCycleLeftMoveWithLossTest() throws Exception {
        String playerInput = "a";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(playerInput.getBytes());
        System.setIn(byteArrayInputStream);
        int[][] startArray = new int[][]{
                {2, 4, 2, 4},
                {8, 8, 4, 2},
                {16, 2, 16, 8},
                {8, 2, 8, 4}
        };
        Game game = new Game();
        game.setField(startArray);
        Method runCycle = Game.class.getDeclaredMethod("gameCycle");
        runCycle.setAccessible(true);
        runCycle.invoke(game);
        int[][] expectedArray = new int[][]{
                {2, 4, 2, 4},
                {16, 4, 2, 0},
                {16, 2, 16, 8},
                {8, 2, 8, 4}
        };
        String[] lines = outputStreamCaptor.toString().split(System.lineSeparator());
        String result = lines[lines.length - 1];
        String expected = "Поражение. Ваши очки: 16";
        assertArrayEquals(expectedArray, game.getField());
        assertEquals(16, game.getPoints());
        assertEquals(expected, result);
    }

    /**
     * Тест выигрышной ситуации в игровом цикле.
     * Выигрышная ситуация достигается перемещением чисел на игровом поле вправо.
     * Проверяется игровое поле, количество очков и выводимое в консоль сообщение.
     *
     * @throws Exception - исключение рефлексии
     */
    @Test
    void gameCycleRightMoveWithWinTest() throws Exception {
        String playerInput = "d";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(playerInput.getBytes());
        System.setIn(byteArrayInputStream);
        int[][] startArray = new int[][]{
                {2, 4, 256, 256},
                {8, 8, 4, 2},
                {16, 2, 16, 8},
                {8, 2, 8, 4}
        };
        Game game = new Game();
        game.setField(startArray);
        Method runCycle = Game.class.getDeclaredMethod("gameCycle");
        runCycle.setAccessible(true);
        runCycle.invoke(game);
        int[][] expectedArray = new int[][]{
                {0, 2, 4, 512},
                {0, 16, 4, 2},
                {16, 2, 16, 8},
                {8, 2, 8, 4}
        };
        String[] lines = outputStreamCaptor.toString().split(System.lineSeparator());
        String result = lines[lines.length - 1];
        String expected = "Победа. Ваши очки: 528";
        assertArrayEquals(expectedArray, game.getField());
        assertEquals(528, game.getPoints());
        assertEquals(expected, result);
    }

}
