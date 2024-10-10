package com.marginallyclever.makelangelo.makeart.imageconverter;

import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.util.PreferencesHelper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterFlowFieldTest {

    @BeforeAll
    public static void init() {
        PreferencesHelper.start();
        Translator.start();
    }

    @BeforeEach
    public void resetStaticFields() throws Exception {
        // Réinitialiser tous les champs statiques à leurs valeurs par défaut avant chaque test
        resetPrivateStaticField("scaleX", 0.01);
        resetPrivateStaticField("scaleY", 0.01);
        resetPrivateStaticField("offsetX", 0.0);
        resetPrivateStaticField("offsetY", 0.0);
        resetPrivateStaticField("stepSize", 10);
        resetPrivateStaticField("stepLength", 10);
        resetPrivateStaticField("stepVariation", 5);
        resetPrivateStaticField("fromEdge", false);
        resetPrivateStaticField("rightAngle", false);
        resetPrivateStaticField("cutoff", 128);
        resetPrivateStaticField("seed", 0);
    }

    private void resetPrivateStaticField(String fieldName, Object newValue) throws Exception {
        Field field = Converter_FlowField.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, newValue);
    }

    /**
     * Méthode testSetScaleX : Vérifie que la méthode setScaleX met à jour correctement le champ statique scaleX.
     */
    @Test
    public void testSetScaleX() throws Exception {
        // ARRANGE
        resetPrivateStaticField("scaleX", 0.01); // Assurer que scaleX commence à la valeur par défaut (0.01)
        double newScaleX = 0.05;

        // ACT
        Converter_FlowField.setScaleX(newScaleX);

        // ASSERT
        Field scaleXField = Converter_FlowField.class.getDeclaredField("scaleX");
        scaleXField.setAccessible(true);
        double actualScaleX = (double) scaleXField.get(null);
        assertEquals(newScaleX, actualScaleX, "scaleX devrait être mis à jour avec la nouvelle valeur");
    }

    /**
     * Méthode testSetScaleY : Vérifie que la méthode setScaleY met à jour correctement le champ statique scaleY.
     */
    @Test
    public void testSetScaleY() throws Exception {
        // ARRANGE
        resetPrivateStaticField("scaleY", 0.01); // Assurer que scaleY commence à la valeur par défaut (0.01)
        double newScaleY = 0.1;

        // ACT
        Converter_FlowField.setScaleY(newScaleY);

        // ASSERT
        Field scaleYField = Converter_FlowField.class.getDeclaredField("scaleY");
        scaleYField.setAccessible(true);
        double actualScaleY = (double) scaleYField.get(null);
        assertEquals(newScaleY, actualScaleY, "scaleY devrait être mis à jour avec la nouvelle valeur");
    }

    /**
     * Méthode testSetStepSize : Vérifie que la méthode setStepSize met à jour correctement le champ statique stepSize.
     */
    @Test
    public void testSetStepSize() throws Exception {
        // ARRANGE
        resetPrivateStaticField("stepSize", 10); // Assurer que stepSize commence à la valeur par défaut (10)
        int newStepSize = 20;

        // ACT
        Converter_FlowField.setStepSize(newStepSize);

        // ASSERT
        Field stepSizeField = Converter_FlowField.class.getDeclaredField("stepSize");
        stepSizeField.setAccessible(true);
        int actualStepSize = (int) stepSizeField.get(null);
        assertEquals(newStepSize, actualStepSize, "stepSize devrait être mis à jour avec la nouvelle valeur");
    }

    /**
     * Méthode testSetFromEdgeTrue : Vérifie que setFromEdge(true) met à jour correctement le champ statique fromEdge à true.
     */
    @Test
    public void testSetFromEdgeTrue() throws Exception {
        // ARRANGE
        resetPrivateStaticField("fromEdge", false); // Assurer que fromEdge commence à false

        // ACT
        Converter_FlowField.setFromEdge(true);

        // ASSERT
        Field fromEdgeField = Converter_FlowField.class.getDeclaredField("fromEdge");
        fromEdgeField.setAccessible(true);
        boolean actualFromEdge = (boolean) fromEdgeField.get(null);
        assertTrue(actualFromEdge, "fromEdge devrait être mis à jour à true");
    }

    /**
     * Méthode testSetFromEdgeFalse : Vérifie que setFromEdge(false) met à jour correctement le champ statique fromEdge à false.
     */
    @Test
    public void testSetFromEdgeFalse() throws Exception {
        // ARRANGE
        resetPrivateStaticField("fromEdge", true); // Assurer que fromEdge commence à true

        // ACT
        Converter_FlowField.setFromEdge(false);

        // ASSERT
        Field fromEdgeField = Converter_FlowField.class.getDeclaredField("fromEdge");
        fromEdgeField.setAccessible(true);
        boolean actualFromEdge = (boolean) fromEdgeField.get(null);
        assertFalse(actualFromEdge, "fromEdge devrait être mis à jour à false");
    }

    /**
     * Méthode testSetOffsetXAndOffsetY : Vérifie que les méthodes setOffsetX et setOffsetY mettent à jour correctement les champs statiques offsetX et offsetY.
     */
    @Test
    public void testSetOffsetXAndOffsetY() throws Exception {
        // ARRANGE
        resetPrivateStaticField("offsetX", 0.0); // Assurer que offsetX commence à la valeur par défaut (0.0)
        resetPrivateStaticField("offsetY", 0.0); // Assurer que offsetY commence à la valeur par défaut (0.0)
        double newOffsetX = 0.5;
        double newOffsetY = -0.5;

        // ACT
        Converter_FlowField.setOffsetX(newOffsetX);
        Converter_FlowField.setOffsetY(newOffsetY);

        // ASSERT
        Field offsetXField = Converter_FlowField.class.getDeclaredField("offsetX");
        offsetXField.setAccessible(true);
        double actualOffsetX = (double) offsetXField.get(null);

        Field offsetYField = Converter_FlowField.class.getDeclaredField("offsetY");
        offsetYField.setAccessible(true);
        double actualOffsetY = (double) offsetYField.get(null);

        assertEquals(newOffsetX, actualOffsetX, "offsetX devrait être mis à jour avec la nouvelle valeur");
        assertEquals(newOffsetY, actualOffsetY, "offsetY devrait être mis à jour avec la nouvelle valeur");
    }

    /**
     * Méthode testGetName : Vérifie que la méthode getName retourne le nom correct depuis le Translator.
     */
    @Test
    public void testGetName() {
        // ARRANGE
        Converter_FlowField converter = new Converter_FlowField();
        String expectedName = Translator.get("Generator_FlowField.name");

        // ACT
        String actualName = converter.getName();

        // ASSERT
        assertEquals(expectedName, actualName, "getName devrait retourner le nom correct.");
    }
}
