package shapes;

class Point {
    int x, y = 0;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean equals(Point p) {
        return p.x == this.x && p.y == this.y;
    }
}

public class Shape {
    private int x = 0;
    private int y = 0;

    Point[] points = new Point[4];

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