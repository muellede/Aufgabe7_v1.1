package de.hrw.swep.biblio.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

import de.hrw.swep.biblio.persistence.DBInterface;
import de.hrw.swep.biblio.persistence.dto.BenutzerDTO;
import de.hrw.swep.biblio.persistence.dto.BuchDTO;
import de.hrw.swep.biblio.persistence.dto.GebuehrDTO;
import de.hrw.swep.biblio.service.benutzer.Benutzer;
import de.hrw.swep.biblio.service.gegenstaende.Buch;

/**
 * Testet die Bibliotheks-Klasse mit einem Mock-Objekt
 * 
 * @author Sandra
 *
 */
public class BibliothekTestMitMock {

	Bibliothek bib;

	@Before
	public void setUp() {
		DBInterface dbi = Mockito.mock(DBInterface.class);

		BenutzerDTO berta = new BenutzerDTO(1, "Berta Brettschneider", "Normal");
		when(dbi.getBenutzerById(1)).thenReturn(berta);
		when(dbi.getGebuehrenByUserId(1)).thenReturn(
				Sets.newSet(new GebuehrDTO("Gebuehren", 1000), new GebuehrDTO(
						"Ueberziehung", 800)));

		BenutzerDTO charly = new BenutzerDTO(2, "Charly Hebdo", "Gesperrt");
		when(dbi.getBenutzerByName("Charly Hebdo")).thenReturn(
				Sets.newSet(charly));
		when(dbi.getGebuehrenByUserId(2)).thenReturn(
				Sets.newSet(new GebuehrDTO("Schulden", 9999)));
		when(dbi.getBenutzerById(2)).thenReturn(charly);

		BuchDTO klatsch = new BuchDTO(1, "Hans", "Klatsch und...",
				"Ausgeliehen");
		when(dbi.getBuchByAutor("Hans")).thenReturn(Sets.newSet(klatsch));
		when(dbi.getBuchByTitle("Klatsch und...")).thenReturn(
				Sets.newSet(klatsch));

		BuchDTO furz = new BuchDTO(2, "Franz", "Furzz", "Verloren");

		when(dbi.getBuchByAutor("Franz")).thenReturn(Sets.newSet(furz));
		when(dbi.getBuchByTitle("furzz")).thenReturn(Sets.newSet(furz));

		bib = new Bibliothek();
		bib.setDb(dbi);

	}

	/**
	 * Testet, ob ein Buch mit gegebenem Titel gefunden wird.
	 */
	@Test
	public void testSucheBuchNachTitel() {
		Set<Buch> b = bib.sucheBuchNachTitel("furzz");
		assertEquals(1, b.size());
		Buch a = b.iterator().next();
		assertEquals("Franz", a.getAutor());

	}



	/**
	 * Testet, ob ein Benutzer mit gegebenem Namen gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachName() {
		Set<Benutzer> b = bib.sucheBenutzerNachName("Charly Hebdo");
		assertEquals(1, b.size());
		Benutzer a = b.iterator().next();
		assertEquals(2, a.getId());
		assertEquals("de.hrw.swep.biblio.service.benutzer.Gesperrt", a
				.getStatus().getClass().getName());
	}

	/**
	 * Testet, ob ein Benutzer mit einer gegebenen ID gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachId() {
		Benutzer b = bib.sucheBenutzerNachId(2);
		assertEquals("Charly Hebdo", b.getName());
		assertEquals("de.hrw.swep.biblio.service.benutzer.Gesperrt", b.getStatus().getClass().getName());
		assertEquals(1, b.getGebuehren().size());
	}

}
