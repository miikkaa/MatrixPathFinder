package se.giraff.matrix.primitives;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatrixElementWithPath extends MatrixElement {

    private final Coordinate coordinate;

    private Set<Path> paths;
    private boolean visited;
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

    public void setOpeningPath() {
        paths = new HashSet<>();
        paths.add(new Path(coordinate));
        interimWeight = getWeight();
    }

    public void setPaths(MatrixElementWithPath element) {
        paths = new HashSet<>();
        addToPaths(element);
        interimWeight = element.getInterimWeight() + this.getWeight();
    }

    public void addToPaths(MatrixElementWithPath element) {
        Set<Path> clonedPaths = cloneAndExtendPaths(element);
        paths.addAll(clonedPaths);
    }

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
