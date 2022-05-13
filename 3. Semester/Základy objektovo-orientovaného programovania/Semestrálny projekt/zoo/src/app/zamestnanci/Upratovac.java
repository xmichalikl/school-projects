package app.zamestnanci;

import app.pavilon.Klietka;

public class Upratovac extends Zamestnanec {
	
	public Upratovac() {
		super();
	}
	
	public void uprac(Klietka klietka) {
		int cistota = klietka.getCistota();
		
		if (cistota < 75) {
			klietka.setCistota(25);
		}
		else {
			klietka.setCistota(100);
			klietka.setCistota(-cistota);
		}
		System.out.println("Zamestnanec [" + getClass().getSimpleName() + "] upratal klietku");
		this.energia--;
	}
}