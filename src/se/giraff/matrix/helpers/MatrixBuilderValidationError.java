package se.giraff.matrix.helpers;

public class MatrixBuilderValidationError extends RuntimeException {
    MatrixBuilderValidationError(String message) {
        super(message);
    }
}
