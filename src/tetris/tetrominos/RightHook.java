package tetris.tetrominos;

public class RightHook extends Tetromino {
    public RightHook() {
        super();

        type = 2;

        drawPoints[3] = new Point(startX-1, startY);
        drawPoints[0] = new Point(startX, startY);
        drawPoints[1] = new Point(startX+1, startY);
        drawPoints[2] = new Point(startX+1, startY-1);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {

    }
}
