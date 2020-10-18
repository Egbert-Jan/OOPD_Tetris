package tetris.tetrominos;

public class RightSkew extends Tetromino {
    public RightSkew() {
        super();

        type = 6;

        drawPoints[3] = new Point(startX-1, startY-1);
        drawPoints[0] = new Point(startX, startY-1);
        drawPoints[1] = new Point(startX, startY);
        drawPoints[2] = new Point(startX+1, startY);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {

    }
}
