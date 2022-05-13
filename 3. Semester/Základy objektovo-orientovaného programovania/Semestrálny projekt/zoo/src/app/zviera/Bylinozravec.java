package app.zviera;

import app.zoo.Zoo;
import app.zooSystem.Enums.typPotravy;

public class Bylinozravec extends Zviera {
	
	public Bylinozravec(String nazov, int hodnota, Zoo zoo) {
		super(nazov, hodnota, zoo);
	}
	
	public Bylinozravec(String nazov, String typ, int hodnota, Zoo zoo) {
		super(nazov, typ, hodnota, zoo);
	}
		
	public void ziedzPotravu() {
		if (this.potrava.druhPotravy == null) {
			System.out.println("Zviera [" + this.nazov + "] nema prideleny typ potravy!");
			this.zdravie.setHodnota(-10);
			return;
		}
		
		if (this.potrava.mnozstvo < 20) {
			System.out.println("Zviera [" + this.nazov + "] je potrebne nakrmit!");
			this.zdravie.setHodnota(-10);
			return;
		}
		
		if (this.potrava.druhPotravy == typPotravy.BYLINY) {
			this.potrava.mnozstvo -= 20;
			this.zdravie.setHodnota(15);
		}
		else if (this.potrava.druhPotravy == typPotravy.MASO) {
			this.potrava.mnozstvo -= 10;
			System.out.println("Zviera [" + this.nazov + "] ma pridelenu zlu potravu!");
			this.zdravie.setHodnota(-15);
		}
		else if (this.potrava.druhPotravy == typPotravy.VSETKO) {
			this.potrava.mnozstvo -= 15;
			System.out.println("Zviera [" + this.nazov + "] ma pridelenu zlu potravu!");
			this.zdravie.setHodnota(-10);
		}	
		
		if (this.zdravie.hodnota <= 0) {
			super.umri(this);
		}
	}
	
	public void znecistniKlietku() {
		if (this.klietka.getCistota() >= 5 ) {
			this.klietka.setCistota(-5);
			System.out.println("Zviera [" + this.nazov + "] zaspinilo klietku");
			System.out.println("Cistota klietky (" + (this.klietka.getCistota()+5) + ") -> (" + this.klietka.getCistota() + ")" );
		}
	}
	
	public void poskodKlietku() {
		if (this.klietka.getPoskodenie() <= 95 ) {
			this.klietka.setPoskodenie(5);
			System.out.println("Zviera [" + this.nazov + "] poskodilo klietku");
			System.out.println("Poskodenie klietky (" + (this.klietka.getPoskodenie()-5) + ") -> (" + this.klietka.getPoskodenie() + ")" );
		}
	}
}