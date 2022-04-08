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
    private int[][] field;

    /**
     * Количество очков
     */
    private int points;

    public Game() {
        this.field = new int[SIDE_SIZE][SIDE_SIZE];
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

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
