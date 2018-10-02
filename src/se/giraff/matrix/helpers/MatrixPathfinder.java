package se.giraff.matrix.helpers;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElementWithPath;
import se.giraff.matrix.primitives.Path;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatrixPathfinder {

    private Matrix originalMatrix;
    private Coordinate from;
    private Coordinate to;

    private MatrixPathfinder(Matrix originalMatrix, Coordinate from, Coordinate to) {
        this.originalMatrix = originalMatrix;
        this.from = from;
        this.to = to;
    }

    public Collection<Path> findPaths() {
        Matrix<MatrixElementWithPath> helperMatrix = getMatrixWithPaths();
        MatrixElementWithPath head = helperMatrix.getElementAt(from);
        MatrixElementWithPath tail = helperMatrix.getElementAt(to);

        head.setOpeningPath();

        Set<MatrixElementWithPath> elementsWithPaths = new HashSet<>();
        elementsWithPaths.add(head);

        while (!tail.isVisited()) {
            // Next element to visit is the one having the lowest interim weight.
            MatrixElementWithPath currentElement = elementsWithPaths.stream()
                    .min(Comparator.comparingInt(MatrixElementWithPath::getInterimWeight))
                    .orElseThrow(() -> new RuntimeException("Could not find the next element to visit!"));

            Collection<MatrixElementWithPath> unvisitedNeighbours = getNeighbours(helperMatrix, currentElement).stream()
                    .filter(element -> !element.isVisited())
                    .collect(Collectors.toSet());

            unvisitedNeighbours.forEach(neighbour -> {
                int result = compareWeightedDistance(neighbour, currentElement);

                if (result > 0) {
                    neighbour.setPaths(currentElement);

                } else if (result == 0) {
                    neighbour.addToPaths(currentElement);
                }

                elementsWithPaths.add(neighbour);
            });

            currentElement.setVisited();
            elementsWithPaths.remove(currentElement);
        }

        return tail.getPaths();
    }

    private Matrix<MatrixElementWithPath> getMatrixWithPaths() {
        int size = originalMatrix.getSize();
        MatrixElementWithPath[][] interimMatrix = new MatrixElementWithPath[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordinate coordinate = Coordinate.from(i, j);

                MatrixElementWithPath interimElement = new MatrixElementWithPath(originalMatrix.getElementAt(coordinate), coordinate);
                interimMatrix[i][j] = interimElement;
            }
        }

        return new Matrix<>(size, interimMatrix);
    }

    private static int compareWeightedDistance(MatrixElementWithPath target, MatrixElementWithPath source) {
        int currentWeight = target.getInterimWeight();
        int newWeight = source.getInterimWeight() + target.getWeight();

        if (currentWeight < newWeight) {
            return -1;
        } else if (currentWeight > newWeight) {
            return 1;
        }
        return 0;
    }

    private static Set<MatrixElementWithPath> getNeighbours(Matrix<MatrixElementWithPath> matrix, MatrixElementWithPath current) {
        Set<MatrixElementWithPath> neighbours = new HashSet<>();
        Coordinate coordinate = current.getCoordinate();

        // Left
        if (coordinate.getX() > 0) {
            Coordinate left = Coordinate.from(coordinate.getX() - 1, coordinate.getY());
            neighbours.add(matrix.getElementAt(left));
        }

        // Right
        if (coordinate.getX() < matrix.getSize() - 1) {
            Coordinate right = Coordinate.from(coordinate.getX() + 1, coordinate.getY());
            neighbours.add(matrix.getElementAt(right));
        }

        // Above
        if (coordinate.getY() > 0) {
            Coordinate above = Coordinate.from(coordinate.getX(), coordinate.getY() - 1);
            neighbours.add(matrix.getElementAt(above));
        }

        // Below
        if (coordinate.getY() < matrix.getSize() - 1) {
            Coordinate below = Coordinate.from(coordinate.getX(), coordinate.getY() + 1);
            neighbours.add(matrix.getElementAt(below));
        }

        return neighbours;
    }

    public static class Builder {

        private Matrix originalMatrix;
        private Coordinate from;
        private Coordinate to;

        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder matrix(Matrix originalMatrix) {
            this.originalMatrix = originalMatrix;
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
            return new MatrixPathfinder(originalMatrix, from, to);
        }

        private void validateParameters() {
            if (originalMatrix == null) {
                throw new RuntimeException("<originalMatrix> is a mandatory parameter!");
            }

            if (from == null) {
                throw new RuntimeException("<from> is a mandatory parameter!");
            }

            if (originalMatrix.hasElementAt(from)) {
                throw new RuntimeException("<from> coordinate is out of bounds!");
            }

            if (to == null) {
                throw new RuntimeException("<to> is a mandatory parameter!");
            }

            if (originalMatrix.hasElementAt(to)) {
                throw new RuntimeException("<to> coordinate is out of bounds!");
            }
        }
    }
}
