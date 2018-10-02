package se.giraff.matrix.primitives;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PathInterimElement extends MatrixElement {

    private final MatrixElement original;
    private final Coordinate coordinate;
    private boolean visited;
    private int interimWeight;

    private Set<Path> paths = null;

    public PathInterimElement(MatrixElement original, Coordinate coordinate) {
        super(original.getWeight());
        this.original = original;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public MatrixElement getOriginal() {
        return original;
    }

    public boolean isVisited() {
        return visited;
    }

    public void markAsVisited() {
        visited = true;
    }

    public int getWeight() {
        return original.getWeight();
    }

    public int getInterimWeight() {
        if (!hasInterimWeight()) {
            throw new RuntimeException("This node does not have an interim weight yet!");
        }
        return interimWeight;
    }

    public boolean hasInterimWeight() {
        return paths != null;
    }

    public void setOriginalPath() {
        paths = new LinkedHashSet<>();
        paths.add(new Path(coordinate));
        interimWeight = getWeight();
    }

    public void setPath(Path path) {
        paths = new LinkedHashSet<>();
        paths.add(path);
        interimWeight = getWeight();
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(PathInterimElement element) {
        paths = element.getPaths().stream()
                .map(Path::clone)
                .collect(Collectors.toSet());
        interimWeight = element.getInterimWeight();
        addToPaths(this);
    }

    public void addToPaths(PathInterimElement element) {
        paths.forEach(p -> p.add(element.getCoordinate()));
        interimWeight += element.getWeight();
    }

    public String toString() {
        return coordinate.toString() + " (w: " + getWeight() +  ", iw: " + interimWeight + ")";
    }
}
