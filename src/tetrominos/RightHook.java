package tetrominos;

public class RightHook extends Tetromino {
    public RightHook() {
        super();

        type = 2;

        points[3] = new Point(startX-1, startY);
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX+1, startY);
        points[2] = new Point(startX+1, startY-1);
    }
}
