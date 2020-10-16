import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import tetrominos.Hook;
import tetrominos.Point;
import tetrominos.*;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine {
    private static String MEDIA_URL = "src/media/";
    private final int TILE_SIZE = 35;

    private int tilesMap[][] = createMap();
    Tetromino currentTetromino = new Hook();

    private DecendTimer decendTimer = new DecendTimer(this);

//    Tetromino[] tetrominos = { new Straight(), new Hook() };

    public static void main(String[] args) {
        Tetris main = new Tetris();
        main.runSketch();
    }

    @Override
    public void setupGame() {
        int worldWidth = TILE_SIZE * 10;
        int worldHeight = TILE_SIZE * 20;

        View view = new View(worldWidth, worldHeight);
        view.setBackground(100, 100, 100);
        setView(view);

        size(worldWidth, worldHeight);

        drawMap();
    }

    @Override
    public void update() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!tryClearCurrentShape()) { return; };

        if(e.getKeyCode() == LEFT && currentTetromino.getMinX() > 0) {
            currentTetromino.goLeft();
        } else if(e.getKeyCode() == RIGHT && currentTetromino.getMaxX() < 9) {
            currentTetromino.goRight();
        } else if(e.getKeyCode() == DOWN) {
            currentTetromino.goDown();
        }

        drawMap();
    }

    TileType[] tileTypes = createTiles();

    public void drawMap() {
        //Draw tetrominos
        for(int y = 0; y < tilesMap.length; y++) {
            for(int x = 0; x < tilesMap.length; x++) {
                if(currentTetromino.shouldDraw(x, y)) {
                    tilesMap[y][x] = currentTetromino.type;
                }
            }
        }

        super.tileMap = new TileMap(TILE_SIZE, tileTypes, tilesMap);
    }

    private TileType[] createTiles() {
        Sprite lightBlueSprite = new Sprite(Tetris.MEDIA_URL.concat("lightBlueTile.png"));
        TileType<Tile> lightBlueTileType = new TileType<>(Tile.class, lightBlueSprite);

        Sprite blueSprite = new Sprite(Tetris.MEDIA_URL.concat("blueTile.png"));
        TileType<Tile> blueTileType = new TileType<>(Tile.class, blueSprite);

        TileType[] tileTypes = new TileType[] {
                lightBlueTileType,
                blueTileType
        };

        return tileTypes;
    }

    boolean tryClearCurrentShape() {

        if(!currentTetromino.canGoDown(tilesMap)) {
            System.out.println("jjaaaaa");
            currentTetromino = new Straight();
            return false;
        }

        for(Point point : currentTetromino.points) {
            if(point.y < 0) { continue; }
            tilesMap[point.y][point.x] = -1;
        }

        return true;
    }

    //Creates a map 20 by 10
    private int[][] createMap() {
        int[][] map = new int[20][10];

        for(int y = 0; y < 20; y++) {
            int[] row = new int[10];
            for(int x = 0; x < 10; x++) {
                row[x] = -1;
            }
            map[y] = row;
        }
        return map;
    }
}