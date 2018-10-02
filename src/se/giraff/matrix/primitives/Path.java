package se.giraff.matrix.primitives;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Path {

    private Collection<Coordinate> coordinates;

    public Path(Coordinate head) {
        this.coordinates = new LinkedHashSet<>();
        addCoordinate(head);
    }

    private Path(Path other) {
        this.coordinates = new LinkedHashSet<>(other.coordinates);
    }

    public void addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public Path copy() {
        return new Path(this);
    }

    public String toString() {
        return coordinates.stream()
                .map(Coordinate::toString)
                .collect(Collectors.joining(", "));
    }
}
