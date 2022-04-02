/**
 * Класс игры
 */
public class Game {

    /**
     * Игровое поле
     */
    private short[][] field;

    /**
     * Количество очков
     */
    private int points;

    public Game() {
        this.field = new short[3][3];
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
