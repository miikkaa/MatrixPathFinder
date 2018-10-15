package se.giraff.matrix.helpers;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.Path;

import java.util.Collection;
import java.util.Optional;

public class MatrixPrinter {

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

    private final Matrix matrix;
    private final Collection<Path> paths;

    public static void print(Matrix matrix, Collection<Path> paths) {
        MatrixPrinter matrixPrinter = new MatrixPrinter(matrix, paths);
        matrixPrinter.print();
    }

    private MatrixPrinter(Matrix matrix, Collection<Path> paths) {
        this.matrix = matrix;
        this.paths = paths;
    }

    private void print() {
        System.out.println("Matrix:");
        System.out.println(matrixToString());

        System.out.println("Found " + paths.size() + " path" + (paths.size() > 1 ? "s" : "") + ":");
        paths.forEach(System.out::println);

        System.out.println("Matrix with the shortest path highlighted:");
        paths.forEach(path -> {
            System.out.println("===================================");
            System.out.println(matrixToString(path));
        });
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
                boolean highlighted = path.isPresent() &&
                        path.get().contains(coordinate);

                sb.append(printElement(weight, highlighted));
            }

            sb.append(NEW_LINE);
            sb.append(NEW_LINE);
        }

        return sb.toString();
    }

    private String printElement(int element, boolean highlighted) {
        String template = highlighted
                ? ELEMENT_HIGHLIGHTED_TPL
                : ELEMENT_TPL;

        return NUM_ROW_PADDING
                + String.format(template, element)
                + NUM_ROW_PADDING;
    }
}
