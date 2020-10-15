import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import shapes.Hook;
import shapes.Point;
import shapes.Shape;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine {
    private static String MEDIA_URL = "src/media/";
    private final int TILE_SIZE = 35;

    private int tilesMap[][] = createMap();
    Shape currentShape = new Hook(5, 0);

    private DecendTimer decendTimer = new DecendTimer(this);

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

        initializeTileMap();
    }

    @Override
    public void update() {
        System.out.println("cool");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        clearMap();

        if(e.getKeyCode() == LEFT) {
            currentShape.goLeft();
        } else if(e.getKeyCode() == RIGHT) {
            currentShape.goRight();
        } else if(e.getKeyCode() == DOWN) {
            currentShape.goDown();
        }

        initializeTileMap();
    }

    TileType[] tileTypes = new TileType[1];

    public void initializeTileMap() {
        Sprite sprite = new Sprite(Tetris.MEDIA_URL.concat("tile.png"));
        TileType<Tile> floorTileType = new TileType<>(Tile.class, sprite);
        tileTypes[0] = floorTileType;

        for(int y = 0; y < tilesMap.length; y++) {
            for(int x = 0; x < tilesMap.length; x++) {

                if(currentShape.isOnPosition(x, y)) {
                    tilesMap[y][x] = currentShape.type;
                }
            }
        }

        super.tileMap = new TileMap(TILE_SIZE, tileTypes, tilesMap);
    }

    void clearMap() {
        for(Point point : currentShape.points) {
            if(point.y < 0) { continue; }
            System.out.println(point.y);
            tilesMap[point.y][point.x] = -1;
        }
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
