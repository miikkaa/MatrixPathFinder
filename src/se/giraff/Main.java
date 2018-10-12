package se.giraff;

import se.giraff.app.LabyrinthApp;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.Path;

import java.util.Arrays;
import java.util.Collection;

import static se.giraff.config.LabyrinthConfig.*;

public class Main {

    public static final String HIGHLIGHT_COLOR = "\u001B[31m";
    public static final String HIGHLIGHT_RESET = "\u001B[0m";
    public static final char STR_INTERSECTION = '+';
    public static final char STR_VERTICAL = '|';
    public static final char STR_HORIZONTAL = '-';

    public static void main(String[] args) {
        int size = DEFAULT_MATRIX_SIZE;

        LabyrinthApp app = new LabyrinthApp(size);
        Coordinate startCoordinate = Coordinate.from(DEFAULT_START_COORDINATE_X, DEFAULT_START_COORDINATE_Y);
        Coordinate endCoordinate = Coordinate.from(size - 1, size - 1);
        Collection<Path> paths = app.findShortestPaths(startCoordinate, endCoordinate);

        System.out.println("Found " + paths.size() + " path" + (paths.size() > 1 ? "s" : "") + ":");
        paths.forEach(System.out::println);

        System.out.println();
        printPaths(app.getMatrix(), paths);
    }

    private static void printPaths(Matrix matrix, Collection<Path> paths) {
        int size = matrix.getSize();

        paths.forEach(path -> {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < size; i++) {
                // HEAD
                if (i == 0) {
                    borderRow(sb, size);
                } else {
                    separatorRow(sb, size);
                }

                blankRow(sb, size);

                // NUMBER
                for (int j = 0; j < size; j++) {
                    if (j == 0) {
                        sb.append(STR_VERTICAL);
                    }
                    Coordinate coordinate = Coordinate.from(i, j);
                    boolean coordinateInPath = path.contains(coordinate);
                    elementRow(sb, matrix.getElementAt(coordinate).getWeight(), coordinateInPath);
                }
                sb.append("\n");

                blankRow(sb, size);

                // TAIL
                if (i == size - 1) {
                    borderRow(sb, size);
                }
            }

            System.out.println(sb.toString());
        });
    }

    private static void elementRow(StringBuilder sb, int weight, boolean highlighted) {
        sb.append("  ");

        if (highlighted) {
            sb.append(HIGHLIGHT_COLOR);
            sb.append("(");
        } else {
            sb.append(" ");
        }

        sb.append(weight);

        if (highlighted) {
            sb.append(")");
            sb.append(HIGHLIGHT_RESET);
        } else {
            sb.append(" ");
        }

        sb.append("  ");
        sb.append(STR_VERTICAL);
    }

    private static void borderRow(StringBuilder sb, int size) {
        for (int j = 0; j < size; j++) {
            // Left border
            if (j == 0) {
                sb.append(STR_INTERSECTION);
            }

            char[] border = new char[7];
            Arrays.fill(border, STR_HORIZONTAL);
            sb.append(border);

            // Right border
            if (j == size - 1) {
                sb.append(STR_INTERSECTION);
            } else {
                sb.append(STR_HORIZONTAL);
            }
        }
        sb.append("\n");
    }

    private static void blankRow(StringBuilder sb, int size) {
        for (int j = 0; j < size; j++) {
            // Left border
            if (j == 0) {
                sb.append(STR_VERTICAL);
            }

            sb.append("       ");

            // Right border
            if (j == size - 1) {
                sb.append(STR_VERTICAL);
            } else {
                sb.append(" ");
            }
        }

        sb.append("\n");
    }

    private static void separatorRow(StringBuilder sb, int size) {
        for (int j = 0; j < size; j++) {
            // Left border
            if (j == 0) {
                sb.append(STR_VERTICAL);
            }

            sb.append("   " + STR_HORIZONTAL + "   ");

            // Right border
            if (j == size - 1) {
                sb.append(STR_VERTICAL);
            } else {
                sb.append(STR_INTERSECTION);
            }
        }

        sb.append("\n");
    }
}

