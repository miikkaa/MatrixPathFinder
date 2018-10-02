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

//        System.out.println("+-------+-------+-------+-------+-------+");
//        System.out.println("|                                       |");
//        System.out.println("|   9   |16 7   |19 3   |21 2   |27 6   |");
//        System.out.println("|                                       |");
//        System.out.println("|   -   +   -   +   -   +   -   +   -   |");
//        System.out.println("|   12      21      23      24          |");
//        System.out.println("|   3   |   9   |   4   |   3   |30 6   |");
//        System.out.println("|                                       |");
//        System.out.println("|   -   +   -   +   -   +   -   +   -   |");
//        System.out.println("|   19                                  |");
//        System.out.println("|   7   |20 1   |24 4   |31 7   |       |");
//        System.out.println("|                                       |");
//        System.out.println("|   -   +   -   +   -   +   -   +   -   |");
//        System.out.println("|   23      24      33                  |");
//        System.out.println("|   4   |   4   |   9   |       |       |");
//        System.out.println("|                                       |");
//        System.out.println("|   -   +   -   +-------+   -   +   -   |");
//        System.out.println("|   30      27  |       |               |");
//        System.out.println("|   7   |   3   |   1   |       |       |");
//        System.out.println("|               |       |               |");
//        System.out.println("+-------+-------+-------+-------+-------+");
    }

    public void print() {
        // TODO: Print the matrix using MatrixPrinter!
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
