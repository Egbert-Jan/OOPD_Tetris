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

    /**
     * Checks if X and Y positions are equal to the point argument
     * @param p The point to check for if it has equal X and Y positions
     * @return A boolean value if the point has the same X and Y values
     */
    public boolean equals(Point p) {
        return p.x == this.x && p.y == this.y;
    }

    /**
     * A static method that returns a boolean if a point in a point array has the same X value
     * @param points The point array to search in
     * @param x The X value to search for
     * @return A boolean if a point in a point array has the same X value
     */
    public static boolean containsX(Point[] points, int x) {
        for (Point p : points) {
            if(p.x == x)
                return true;
        }

        return false;
    }


}