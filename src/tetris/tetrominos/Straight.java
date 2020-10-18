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

        points = up;
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {
        //Turn point
        Point tP = points[1];

        if(rotationNumber == 0 || rotationNumber == 2) {
            points[0] = new Point(tP.x-1, tP.y);
            points[1] = new Point(tP.x, tP.y);
            points[2] = new Point(tP.x+1, tP.y);
            points[3] = new Point(tP.x+2, tP.y);
        } else {
            points[0] = new Point(tP.x, tP.y-1);
            points[1] = new Point(tP.x, tP.y);
            points[2] = new Point(tP.x, tP.y+1);
            points[3] = new Point(tP.x, tP.y+2);
        }

    }
}
