package app.zamestnanci;

import app.stanok.*;
import app.zoo.SkladZoo;

public class Predavac extends Zamestnanec {
	
	private Stanok stanok;
	private SkladZoo sklad;
	
	public Predavac(Stanok stanok, SkladZoo sklad) {
		super();
		this.stanok = stanok;
		this.sklad = sklad;
	}
	
	public void skontrolujTovar() {
		
		if (this.stanok.getTovar() < (this.stanok.getKapacita()/2)) {
			doplnTovar();
		}
	}
	
	public void doplnTovar() {
		
		int kapacita = this.stanok.getKapacita();
		int tovar = this.stanok.getTovar();
		int dopln = kapacita-tovar;
		
		if (this.stanok instanceof StanokSObcerstvenim) {
			
			if (this.sklad.getObcerstvenie() < dopln) {
				upozorniSkladnika();
			}
			
			this.sklad.setObcerstvenie(-dopln);
			this.stanok.setTovar(dopln);
		}
		
		if (this.stanok instanceof StanokSoSuvenirmi) {
			this.sklad.setSuveniry(-dopln);
			this.stanok.setTovar(dopln);
		}
		System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] doplnil tovar v stanku [" + this.stanok.getClass().getSimpleName() + "]");
	}
	
	public void upozorniSkladnika() {
		this.sklad.getSkladnik().skontrolujZasoby();
	}
}
