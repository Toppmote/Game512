package game;

import java.util.Arrays;
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
        final String RULES_STRING = "game.Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." +
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

    /**
     * Функция сдвигания всех плиток влево
     *
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean moveLeft() {
        boolean isMoved = false;
        for (int i = 0; i < SIDE_SIZE; i++) {
            int[] newRow = Arrays.copyOf(this.gameField[i], SIDE_SIZE);
            boolean isSlided = false;
            boolean[] isCombined = new boolean[SIDE_SIZE];
            for (int j = 1; j < SIDE_SIZE; j++) {
                if (newRow[j] == 0)
                    continue;
                while (j != 0) {
                    if (newRow[j - 1] == 0) {
                        newRow[j - 1] = newRow[j];
                        newRow[j--] = 0;
                        isSlided = true;
                    } else
                        break;
                }
                if (j == 0)
                    continue;
                if (newRow[j - 1] == newRow[j] && !isCombined[j - 1]) {
                    int newValue = newRow[j] << 1;
                    newRow[j - 1] = newValue;
                    newRow[j] = 0;
                    isCombined[j - 1] = true;
                    isSlided = true;
                    points += newValue;
                }
            }
            if (isSlided) {
                this.gameField[i] = newRow;
                isMoved = true;
            }
        }
        return isMoved;
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