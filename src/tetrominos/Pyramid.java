package tetrominos;

public class Pyramid extends Tetromino {
    public Pyramid() {
        super();

        type = 5;

        points[3] = new Point(startX-1, startY);
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX, startY-1);
        points[2] = new Point(startX+1, startY);
    }
}
