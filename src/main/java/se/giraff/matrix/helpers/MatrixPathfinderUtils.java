/*
 * Copyright (c) 2009-2018 Ericsson AB, Sweden. All rights reserved.
 *
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden.
 * The program(s) may be used  and/or copied with the written permission from Ericsson AB
 * or in accordance with the terms and conditions stipulated in the agreement/contract under
 * which the program(s) have been supplied.
 *
 */
package se.giraff.matrix.helpers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import se.giraff.matrix.primitives.Coordinate;
import se.giraff.matrix.primitives.MatrixElementWithPath;
import se.giraff.matrix.primitives.Path;

class MatrixPathfinderUtils {

    /**
     * Compares the current distance of the target element with a new calculated distance
     * (navigation direction: source -> target).
     *
     * @param source Element we are navigating from
     * @param target Element we are navigating to
     * @return Return an numeric value that indicates whether the current distance is:
     * less than the new distance (returns -1),
     * greater than the new distance (returns 1)
     * equal the new distance (returns 0).
     */
    static int compareDistance(MatrixElementWithPath source, MatrixElementWithPath target) {
        int currentDistance = target.getDistance();
        int newDistance = calculateDistance(source, target);

        if (currentDistance < newDistance) {
            return -1;
        } else if (currentDistance > newDistance) {
            return 1;
        }
        return 0;
    }

    /**
     * Calculates a distance from an element to the current element.
     *
     * @param source Element to measure distance from
     * @param target Element to measure distance to
     * @return Distance between the source and the current element
     */
    static int calculateDistance(MatrixElementWithPath source, MatrixElementWithPath target) {
        return source.getDistance() + target.getWeight();
    }

    /**
     * Sets an initial path on the starting element that all subsequent paths will be built upon.
     *
     * @param element Element to initialize
     */
    static void setInitialPath(MatrixElementWithPath element) {
        Set<Path> paths = new HashSet<>();
        paths.add(new Path(element.getCoordinate()));
        element.setPaths(paths);
        element.setDistance(element.getWeight());
    }

    /**
     * Connects the source element with the target element, replacing the target's paths.
     *
     * Copies the paths from the source element and sets them on the target element. The coordinate of the target is included.
     * The distance is updated too.
     *
     * @param source Element to take paths from
     * @param target Element to set paths on
     */
    static void updatePaths(MatrixElementWithPath source, MatrixElementWithPath target) {
        target.setPaths(copyAndLengthenPaths(source.getPaths(), target.getCoordinate()));
        target.setDistance(calculateDistance(source, target));
    }

    /**
     * Connects the source element with the target element, but retains the target's paths.
     * The paths are copied, which indicates that the distance of the element is unchanged.
     *
     * @param source Element to take paths from
     * @param target Element to add paths to
     */
    static void joinPaths(MatrixElementWithPath source, MatrixElementWithPath target) {
        Set<Path> clonedPaths = copyAndLengthenPaths(source.getPaths(), target.getCoordinate());
        target.getPaths().addAll(clonedPaths);
    }

    /**
     * Copies a collection of paths and lengthens each path with the current element.
     *
     * @param paths      Paths to copy
     * @param coordinate A coordinate to lengthen the paths with
     * @return A collection of paths that include the current coordinate
     */
    private static Set<Path> copyAndLengthenPaths(Collection<Path> paths, Coordinate coordinate) {
        return paths.stream()
                .map(path -> path.cloneAndLengthen(coordinate))
                .collect(Collectors.toSet());
    }
}
