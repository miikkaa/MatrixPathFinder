package se.giraff.app;

import static se.giraff.config.LabyrinthConfig.DEFAULT_HIGHEST_ELEMENT_WEIGHT;
import static se.giraff.config.LabyrinthConfig.DEFAULT_LOWEST_ELEMENT_WEIGHT;
import static se.giraff.config.LabyrinthConfig.DEFAULT_MATRIX_SIZE;

import java.util.AbstractMap;
import java.util.Collection;

import se.giraff.matrix.helpers.MatrixFactory;
import se.giraff.matrix.helpers.MatrixPathfinder;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixTrace;
import se.giraff.matrix.primitives.Path;

public class LabyrinthApp {

    private final Matrix matrix;

    public LabyrinthApp() {
        // Creates a matrix and fills it with random elements in the given range
        this.matrix = MatrixFactory.createMatrixWithElements(DEFAULT_MATRIX_SIZE, DEFAULT_LOWEST_ELEMENT_WEIGHT, DEFAULT_HIGHEST_ELEMENT_WEIGHT);
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public MatrixTrace findShortestPaths(Coordinate startCoordinate, Coordinate endCoordinate) {
        MatrixPathfinder pathfinder = MatrixPathfinder.Builder.create()
                .matrix(matrix)
                .from(startCoordinate)
                .to(endCoordinate)
                .build();

        return pathfinder.findPaths();
    }
}
