package tetris.tetrominos;

public class Straight extends Tetromino {
    public Straight() {
        super();

        type = 0;

        int sX = startX;
        int sY = startY;

        Point[] up = {
            new Point(sX, sY),
            new Point(sX+1, sY),
            new Point(sX+2, sY),
            new Point(sX+3, sY)
        };

        drawPoints = up;
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {
        //Turn point
        Point tP = drawPoints[1];

        if(rotationNumber == 0 || rotationNumber == 2) {
            drawPoints[0] = new Point(tP.x-1, tP.y);
            drawPoints[1] = new Point(tP.x, tP.y);
            drawPoints[2] = new Point(tP.x+1, tP.y);
            drawPoints[3] = new Point(tP.x+2, tP.y);
        } else {
            drawPoints[0] = new Point(tP.x, tP.y-1);
            drawPoints[1] = new Point(tP.x, tP.y);
            drawPoints[2] = new Point(tP.x, tP.y+1);
            drawPoints[3] = new Point(tP.x, tP.y+2);
        }

    }
}
