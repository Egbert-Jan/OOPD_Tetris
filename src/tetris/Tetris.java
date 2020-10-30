package tetris;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.persistence.FilePersistence;
import nl.han.ica.oopg.persistence.IPersistence;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import tetris.tetrominos.*;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine {
    private static String MEDIA_URL = "src/tetris/media/";

    private final int TILE_SIZE = 35;

    private int worldWidth = TILE_SIZE * 10;
    private int worldHeight = TILE_SIZE * 21;

    private TetrisMap tetrisMap = new TetrisMap();
    private TileType[] tileTypes = createTiles();
    private Tetromino currentTetromino = Tetromino.generateRandomTetromino();
    private DecendTimer decendTimer;

    private int totalPoints = 0;
    GameStatus gameStatus = GameStatus.Paused;

    private IPersistence persistence;
    private int highScore;

    private Sound popSound = new Sound(this, MEDIA_URL + "sounds/popSound.mp3");
    private Sound levelUpSound = new Sound(this, MEDIA_URL + "sounds/levelUp.wav");

    private TextObject currentScoreTextObject = new TextObject("Score: 0", 25);
    private TextObject highScoreText = new TextObject("HighScore: 0", 25);
    private TextObject currentScoreText = new TextObject("Score: 0", 25);
    private TextObject newHighScoreText = new TextObject("New HighScore!", 25);
    private TextObject continueText = new TextObject("Press Enter to restart", 16);

    public static void main(String[] args) {
        Tetris main = new Tetris();
        main.runSketch();
    }

    @Override
    public void setupGame() {
        gameStatus = GameStatus.Paused;

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

        showInfoScreen(true, true);
    }

    @Override
    public void update() { }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == RETURN || e.getKeyCode() == ENTER)
            restartGame();

        if(e.getKeyCode() == ESC) {
            if(gameStatus == GameStatus.Playing) {
                gameStatus = GameStatus.Paused;
                tetrisMap.hideMap();
                drawMap();

                showInfoScreen(false, true);
            } else if(gameStatus == GameStatus.Ended) {
                restartGame();
            } else {
                gameStatus = GameStatus.Playing;
                tetrisMap.showMap();
                deleteInfoObjects();
            }
        }

        if(gameStatus != GameStatus.Playing)
            return;

        if(handleKeypress(e.getKeyCode())) {
            popSound.rewind();
            popSound.play();
        }

        drawMap();
    }

    /**
     *
     * @param keyCode Of the key that has been pressed
     * @return If the keypress action has been executed
     */
    private boolean handleKeypress(int keyCode) {
        if(keyCode == LEFT) {
            return currentTetromino.goLeft(tetrisMap.map());
        } else if(keyCode == RIGHT) {
            return currentTetromino.goRight(tetrisMap.map());
        } else if(keyCode == DOWN) {
            return handleGoDown();
        } else if(keyCode == UP) {
            return currentTetromino.nextRotation(tetrisMap.map());
        } else if(keyCode == ALT || keyCode == SHIFT) {
            while(handleGoDown());
            return true;
        }

        return false;
    }


    /**
     * Move the Tetromino down and other behaviour that comes with it
     * @return Boolean if the Tetromino went down
     */
    boolean handleGoDown() {
        if(!currentTetromino.goDown(tetrisMap.map())) {
            drawMap();
            int amountOfRows = 0;

            for (int y = 1; y < tetrisMap.map().length; y++) {
                if (tetrisMap.isFullRow(y)) {
                    tetrisMap.moveRowsDown(y);
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

            if(!currentTetromino.canGoDown(tetrisMap.map())) {
                System.out.println(totalPoints);
                currentTetromino.clearTetromino(tetrisMap.map());

                showInfoScreen(totalPoints > highScore, false);
                trySavingHighScore();
            }

            return false;
        }

        return true;
    }

    /**
     * Draws the map on the screen
     */
    void drawMap() {
        this.tileMap = tetrisMap.generateMap(currentTetromino, tileTypes, TILE_SIZE);
    }

    /**
     * Saves the high score if the current score is bigger than the high score
     * @return A boolean if it updated the high score
     */
    private boolean trySavingHighScore() {
        if(totalPoints > highScore) {
            persistence.saveData(Integer.toString(totalPoints));
            highScore = totalPoints;
            return true;
        }
        return false;
    }


    /**
     * Creates TileTypes with different colors to show on the screen
     * @return Array with different TileTypes
     */
    private TileType[] createTiles() {
        Sprite darkTopTile = new Sprite(Tetris.MEDIA_URL.concat("darkGrayTile3.png"));
        Sprite backgroundTile = new Sprite(Tetris.MEDIA_URL.concat("backgroundTile.png"));
        Sprite indicationTile = new Sprite(Tetris.MEDIA_URL.concat("lightGrayTile.png"));

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

                new TileType<>(Tile.class, indicationTile),
                new TileType<>(Tile.class, darkTopTile),
        };
    }

    /**
     * Shows information on the screen. Depending on the parameters if will show different text and styles.
     * @param newHighScore A boolean value indicating if it should show that the user reached a new highscore
     * @param beginScreen A boolean value indicating if it should show the begin screen information.
     */
    private void showInfoScreen(boolean newHighScore, boolean beginScreen) {
        gameStatus = beginScreen ? GameStatus.Paused : GameStatus.Ended;

        deleteInfoObjects();

        int width = worldWidth;
        int height = worldHeight;

//        Dashboard dashboard = new Dashboard(width/4, (height/2) - (width/2), width/2, width/2);
//        dashboard.setBackground(100, 100, 100);

        highScoreText.setText(!beginScreen ? "HighScore: " + highScore : "Arrow keys: Move And Rotate");
        highScoreText.setFontSize(!beginScreen ? 25 : 18);
        highScoreText.setForeColor(255, 255, 255, 255);
        highScoreText.setX(!beginScreen ? width / 4 : 20);
        highScoreText.setY(height/2 - 150);
        addGameObject(highScoreText);

        currentScoreText.setText(!beginScreen ? "Score: " + totalPoints : "Alt: Hard Drop");
        currentScoreText.setFontSize(!beginScreen ? 25 : 18);
        currentScoreText.setForeColor(255, 255, 255, 255);
        currentScoreText.setX(!beginScreen ? width / 4 : 20);
        currentScoreText.setY(height/2 - 120);
        addGameObject(currentScoreText);

        if(newHighScore || beginScreen) {
            newHighScoreText.setText(!beginScreen ? "New HighScore!" : "Esc: Pause | Enter: Restart Game");
            newHighScoreText.setFontSize(!beginScreen ? 25 : 18);
            newHighScoreText.setForeColor(255, 255, 255, 255);
            newHighScoreText.setX(!beginScreen ? width / 4 : 20);
            newHighScoreText.setY(height/2 - 80);
            addGameObject(newHighScoreText);
        }

        continueText.setText(!beginScreen ? "Press Enter/ESC to restart" : "Press ESC to start");
        continueText.setForeColor(255, 255, 255, 255);
        continueText.setX(!beginScreen ? width / 4 : 20);
        continueText.setY(height/2 - 40);
        addGameObject(continueText);

//        addDashboard(dashboard, 10);
//        deleteDashboard(dashboard);
    }

    /**
     * Delete all information screen objects
     */
    private void deleteInfoObjects() {
        deleteGameObject(highScoreText);
        deleteGameObject(currentScoreText);
        deleteGameObject(newHighScoreText);
        deleteGameObject(continueText);
    }

    /**
     * Restarts a freshly new game.
     */
    private void restartGame() {
        deleteInfoObjects();

        trySavingHighScore();

        totalPoints = 0;
        currentTetromino = Tetromino.generateRandomTetromino();
        tetrisMap.setNewEmptyMap();

        currentScoreTextObject.setText("Score: 0");

        gameStatus = GameStatus.Playing;
    }
}