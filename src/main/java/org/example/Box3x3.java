package org.example;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.example.Direction.*;

public class Box3x3 implements Cloneable {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private final Tile[][] tiles;
    private final Tile emptyTile;

    public Box3x3(int[][] intTiles) throws InvalidBox3x3Exception {
        tiles = wrap(intTiles);
        emptyTile = getEmptyTile(tiles);
    }

    private Box3x3(Tile[][] tiles, Tile emptyTile) {
        this.tiles = tiles;
        this.emptyTile = emptyTile;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Tile[][] tiles = new Tile[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tiles[i][j] = (Tile) this.tiles[i][j].clone();
            }
        }
        return new Box3x3(tiles, (Tile) emptyTile.clone());
    }

    private static Tile[][] wrap(int[][] intTiles) {
        Tile[][] tiles = new Tile[ROWS][COLS];
        IntStream.range(0, ROWS).forEach(i -> {
            IntStream.range(0, COLS).forEach(j -> {
                tiles[i][j] = new Tile(intTiles[i][j], j, i);
            });
        });
        return tiles;
    }

    private static Tile getEmptyTile(Tile[][] tiles) throws InvalidBox3x3Exception {
        return Stream.of(tiles)
                .flatMap(Stream::of)
                .filter(tile -> tile.getValue() == 0)
                .findFirst()
                .orElseThrow(() -> new InvalidBox3x3Exception("(Box3x3 has no empty tile)"));
    }

    public int getNumOfDifferences(int[][] goal) {
        IntCounter counter = new IntCounter();
        IntStream.range(0, ROWS).forEach(i -> {
            IntStream.range(0, COLS).forEach(j -> {
                if (!tiles[i][j].equals(emptyTile) && tiles[i][j].getValue() != goal[i][j])
                    counter.increment();
            });
        });
        return counter.get();
    }

    public void print() {
        IntStream.range(0, ROWS).forEach(i -> {
            IntStream.range(0, COLS).forEach(j -> {
                System.out.printf("%d ", tiles[i][j].getValue());
            });
            System.out.println();
        });
    }

    public void moveEmptyTile(Direction direction) throws OutOfBoxException {
        int x, y;
        switch (direction) {
            case UP -> {
                x = emptyTile.getX() + UP.getX();
                y = emptyTile.getY() + UP.getY();
                if (isSafe(x, y))
                    swap(emptyTile, tiles[y][x]);
            }
            case RIGHT -> {
                x = emptyTile.getX() + RIGHT.getX();
                y = emptyTile.getY() + RIGHT.getY();
                if (isSafe(x, y)) {
                    swap(emptyTile, tiles[y][x]);
                }
            }
            case DOWN -> {
                x = emptyTile.getX() + DOWN.getX();
                y = emptyTile.getY() + DOWN.getY();
                if (isSafe(x, y))
                    swap(emptyTile, tiles[y][x]);
            }
            case LEFT -> {
                x = emptyTile.getX() + LEFT.getX();
                y = emptyTile.getY() + LEFT.getY();
                if (isSafe(x, y))
                    swap(emptyTile, tiles[y][x]);
            }
            default -> throw new IllegalArgumentException("(Invalid direction)");
        }
    }

    private boolean isSafe(int x, int y) throws OutOfBoxException {
        if ((y >= 0 && y < ROWS) && (x >= 0 && x < COLS)) {
            return true;
        }
        throw new OutOfBoxException();
    }

    private void swap(Tile t1, Tile t2) {
        tiles[t1.getY()][t1.getX()] = t2;
        tiles[t2.getY()][t2.getX()] = t1;
        t1.setX(t2.getX());
        t1.setY(t2.getY());
        t2.setX(t1.getX());
        t2.setY(t1.getY());
    }
}
