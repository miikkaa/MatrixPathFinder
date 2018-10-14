package se.giraff.matrix.primitives;

public class Matrix<T extends MatrixElement> {

    /**
     * Misha: Assuming we are working with a square matrix.
     * Otherwise, use this as a base class with X-size and Y-size and create a subclass SquareMatrix.
     */
    private final int size;
    private final T[][] elements;

    public Matrix(T[][] elements) {
        assertSquareMatrix(elements);

        this.elements = elements;
        this.size = elements.length;
    }

    public int getSize() {
        return size;
    }

    public T getElementAt(Coordinate coordinate) {
        final String outOfBoundsMessage = "Invalid %s-coordinate, expecting a value within the <0-%s> range!";

        if (!isValidIndex(coordinate.getX())) {
            throw new IndexOutOfBoundsException(String.format(outOfBoundsMessage, "X", this.size));
        }
        if (!isValidIndex(coordinate.getY())) {
            throw new IndexOutOfBoundsException(String.format(outOfBoundsMessage, "Y", this.size));
        }
        return elements[coordinate.getX()][coordinate.getY()];
    }

    public boolean hasElementAt(Coordinate coordinate) {
        return isValidIndex(coordinate.getX()) &&
                isValidIndex(coordinate.getY());
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }

    private void assertSquareMatrix(T[][] elements) {
        assert elements != null;
        assert elements.length > 0;

        int size = elements.length;
        for (T[] element : elements) {
            assert element.length == size;
        }
    }
}
