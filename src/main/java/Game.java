/**
 * Класс игры
 */
public class Game {

    /**
     * Размер стороны поля
     */
    private final int SIDE_SIZE = 4;

    /**
     * Игровое поле
     */
    private short[][] field;

    /**
     * Количество очков
     */
    private int points;

    public Game() {
        this.field = new short[SIDE_SIZE][SIDE_SIZE];
        this.points = 0;
    }

    public short[][] getField() {
        return field;
    }

    public void setField(short[][] field) {
        this.field = field;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
