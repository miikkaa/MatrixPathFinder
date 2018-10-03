package se.giraff.matrix.helpers;

import org.junit.jupiter.api.Test;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;

import static org.junit.jupiter.api.Assertions.*;

class MatrixPathfinderTest {

    @Test
    void testBuilderValidationWithoutMandatoryParams() {
        Throwable exception;

        final Matrix<MatrixElement> testMatrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        final Coordinate testCoordinate = Coordinate.from(1, 1);

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testCoordinate)
                .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .to(testCoordinate)
                .build());
        assertTrue(exception.getMessage().contains("mandatory"));

        //
        assertDoesNotThrow(() -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testCoordinate)
                .to(testCoordinate)
                .build()
        );
    }

    @Test
    void testBuilderValidationWithIllegalIndices() {
        Throwable exception;

        final Matrix<MatrixElement> testMatrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        final Coordinate testCoordinate = Coordinate.from(0, 0);
        final Coordinate testCoordinateOutOfBounds = Coordinate.from(testMatrix.getSize(), 0);

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testCoordinate)
                .to(testCoordinateOutOfBounds)
                .build());
        assertTrue(exception.getMessage().contains("bounds"));

        exception = assertThrows(MatrixBuilderValidationError.class, () -> MatrixPathfinder.Builder.create()
                .matrix(testMatrix)
                .from(testCoordinateOutOfBounds)
                .to(testCoordinate)
                .build());
        assertTrue(exception.getMessage().contains("bounds"));
    }

//    @Test
//    void findPaths() {
//        Matrix<MatrixElement> matrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX.length, MatrixTestHelper.DEFAULT_MATRIX);
//        MatrixPathfinder.Builder.create()
//                .matrix(new Matrix<>(1, null))
//                .build();
//    }
}