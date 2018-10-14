package se.giraff.matrix.primitives;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatrixElementWithPath extends MatrixElement {

    private final Coordinate coordinate;

    private Set<Path> paths;
    private boolean visited;

    // Initial weight of the node is "infinite", indicating it has not been visited
    private int interimWeight = Integer.MAX_VALUE;

    public MatrixElementWithPath(MatrixElement original, Coordinate coordinate) {
        super(original.getWeight());
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public int getInterimWeight() {
        return interimWeight;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    /**
     * Initializes the element which is the head (the initial element) of the path with an initial path.
     */
    public void markAsInitPathElement() {
        paths = new HashSet<>();
        paths.add(new Path(coordinate));
        interimWeight = getWeight();
    }

    /**
     * Copies the paths of the target element and sets them on the current element. The coordinate of the element is included.
     * Updates the interim weight.
     *
     * @param element The target element to take the paths of
     */
    public void setPaths(MatrixElementWithPath element) {
        paths = new HashSet<>();
        addToPaths(element);
        interimWeight = element.getInterimWeight() + this.getWeight();
    }

    /**
     * Adds the paths of the target element to the current element.
     * Copying paths, which indicates that the interim weight is unchanged
     *
     * @param element The target element to copy the paths of
     */
    public void addToPaths(MatrixElementWithPath element) {
        Set<Path> clonedPaths = cloneAndExtendPaths(element);
        paths.addAll(clonedPaths);
    }

    /**
     * Copies the paths of the target element, extended with the coordinate of the current element.
     *
     * @param element The target element to copy the paths of
     * @return A collection of paths that include the current coordinate
     */
    private Set<Path> cloneAndExtendPaths(MatrixElementWithPath element) {
        return element.getPaths().stream()
                .map(path -> {
                    Path newPath = path.copy();
                    newPath.addCoordinate(this.getCoordinate());
                    return newPath;
                })
                .collect(Collectors.toSet());
    }

    public String toString() {
        return coordinate.toString() + " (w: " + getWeight() +  ", iw: " + interimWeight + ")";
    }
}
