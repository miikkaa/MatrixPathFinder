package se.giraff.matrix.helpers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.giraff.matrix.helpers.MatrixTestHelper.DEFAULT_MATRIX_SHORTEST_DISTANCE;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElement;
import se.giraff.matrix.primitives.MatrixTrace;
import se.giraff.matrix.primitives.Path;

class MatrixPrinterTest {

    @Test
    void testMatrixPrint() {
        Matrix<MatrixElement> matrix = new Matrix<>(MatrixTestHelper.DEFAULT_MATRIX);
        Collection<Path> paths = MatrixTestHelper.DEFAULT_MATRIX_PATHS;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedOutputStream os = new BufferedOutputStream(out);
        MatrixPrinter.print(matrix, new MatrixTrace(DEFAULT_MATRIX_SHORTEST_DISTANCE, paths), new PrintStream(os));

        String result = new String(out.toByteArray());

        paths.forEach(path -> assertTrue(result.contains(path.toString())));

        int size = matrix.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordinate coordinate = Coordinate.from(i, j);
                MatrixElement element = matrix.getElementAt(coordinate);

                paths.forEach(path -> {
                    boolean highlighted = path.contains(coordinate);
                    String printedElement = MatrixPrinter.printElement(element.getWeight(), highlighted);

                    assertTrue(result.contains(printedElement));
                });
            }
        }
    }
}