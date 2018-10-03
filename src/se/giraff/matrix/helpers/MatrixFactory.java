package se.giraff.matrix.helpers;

import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;

import java.util.PrimitiveIterator;
import java.util.Random;

public class MatrixFactory {

    public static Matrix createMatrixWithElements(int size, MatrixElement[][] elements) {
        return new Matrix<>(elements);
    }

    public static Matrix createMatrixWithElements(int size, int minWeight, int maxWeight) {
        MatrixElement[][] matrixElements = generateMatrixElements(size, minWeight, maxWeight);
        MatrixElement[][] matrixElements2 = new MatrixElement[][]{
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
        return new Matrix<>(matrixElements);
    }

    private static MatrixElement[][] generateMatrixElements(int size, int minWeight, int maxWeight) {
        MatrixElement[][] matrixElements = new MatrixElement[size][size];

        int numberOfElements = size * size;
        // Providing an exclusive upper boundary below, therefore +1.
        int maxWeightExclusive = maxWeight + 1;

        PrimitiveIterator.OfInt weights = new Random()
                .ints(numberOfElements, minWeight, maxWeightExclusive)
                .iterator();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixElements[i][j] = new MatrixElement(weights.nextInt());
            }
        }

        return matrixElements;
    }
}
