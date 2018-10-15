/*
 * Copyright (c) 2009-2018 Ericsson AB, Sweden. All rights reserved.
 *
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden.
 * The program(s) may be used  and/or copied with the written permission from Ericsson AB
 * or in accordance with the terms and conditions stipulated in the agreement/contract under
 * which the program(s) have been supplied.
 *
 */
package se.giraff.matrix.primitives;

import java.util.Collection;

public class MatrixTrace {

    private final int distance;
    private final Collection<Path> paths;

    public MatrixTrace(int distance, Collection<Path> paths) {
        this.distance = distance;
        this.paths = paths;
    }

    public int getDistance() {
        return distance;
    }

    public Collection<Path> getPaths() {
        return paths;
    }
}
