package se.giraff.matrix.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.MatrixElement;
import se.giraff.matrix.primitives.MatrixElementWithPath;
import se.giraff.matrix.primitives.Path;

class MatrixPathfinderUtilsTest {

    @Test
    void testCalculateDistances() {
        MatrixElementWithPath element00 = createElement(1, 0, 0);
        element00.setDistance(element00.getWeight());

        MatrixElementWithPath element01 = createElement(2, 0, 1);

        // Distance between 0,0 -> 0,1 is their combined weight
        int expectedDistance = element00.getWeight() + element01.getWeight();
        int calculatedDistance = MatrixPathfinderUtils.calculateDistance(element00, element01);

        assertEquals(expectedDistance, calculatedDistance);
    }

    @Test
    void testCompareDistances() {
        MatrixElementWithPath element00 = createElement(1, 0, 0);
        element00.setDistance(element00.getWeight());

        MatrixElementWithPath element01 = createElement(2, 0, 1);
        // 0,0 -> 0,1
        element01.setDistance(MatrixPathfinderUtils.calculateDistance(element00, element01));

        MatrixElementWithPath element10 = createElement(5, 1, 0);
        // 0,0 -> 1,0
        element10.setDistance(MatrixPathfinderUtils.calculateDistance(element00, element10));

        MatrixElementWithPath element11 = createElement(10, 1, 1);

        // Set distance via 1,0 (0,0 -> 1,0 -> 1,1)
        element11.setDistance(MatrixPathfinderUtils.calculateDistance(element10, element11));

        // Ensure path via 0,1 is shorter
        assertTrue(element01.getWeight() < element10.getWeight());
        assertEquals(1, MatrixPathfinderUtils.compareDistance(element01, element11));

        // Set distance via 0,1 (0,0 -> 0,1 -> 1,1)
        element11.setDistance(MatrixPathfinderUtils.calculateDistance(element01, element11));

        // Ensure path via 1,0 is longer
        assertEquals(-1, MatrixPathfinderUtils.compareDistance(element10, element11));


        // Clone with a new weight (= 0,1 weight)
        MatrixElementWithPath newElement10 = createElement(element01.getWeight(), element10.getCoordinate().getX(), element10.getCoordinate().getY());
        // 0,0 -> 1,0
        newElement10.setDistance(MatrixPathfinderUtils.calculateDistance(element00, newElement10));

        // Ensure path via 0,1 or NEW 1,0 is the same
        assertEquals(0, MatrixPathfinderUtils.compareDistance(newElement10, element11));
    }

    @Test
    void testInitialPath() {
        MatrixElementWithPath element00 = createElement(1, 0, 0);
        MatrixPathfinderUtils.setInitialPath(element00);

        assertEquals(element00.getWeight(), element00.getDistance());
        assertEquals(element00.getPaths().size(), 1);
    }

    @Test
    void testUpdatePaths() {
        MatrixElementWithPath element00 = createElement(1, 0, 0);
        MatrixPathfinderUtils.setInitialPath(element00);


        MatrixElementWithPath element01 = createElement(5, 0, 1);

        assertNull(element01.getPaths());
        int expectedDistance00to01 = MatrixPathfinderUtils.calculateDistance(element00, element01);

        // ==========
        // 0,0 -> 0,1
        MatrixPathfinderUtils.updatePaths(element00, element01);

        assertNotNull(element01.getPaths());
        assertEquals(1, element01.getPaths().size());
        assertEquals(expectedDistance00to01, element01.getDistance());

        Path path = element01.getPaths().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path not found!"));

        assertPathContainsElements(path, element00, element01);


        // ==========
        MatrixElementWithPath element10 = createElement(10, 1, 0);
        // 0,0 -> 1,0
        MatrixPathfinderUtils.updatePaths(element00, element10);


        // ==========
        MatrixElementWithPath element11 = createElement(10, 1, 1);
        // 0,0 -> 0,1 -> 1,1
        MatrixPathfinderUtils.updatePaths(element01, element11);

        assertEquals(1, element11.getPaths().size());
        path = element11.getPaths().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path not found!"));

        assertPathContainsElements(path, element00, element01, element11);
        int distance00to11 = element00.getWeight() + element01.getWeight() + element11.getWeight();
        assertEquals(distance00to11, element11.getDistance());


        // ==========
        // 0,0 -> 1,0 -> 1,1
        MatrixPathfinderUtils.updatePaths(element10, element11);

        // Ensure new distance is set
        assertNotEquals(distance00to11, element11.getDistance());
        distance00to11 = element00.getWeight() + element10.getWeight() + element11.getWeight();
        assertEquals(distance00to11, element11.getDistance());

        assertEquals(1, element11.getPaths().size());
        path = element11.getPaths().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path not found!"));

        assertPathContainsElements(path, element00, element10, element11);
    }

    @Test
    void testJoinPaths() {
        MatrixElementWithPath element00 = createElement(1, 0, 0);
        MatrixPathfinderUtils.setInitialPath(element00);

        Path path = getFirstPath(element00);
        assertEquals(1, element00.getPaths().size());
        assertTrue(path.contains(element00.getCoordinate()));

        // ==========
        MatrixElementWithPath element01 = createElement(5, 0, 1);

        assertNull(element01.getPaths());
        int expectedDistance00to01 = MatrixPathfinderUtils.calculateDistance(element00, element01);

        // 0,0 -> 0,1
        MatrixPathfinderUtils.updatePaths(element00, element01);

        // Ensure the original path is unchanged
        assertEquals(1, element00.getPaths().size());
        path = getFirstPath(element00);
        assertTrue(path.contains(element00.getCoordinate()));
        assertFalse(path.contains(element01.getCoordinate()));

        // Ensure paths are set and the distance is updated
        assertNotNull(element01.getPaths());
        assertEquals(1, element01.getPaths().size());
        assertEquals(expectedDistance00to01, element01.getDistance());

        path = getFirstPath(element01);

        assertPathContainsElements(path, element00, element01);


        // ==========
        MatrixElementWithPath element10 = createElement(5, 1, 0);
        // 0,0 -> 1,0
        MatrixPathfinderUtils.updatePaths(element00, element10);


        // ==========
        MatrixElementWithPath element11 = createElement(10, 1, 1);
        // 0,0 -> 0,1 -> 1,1
        MatrixPathfinderUtils.updatePaths(element01, element11);

        int distance00to11 = element00.getWeight() + element01.getWeight() + element11.getWeight();
        assertEquals(distance00to11, element11.getDistance());
        assertEquals(1, element11.getPaths().size());
        path = getFirstPath(element11);

        assertPathContainsElements(path, element00, element01, element11);


        // ==========
        // Add 0,0 -> 1,0 -> 1,1
        MatrixPathfinderUtils.joinPaths(element10, element11);

        assertEquals(distance00to11, element11.getDistance());
        assertEquals(2, element11.getPaths().size());
        element11.getPaths()
                .forEach(p -> assertPathContainsElements(p, element00, element11));
    }

    private MatrixElementWithPath createElement(int elementWeight, int coordinateX, int coordinateY) {
        return new MatrixElementWithPath(new MatrixElement(elementWeight), Coordinate.from(coordinateX, coordinateY));
    }

    private void assertPathContainsElements(Path path, MatrixElementWithPath... elements) {
        for (MatrixElementWithPath element : elements) {
            assertTrue(path.contains(element.getCoordinate()));
        }
    }

    private Path getFirstPath(MatrixElementWithPath element) {
        assert element.getPaths() != null;
        assert element.getPaths().size() > 0;

        return element.getPaths().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path not found!"));
    }
}