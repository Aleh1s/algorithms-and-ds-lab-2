package org.example.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tile implements Cloneable {
    private final int value;
    private int x;
    private int y;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public void setXY(int x, int y) {
        setX(x);
        setY(y);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;

        if (value != tile.value) return false;
        if (x != tile.x) return false;
        return y == tile.y;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
