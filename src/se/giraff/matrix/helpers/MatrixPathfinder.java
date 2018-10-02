package se.giraff.matrix.helpers;

import se.giraff.matrix.primitives.*;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixPathfinder {

    private Matrix matrix;
    private Coordinate from;
    private Coordinate to;

    private MatrixElement fromElement;
    private MatrixElement toElement;

    private Collection<Path> paths;

    private MatrixPathfinder(Matrix matrix, Coordinate from, Coordinate to) {
        this.matrix = matrix;
        this.from = from;
        this.to = to;
        // this.paths.add(this.matrix.getElementAt(from));

    }

    private Matrix<PathInterimElement> getHelperMatrix() {
        PathInterimElement[][] interimMatrix = new PathInterimElement[matrix.getSize()][matrix.getSize()];

        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                Coordinate coordinate = Coordinate.from(i, j);

                PathInterimElement interimElement = new PathInterimElement(matrix.getElementAt(coordinate), coordinate);
                interimMatrix[i][j] = interimElement;
            }
        }

        return new Matrix<>(matrix.getSize(), interimMatrix);
    }

    public Collection<Path> findPaths() {
        fromElement = matrix.getElementAt(from);
        toElement = matrix.getElementAt(to);

        paths = new HashSet<>();

        Set<PathInterimElement> weighted = new HashSet<>();
//        Set<PathInterimElement> visited = new HashSet<>();
//        weighted.add(head.getCoordinate());
//        head.setInitialPath();
//        PathInterimElement current = head;
        Matrix<PathInterimElement> tempMatrix = getHelperMatrix();
        PathInterimElement head = tempMatrix.getElementAt(from);
        PathInterimElement tail = tempMatrix.getElementAt(to);
        PathInterimElement current = head;
        current.setPath(new Path(from));
        weighted.add(current);

        while (true) {

            System.out.println("Current: " + current);
            PathInterimElement finalCurrent = current;
            Collection<PathInterimElement> neighbours = getNeighbours(tempMatrix, finalCurrent);
            System.out.println("Neighbours: " + neighbours);
            System.out.println("Not visited neighbours: " + neighbours.stream().filter(e -> !e.isVisited()).collect(Collectors.toSet()));
            neighbours.stream()
                    .filter(e -> !e.isVisited())
                    .forEach(e -> {
                        if (!e.hasInterimWeight()) {
                            e.setPaths(finalCurrent);
                            weighted.add(e);
                            System.out.println("New IW: " + e.getInterimWeight() + " @ " + e.getCoordinate());
                        } else {
                            int newInterimWeight = finalCurrent.getInterimWeight() + e.getWeight();
                            if (newInterimWeight < e.getInterimWeight()) {
                                e.setPaths(finalCurrent);
                                System.out.println("REPLACE IW: " + newInterimWeight + " @ " + e.getCoordinate());
                            } else if (newInterimWeight == e.getInterimWeight()) {
                                e.addToPaths(finalCurrent);
                                System.out.println("UPDATE IW: " + newInterimWeight + " @ " + e.getCoordinate());
                            } else {
                                System.out.println("KEEP IW: (" + e.getInterimWeight() + " > " + newInterimWeight + ")" + " @ " + e.getCoordinate());
                            }
                        }
                    });

            finalCurrent.markAsVisited();

            if (tail.isVisited()) {
                break;
            }

            // Get next current
            current = weighted.stream()
                    .filter(el -> !el.isVisited())
                    .min(Comparator.comparingInt(PathInterimElement::getInterimWeight))
                    .orElseThrow(() -> new RuntimeException("Could not find the next element to visit!"));
        }

        System.out.println("Paths:");
        System.out.println(tail.getPaths());
//
//
//        for (int i = 0; i < matrix.getSize(); i++) {
//            for (int j = 0; j < matrix.getSize(); j++) {
//                Coordinate coordinate = Coordinate.from(i, j);
//                interimMatrix[i][j]
//            }
//        }
//        Set<PathInterimElement> interimElements = elementsAsList.stream()
//                .map(PathInterimElement::new)
//                .collect(Collectors.toSet());
//
//        PathInterimElement head = interimElements.stream()
//                .filter(element -> element);
//
//        return paths;
        throw new RuntimeException("Not implemented!");
    }

    private Set<PathInterimElement> getNeighbours(Matrix<PathInterimElement> tempMatrix, PathInterimElement current) {
        Set<PathInterimElement> neighbours = new HashSet<>();
        Coordinate coordinate = current.getCoordinate();

        // Left
        if (coordinate.getX() > 0) {
            Coordinate left = Coordinate.from(coordinate.getX() - 1, coordinate.getY());
            neighbours.add(tempMatrix.getElementAt(left));
        }

        // Right
        if (coordinate.getX() < tempMatrix.getSize() - 1) {
            Coordinate right = Coordinate.from(coordinate.getX() + 1, coordinate.getY());
            neighbours.add(tempMatrix.getElementAt(right));
        }

        // Above
        if (coordinate.getY() > 0) {
            Coordinate above = Coordinate.from(coordinate.getX(), coordinate.getY() - 1);
            neighbours.add(tempMatrix.getElementAt(above));
        }

        // Below
        if (coordinate.getY() < tempMatrix.getSize() - 1) {
            Coordinate below = Coordinate.from(coordinate.getX(), coordinate.getY() + 1);
            neighbours.add(tempMatrix.getElementAt(below));
        }

        return neighbours;
    }

    public static class Builder {

        private Matrix matrix;
        private Coordinate from;
        private Coordinate to;

        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder matrix(Matrix matrix) {
            this.matrix = matrix;
            return this;
        }

        public Builder from(Coordinate from) {
            this.from = from;
            return this;
        }

        public Builder to(Coordinate to) {
            this.to = to;
            return this;
        }

        public MatrixPathfinder build() {
            validateParameters();
            return new MatrixPathfinder(matrix, from, to);
        }

        private void validateParameters() {
            if (matrix == null) {
                throw new RuntimeException("<matrix> is a mandatory parameter!");
            }

            if (from == null) {
                throw new RuntimeException("<from> is a mandatory parameter!");
            }

            if (matrix.hasElementAt(from)) {
                throw new RuntimeException("<from> coordinate is out of bounds!");
            }

            if (to == null) {
                throw new RuntimeException("<to> is a mandatory parameter!");
            }

            if (matrix.hasElementAt(to)) {
                throw new RuntimeException("<to> coordinate is out of bounds!");
            }
        }

    }

}
