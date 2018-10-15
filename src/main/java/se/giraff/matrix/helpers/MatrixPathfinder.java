package se.giraff.matrix.helpers;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.Matrix;
import se.giraff.matrix.primitives.MatrixElementWithPath;
import se.giraff.matrix.primitives.MatrixTrace;
import se.giraff.matrix.primitives.Path;

public class MatrixPathfinder {

    private Matrix originalMatrix;
    private Coordinate from;
    private Coordinate to;

    private MatrixPathfinder(Matrix originalMatrix, Coordinate from, Coordinate to) {
        this.originalMatrix = originalMatrix;
        this.from = from;
        this.to = to;
    }

    /**
     * Finds shortest paths between two coordinates in a given matrix.
     *
     * @return Shortest paths found for the current Pathfinder instance
     */
    public MatrixTrace findPaths() {
        Matrix<MatrixElementWithPath> helperMatrix = createHelperMatrix();
        MatrixElementWithPath head = helperMatrix.getElementAt(from);
        MatrixElementWithPath tail = helperMatrix.getElementAt(to);

        Set<MatrixElementWithPath> queuedElements = new HashSet<>();
        queuedElements.add(head);

        Set<MatrixElementWithPath> visitedElements = new HashSet<>();

        while (true) {
            // Next element to visit is a queued element with the shortest distance from the start
            MatrixElementWithPath fromElement = queuedElements.stream()
                    .min(Comparator.comparingInt(MatrixElementWithPath::getDistance))
                    .orElseThrow(() -> new RuntimeException("Could not find the next element to visit!"));

            Collection<MatrixElementWithPath> unvisitedNeighbours = getNeighbours(helperMatrix, fromElement).stream()
                    .filter(element -> !visitedElements.contains(element))
                    .collect(Collectors.toSet());

            unvisitedNeighbours.forEach(toElement -> {
                int result = MatrixPathfinderUtils.compareDistance(fromElement, toElement);

                // Shorter distance is found, take the element's paths
                if (result > 0) {
                    MatrixPathfinderUtils.updatePaths(fromElement, toElement);

                // Same distance is found, converge the element's and the neighbour's paths
                } else if (result == 0) {
                    MatrixPathfinderUtils.joinPaths(fromElement, toElement);
                }

                queuedElements.add(toElement);
            });

            // No more visits to the element when all of the adjacent elements have been visited once
            visitedElements.add(fromElement);
            queuedElements.remove(fromElement);

            if (fromElement.equals(tail)) {
                break;
            }
        }

        // Distance is the edges, not the nodes
        final int distanceExcludingEdges = tail.getDistance() - head.getWeight() - tail.getWeight();

        return new MatrixTrace(distanceExcludingEdges, tail.getPaths());
    }

    private Matrix<MatrixElementWithPath> createHelperMatrix() {
        int size = originalMatrix.getSize();
        MatrixElementWithPath[][] matrixElements = new MatrixElementWithPath[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordinate coordinate = Coordinate.from(i, j);

                matrixElements[i][j] = new MatrixElementWithPath(originalMatrix.getElementAt(coordinate), coordinate);

                if (coordinate.equals(from)) {
                    MatrixPathfinderUtils.setInitialPath(matrixElements[i][j]);
                }
            }
        }

        return new Matrix<>(matrixElements);
    }

    /**
     * Given a matrix, finds the neighbours for a specified matrix element.
     * A neighbour is a node directly to the left or right, or above or below a target element.
     *
     * @param matrix A matrix to perform the search in
     * @param target A matrix element to find the neighbours for
     * @return A collection of matrix elements adjacent to the provided target
     */
    private static Set<MatrixElementWithPath> getNeighbours(Matrix<MatrixElementWithPath> matrix, MatrixElementWithPath target) {
        Set<MatrixElementWithPath> neighbours = new HashSet<>();
        Coordinate coordinate = target.getCoordinate();

        Coordinate left = Coordinate.leftOf(coordinate);
        if (matrix.hasElementAt(left)) {
            neighbours.add(matrix.getElementAt(left));
        }

        Coordinate right = Coordinate.rightOf(coordinate);
        if (matrix.hasElementAt(right)) {
            neighbours.add(matrix.getElementAt(right));
        }

        Coordinate above = Coordinate.aboveOf(coordinate);
        if (matrix.hasElementAt(above)) {
            neighbours.add(matrix.getElementAt(above));
        }

        Coordinate below = Coordinate.belowOf(coordinate);
        if (matrix.hasElementAt(below)) {
            neighbours.add(matrix.getElementAt(below));
        }

        return neighbours;
    }

    public static class Builder {

        private Matrix matrix;
        private Coordinate from;
        private Coordinate to;

        private Builder() {
        }

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
                throw new MatrixBuilderValidationException("<matrix> is a mandatory parameter!");
            }

            if (from == null) {
                throw new MatrixBuilderValidationException("<from> is a mandatory parameter!");
            }

            if (!matrix.hasElementAt(from)) {
                throw new MatrixBuilderValidationException("<from> coordinate is out of bounds!");
            }

            if (to == null) {
                throw new MatrixBuilderValidationException("<to> is a mandatory parameter!");
            }

            if (!matrix.hasElementAt(to)) {
                throw new MatrixBuilderValidationException("<to> coordinate is out of bounds!");
            }
        }
    }
}
