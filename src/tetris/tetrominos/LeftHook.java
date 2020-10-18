package tetris.tetrominos;

public class LeftHook extends Tetromino {
    public LeftHook() {
        super();

        type = 1;

        drawPoints[3] = new Point(startX, startY-1);
        drawPoints[0] = new Point(startX, startY);
        drawPoints[1] = new Point(startX+1, startY);
        drawPoints[2] = new Point(startX+2, startY);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {

    }
}
