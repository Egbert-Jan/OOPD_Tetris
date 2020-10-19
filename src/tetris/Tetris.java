package tetris;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import tetris.tetrominos.*;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine {
    private static String MEDIA_URL = "src/tetris/media/";

    private final int TILE_SIZE = 35;

    private int tilesMap[][] = createMap();
    private TileType[] tileTypes = createTiles();
    private Tetromino currentTetromino = Tetromino.generateRandomTetromino();
    private DecendTimer decendTimer;

    private int totalPoints = 0;

    private Sound popSound = new Sound(this, MEDIA_URL + "sounds/popSound.mp3");
    private Sound levelUpSound = new Sound(this, MEDIA_URL + "sounds/levelUp.wav");

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

        decendTimer = new DecendTimer(this);
    }

    @Override
    public void update() {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(handleKeypress(e.getKeyCode())) {
            popSound.rewind();
            popSound.play();
        }

        drawMap();
    }

    private boolean handleKeypress(int keyCode) {
        if(keyCode == LEFT) {
            return currentTetromino.goLeft(tilesMap);
        } else if(keyCode == RIGHT) {
            return currentTetromino.goRight(tilesMap);
        } else if(keyCode == DOWN) {
            return handleGoDown();
        } else if(keyCode == UP) {
            return currentTetromino.nextRotation(tilesMap, currentTetromino);
        }

        return false;
    }

    boolean handleGoDown() {
        if(!currentTetromino.goDown(tilesMap)) {
            int amountOfRows = 0;

            for (int y = 0; y < tilesMap.length; y++) {
                if (isFullRow(tilesMap[y])) {
                    moveRowsDown(y);
                    amountOfRows++;

                    levelUpSound.rewind();
                    levelUpSound.play();
                }
            }

            if (amountOfRows == 1) {
                totalPoints += 40;
            } else if (amountOfRows == 2) {
                totalPoints += 100;
            } else if (amountOfRows == 3) {
                totalPoints += 300;
            } else if (amountOfRows == 4) {
                totalPoints += 1200;
            }

            currentTetromino = Tetromino.generateRandomTetromino();

            if(!currentTetromino.canGoDown(tilesMap)) {
                System.out.println(totalPoints);
                totalPoints = 0;
                tilesMap = createMap();
                currentTetromino = Tetromino.generateRandomTetromino();
            }

            return false;
        }

        return true;
    }

    private void moveRowsDown(int row) {
        if(row == 0) {
            int[] newRow = new int[10];

            for(int i = 0; i < 10; i++) {
                newRow[i] = Tetromino.backgroundNr;
            }

            tilesMap[0] = newRow;
            return;
        }

        tilesMap[row] = tilesMap[row-1];

        moveRowsDown(row-1);
    }

    private boolean isFullRow(int[] row) {
        for(int i = 0; i < row.length; i++) {
            if(row[i] == Tetromino.backgroundNr)
                return false;
        }

        return true;
    }

    void drawMap() {
        //Draw tetris.tetrominos
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
        Sprite backgroundTile = new Sprite(Tetris.MEDIA_URL.concat("backgroundTile.png"));

        Sprite lightBlueSprite = new Sprite(Tetris.MEDIA_URL.concat("lightBlueTile.png"));
        Sprite blueSprite = new Sprite(Tetris.MEDIA_URL.concat("blueTile.png"));
        Sprite orangeSprite = new Sprite(Tetris.MEDIA_URL.concat("orangeTile.png"));
        Sprite yellowSprite = new Sprite(Tetris.MEDIA_URL.concat("yellowTile.png"));
        Sprite greenSprite = new Sprite(Tetris.MEDIA_URL.concat("greenTile.png"));
        Sprite purpleSprite = new Sprite(Tetris.MEDIA_URL.concat("purpleTile.png"));
        Sprite redSprite = new Sprite(Tetris.MEDIA_URL.concat("redTile.png"));

        return new TileType[] {
                new TileType<>(Tile.class, backgroundTile),
                new TileType<>(Tile.class, lightBlueSprite),
                new TileType<>(Tile.class, blueSprite),
                new TileType<>(Tile.class, orangeSprite),
                new TileType<>(Tile.class, yellowSprite),
                new TileType<>(Tile.class, greenSprite),
                new TileType<>(Tile.class, purpleSprite),
                new TileType<>(Tile.class, redSprite),
        };
    }

    //Creates a map 20 by 10
    private int[][] createMap() {
        int[][] map = new int[20][10];

        for(int y = 0; y < 20; y++) {
            int[] row = new int[10];
            for(int x = 0; x < 10; x++) {
                row[x] = Tetromino.backgroundNr;
            }
            map[y] = row;
        }
        return map;
    }

    public static int[][] copyMap(int[][] map) {

        int[][] copyMap = new int[20][10];

        for(int y = 0; y < 20; y++) {
            int[] row = new int[10];
            for (int x = 0; x < 10; x++) {
                row[x] = map[y][x];
            }
            copyMap[y] = row;
        }

        return copyMap;
    }
}