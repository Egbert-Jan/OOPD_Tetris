package tetrominos;

import java.util.HashMap;

public class Tetromino {
    static int startX = 4;
    static int startY = 1;
    public Point[] points = new Point[4];

    public int type = 0;


    public Tetromino() { }

    private Point[] getLowestPoints() {
        HashMap<Integer, Point> hashMap = new HashMap<>();

        for(Point point : points) {
            hashMap.putIfAbsent(point.x, point);

            if(hashMap.get(point.x).y < point.y)
            {
                hashMap.put(point.x, point);
            }
        }

        return hashMap.values().toArray(new Point[0]);
    }

    public boolean canGoDown(int[][] map) {
        Point[] lowestPoints = getLowestPoints();

        for(Point point: lowestPoints) {
            if(point.y == 19 || map[point.y+1][point.x] != -1) {
                return false;
            }
        }

        return true;
    }

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


}