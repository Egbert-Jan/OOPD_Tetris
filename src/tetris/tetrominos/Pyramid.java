package tetris.tetrominos;

public class Pyramid extends Tetromino {
    public Pyramid() {
        super();

        type = 6;

        int sX = startX;
        int sY = startY;

        points[0] = new Point(sX, sY);
        points[1] = new Point(sX+1, sY);
        points[2] = new Point(sX+1, sY-1);
        points[3] = new Point(sX+2, sY);

    }

    @Override
    public Point[] rotate(int rotationNumber) {
        //Turn point
        Point turnPoint = points[1];
        int x = turnPoint.x;
        int y = turnPoint.y;

        Point[] points = new Point[4];
        points[1] = turnPoint;

        if(rotationNumber == 0) {
            points[0] = new Point(x-1, y);
            points[2] = new Point(x, y-1);
            points[3] = new Point(x+1, y);
        } else if (rotationNumber == 1){
            points[0] = new Point(x, y-1);
            points[2] = new Point(x+1, y);
            points[3] = new Point(x, y+1);
        } else if(rotationNumber == 2) {
            points[0] = new Point(x-1, y);
            points[2] = new Point(x, y+1);
            points[3] = new Point(x+1, y);
        } else {
            points[0] = new Point(x, y-1);
            points[2] = new Point(x-1, y);
            points[3] = new Point(x, y+1);
        }

        return points;
    }
}
