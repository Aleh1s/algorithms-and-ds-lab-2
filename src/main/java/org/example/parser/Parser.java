package org.example.parser;

import org.example.property.Property;

import java.util.Arrays;
import java.util.Properties;

public class Parser {

    public static int[][] getGoalState() {
        Properties properties = Property.getInstance().getProperties();
        String[] rows = new String[3];
        for (int i = 1; i <= 3; i++)
            rows[i - 1] = properties.getProperty(
                    String.format("goal.state.row-%d", i));
        return parse(rows);
    }

    private static int[][] parse(String[] rows) {
        return Arrays.stream(rows)
                .map(row -> row.split(" "))
                .map(Parser::from)
                .toArray(int[][]::new);
    }

    private static int[] from(String[] nums) {
        return Arrays.stream(nums)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
