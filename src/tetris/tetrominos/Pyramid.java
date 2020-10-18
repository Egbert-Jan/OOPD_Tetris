package tetris.tetrominos;

public class Pyramid extends Tetromino {
    public Pyramid() {
        super();

        type = 5;

        int sX = startX;
        int sY = startY;

        drawPoints[0] = new Point(sX, sY);
        drawPoints[1] = new Point(sX+1, sY);
        drawPoints[2] = new Point(sX+1, sY-1);
        drawPoints[3] = new Point(sX+2, sY);

    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {
        //Turn point
        Point tP = drawPoints[1];

        int x = tP.x;
        int y = tP.y;

        if(rotationNumber == 0) {
            drawPoints[0] = new Point(x-1, y);
            drawPoints[2] = new Point(x, y-1);
            drawPoints[3] = new Point(x+1, y);
        } else if (rotationNumber == 1){
            drawPoints[0] = new Point(x, y-1);
            drawPoints[2] = new Point(x+1, y);
            drawPoints[3] = new Point(x, y+1);
        } else if(rotationNumber == 2) {
            drawPoints[0] = new Point(x-1, y);
            drawPoints[2] = new Point(x, y+1);
            drawPoints[3] = new Point(x+1, y);
        } else {
            drawPoints[0] = new Point(x, y-1);
            drawPoints[2] = new Point(x-1, y);
            drawPoints[3] = new Point(x, y+1);
        }
    }
}
