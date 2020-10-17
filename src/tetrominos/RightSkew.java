package tetrominos;

public class RightSkew extends Tetromino {
    public RightSkew() {
        super();

        type = 6;

        points[3] = new Point(startX-1, startY-1);
        points[0] = new Point(startX, startY-1);
        points[1] = new Point(startX, startY);
        points[2] = new Point(startX+1, startY);
    }
}
