package org.example.node;

import lombok.Getter;
import org.example.utils.IntCounter;
import org.example.exception.InvalidBox3x3Exception;
import org.example.exception.OutOfBoxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class Box3x3 {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private final Tile[][] tiles;
    private final Tile emptyTile;
    public Box3x3(int[][] intTiles) throws InvalidBox3x3Exception {
        isValid(intTiles);
        tiles = wrap(intTiles);
        emptyTile = getEmptyTile(tiles);
    }
    public static void isValid(int[][] intTiles) throws InvalidBox3x3Exception {
        isSizeValid(intTiles);
        areNumsValid(intTiles);
    }
    private static void areNumsValid(int[][] intTiles) throws InvalidBox3x3Exception {
        List<Integer> validSequence = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> currSequence = Stream.of(intTiles)
                .flatMapToInt(IntStream::of)
                .boxed()
                .toList();
        List<Integer> nums = new ArrayList<>(validSequence);
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                nums.remove(Integer.valueOf(intTiles[i][j]));
        if (nums.size() != 0)
            throw new InvalidBox3x3Exception(
                    String.format(
                            "Box must contain a sequence of %s numbers, but actually %s", validSequence, currSequence));
    }
    private static void isSizeValid(int[][] intTiles) throws InvalidBox3x3Exception {
        int actualRows = intTiles.length;
        if (actualRows != ROWS)
            throw new InvalidBox3x3Exception(
                    String.format("Invalid num of rows. Must be %d, but actually is %d.", ROWS, actualRows));
        for (int[] intTile : intTiles)
            if (intTile.length != COLS)
                throw new InvalidBox3x3Exception(
                        String.format("Invalid num of cols. Must be %d, but actually is %d.", COLS, intTile.length));
    }
    private Box3x3(Tile[][] tiles, Tile emptyTile) {
        this.tiles = tiles;
        this.emptyTile = emptyTile;
    }
    public Box3x3 clone() throws CloneNotSupportedException {
        Tile[][] tiles = new Tile[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tiles[i][j] = (Tile) this.tiles[i][j].clone();
            }
        }
        try {
            return new Box3x3(tiles, getEmptyTile(tiles));
        } catch (InvalidBox3x3Exception e) {
            throw new CloneNotSupportedException();
        }
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
                if (tiles[i][j].getValue() != 0 && tiles[i][j].getValue() != goal[i][j])
                    counter.increment();
            });
        });
        return counter.get();
    }

    public void moveEmptyTile(Direction direction) throws OutOfBoxException {
        moveTileIfSafe(direction, emptyTile);
    }

    private void moveTileIfSafe(Direction dir, Tile t) throws OutOfBoxException {
        int x = t.getX() + dir.getX(), y = t.getY() + dir.getY();
        if (isSafe(x, y))
            swap(emptyTile, tiles[y][x]);
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
        int t1X = t1.getX(),
                t1Y = t1.getY();
        t1.setXY(t2.getX(), t2.getY());
        t2.setXY(t1X, t1Y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box3x3 box3x3)) return false;

        if (!Arrays.deepEquals(tiles, box3x3.tiles)) return false;
        return Objects.equals(emptyTile, box3x3.emptyTile);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(tiles);
        result = 31 * result + (emptyTile != null ? emptyTile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, ROWS).forEach(i -> {
            IntStream.range(0, COLS).forEach(j -> {
                sb.append(tiles[i][j].toString()).append(' ');
            });
            sb.setLength(sb.length() - 1);
            sb.append('\n');
        });
        return sb.toString();
    }
}
