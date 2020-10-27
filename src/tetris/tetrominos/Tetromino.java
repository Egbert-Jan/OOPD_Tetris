package tetris.tetrominos;

import tetris.Tetris;

import java.util.HashMap;
import java.util.Random;


/**
 * The base class of a Tetromino with all the shared functionality
 */
public abstract class Tetromino {
    private static final Class[] TETROMINOS = {
           Straight.class, LeftHook.class, RightHook.class, Square.class, LeftSkew.class, Pyramid.class, RightSkew.class
    };

    final int startX = 3;
    final int startY = 1;

    public static final int backgroundNr = 0;
    public static final int indicationNr = 8;

    Point[] points = new Point[4];

    public int type = 0;

    //0 = UP, 1 = Right, 2 = Down, 3 = Left
    private int rotationNumber = 0;

    Tetromino() { }


    /**
     * Move Tetromino down
     * @param map the two dimensional array where the Tetromino is in
     * @return boolean value if the Tetromino moved down or not
     */
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

    private boolean canGoLeft(int[][] map) {
        for(Point point: getSidePoints(false)) {
            if(point.x == 0)
                return false;

            if(point.x > 0 && (map[point.y][point.x-1] != backgroundNr && map[point.y][point.x-1] != indicationNr))
                return false;
        }
        return true;
    }

    /**
     * Move Tetromino left
     * @param map
     * @return boolean value if the Tetromino moved left
     */
    public boolean goLeft(int[][] map) {
        if(!canGoLeft(map)) {
            return false;
        }

        clearTetromino(map);

        for(Point point: points) {
            point.x--;
        }

        return true;
    }

    private boolean canGoRight(int[][] map) {
        for(Point point: getSidePoints(true)) {
            if(point.x == 9)
                return false;

            if(point.x < 9 && map[point.y][point.x+1] != backgroundNr && map[point.y][point.x+1] != indicationNr)
                return false;
        }
        return true;
    }

    /**
     * Move Tetromino right
     * @param map
     * @return boolean value if the Tetromino moved right
     */
    public boolean goRight(int[][] map) {
        if(!canGoRight(map)) {
            return false;
        }

        clearTetromino(map);

        for (Point point : points) {
            point.x++;
        }

        return true;
    }

    /**
     * Try to rotate the Tetromino
     * @param map
     * @param currentTetromino
     * @return boolean if the tetromino has been rotated
     */
    public final boolean nextRotation(int[][] map, Tetromino currentTetromino) {
        int[][] tempMap = Tetris.copyMap(map);

        clearTetromino(map);

        rotationNumber++;
        rotationNumber = rotationNumber > 3 ? 0 : rotationNumber;

        rotate(map, rotationNumber);

        if(!canGoDown(map)) {

            //Kan niet omlaag

            int status = 0;


            if (goRight(map)) {
                if (canGoDown(map)) {
                    return true;
                } else {
                    goLeft(map);
                }
            }


            if(goLeft(map)) {
                if (canGoDown(map)) {
                    return true;
                } else {
                    goRight(map);
                }
            }

            reverseRotation(map, tempMap, 0);

            return false;
        }

        return true;
    }

    public void reverseRotation(int[][] map, int[][] tempMap, int status) {
        if(!canGoDown(map)) {
////            if(status == 1) {
////                goRight(map);
////            } else if(status == 2) {
////                goLeft(map);
////            }

            rotationNumber--;
            rotationNumber = rotationNumber < 0 ? 3 : rotationNumber;

            //Rotate back
            rotate(map, rotationNumber);

            for(int row = 0; row < map.length; row++) {
                map[0] = tempMap[0];
            }

//            return false;
        }

//        return true;
    }

    /**
     * A abstract method to rotate the shape
     * @param map
     * @param rotationNumber 0 = UP, 1 = Right, 2 = Down, 3 = Left
     */
    protected abstract void rotate(int[][] map, int rotationNumber);


    /**
     * Get the lowest points of the Tetromino
     * @return an array with the lowest points of the Tetromino
     */
    public Point[] getLowestPoints() {
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

    /**
     * Get most left or right points from the Tetromino
     * @param rightSide a boolean value specifying if it should return the Points from the left or right side
     * @return an array with the left or right points of the Tetromino
     */
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

    /**
     * Check if the Tetromino can move down
     * @param map the two dimensional array where the Tetromino is in
     * @return a boolean value if the Tetromino can move down
     */
    public boolean canGoDown(int[][] map) {
        Point[] lowestPoints = getLowestPoints();

        for(Point point: lowestPoints) {
            if(point.x >= 10 || point.x <= -1)
                return false;

            if(point.y >= 20 || (map[point.y+1][point.x] != backgroundNr && map[point.y+1][point.x] != indicationNr))
                return false;
        }

        return true;
    }

    /**
     *
     * @param x position of the place to draw
     * @param y position of the place to draw
     * @return a boolean value if a part of the Tetromino has to be drawn on the x,y position
     */
    public boolean containsPointAt(int x, int y) {
        for (Point point: points) {
            if(point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clear this Tetromino from the map
     * @param map
     */
    public void clearTetromino(int[][] map) {
        for(Point point : points) {
            if(point.y < 0 || point.y > 20 || point.x < 0 || point.x > 9) { continue; }
            map[point.y][point.x] = backgroundNr;
        }
    }

    /**
     * Generates a random Tetromino
     * @return The random Tetromino
     */
    public static Tetromino generateRandomTetromino() {
        try {
            int randomNr = new Random().nextInt(TETROMINOS.length);
            return (Tetromino) TETROMINOS[randomNr].newInstance();
        } catch (IllegalAccessException | InstantiationException a) {
            System.out.println("generateRandomTetromino() failed");
            return null;
        }
    }
}