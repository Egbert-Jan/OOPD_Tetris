package tetris;

import com.sun.tools.javac.comp.Enter;
import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.persistence.FilePersistence;
import nl.han.ica.oopg.persistence.IPersistence;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.userinput.IKeyInput;
import nl.han.ica.oopg.view.View;
import tetris.tetrominos.*;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine implements IKeyInput {
    private static String MEDIA_URL = "src/tetris/media/";

    private final int TILE_SIZE = 35;

    private int worldWidth = TILE_SIZE * 10;
    private int worldHeight = TILE_SIZE * 21;

    private int tilesMap[][] = createMap();
    private TileType[] tileTypes = createTiles();
    private Tetromino currentTetromino = Tetromino.generateRandomTetromino();
    private DecendTimer decendTimer;

    private int totalPoints = 0;
    boolean gameIsStopped = false;

    private IPersistence persistence;
    private int highScore;

    private Sound popSound = new Sound(this, MEDIA_URL + "sounds/popSound.mp3");
    private Sound levelUpSound = new Sound(this, MEDIA_URL + "sounds/levelUp.wav");

    private TextObject currentScoreTextObject = new TextObject("Score: 0", 25);
    private TextObject highScoreText = new TextObject("HighScore: 0", 25);
    private TextObject currentScoreText = new TextObject("Score: 0", 25);
    private TextObject newHighScoreText = new TextObject("New HighScore!", 25);
    private TextObject continueText = new TextObject("Press Enter key to continue", 16);

    public static void main(String[] args) {
        Tetris main = new Tetris();
        main.runSketch();
    }

    @Override
    public void setupGame() {
        View view = new View(worldWidth, worldHeight);
        setView(view);

        size(worldWidth, worldHeight);

        drawMap();

        decendTimer = new DecendTimer(this);
        persistence = new FilePersistence("tetris/media/files/highScore.txt");
        highScore = Integer.parseInt(persistence.loadDataString());

        currentScoreTextObject.setX(5);
        currentScoreTextObject.setForeColor(255, 255, 255, 255);
        addGameObject(currentScoreTextObject);

        frameRate(30);
    }

    @Override
    public void update() { }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == RETURN || e.getKeyCode() == ENTER)
            restartGame();

        if(gameIsStopped)
            return;

        if(handleKeypress(e.getKeyCode())) {
            popSound.rewind();
            popSound.play();
        }

        drawMap();
    }

    /**
     *  keyFunctions only work on a GameObject. Should change this
     */
    @Override
    public void keyReleased(int keyCode, char key) { }

    @Override
    public void keyPressed(int keyCode, char key) { }

    /**
     *
     * @param keyCode of the key that has been pressed
     * @return if the keypress action has been executed
     */
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

    /**
     * Move the Tetromino down and other behaviour that comes with it
     * @return boolean if the Tetromino went down
     */
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

            currentScoreTextObject.setText("Score: " + totalPoints);

            currentTetromino = Tetromino.generateRandomTetromino();

            if(!currentTetromino.canGoDown(tilesMap)) {
                System.out.println(totalPoints);
                currentTetromino.clearTetromino(tilesMap);

                if(totalPoints > highScore) {
                    persistence.saveData(Integer.toString(totalPoints));
                }

                showEndGameView(totalPoints > highScore);

                highScore = totalPoints;
            }

            return false;
        }

        return true;
    }

    /**
     * Recursive function that moves all the rows above the initial row down
     * @param row the row to start from
     */
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

    /**
     * Check if this row is full with Tetrominos
     * @param row
     * @return a boolean if the row is full with Tetrominos
     */
    private boolean isFullRow(int[] row) {
        for(int i = 0; i < row.length; i++) {
            if(row[i] == Tetromino.backgroundNr)
                return false;
        }

        return true;
    }

    /**
     * Draws all the Tetromino's on the screen
     */
    void drawMap() {
        for(int y = 0; y < tilesMap.length; y++) {
            for(int x = 0; x < tilesMap.length; x++) {
                if(currentTetromino.shouldDraw(x, y)) {
                    tilesMap[y][x] = currentTetromino.type;
                }
            }
        }

        super.tileMap = new TileMap(TILE_SIZE, tileTypes, tilesMap);
    }

    /**
     * Creates TileTypes with different colors to show on the screen
     * @return array with different TileTypes
     */
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

    /**
     * Creates map of 20 by 10
     * @return a two dimensional array of 20 by 10
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
        return map;
    }

    /**
     * Copies the two dimensional by value
     * @param map
     * @return the same map as input but by value
     */
    public static int[][] copyMap(int[][] map) {
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

    private void showEndGameView(boolean newHighScore) {
        gameIsStopped = true;

        deleteGameOverObjects();

        int width = worldWidth;
        int height = worldHeight;

//        Dashboard dashboard = new Dashboard(width/4, (height/2) - (width/2), width/2, width/2);
//        dashboard.setBackground(100, 100, 100);

        highScoreText.setText("HighScore: " + highScore);
        highScoreText.setForeColor(255, 255, 255, 255);
        highScoreText.setX(width/4);
        highScoreText.setY(height/2 - 150);
        addGameObject(highScoreText);

        currentScoreText.setText("Score: " + totalPoints);
        currentScoreText.setForeColor(255, 255, 255, 255);
        currentScoreText.setX(width/4);
        currentScoreText.setY(height/2 - 120);
        addGameObject(currentScoreText);

        if(newHighScore) {
            newHighScoreText.setForeColor(255, 255, 255, 255);
            newHighScoreText.setX(width / 4);
            newHighScoreText.setY(height/2 - 80);
            addGameObject(newHighScoreText);
        }

        continueText.setForeColor(255, 255, 255, 255);
        continueText.setX(width / 4);
        continueText.setY(height/2 - 40);
        addGameObject(continueText);

//        addDashboard(dashboard, 10);
//        deleteDashboard(dashboard);
    }

    private void deleteGameOverObjects() {
        deleteGameObject(highScoreText);
        deleteGameObject(currentScoreText);
        deleteGameObject(newHighScoreText);
        deleteGameObject(continueText);
    }

    private void restartGame() {
        deleteGameOverObjects();

        totalPoints = 0;
        currentTetromino = Tetromino.generateRandomTetromino();
        tilesMap = createMap();

        gameIsStopped = false;
    }
}