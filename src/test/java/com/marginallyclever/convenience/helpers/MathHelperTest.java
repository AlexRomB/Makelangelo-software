package com.marginallyclever.convenience.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import javax.vecmath.Point2d;

public class MathHelperTest {
    @Test
    public void testBetween() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, Math.random());
            assert(MathHelper.between(a, b, c, epsilon));
        }
    }

    // Test que deux paires de points sont considérées égales à l'intérieur d'un epsilon donné
    @Test
    public void testEqualTuplesWithinEpsilon() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0001, 2.0001);
        Point2d a1 = new Point2d(3.0001, 4.0001);
        Point2d b0 = new Point2d(1.0002, 2.0002);
        Point2d b1 = new Point2d(3.0002, 4.0002);
        double epsilon = 0.001;

        // ACT & ASSERT
        assert(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }

    // Test que deux paires de points ne sont pas considérées égales au-delà d'un epsilon donné
    @Test
    public void testNotEqualTuplesOutsideEpsilon() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(1.5, 2.5);
        Point2d b1 = new Point2d(3.5, 4.5);
        double epsilon = 0.1;

        // ACT & ASSERT
        assertFalse(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }

    // Test que l'ordre des tuples n'affecte pas l'égalité dans un epsilon donné
    @Test
    public void testSwappedTuplesWithinEpsilon() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(3.00001, 4.00001);
        Point2d b1 = new Point2d(1.00001, 2.00001);
        double epsilon = 0.001;

        // ACT & ASSERT
        assert(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }

    // Test que même en inversant l'ordre, les tuples ne sont pas égaux au-delà d'un certain epsilon
    @Test
    public void testNotEqualTuplesEvenWhenSwapped() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(5.0, 6.0);
        Point2d b1 = new Point2d(7.0, 8.0);
        double epsilon = 0.1;

        // ACT & ASSERT
        assertFalse(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }

    // Test que deux tuples ne sont pas égaux avec un epsilon de zéro, sauf s'ils sont strictement identiques
    @Test
    public void testEqualTuplesWithZeroEpsilon() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(1.0, 2.0);
        Point2d b1 = new Point2d(3.0, 4.0);
        double epsilon = 0.0;

        // ACT & ASSERT
        assertFalse(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }



    // Test que même avec un epsilon très petit, les tuples sont considérés différents
    @Test
    public void testVerySmallEpsilon() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(1.000001, 2.000001);
        Point2d b1 = new Point2d(3.000001, 4.000001);
        double epsilon = 1e-6;

        // ACT & ASSERT
        assertFalse(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }

    // Test que deux paires de tuples identiques sont égales à l'intérieur d'un epsilon
    @Test
    public void testDegenerateCaseSameTuples() {

        // ARRANGE
        Point2d a0 = new Point2d(1.0, 2.0);
        Point2d a1 = new Point2d(3.0, 4.0);
        Point2d b0 = new Point2d(1.0, 2.0);
        Point2d b1 = new Point2d(3.0, 4.0);
        double epsilon = 0.1;

        // ACT & ASSERT
        assert(MathHelper.equals(a0, a1, b0, b1, epsilon));
    }


    // Test que la méthode lengthSquared calcule correctement la somme des carrés des coordonnées
    @Test
    public void testLenghtSquared() {

        // ARRANGE
        double x = 2;
        double y = 5;

        // ACT & ASSERT
        assert(MathHelper.lengthSquared(x, y) == 29);
    }

    @Test
    public void testNotBetween() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for(int i=0;i<50;++i) {
            a.set(Math.random()*500-250, Math.random()*500-250);
            b.set(Math.random()*500-250, Math.random()*500-250);
            c = MathHelper.lerp(a,b,1.0+epsilon+Math.random());
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for(int i=0;i<50;++i) {
            a.set(Math.random()*500-250, Math.random()*500-250);
            b.set(Math.random()*500-250, Math.random()*500-250);
            c = MathHelper.lerp(a,b,-epsilon-Math.random());
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }
    }


    @Test
    public void testOffLine() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d ortho = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for(int i=0;i<50;++i) {
            a.set(Math.random()*500-250, Math.random()*500-250);
            b.set(Math.random()*500-250, Math.random()*500-250);
            c = MathHelper.lerp(a,b,Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x+=ortho.y;
            c.y+=ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for(int i=0;i<50;++i) {
            a.set(Math.random()*500-250, Math.random()*500-250);
            b.set(Math.random()*500-250, Math.random()*500-250);
            c = MathHelper.lerp(a,b,1.0+epsilon+Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x+=ortho.y;
            c.y+=ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for(int i=0;i<50;++i) {
            a.set(Math.random()*500-250, Math.random()*500-250);
            b.set(Math.random()*500-250, Math.random()*500-250);
            c = MathHelper.lerp(a,b,-epsilon-Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x+=ortho.y;
            c.y+=ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }
    }
}
