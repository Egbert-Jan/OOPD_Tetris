package shapes;

abstract public class Shape {
    static int startX = 5;
    static int startY = 1;
    public Point[] points = new Point[4];

    public int type = 0;

    public Shape() { }

    public int getMinX() {
        int minX = points[0].x;

        for(Point point : points) {
            if(point.x < minX) {
                minX = point.x;
            }
        }
        return minX;
    }

    public int getMaxX() {
        int maxX = points[0].x;

        for(Point point : points) {
            if(point.x > maxX) {
                maxX = point.x;
            }
        }
        return maxX;
    }

    public int getMinY() {
        int minY = points[0].y;

        for(Point point : points) {
            if(point.y < minY) {
                minY = point.y;
            }
        }
        return minY;
    }

    public int getMaxY() {
        int maxY = points[0].y;

        for(Point point : points) {
            if(point.y > maxY) {
                maxY = point.y;
            }
        }
        return maxY;
    }


    public boolean shouldDraw(int x, int y) {
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

    public boolean canGoDown() {



        return false;
    }
}