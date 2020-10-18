package tetris.tetrominos;

public class Square extends Tetromino {
    public Square() {
        super();

        type = 3;

        drawPoints[3] = new Point(startX, startY+1);
        drawPoints[0] = new Point(startX, startY);
        drawPoints[1] = new Point(startX+1, startY);
        drawPoints[2] = new Point(startX+1, startY+1);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {

    }
}
