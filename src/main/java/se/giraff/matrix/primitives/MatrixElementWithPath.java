package se.giraff.matrix.primitives;

import java.util.Set;

public class MatrixElementWithPath extends MatrixElement {

    private final Coordinate coordinate;

    /**
     * Paths leading from a start coordinate of a matrix being visited to the current element.
     */
    private Set<Path> paths;

    /**
     * Distance represents an accumulated weight of the elements in a node's paths (i.e the length of a path).
     * Initial distance of a node is "infinite", indicating it has not been visited.
     */
    private int distance = Integer.MAX_VALUE;

    public MatrixElementWithPath(MatrixElement original, Coordinate coordinate) {
        super(original.getWeight());
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }

    public String toString() {
        return coordinate.toString() + " (w: " + getWeight() + ", d: " + distance + ")";
    }
}
