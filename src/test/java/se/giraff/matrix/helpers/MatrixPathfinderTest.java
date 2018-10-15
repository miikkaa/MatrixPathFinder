package se.giraff.matrix.helpers;

import org.junit.jupiter.api.Test;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;
import se.giraff.matrix.primitives.MatrixTrace;
import se.giraff.matrix.primitives.Path;

import java.util.AbstractMap;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MatrixPathfinderTest {

    @Test
    void testBuilderValidationWithoutMandatoryParams() {
        Throwable exception;

        final Matrix<MatrixElement> testMatrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        final Coordinate testCoordinate = Coordinate.from(1, 1);

        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create().build());
        assertTrue(exception.getMessage().contains("mandatory"));

        // Missing TO and FROM-coordinates
        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create()
                        .matrix(testMatrix)
                        .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        // Missing TO-coordinate
        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create()
                        .matrix(testMatrix)
                        .from(testCoordinate)
                        .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        // Missing FROM-coordinate
        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create()
                        .matrix(testMatrix)
                        .to(testCoordinate)
                        .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        // Both TO and FROM-coordinates are present
        assertDoesNotThrow(() -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testCoordinate)
                .to(testCoordinate)
                .build());
    }

    @Test
    void testBuilderValidationWithIllegalIndices() {
        Throwable exception;

        final Matrix<MatrixElement> testMatrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        final Coordinate testCoordinate = Coordinate.from(0, 0);
        final Coordinate testCoordinateOutOfBounds = Coordinate.from(testMatrix.getSize(), 0);

        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create()
                        .matrix(testMatrix)
                        .from(testCoordinate)
                        .to(testCoordinateOutOfBounds)
                        .build());
        assertTrue(exception.getMessage().contains("bounds"));

        exception = assertThrows(MatrixBuilderValidationException.class,
                () -> MatrixPathfinder.Builder.create()
                        .matrix(testMatrix)
                        .from(testCoordinateOutOfBounds)
                        .to(testCoordinate)
                        .build());
        assertTrue(exception.getMessage().contains("bounds"));
    }

    @Test
    void testFindPaths() {
        final Matrix<MatrixElement> testMatrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        final Coordinate testFromCoordinate = Coordinate.from(0, 0);
        final Coordinate testToCoordinate = Coordinate.from(testMatrix.getSize() - 1, testMatrix.getSize() - 1);
        MatrixTrace matrixTrace = MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testFromCoordinate)
                .to(testToCoordinate)
                .build()
                .findPaths();

        assertEquals(MatrixTestHelper.DEFAULT_MATRIX_SHORTEST_DISTANCE, matrixTrace.getDistance());
        assertContainsInAnyOrder(MatrixTestHelper.DEFAULT_MATRIX_PATHS, matrixTrace.getPaths());
    }

    private void assertContainsInAnyOrder(Collection<?> actual, Collection<?> expected) {
        actual.forEach(item -> {
            assert expected.contains(item);
        });
    }
}