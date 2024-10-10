package com.marginallyclever.makelangelo;

import com.marginallyclever.util.PreferencesHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class TranslatorTest {

	@BeforeAll
	public static void beforeAll() {
		PreferencesHelper.start();
		Translator.start();
	}

	// Réinitialiser les préférences de langue après chaque test
	@AfterEach
	public void afterEach() {
		Preferences languagePreferenceNode = PreferencesHelper.getPreferenceNode(PreferencesHelper.MakelangeloPreferenceKey.LANGUAGE);
		languagePreferenceNode.put("language", "English");
		Translator.setCurrentLanguage("English");
	}

	@Test
	public void startTranslatorTwiceTest() {
		String[] first = Translator.getLanguageList();
		Translator.start();
		String[] second = Translator.getLanguageList();
		assertArrayEquals(first, second);
	}

	@Test
	public void loadLanguageTest() {
		String[] available = Translator.getLanguageList();
		int current = Translator.getCurrentLanguageIndex();
		assertNotNull(available[current]);
	}

	@Test
	public void changeLanguageTest() {
		String[] available = Translator.getLanguageList();
		assertTrue(available.length > 1, "More than one language needed to complete test.");
		int current = Translator.getCurrentLanguageIndex();
		try {
			int next = (current + 1) % available.length;
			Translator.setCurrentLanguage(available[next]);
			Translator.saveConfig();
			Translator.loadConfig();
			int read = Translator.getCurrentLanguageIndex();
			assertEquals(read, next, "Changing language failed.");
		} finally {
			// return to previous state
			Translator.setCurrentLanguage(available[current]);
			Translator.saveConfig();
		}
	}

	@Test
	public void getOneValueThatExists() {assertEquals("Makelangelo", Translator.get("Robot"));}

	@Test
	public void getOneValueThatDoesNotExist() {
		assertEquals(Translator.MISSING + "DoesNotExist", Translator.get("DoesNotExist"));
	}

	// Test pour la première fois de chargement des fichiers de langue quand aucune préférence n'existe
	@Test
	public void testIsFirstTimeLoadingLanguageFiles_NoPreferenceExists() {

		// ARRANGE
		Preferences languagePreferenceNode = PreferencesHelper.getPreferenceNode(PreferencesHelper.MakelangeloPreferenceKey.LANGUAGE);
		try {
			languagePreferenceNode.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
			fail("Failed to clear preferences: " + e.getMessage());
		}

		Translator.loadLanguages();

		// ACT
		boolean isFirstTime = Translator.isThisTheFirstTimeLoadingLanguageFiles();

		// ASSERT
		assertTrue(isFirstTime, "Expected first time loading language files when no preference exists.");
	}

	// Test de chargement non premier quand une préférence existe et que la langue est disponible
	@Test
	public void testIsFirstTimeLoadingLanguageFiles_PreferenceExistsAndLanguageAvailable() {

		// ARRANGE
		Translator.loadLanguages();
		String[] languages = Translator.getLanguageList();
		assertTrue(languages.length > 0, "No languages available for testing.");
		String availableLanguage = languages[0];
		Preferences languagePreferenceNode = PreferencesHelper.getPreferenceNode(PreferencesHelper.MakelangeloPreferenceKey.LANGUAGE);
		languagePreferenceNode.put("language", availableLanguage);

		Translator.loadLanguages();

		// ACT
		boolean isFirstTime = Translator.isThisTheFirstTimeLoadingLanguageFiles();

		// ASSERT
		assertFalse(isFirstTime, "Expected not first time loading language files when preference exists and language is available.");
	}

	// Test de premier chargement quand une préférence existe mais que la langue n'est pas disponible
	@Test
	public void testIsFirstTimeLoadingLanguageFiles_PreferenceExistsButLanguageUnavailable() {

		// ARRANGE
		Translator.loadLanguages();
		String[] languages = Translator.getLanguageList();
		String unavailableLanguage = "NonExistentLanguage";
		for (String lang : languages) {
			assertNotEquals(unavailableLanguage, lang, "Test requires that '" + unavailableLanguage + "' is not an available language.");
		}

		Preferences languagePreferenceNode = PreferencesHelper.getPreferenceNode(PreferencesHelper.MakelangeloPreferenceKey.LANGUAGE);
		languagePreferenceNode.put("language", unavailableLanguage);

		Translator.loadLanguages();

		// ACT
		boolean isFirstTime = Translator.isThisTheFirstTimeLoadingLanguageFiles();

		// ASSERT
		assertTrue(isFirstTime, "Expected first time loading language files when preference exists but language is unavailable.");
	}
}
