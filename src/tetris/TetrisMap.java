package tetris;

import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import tetris.tetrominos.Point;
import tetris.tetrominos.Tetromino;

public class TetrisMap {

    private int[][] tilesMap = createMap();
    private int[][] tempMap = createMap();


    TetrisMap() { }


    int[][] map() {
        return tilesMap;
    }

    void hideMap() {
        tempMap = createMap();
        tempMap = copyMap(tilesMap);
        tilesMap = createMap();
    }

    void showMap() {
        tilesMap = copyMap(tempMap);
    }

    void setNewEmptyMap() {
        tilesMap = createMap();
    }

    /**
     *
     * @param currentTetromino The current tetromino displayed on the screen
     * @param tileTypes The list with tileTypes that are availible
     * @param tileSize The size of each tile
     * @return A TileMap
     */
    TileMap generateMap(Tetromino currentTetromino, TileType[] tileTypes, int tileSize) {
        Point[] points = currentTetromino.getLowestPoints();
        for(int y = 0; y < tilesMap.length; y++) {
            for(int x = 0; x < tilesMap[y].length; x++) {

                if(y == 0) {
                    tilesMap[y][x] = Tetromino.topRowNr;
                }

                if(tilesMap[y][x] == Tetromino.backgroundNr) {
                    for (Point p : points) {
                        if(x == p.x && y > p.y)
                            tilesMap[y][x] = Tetromino.indicationNr;
                    }
                }

                if(tilesMap[y][x] == Tetromino.indicationNr) {
                    if(!Point.containsX(points, x))
                        tilesMap[y][x] = Tetromino.backgroundNr;
                }

                if(currentTetromino.containsPointAt(x, y)) {
                    tilesMap[y][x] = currentTetromino.type;
                }
            }
        }

        return new TileMap(tileSize, tileTypes, tilesMap);
    }

    /**
     * Recursive function that moves all the rows above the initial row down
     * @param row The row to start from
     */
     void moveRowsDown(int row) {
        if(row == 1) {
            int[] newRow = new int[10];

            for(int i = 0; i < 10; i++) {
                newRow[i] = Tetromino.backgroundNr;
            }

            tilesMap[1] = newRow;
            return;
        }

        tilesMap[row] = tilesMap[row-1];

        moveRowsDown(row-1);
    }

    /**
     * Check if this row is full with Tetrominos
     * @return A boolean value if the row is full with Tetrominos
     */
    boolean isFullRow(int y) {
        for(int i: tilesMap[y]) {
            if(i == Tetromino.backgroundNr || i == Tetromino.indicationNr)
                return false;
        }

        return true;
    }

    /**
     * Creates map of 21 by 10
     * @return A two dimensional array of 21 by 10
     */
    private int[][] createMap() {
        int[][] map = new int[21][10];

        for(int y = 0; y < 21; y++) {
            int[] row = new int[10];
            for(int x = 0; x < 10; x++) {
                row[x] = Tetromino.backgroundNr;
            }
            map[y] = row;
        }

        System.out.println(map.length);
        return map;
    }

    /**
     * Copies the two dimensional by value
     * @param map The two dimensional map as int array
     * @return The same map as input but by value
     */
    private int[][] copyMap(int[][] map) {
        int[][] copyMap = new int[21][10];

        for(int y = 0; y < 21; y++) {
            int[] row = new int[10];
            for (int x = 0; x < 10; x++) {
                row[x] = map[y][x];
            }
            copyMap[y] = row;
        }

        return copyMap;
    }

}
