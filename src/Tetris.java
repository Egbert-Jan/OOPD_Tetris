import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import shapes.Hook;
import shapes.Point;
import shapes.*;

import java.awt.event.KeyEvent;

public class Tetris extends GameEngine {
    private static String MEDIA_URL = "src/media/";
    private final int TILE_SIZE = 35;

    private int tilesMap[][] = createMap();
    Shape currentShape = new Hook();

    private DecendTimer decendTimer = new DecendTimer(this);

//    Shape[] shapes = { new Rectangle(), new Hook() };

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

        if(e.getKeyCode() == LEFT && currentShape.getMinX() > 0) {
            currentShape.goLeft();
        } else if(e.getKeyCode() == RIGHT && currentShape.getMaxX() < 9) {
            currentShape.goRight();
        } else if(e.getKeyCode() == DOWN) {
            currentShape.goDown();
        }

        drawMap();
    }

    TileType[] tileTypes = createTiles();

    public void drawMap() {
        //Draw shapes
        for(int y = 0; y < tilesMap.length; y++) {
            for(int x = 0; x < tilesMap.length; x++) {
                if(currentShape.shouldDraw(x, y)) {
                    tilesMap[y][x] = currentShape.type;
                }
            }
        }

        super.tileMap = new TileMap(TILE_SIZE, tileTypes, tilesMap);
    }

    private TileType[] createTiles() {
        Sprite sprite = new Sprite(Tetris.MEDIA_URL.concat("tile.png"));
        TileType<Tile> floorTileType = new TileType<>(Tile.class, sprite);

        TileType[] tileTypes = new TileType[] {
            floorTileType,
        };

        return tileTypes;
    }

    boolean tryClearCurrentShape() {

        if(currentShape.getMaxY() == 19) {
            currentShape = new Rectangle();
            return false;
        }

        for(Point point : currentShape.points) {
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

//
//     if(currentShape.getMaxY() == 19) {
//             System.out.println("si");
//             currentShape = new Rectangle();
//             super.tileMap = new TileMap(TILE_SIZE, tileTypes, tilesMap);
//             return;
//             }
