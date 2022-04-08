package game;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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
        final String RULES_STRING = "game.Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." + "Как только значение одной клетки достигнет 512 - вы победили. Если вы не сможете больше двигать цифры - " + "вы проиграли.\n";
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
            if (this.gameField[row][column] == 0) this.gameField[row][column] = 2;
            else i--;
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
            boolean isSlided = this.slideLeft(newRow);
            if (isSlided) {
                this.gameField[i] = newRow;
                isMoved = true;
            }
        }
        return isMoved;
    }

    /**
     * Функция сдвигания всех плиток вверх
     *
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean moveUp() {
        boolean isMoved = false;
        for (int i = 0; i < SIDE_SIZE; i++) {
            int[] newColumn = copyColumn(i);
            boolean isSlided = this.slideLeft(newColumn);
            if (isSlided) {
                this.insertColumn(newColumn, i);
                isMoved = true;
            }
        }
        return isMoved;
    }

    /**
     * Процедура сдвигания всех чисел влево для одномерного массива.
     *
     * @param arrayForSlide массив для сдвигания чисел в нем
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean slideLeft(int[] arrayForSlide) {
        boolean isSlided = false;
        boolean[] isCombined = new boolean[SIDE_SIZE];
        for (int j = 1; j < SIDE_SIZE; j++) {
            if (arrayForSlide[j] == 0) continue;
            while (j != 0) {
                if (arrayForSlide[j - 1] == 0) {
                    arrayForSlide[j - 1] = arrayForSlide[j];
                    arrayForSlide[j--] = 0;
                    isSlided = true;
                } else break;
            }
            if (j == 0) continue;
            if (arrayForSlide[j - 1] == arrayForSlide[j] && !isCombined[j - 1]) {
                int newValue = arrayForSlide[j] << 1;
                arrayForSlide[j - 1] = newValue;
                arrayForSlide[j] = 0;
                isCombined[j - 1] = true;
                isSlided = true;
                points += newValue;
            }
        }
        return isSlided;
    }

    /**
     * Функция копирования столбца игрового поля
     *
     * @param columnIndex индекс столбца для копирования
     * @return массив со скопированным столбцом
     */
    private int[] copyColumn(int columnIndex) {
        return IntStream.range(0, SIDE_SIZE)
                .map(currIndex -> this.gameField[currIndex][columnIndex])
                .toArray();
    }

    /**
     * Процедура вставки столбца в игровое поле
     *
     * @param insertableColumn вставляемый столбец
     * @param columnIndex      индекс для вставки столбца
     */
    private void insertColumn(int[] insertableColumn, int columnIndex) {
        for (int i = 0; i < SIDE_SIZE; i++)
            this.gameField[i][columnIndex] = insertableColumn[i];
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
