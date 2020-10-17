package tetrominos;

import java.util.HashMap;
import java.util.Random;

public class Tetromino {
    public static final Class[] TETROMINOS = { Straight.class, LeftHook.class, RightHook.class, Square.class, LeftSkew.class, Pyramid.class, RightSkew.class };
    static int startX = 4;
    static int startY = 1;

    Point[] points = new Point[4];

    public int type = 0;

    public Tetromino() { }

    public static Tetromino generateRandomTetromino() {
        try {
            int randomNr = new Random().nextInt(TETROMINOS.length);
            return (Tetromino) TETROMINOS[randomNr].newInstance();
        } catch (IllegalAccessException | InstantiationException a) {
            return null;
        }
    }

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

    private Point[] getSidePoints(boolean rightSide) {
        HashMap<Integer, Point> hashMap = new HashMap<>();

        for(Point point : points) {
            hashMap.putIfAbsent(point.y, point);

            if(rightSide) {
                if(hashMap.get(point.y).x < point.x)
                {
                    hashMap.put(point.y, point);
                }
            } else {
                if(hashMap.get(point.y).x > point.x)
                {
                    hashMap.put(point.y, point);
                }
            }
        }

        return hashMap.values().toArray(new Point[0]);
    }

    private boolean canGoDown(int[][] map) {
        Point[] lowestPoints = getLowestPoints();

        for(Point point: lowestPoints) {
            if(point.y == 19 || map[point.y+1][point.x] != -1) {
                return false;
            }
        }

        return true;
    }

    public boolean shouldDraw(int x, int y) {
        for (Point point: points) {
            if(point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    public boolean goDown(int[][] map) {

        if(!canGoDown(map)) {
            return false;
        }

        clearTetromino(map);

        for(Point point: points) {
            point.y++;
        }

        return true;
    }

    private void clearTetromino(int[][] map) {
        for(Point point : points) {
            if(point.y < 0) { continue; }
            map[point.y][point.x] = -1;
        }
    }

    public boolean goLeft(int[][] map) {
        for(Point point: getSidePoints(false)) {
            if(point.x == 0 || map[point.y][point.x-1] != -1) {
                return false;
            }
        }

        clearTetromino(map);

        for(Point point: points) {
            point.x--;
        }

        return true;
    }

    public boolean goRight(int[][] map) {
        for(Point point: getSidePoints(true)) {
            if(point.x == 9 || map[point.y][point.x+1] != -1) {
                return false;
            }
        }

        clearTetromino(map);

        for (Point point : points) {
            point.x++;
        }

        return true;
    }
}