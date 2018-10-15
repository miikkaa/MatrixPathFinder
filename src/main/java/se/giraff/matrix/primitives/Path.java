package se.giraff.matrix.primitives;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Path {

    private Set<Coordinate> coordinates;

    public Path(Coordinate head) {
        this.coordinates = new LinkedHashSet<>();
        addCoordinate(head);
    }

    private Path(Path other) {
        this.coordinates = new LinkedHashSet<>(other.coordinates);
    }

    public Path addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
        return this;
    }

    public boolean contains(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }

    public Path lengthen(Coordinate coordinate) {
        return new Path(this).addCoordinate(coordinate);
    }

    public String toString() {
        return coordinates.stream()
                .map(Coordinate::toString)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path = (Path) o;
        if (Objects.equals(coordinates, path.coordinates)) {
            return true;
        }
        return path.coordinates.containsAll(coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
