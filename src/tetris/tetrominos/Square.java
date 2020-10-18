package tetris.tetrominos;

public class Square extends Tetromino {
    public Square() {
        super();

        type = 3;

        points[3] = new Point(startX, startY+1);
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX+1, startY);
        points[2] = new Point(startX+1, startY+1);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) { }
}
