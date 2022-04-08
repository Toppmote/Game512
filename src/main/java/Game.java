import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс игры
 */
public class Game {

    /**
     * Размер стороны поля
     */
    private final int SIDE_SIZE = 4;

    /**
     * игровое поле
     */
    private int[][] gameField;

    /**
     * Количество очков
     */
    private int points;

    public Game() {
        this.gameField = new int[SIDE_SIZE][SIDE_SIZE];
        this.points = 0;
    }

    /**
     * Процедура печати правил
     */
    private void printRules() {
        final String RULES_STRING = "Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." +
                "Как только значение одной клетки достигнет 512 - вы победили. Если вы не сможете больше двигать цифры - " +
                "вы проиграли.\n";
        System.out.print(RULES_STRING);
    }

    /**
     * Процедура генерации стартового поля. Генерируем две клетки со значением 2 в случайных местах игрового поля.
     */
    private void initField() {
        final int INIT_POSITION_COUNT = 2;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < INIT_POSITION_COUNT; i++) {
            int position = random.nextInt(SIDE_SIZE << 1);
            int row = position / SIDE_SIZE;
            int column = position % SIDE_SIZE;
            if (this.gameField[row][column] == 0)
                this.gameField[row][column] = 2;
            else
                i--;
        }
    }

    public int[][] getField() {
        return gameField;
    }

    public void setField(int[][] field) {
        this.gameField = field;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
