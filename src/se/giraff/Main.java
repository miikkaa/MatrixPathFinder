package se.giraff;

import se.giraff.app.LabyrinthApp;
import se.giraff.matrix.helpers.MatrixPrinter;
import se.giraff.matrix.primitives.Path;

import java.util.Collection;

import static se.giraff.config.LabyrinthConfig.*;

public class Main {

    public static void main(String[] args) {
        LabyrinthApp app = new LabyrinthApp(DEFAULT_MATRIX_SIZE);
        Collection<Path> paths = app.findShortestPaths(DEFAULT_START_COORDINATE, DEFAULT_END_COORDINATE);

        MatrixPrinter.print(app.getMatrix(), paths);
    }
}

