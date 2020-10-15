package shapes;

public class Shape {
    public int x, y = 0;

    public Point[] points = new Point[4];

    //Green Tile
    public int type = 0;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOnPosition(int x, int y) {
        for (Point point: points) {
            if(point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    public void goDown() {
        for(Point point: points) {
            point.y++;
        }
    }

    public void goLeft() {
        for(Point point: points) {
            point.x--;
        }
    }

    public void goRight() {
        for(Point point: points) {
            point.x++;
        }
    }
}