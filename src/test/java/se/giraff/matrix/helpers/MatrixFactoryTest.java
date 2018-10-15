package se.giraff.matrix.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.giraff.matrix.helpers.MatrixFactory.MIN_ALLOWED_MATRIX_SIZE;

import org.junit.jupiter.api.Test;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;

class MatrixFactoryTest {

    @Test
    void testCreateTooSmallMatrixFails() {
        assertThrows(AssertionError.class,
                () -> MatrixFactory.createMatrixWithElements(MIN_ALLOWED_MATRIX_SIZE - 1, 1, 2));
    }

    @Test
    void testCreateMatrixWithIncorrectWeightBoundariesFails() {
        assertThrows(IllegalArgumentException.class,
                () -> MatrixFactory.createMatrixWithElements(MIN_ALLOWED_MATRIX_SIZE, 10, 1));
    }

    @Test
    void testCreateMatrixWithElements() {
        int matrixSize = MIN_ALLOWED_MATRIX_SIZE + 1;
        int minWeight = 2;
        int maxWeight = 5;

        Matrix<MatrixElement> matrix = MatrixFactory.createMatrixWithElements(matrixSize, minWeight, maxWeight);
        assertEquals(matrixSize, matrix.getSize());

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int weight = matrix.getElementAt(Coordinate.from(i, j)).getWeight();
                assertTrue(weight >= minWeight);
                assertTrue(weight <= maxWeight);
            }
        }
    }
}