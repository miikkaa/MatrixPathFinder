package se.giraff.matrix.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.MatrixElement;
import se.giraff.matrix.primitives.Path;

class MatrixTestHelper {

    final static MatrixElement[][] DEFAULT_MATRIX = new MatrixElement[][]{
            new MatrixElement[]{
                    new MatrixElement(1),
                    new MatrixElement(3),
                    new MatrixElement(2),
                    new MatrixElement(5),
                    new MatrixElement(9),
            },
            new MatrixElement[]{
                    new MatrixElement(6),
                    new MatrixElement(5),
                    new MatrixElement(1),
                    new MatrixElement(3),
                    new MatrixElement(3),
            },
            new MatrixElement[]{
                    new MatrixElement(4),
                    new MatrixElement(2),
                    new MatrixElement(1),
                    new MatrixElement(4),
                    new MatrixElement(5),
            },
            new MatrixElement[]{
                    new MatrixElement(8),
                    new MatrixElement(2),
                    new MatrixElement(8),
                    new MatrixElement(4),
                    new MatrixElement(1),
            },
            new MatrixElement[]{
                    new MatrixElement(7),
                    new MatrixElement(1),
                    new MatrixElement(2),
                    new MatrixElement(2),
                    new MatrixElement(3),
            },
    };

    final static Collection<Path> DEFAULT_MATRIX_PATHS = new ArrayList<>(Arrays.asList(
            new Path(Coordinate.from(0, 0))
                    .addCoordinate(Coordinate.from(0, 1))
                    .addCoordinate(Coordinate.from(0, 2))
                    .addCoordinate(Coordinate.from(1, 2))
                    .addCoordinate(Coordinate.from(2, 2))
                    .addCoordinate(Coordinate.from(2, 3))
                    .addCoordinate(Coordinate.from(3, 3))
                    .addCoordinate(Coordinate.from(3, 4))
                    .addCoordinate(Coordinate.from(4, 4)),

            new Path(Coordinate.from(0, 0))
                    .addCoordinate(Coordinate.from(0, 1))
                    .addCoordinate(Coordinate.from(0, 2))
                    .addCoordinate(Coordinate.from(1, 2))
                    .addCoordinate(Coordinate.from(2, 2))
                    .addCoordinate(Coordinate.from(2, 1))
                    .addCoordinate(Coordinate.from(3, 1))
                    .addCoordinate(Coordinate.from(4, 1))
                    .addCoordinate(Coordinate.from(4, 2))
                    .addCoordinate(Coordinate.from(4, 3))
                    .addCoordinate(Coordinate.from(4, 4))
    ));

    final static int DEFAULT_MATRIX_SHORTEST_DISTANCE = 16;

}
