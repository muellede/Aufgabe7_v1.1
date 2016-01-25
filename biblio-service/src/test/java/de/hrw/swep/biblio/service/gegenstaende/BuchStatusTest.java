//CHECKSTYLE:OFF
package de.hrw.swep.biblio.service.gegenstaende;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hrw.swep.biblio.service.IllegalStateTransition;
import de.hrw.swep.biblio.service.benutzer.Benutzer;

public class BuchStatusTest {

	@Test
	public void testFrei() {
		Buch b = new Buch("Lost and Found", "Karl Kaos");
		b.setState(new Ausgeliehen(b));
		b.zurueckgeben();
		
		assertEquals("de.hrw.swep.biblio.service.gegenstaende.Frei", b.getState().getClass().getName());
	}
	
	@Test
	public void testAusgeliehen() {
		Buch b = new Buch("Lost and Found", "Karl Kaos");
		b.setState(new Frei(b));
		Benutzer a = new Benutzer(1, "Tester");
		b.ausleihen(a);
		
		assertEquals("de.hrw.swep.biblio.service.gegenstaende.Ausgeliehen", b.getState().getClass().getName());
	}
	
	@Test(expected = IllegalStateTransition.class)
	public void verloren()  {
		Buch b = new Buch("Lost and Found", "Karl Kaos");
		b.setState(new Verloren(b));
		Benutzer a = new Benutzer(1, "Tester");
		b.ausleihen(a);
	}
}
