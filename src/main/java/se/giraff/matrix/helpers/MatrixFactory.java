package se.giraff.matrix.helpers;

import java.util.PrimitiveIterator;
import java.util.Random;

import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;

public class MatrixFactory {

    final static int MIN_ALLOWED_MATRIX_SIZE = 2;

    /**
     * Builds a matrix of a given size and fills it with elements within the provided weight range.
     *
     * @param size      Matrix size
     * @param minWeight The lowest weight
     * @param maxWeight The highest weight
     * @return A matrix filled with random elements
     */
    public static Matrix<MatrixElement> createMatrixWithElements(int size, int minWeight, int maxWeight) {
        MatrixElement[][] matrixElements = generateMatrixElements(size, minWeight, maxWeight);

        return new Matrix<>(matrixElements);
    }

    private static MatrixElement[][] generateMatrixElements(int size, int minWeight, int maxWeight) {
        assert size >= MIN_ALLOWED_MATRIX_SIZE;

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
