package tetris.tetrominos;

public class RightSkew extends Tetromino {
    public RightSkew() {
        super();

        type = 6;

        int sX = startX;
        int sY = startY;

        points[0] = new Point(sX-1, sY);
        points[1] = new Point(sX, sY);
        points[2] = new Point(sX, sY+1);
        points[3] = new Point(sX+1, sY+1);
    }

    @Override
    public void rotate(int[][] map, int rotationNumber) {
        //Turn point
        Point tP = points[1];

        int x = tP.x;
        int y = tP.y;

        if(rotationNumber == 0 || rotationNumber == 2) {
            points[0] = new Point(x-1, y);
            points[2] = new Point(x, y+1);
            points[3] = new Point(x+1, y+1);
        } else {
            points[0] = new Point(x, y-1);
            points[2] = new Point(x-1, y);
            points[3] = new Point(x-1, y+1);
        }
    }
}
