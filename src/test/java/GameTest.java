import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

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
     * Т.к. метод приватный, запускаем его через рефлексию.
     */
    @Test
    void printRulesTest() throws Exception {
        final String RULES_STRING = "Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." +
                "Как только значение одной клетки достигнет 512 - вы победили. Если вы не сможете больше двигать цифры - " +
                "вы проиграли.\n";
        Method printRules = Game.class.getDeclaredMethod("printRules");
        printRules.setAccessible(true);
        printRules.invoke(new Game());
        assertEquals(outputStreamCaptor.toString(), RULES_STRING);
    }

}
