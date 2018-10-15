package se.giraff.matrix.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixElementWithPathTest {

    @Test
    void testSetPaths() {
        MatrixElementWithPath elementFirst = new MatrixElementWithPath(new MatrixElement(1), Coordinate.from(0, 0));
        elementFirst.markAsInitPathElement();

        MatrixElementWithPath elementSecond = new MatrixElementWithPath(new MatrixElement(1), Coordinate.from(0, 1));
        elementSecond.setPaths(elementFirst);

        assertEquals(elementFirst.getPaths().size(), elementSecond.getPaths().size());

        elementFirst.getPaths().forEach(path -> {
            assertTrue(path.contains(elementFirst.getCoordinate()));
            assertFalse(path.contains(elementSecond.getCoordinate()));
        });

        elementSecond.getPaths().forEach(path -> {
            assertTrue(path.contains(elementFirst.getCoordinate()));
            assertTrue(path.contains(elementSecond.getCoordinate()));
        });

        assertEquals(elementFirst.getWeight() + elementSecond.getWeight(), elementSecond.getInterimWeight());
    }
}