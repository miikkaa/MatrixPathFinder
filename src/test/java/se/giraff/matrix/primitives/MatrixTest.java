package se.giraff.matrix.primitives;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MatrixTest {

    @Test
    void testMatrixAssertions() {
        assertThrows(AssertionError.class, () -> new Matrix<>(null));

        assertThrows(AssertionError.class, () -> new Matrix<>(new MatrixElement[0][0]));

        assertThrows(AssertionError.class, () -> new Matrix<>(new MatrixElement[][]{}));

        assertThrows(AssertionError.class, () -> new Matrix<>(new MatrixElement[1][2]));
    }

    @Test
    void testGetElementAt() {
        Matrix matrix = new Matrix<>(new MatrixElement[2][2]);

        assertThrows(IndexOutOfBoundsException.class,
                () -> matrix.getElementAt(Coordinate.from(0, matrix.getSize() + 1)));

        assertThrows(IndexOutOfBoundsException.class,
                () -> matrix.getElementAt(Coordinate.from(matrix.getSize() + 1, 0)));

        assertDoesNotThrow(() -> matrix.getElementAt(Coordinate.from(0, 0)));
    }

    @Test
    void testHasElementAt() {
        Matrix matrix = new Matrix<>(new MatrixElement[2][2]);

        Coordinate coordinateIn = Coordinate.from(0, 0);
        assertTrue(matrix.hasElementAt(coordinateIn));

        Coordinate coordinateOutside = Coordinate.from(matrix.getSize() + 1, 0);
        assertFalse(matrix.hasElementAt(coordinateOutside));
    }
}