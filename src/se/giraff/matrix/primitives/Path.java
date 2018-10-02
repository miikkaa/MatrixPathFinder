package se.giraff.matrix.primitives;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Path {

    private Collection<Coordinate> path;

    public Path(Coordinate head) {
        this.path = new LinkedHashSet<>();
        add(head);
    }

    private Path(Path another) {
        this.path = new LinkedHashSet<>(another.path);
    }

    public void add(Coordinate coordinate) {
        path.add(coordinate);
    }

    public Path clone() {
        return new Path(this);
    }

    public String toString() {
        return path.stream()
                .map(Coordinate::toString)
                .collect(Collectors.joining(", "));
    }
}
