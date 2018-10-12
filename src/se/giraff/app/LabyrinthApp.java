package se.giraff.app;

import se.giraff.matrix.helpers.MatrixFactory;
import se.giraff.matrix.helpers.MatrixPathfinder;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.Path;

import java.util.Collection;

import static se.giraff.config.LabyrinthConfig.*;

public class LabyrinthApp {

    private int size;
    private Matrix matrix;

    public LabyrinthApp(int size) {
        if (size < MIN_ALLOWED_MATRIX_SIZE) {
            throw new RuntimeException(String.format("The provided size is below the minimum allowed size <%s>!", MIN_ALLOWED_MATRIX_SIZE));
        }

        this.size = size;
        this.matrix = MatrixFactory.createMatrixWithElements(this.size, DEFAULT_LOWEST_ELEMENT_WEIGHT, DEFAULT_HIGHEST_ELEMENT_WEIGHT);
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Collection<Path> findShortestPaths(Coordinate startCoordinate, Coordinate endCoordinate) {
        MatrixPathfinder pathfinder = MatrixPathfinder.Builder.create()
                .matrix(matrix)
                .from(startCoordinate)
                .to(endCoordinate)
                .build();

        return pathfinder.findPaths();
    }

}
