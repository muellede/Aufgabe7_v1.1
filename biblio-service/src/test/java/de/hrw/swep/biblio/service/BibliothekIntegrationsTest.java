package de.hrw.swep.biblio.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Set;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import de.hrw.swep.biblio.persistence.DAO;
import de.hrw.swep.biblio.service.benutzer.Benutzer;
import de.hrw.swep.biblio.service.gegenstaende.Buch;

/**
 * Testet die Bibliotheksklasse mit der echten Datenbank.
 * 
 * @author M. Friedrich
 *
 */

public class BibliothekIntegrationsTest {

	Bibliothek bib;

	@Before
	public void setUp() {
		bib = new Bibliothek();
		bib.setDb(new DAO());
	}

	/**
	 * Testet, ob ein Buch fuer einen gegebenen Titel gefunden wird.
	 */
	@Test
	public void testSucheBuchNachTitel() {
		Set<Buch> b = bib.sucheBuchNachTitel("Lost and Found");
		assertEquals(1, b.size());
		Buch a = b.iterator().next();
		assertEquals("Karl Kaos", a.getAutor());
		assertEquals("de.hrw.swep.biblio.service.gegenstaende.Verloren", a
				.getState().getClass().getName());

	}

	/**
	 * Testet, ob ein Buch fuer einen gegebenen Autor gefunden wird.
	 */
	@Test
	public void testSucheBuchNachAutor() {
		Set<Buch> b = bib.sucheBuchNachAutor("Lars Lehmann");
		Buch a = b.iterator().next();
		assertEquals("Herr L.", a.getTitel());
		assertEquals("de.hrw.swep.biblio.service.gegenstaende.Frei", a
				.getState().getClass().getName());

	}

	/**
	 * Testet, ob ein Benutzer mit einem gegebenen Namen gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachName() {
		Set<Benutzer> b = bib.sucheBenutzerNachName("Adalbert Alt");
		assertEquals(1, b.size());
		Benutzer a = b.iterator().next();
		assertEquals("de.hrw.swep.biblio.service.benutzer.Normal", a
				.getStatus().getClass().getName());
		assertEquals(1, a.getId());
	}

	/**
	 * Testet, ob ein Benutzer mit einer gegebenen ID gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachId() {
		Benutzer b = bib.sucheBenutzerNachId(2);
		assertEquals("Berta Brettschneider", b.getName());
		assertEquals("de.hrw.swep.biblio.service.benutzer.Gesperrt", b
				.getStatus().getClass().getName());
		assertEquals(2, b.getGebuehren().size());
	}

}
