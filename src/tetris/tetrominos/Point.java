package tetris.tetrominos;


/**
 * A point representing a location on the Tetromino map
 */
public class Point {
    public int x, y = 0;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point p) {
        return p.x == this.x && p.y == this.y;
    }
}