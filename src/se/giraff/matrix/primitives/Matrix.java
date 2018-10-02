package se.giraff.matrix.primitives;

public class Matrix<T extends MatrixElement> {

    /**
     * Misha: Assuming we are working with a square matrix.
     * Otherwise, use this as a base class with X-size and Y-size and create a subclass SquareMatrix.
     */
    private final int size;
    private final T[][] elements;

    public Matrix(int size, T[][] elements) {
        this.size = size;
        this.elements = elements;
    }

    public int getSize() {
        return size;
    }

    public T getElementAt(Coordinate coordinate) {
        final String outOfBoundsMessage = "Invalid %s-coordinate, expecting a value within the <0-%s> range!";

        if (validateIndex(coordinate.getX())) {
            throw new IndexOutOfBoundsException(String.format(outOfBoundsMessage, "X", this.size));
        }
        if (validateIndex(coordinate.getY())) {
            throw new IndexOutOfBoundsException(String.format(outOfBoundsMessage, "Y", this.size));
        }
        return elements[coordinate.getX()][coordinate.getY()];
    }

    public boolean hasElementAt(Coordinate coordinate) {
        return validateIndex(coordinate.getX()) &&
                validateIndex(coordinate.getY());
    }

    private boolean validateIndex(int index) {
        return index < 0 || index >= size;
    }
}
