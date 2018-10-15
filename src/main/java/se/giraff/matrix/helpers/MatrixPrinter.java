package se.giraff.matrix.helpers;

import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixTrace;
import se.giraff.matrix.primitives.Path;

public class MatrixPrinter {

    public static final PrintStream DEFAULT_PRINT_STREAM = System.out;

    // = ANSI RED
    private static final String HIGHLIGHT_COLOR = "\u001B[31m";
    private static final String HIGHLIGHT_RESET = "\u001B[0m";

    private static final String NUM_ROW_PADDING = "  ";
    private static final String NUM_HIGHLIGHT_LEFT = "(";
    private static final String NUM_HIGHLIGHT_RIGHT = ")";

    private static final String NEW_LINE = "\n";
    private static final String WHITESPACE = " ";

    private static final String ELEMENT_TPL = WHITESPACE + "%s" + WHITESPACE;
    private static final String ELEMENT_HIGHLIGHTED_TPL = HIGHLIGHT_COLOR +
            NUM_HIGHLIGHT_LEFT + "%s" + NUM_HIGHLIGHT_RIGHT +
            HIGHLIGHT_RESET;

    private static final int CELL_WIDTH = 7;

    private final Matrix matrix;
    private final MatrixTrace matrixTrace;
    private final Printer printer;

    public static void print(Matrix matrix, MatrixTrace matrixTrace, PrintStream printStream) {
        MatrixPrinter matrixPrinter = new MatrixPrinter(matrix, matrixTrace, printStream);
        matrixPrinter.printMatrixWithPaths();
        printStream.flush();
    }

    private static String multiply(String input, int times) {
        String[] stringArray = new String[times];
        Arrays.fill(stringArray, input);
        return String.join("", stringArray);
    }

    private MatrixPrinter(Matrix matrix, MatrixTrace matrixTrace, PrintStream printStream) {
        this.matrix = matrix;
        this.matrixTrace = matrixTrace;
        this.printer = new Printer(printStream);
    }

    private void printMatrixWithPaths() {
        printer.print("Original matrix:");
        printer.print(matrixToString());

        if (matrixTrace.getPaths() != null && !matrixTrace.getPaths().isEmpty()) {
            Collection<Path> paths = matrixTrace.getPaths();

            printer.print("Found <" + paths.size() + " path" + (paths.size() > 1 ? "s" : "") + "> with distance <" + matrixTrace.getDistance() + ">:");
            paths.forEach(p -> printer.print(p.toString()));

            final String pathSeparator = multiply("=", matrix.getSize() * CELL_WIDTH);

            printer.newline();
            printer.newline();
            printer.print("Matrix with the shortest path highlighted:");

            paths.forEach(path -> {
                printer.newline();
                printer.print(matrixToString(path));
                printer.print(pathSeparator);
            });
        }
    }

    private String matrixToString() {
        return matrixToString(Optional.empty());
    }

    private String matrixToString(Path path) {
        return matrixToString(Optional.of(path));
    }

    private String matrixToString(Optional<Path> path) {
        int size = matrix.getSize();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(NEW_LINE);

            for (int j = 0; j < size; j++) {
                Coordinate coordinate = Coordinate.from(i, j);
                int weight = matrix.getElementAt(coordinate).getWeight();
                boolean highlighted = path.isPresent() && path.get().contains(coordinate);

                sb.append(printElement(weight, highlighted));
            }

            sb.append(NEW_LINE);
            sb.append(NEW_LINE);
        }

        return sb.toString();
    }

    static String printElement(int element, boolean highlighted) {
        String template = highlighted
                ? ELEMENT_HIGHLIGHTED_TPL
                : ELEMENT_TPL;

        return NUM_ROW_PADDING
                + String.format(template, element)
                + NUM_ROW_PADDING;
    }

    private final class Printer {

        private final PrintStream printStream;

        private Printer(PrintStream printStream) {
            this.printStream = printStream;
        }

        private void print(String string) {
            printStream.println(string);
        }

        private void newline() {
            printStream.println();
        }
    }
}
