package app.zviera;

import app.zoo.Zoo;
import app.zooSystem.Enums.typPotravy;

public class Vsezravec extends Zviera {
	
	public Vsezravec(String nazov, int hodnota, Zoo zoo) {
		super(nazov, hodnota, zoo);
	}
	
	public Vsezravec(String nazov, String typ, int hodnota, Zoo zoo) {
		super(nazov, typ, hodnota, zoo);
	}
	
	public void ziedzPotravu() {
		if (this.potrava.druhPotravy == null) {
			System.out.println("Zviera [" + this.nazov + "] nema prideleny typ potravy!");
			this.zdravie.setHodnota(-10);
			return;
		}
		
		if (this.potrava.mnozstvo < 25) {
			System.out.println("Zviera [" + this.nazov + "] je potrebne nakrmit!");
			this.zdravie.setHodnota(-10);
			return;
		}
		
		if (this.potrava.druhPotravy == typPotravy.VSETKO) {
			this.potrava.mnozstvo -= 25;
			this.zdravie.setHodnota(15);
		}
		else if (this.potrava.druhPotravy == typPotravy.MASO) {
			this.potrava.mnozstvo -= 25;
			System.out.println("Zviera [" + this.nazov + "] ma pridelenu zlu potravu!");
			this.zdravie.setHodnota(-10);
		}
		else if (this.potrava.druhPotravy == typPotravy.BYLINY) {
			this.potrava.mnozstvo -= 25;
			System.out.println("Zviera [" + this.nazov + "] ma pridelenu zlu potravu!");
			this.zdravie.setHodnota(-10);
		}	
		
		if (this.zdravie.hodnota <= 0) {
			super.umri(this);
		}
	}
	
	public void znecistniKlietku() {
		super.znecistniKlietku();
	}
	
	public void poskodKlietku () {
		super.poskodKlietku();
	}
}