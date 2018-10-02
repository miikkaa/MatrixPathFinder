package se.giraff;

import se.giraff.app.LabyrinthApp;
import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Path;

import java.util.Collection;

import static se.giraff.config.LabyrinthConfig.*;

public class Main {

    public static void main(String[] args) {
        int size = DEFAULT_MATRIX_SIZE;

        LabyrinthApp app = new LabyrinthApp(size);
        Coordinate startCoordinate = Coordinate.from(DEFAULT_START_COORDINATE_X, DEFAULT_START_COORDINATE_Y);
        Coordinate endCoordinate = Coordinate.from(size - 1, size - 1);
        Collection<Path> paths = app.findShortestPaths(startCoordinate, endCoordinate);

        System.out.println("Paths count: " + paths.size());
        paths.forEach(System.out::println);
    }
}

