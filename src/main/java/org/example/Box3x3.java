package org.example;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Box3x3 {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private final Tile[][] tiles;
    private final Tile emptyTile;

    public Box3x3(int[][] intTiles) throws InvalidBox3x3Exception {
        tiles = wrap(intTiles);
        emptyTile = getEmptyTile(tiles);
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
