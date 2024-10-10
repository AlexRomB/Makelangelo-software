package com.marginallyclever.convenience;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class LineInterpolatorTest {

    private static final double DELTA = 1e-6;

    // Test du constructeur par défaut
    @Test
    public void testDefaultConstructor() {

        // ACT
        LineInterpolator interpolator = new LineInterpolator();

        // ASSERT
        assertEquals(0.0, interpolator.getStart().x, DELTA, "Default start point x should be 0.0.");
        assertEquals(0.0, interpolator.getStart().y, DELTA, "Default start point y should be 0.0.");
        assertEquals(0.0, interpolator.getEnd().x, DELTA, "Default end point x should be 0.0.");
        assertEquals(0.0, interpolator.getEnd().y, DELTA, "Default end point y should be 0.0.");
    }
    
    // Test de la construction avec des points définis
    @Test
    public void testConstructorWithPoints() {

        // ARRANGE
        Point2D start = new Point2D(1, 2);
        Point2D end = new Point2D(3, 4);

        // ACT
        LineInterpolator interpolator = new LineInterpolator(start, end);

        // ASSERT
        assertEquals(start.x, interpolator.getStart().x, DELTA, "Start point x-coordinate should be initialized correctly.");
        assertEquals(start.y, interpolator.getStart().y, DELTA, "Start point y-coordinate should be initialized correctly.");
        assertEquals(end.x, interpolator.getEnd().x, DELTA, "End point x-coordinate should be initialized correctly.");
        assertEquals(end.y, interpolator.getEnd().y, DELTA, "End point y-coordinate should be initialized correctly.");
    }


    // Test des setters et getters
    @Test
    public void testSettersAndGetters() {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator();
        Point2D start = new Point2D(5, 6);
        Point2D end = new Point2D(7, 8);

        // ACT
        interpolator.setStart(start);
        interpolator.setEnd(end);

        // ASSERT
        assertEquals(start.x, interpolator.getStart().x, DELTA, "Start point x-coordinate should be updated correctly.");
        assertEquals(start.y, interpolator.getStart().y, DELTA, "Start point y-coordinate should be updated correctly.");
        assertEquals(end.x, interpolator.getEnd().x, DELTA, "End point x-coordinate should be updated correctly.");
        assertEquals(end.y, interpolator.getEnd().y, DELTA, "End point y-coordinate should be updated correctly.");
    }


    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0, 2.0", // t=0 (debut)
            "1.0, 3.0, 4.0", // t=1 (fin)
            "0.5, 2.0, 3.0"  // t=0.5 (millieu)
    })
    // Test de la récupération de point selon t
    public void testGetPoint(double t, double expectedX, double expectedY) {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(1, 2), new Point2D(3, 4));
        Point2D p = new Point2D();

        // ACT
        interpolator.getPoint(t, p);

        // ASSERT
        assertEquals(expectedX, p.x, DELTA, "Point x-coordinate mismatch at t=" + t);
        assertEquals(expectedY, p.y, DELTA, "Point y-coordinate mismatch at t=" + t);
    }

    // Test de l'extrapolation de points en dehors de l'intervalle [0, 1]
    @Test
    public void testGetPointOutsideRange() {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(0, 0), new Point2D(2, 2));
        Point2D p = new Point2D();

        // ACT
        interpolator.getPoint(-0.5, p);

        // ASSERT
        assertEquals(-1.0, p.x, DELTA, "Point at t=-0.5 x-coordinate should extrapolate correctly.");
        assertEquals(-1.0, p.y, DELTA, "Point at t=-0.5 y-coordinate should extrapolate correctly.");

        // ACT
        interpolator.getPoint(1.5, p);

        // ASSERT
        assertEquals(3.0, p.x, DELTA, "Point at t=1.5 x-coordinate should extrapolate correctly.");
        assertEquals(3.0, p.y, DELTA, "Point at t=1.5 y-coordinate should extrapolate correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0, 0.0",  // t=0 (debut)
            "1.0, 1.0, 0.0",  // t=1 (fin)
            "0.5, 1.0, 0.0"   // t=0.5 (millieu)
    })

    // Test de la tangente en fonction de t
    public void testGetTangent(double t, double expectedX, double expectedY) {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(0, 0), new Point2D(4, 0));
        Point2D tangent = new Point2D();

        // ACT
        interpolator.getTangent(t, tangent);

        // ASSERT
        assertEquals(expectedX, tangent.x, DELTA, "Tangent x-coordinate mismatch at t=" + t);
        assertEquals(expectedY, tangent.y, DELTA, "Tangent y-coordinate mismatch at t=" + t);
    }

    // Test de la tangente et normale avec t négatif
    @Test
    public void testGetTangentAndNormalWithNegativeT() {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(1, 1), new Point2D(4, 5));
        Point2D v = new Point2D();
        Point2D n = new Point2D();

        // ACT
        interpolator.getTangent(-0.5, v);
        interpolator.getNormal(-0.5, n);

        // ASSERT
        double length = Math.sqrt(3 * 3 + 4 * 4);
        assertEquals(3 / length, v.x, DELTA, "Tangent x should be calculated at t=0.");
        assertEquals(4 / length, v.y, DELTA, "Tangent y should be calculated at t=0.");

        assertEquals(4 / length, n.x, DELTA, "Normal x should be calculated at t=0.");
        assertEquals(-3 / length, n.y, DELTA, "Normal y should be calculated at t=0.");
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 3.0, 4.0",  // debut
            "1.0, 3.0, 4.0",  // fin
            "0.5, 3.0, 4.0"   // millieu
    })
    // Test de la tangente pour une ligne inclinée
    public void testGetTangentForAngledLine(double t, double expectedX, double expectedY) {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(0, 0), new Point2D(3, 4));
        Point2D tangent = new Point2D();

        // ACT
        interpolator.getTangent(t, tangent);

        // ASSERT
        double length = Math.sqrt(expectedX * expectedX + expectedY * expectedY);
        assertEquals(expectedX / length, tangent.x, DELTA, "Tangent x-coordinate mismatch at t=" + t);
        assertEquals(expectedY / length, tangent.y, DELTA, "Tangent y-coordinate mismatch at t=" + t);
    }

    // Test de la tangente et normale avec t supérieur à 1
    @Test
    public void testGetTangentAndNormalWithTGreaterThanOne() {

        // ARRANGE
        LineInterpolator interpolator = new LineInterpolator(new Point2D(1, 1), new Point2D(4, 5));
        Point2D v = new Point2D();
        Point2D n = new Point2D();

        // ACT
        interpolator.getTangent(1.5, v);
        interpolator.getNormal(1.5, n);

        // ASSERT
        double length = Math.sqrt(3 * 3 + 4 * 4);
        assertEquals(3 / length, v.x, DELTA, "Tangent x should be calculated at t=1 - SMALL_VALUE.");
        assertEquals(4 / length, v.y, DELTA, "Tangent y should be calculated at t=1 - SMALL_VALUE.");

        assertEquals(4 / length, n.x, DELTA, "Normal x should be calculated at t=1 - SMALL_VALUE.");
        assertEquals(-3 / length, n.y, DELTA, "Normal y should be calculated at t=1 - SMALL_VALUE.");
    }
}
