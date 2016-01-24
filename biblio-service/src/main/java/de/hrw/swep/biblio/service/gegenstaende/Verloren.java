//CHECKSTYLE:OFF
package de.hrw.swep.biblio.service.gegenstaende;

import de.hrw.swep.biblio.service.IllegalStateTransition;
import de.hrw.swep.biblio.service.benutzer.Benutzer;

public class Verloren implements Ausleihstatus {
	
	private Gegenstand gegenstand;
	
	public Verloren (Gegenstand g) {
		this.gegenstand = g;
	}

	@Override
	public void ausleihen(Benutzer user) {
		// TODO Auto-generated method stub
		throw new IllegalStateTransition();
	}

	@Override
	public void zurueckgeben() {
		// TODO Auto-generated method stub
		this.gegenstand.setState(new Frei(gegenstand));
	}

	@Override
	public void verloren() {
		// TODO Auto-generated method stub
		throw new IllegalStateTransition();
	}

	
}
