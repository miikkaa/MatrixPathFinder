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

    public int getNumberOfElements() {
        return size * size;
    }

    public T getElementAt(Coordinate coordinate) {
        if (validateIndex(coordinate.getX())) {
            throw new RuntimeException(String.format("Invalid X-coordinate, expecting a value within the <0-%s> range!", this.size));
        }
        if (validateIndex(coordinate.getY())) {
            throw new RuntimeException(String.format("Invalid Y-coordinate, expecting a value within the <0-%s> range!", this.size));
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
