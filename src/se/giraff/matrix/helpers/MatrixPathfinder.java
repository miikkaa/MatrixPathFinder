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

    /**
     * Finds shortest paths between two coordinates in a given matrix.
     *
     * @return Shortest paths found for the current Pathfinder instance
     */
    public Collection<Path> findPaths() {
        Matrix<MatrixElementWithPath> helperMatrix = createMatrixWithPaths();
        MatrixElementWithPath head = helperMatrix.getElementAt(from);
        MatrixElementWithPath tail = helperMatrix.getElementAt(to);

        head.markAsInitPathElement();

        // Elements to visit
        Set<MatrixElementWithPath> queue = new HashSet<>();
        queue.add(head);

        while (!tail.isVisited()) {
            // Next element to visit is the one having the lowest interim weigh
            MatrixElementWithPath currentElement = queue.stream()
                    .min(Comparator.comparingInt(MatrixElementWithPath::getInterimWeight))
                    .orElseThrow(() -> new RuntimeException("Could not find the next element to visit!"));

            Collection<MatrixElementWithPath> unvisitedNeighbours = getNeighbours(helperMatrix, currentElement).stream()
                    .filter(element -> !element.isVisited())
                    .collect(Collectors.toSet());

            unvisitedNeighbours.forEach(neighbour -> {
                int result = compareWeightedElements(neighbour, currentElement);

                // New interim weight (indicating a "shorter" distance) is found, take the neighbour's paths
                if (result > 0) {
                    neighbour.setPaths(currentElement);

                // Same interim weight is found, converge the element's and the neighbour's paths
                } else if (result == 0) {
                    neighbour.addToPaths(currentElement);
                }

                queue.add(neighbour);
            });

            // Mark as visited when the adjacent elements have been visited
            currentElement.setVisited();
            queue.remove(currentElement);
        }

        return tail.getPaths();
    }

    /**
     * Creates a helper matrix that contains the interim weights of the elements and paths.
     *
     * @return A matrix
     */
    private Matrix<MatrixElementWithPath> createMatrixWithPaths() {
        int size = originalMatrix.getSize();
        MatrixElementWithPath[][] interimMatrix = new MatrixElementWithPath[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordinate coordinate = Coordinate.from(i, j);

                MatrixElementWithPath interimElement = new MatrixElementWithPath(originalMatrix.getElementAt(coordinate), coordinate);
                interimMatrix[i][j] = interimElement;
            }
        }

        return new Matrix<>(interimMatrix);
    }

    /**
     * Compares the current interim weight of the target element with a new calculated interim weight (source -> target).
     *
     * @param target The element to check the interim weight of
     * @param source The element to
     * @return Return an numeric value that indicates whether the current interim weight is:
     *  less than the new weight (returns -1),
     *  greater than the new weight (returns 1)
     *  equal the new weight (returns 0).
     */
    private static int compareWeightedElements(MatrixElementWithPath target, MatrixElementWithPath source) {
        int currentWeight = target.getInterimWeight();
        int newWeight = source.getInterimWeight() + target.getWeight();

        if (currentWeight < newWeight) {
            return -1;
        } else if (currentWeight > newWeight) {
            return 1;
        }
        return 0;
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
