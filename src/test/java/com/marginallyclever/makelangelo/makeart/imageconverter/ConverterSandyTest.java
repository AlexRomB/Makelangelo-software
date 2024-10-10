package com.marginallyclever.makelangelo.makeart.imageconverter;

import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.makelangelo.makeart.TransformedImage;
import com.marginallyclever.makelangelo.makeart.imagefilter.FilterDesaturate;
import com.marginallyclever.makelangelo.paper.Paper;
import com.marginallyclever.makelangelo.turtle.Turtle;
import com.marginallyclever.util.PreferencesHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterSandyTest {

    @BeforeAll
    public static void init() {
        PreferencesHelper.start();
        Translator.start();
    }

    /**
     * Méthode testGetName : Vérifie que la méthode getName retourne le nom correct depuis le Translator.
     */
    @Test
    public void testGetName() {
        // ARRANGE
        Converter_Sandy converter = new Converter_Sandy();
        String expectedName = Translator.get("SandyNoble.title");

        // ACT
        String actualName = converter.getName();

        // ASSERT
        assertEquals(expectedName, actualName, "getName devrait retourner le nom correct.");
    }

    /**
     * Méthode testSetAndGetScale : Vérifie que les méthodes setScale et getScale fonctionnent correctement, y compris pour les valeurs limites.
     */
    @Test
    public void testSetAndGetScale() {
        // ARRANGE
        Converter_Sandy converter = new Converter_Sandy();

        // ACT & ASSERT
        int defaultScale = converter.getScale();
        assertEquals(150, defaultScale, "L'échelle par défaut devrait être 150.");

        converter.setScale(100);
        assertEquals(100, converter.getScale(), "L'échelle devrait être mise à jour à 100.");

        converter.setScale(0);
        assertEquals(1, converter.getScale(), "L'échelle devrait être définie à la valeur minimale 1 lorsque zéro est fourni.");

        converter.setScale(-10);
        assertEquals(1, converter.getScale(), "L'échelle devrait être définie à la valeur minimale 1 lorsqu'une valeur négative est fournie.");
    }

    /**
     * Méthode testSetAndGetDirection : Vérifie que les méthodes setDirection et getDirectionIndex fonctionnent correctement, y compris pour les valeurs limites.
     */
    @Test
    public void testSetAndGetDirection() {
        // ARRANGE
        Converter_Sandy converter = new Converter_Sandy();
        String[] directions = converter.getDirections();

        // ACT & ASSERT
        int defaultDirectionIndex = converter.getDirectionIndex();
        assertEquals(0, defaultDirectionIndex, "L'indice de direction par défaut devrait être 0.");

        converter.setDirection(2);
        assertEquals(2, converter.getDirectionIndex(), "L'indice de direction devrait être mis à jour à 2.");

        converter.setDirection(-5);
        assertEquals(0, converter.getDirectionIndex(), "L'indice de direction devrait être défini à 0 lorsqu'une valeur négative est fournie.");

        converter.setDirection(directions.length + 5);
        assertEquals(directions.length - 1, converter.getDirectionIndex(), "L'indice de direction devrait être défini au maximum valide lorsque la valeur dépasse la longueur du tableau.");
    }

    /**
     * Méthode testStartMethod : Vérifie que la méthode start initialise correctement le processus de conversion et crée les mouvements du Turtle.
     */
    @Test
    public void testStartMethod() {
        // ARRANGE
        TestableConverterSandy converter = new TestableConverterSandy();
        StubPaper paper = new StubPaper();
        StubTransformedImage image = new StubTransformedImage();
        converter.setMyImage(image);

        // ACT
        converter.start(paper, image);

        // ASSERT
        assertNotNull(converter.getTurtle(), "Le Turtle devrait être initialisé après l'appel de start.");

        assertTrue(converter.isConversionFinished(), "La conversion devrait être marquée comme terminée.");

        Turtle turtle = converter.getTurtle();
        assertTrue(turtle.getHasAnyDrawingMoves(), "Le Turtle devrait avoir enregistré des mouvements de dessin.");
    }

    /**
     *     Sous-classe de Converter_Sandy pour permettre l'accès aux composants internes comme turtle et myImage
     */
    static class TestableConverterSandy extends Converter_Sandy {
        private boolean conversionFinished = false;

        @Override
        protected void fireConversionFinished() {
            super.fireConversionFinished();
            this.conversionFinished = true;
        }

        public boolean isConversionFinished() {
            return conversionFinished;
        }

        public Turtle getTurtle() {
            return turtle;
        }

        public void setMyImage(TransformedImage image) {
            this.myImage = image;
        }
    }

    /**
     * Implémentation simulée de Paper pour fournir des dimensions et des marges cohérentes pour les tests
     */
    static class StubPaper extends Paper {
        @Override
        public double getPaperBottom() {
            return -100.0;
        }

        @Override
        public double getPaperTop() {
            return 100.0;
        }

        @Override
        public double getPaperLeft() {
            return -100.0;
        }

        @Override
        public double getPaperRight() {
            return 100.0;
        }

        @Override
        public Rectangle2D.Double getMarginRectangle() {
            return new Rectangle2D.Double(-90.0, -90.0, 180.0, 180.0);
        }

        @Override
        public double getCenterX() {
            return 0.0;
        }

        @Override
        public double getCenterY() {
            return 0.0;
        }
    }

    /**
     * Implémentation simulée de TransformedImage
     */
    static class StubTransformedImage extends TransformedImage {
        public StubTransformedImage() {
            super(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        }

        @Override
        public int sample(double x1, double y1, double x2, double y2) {
            return 128; // Gris
        }
    }

    /**
    *Implémentation simulée de FilterDesaturate
     */
    static class StubFilterDesaturate extends FilterDesaturate {
        private TransformedImage myImage;

        public StubFilterDesaturate(TransformedImage image) {
            super(image);
            this.myImage = image;
        }

        @Override
        public TransformedImage filter() {
            return myImage; // Retourne l'image stockée dans cette classe
        }
    }
}
