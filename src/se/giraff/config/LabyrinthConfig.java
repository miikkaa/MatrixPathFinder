package se.giraff.config;

import se.giraff.matrix.primitives.Coordinate;

public final class LabyrinthConfig {
    public final static int MIN_ALLOWED_MATRIX_SIZE = 2;
    public final static int DEFAULT_MATRIX_SIZE = 5;

    public final static Coordinate DEFAULT_START_COORDINATE = Coordinate.from(0, 0);
    public final static Coordinate DEFAULT_END_COORDINATE = Coordinate.from(DEFAULT_MATRIX_SIZE - 1, DEFAULT_MATRIX_SIZE - 1);

    public final static int DEFAULT_LOWEST_ELEMENT_WEIGHT = 1;
    public final static int DEFAULT_HIGHEST_ELEMENT_WEIGHT = 9;
}
