package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
     * Процедура запуска игры
     */
    public void startGame() {
        this.printRules();
        this.initField();
        this.gameCycle();
    }

    /**
     * Процедура запуска игрового цикла
     */
    private void gameCycle() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            this.printField();
            System.out.print("\nВаш ход: ");
            String input = scanner.next();
            if (input.length() != 1)
                continue;
            char ch = input.charAt(0);
            boolean isMoved = false;
            switch (ch) {
                case 'w':
                    isMoved = this.moveUp();
                    break;
                case 's':
                    isMoved = this.moveDown();
                    break;
                case 'a':
                    isMoved = this.moveLeft();
                    break;
                case 'd':
                    isMoved = this.moveRight();
                    break;
                case 'q':
                    System.out.println(System.lineSeparator() + "Спасибо за игру. Ваши очки: " + points);
                    isRunning = false;
                    break;
                default:
                    continue;
            }
            if (isMoved) {
                if (this.checkWin()) {
                    System.out.println(System.lineSeparator() + "Победа. Ваши очки: " + points);
                    break;
                }
                if (this.checkLoss()) {
                    System.out.println(System.lineSeparator() + "Поражение. Ваши очки: " + points);
                    break;
                }
                this.generateNewFieldNumber();
            }
        }
    }

    /**
     * Процедура печати правил
     */
    private void printRules() {
        final String RULES_STRING = "Game 512.\n" + "Двигайте цифры вверх вниз влево или право соединяя одинаковые цифры." + "Как только значение одной клетки достигнет 512 - вы победили. Если вы не сможете больше двигать цифры - " + "вы проиграли.\n";
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
     * Процедура сдвигания всех плиток вправо
     *
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean moveRight() {
        boolean isMoved = false;
        for (int i = 0; i < SIDE_SIZE; i++) {
            int[] newRow = Arrays.copyOf(this.gameField[i], SIDE_SIZE);
            boolean isSlided = this.slideRight(newRow);
            if (isSlided) {
                this.gameField[i] = newRow;
                isMoved = true;
            }
        }
        return isMoved;
    }

    /**
     * Процедура сдвигания всех плиток вниз
     *
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean moveDown() {
        boolean isMoved = false;
        for (int i = 0; i < SIDE_SIZE; i++) {
            int[] newColumn = copyColumn(i);
            boolean isSlided = this.slideRight(newColumn);
            if (isSlided) {
                this.insertColumn(newColumn, i);
                isMoved = true;
            }
        }
        return isMoved;
    }

    /**
     * Процедура сдвигания всех чисел вправо для одномерного массива.
     *
     * @param arrayForSlide массив для сдвигания чисел в нем
     * @return true - если был сделан сдвиг, false - если сдвига не было
     */
    private boolean slideRight(int[] arrayForSlide) {
        boolean isSlided = false;
        boolean[] isCombined = new boolean[SIDE_SIZE];
        for (int j = SIDE_SIZE - 2; j >= 0; j--) {
            if (arrayForSlide[j] == 0)
                continue;
            while (j != SIDE_SIZE - 1) {
                if (arrayForSlide[j + 1] == 0) {
                    arrayForSlide[j + 1] = arrayForSlide[j];
                    arrayForSlide[j++] = 0;

                    isSlided = true;
                } else
                    break;
            }
            if (j == SIDE_SIZE - 1)
                continue;
            if (arrayForSlide[j + 1] == arrayForSlide[j] && !isCombined[j + 1]) {
                int newValue = arrayForSlide[j] << 1;
                arrayForSlide[j + 1] = newValue;
                arrayForSlide[j] = 0;
                isCombined[j + 1] = true;
                isSlided = true;
                points += newValue;
            }
        }
        return isSlided;
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

    /**
     * Функция проверки игры на выигрыш. Проверяем, имеет ли игровое поле ячейку со значением 512
     *
     * @return true - если выигрыш, false - если игра продолжается
     */
    private boolean checkWin() {
        return Arrays.stream(gameField)
                .flatMapToInt(Arrays::stream)
                .max().orElse(0) == 512;
    }

    /**
     * Функция проверки игры на проигрыш
     *
     * @return true - если проигрыш, false - если игра продолжается
     */
    private boolean checkLoss() {
        int minValue = Arrays.stream(gameField)
                .flatMapToInt(Arrays::stream)
                .max().orElse(0);
        if (minValue != 0) {
            for (int[] row : this.gameField) {
                for (int i = 1; i < SIDE_SIZE - 1; i++)
                    if (row[i] == row[i - 1] || row[i] == row[i + 1])
                        return false;
            }
            for (int i = 0; i < SIDE_SIZE; i++)
                for (int j = 1; j < SIDE_SIZE - 1; j++)
                    if (this.gameField[i][j] == this.gameField[i][j - 1] || this.gameField[i][j] == this.gameField[i][j + 1])
                        return false;
            return true;
        } else
            return false;
    }

    /**
     * Процедура генерации нового числа на игровом поле
     */
    private void generateNewFieldNumber() {
        List<int[]> zeroValueIndexes = new ArrayList<>();
        for (int i = 0; i < SIDE_SIZE; i++)
            for (int j = 0; j < SIDE_SIZE; j++)
                if (gameField[i][j] == 0)
                    zeroValueIndexes.add(new int[]{i, j});
        int cellForInsert = ThreadLocalRandom.current().nextInt(zeroValueIndexes.size());
        int[] indexes = zeroValueIndexes.get(cellForInsert);
        this.gameField[indexes[0]][indexes[1]] = 2;
    }

    /**
     * Процедура печати игрового поля
     */
    private void printField() {
        System.out.println();
        for (int[] row : gameField) {
            for (int numbers : row) {
                System.out.print(numbers + "\t");
            }
            System.out.println();
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
