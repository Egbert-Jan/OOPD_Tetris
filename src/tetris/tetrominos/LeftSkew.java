package tetris.tetrominos;

public class LeftSkew extends Tetromino {
    public LeftSkew() {
        super();

        type = 5;


        int sX = startX;
        int sY = startY;

        points[0] = new Point(sX, sY);
        points[1] = new Point(sX+1, sY);//hier
        points[2] = new Point(sX+1, sY-1);
        points[3] = new Point(sX+2, sY-1);
    }

    @Override
    public Point[] pointsForRotation(int rotationNumber) {
        //Turn point
        Point tP = points[1];
        int x = tP.x;
        int y = tP.y;

        Point[] points = new Point[4];
        points[1] = tP;

        if(rotationNumber == 0 || rotationNumber == 2) {
            points[0] = new Point(x-1, y);
            points[2] = new Point(x, y-1);
            points[3] = new Point(x+1, y-1);
        } else {
            points[0] = new Point(x, y-1);
            points[2] = new Point(x+1, y);
            points[3] = new Point(x+1, y+1);
        }

        return points;
    }

}
