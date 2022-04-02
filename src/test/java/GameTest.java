import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

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

}
