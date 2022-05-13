package app.zamestnanci;

import app.zviera.Zviera;

public class Osetrovatel extends Zamestnanec implements KontrolaKlietky {

	public Osetrovatel() {
		super();
	}

	public void osetri(Zviera zviera) {
		int zdravie = zviera.getZdravotnyStav().getHodnota();
		
		if (zdravie <= 0) {
			zviera.umri(zviera);
		}
		
		if (zdravie < 75) {
			zviera.getZdravotnyStav().setHodnota(25);
		}
		else {
			zviera.getZdravotnyStav().setHodnota(100);
			zviera.getZdravotnyStav().setHodnota(-zdravie);
		}
		System.out.println("Zamestnanec [" + getClass().getSimpleName() + "] osetril zviera [" + zviera.getNazov() + "]");
		this.energia--;
		skontrolujKlietku(zviera.getKlietka(), this);
	}
}
