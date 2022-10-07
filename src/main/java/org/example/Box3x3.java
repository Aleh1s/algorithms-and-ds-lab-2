package org.example;

import java.util.stream.IntStream;

public class Box3x3 {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private Tile[][] tiles;
    private Tile emptyTile;

    public int getNumOfDifferences(Box3x3 another) {
        IntCounter counter = new IntCounter();
        IntStream.range(0, ROWS).forEach(i -> {
            IntStream.range(0, COLS).forEach(j -> {
                if (!tiles[i][j].equals(emptyTile) && !tiles[i][j].equals(another.tiles[i][j]))
                    counter.increment();
            });
        });
        return counter.get();
    }
}
