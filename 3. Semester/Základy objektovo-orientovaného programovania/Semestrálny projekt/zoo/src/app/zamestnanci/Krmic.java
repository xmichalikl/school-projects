package app.zamestnanci;


import java.util.Random;

import app.zoo.SkladZoo;
import app.zooSystem.Enums.typPotravy;
import app.zviera.Bylinozravec;
import app.zviera.Masozravec;
import app.zviera.Vsezravec;
import app.zviera.Zviera;

public class Krmic extends Zamestnanec implements KontrolaKlietky{
	
	private SkladZoo sklad;

	public Krmic(SkladZoo sklad) {
		super();
		this.sklad = sklad;
	}
	
	
	public void nakrm(Zviera zviera) {
		int mnozstvo = zviera.getPotrava().getMnozstvo();
		int kvalita = zviera.getPotrava().getKvalita();
		
		Random random = new Random();
		int chyba = 0; 
		
		if (this.skusenosti < 2) {
			chyba = random.nextInt(5-0+1)+0;
		}
	
		if (zviera instanceof Masozravec) {
			
			if (this.sklad.getPotravaMaso() < (100 - zviera.getPotrava().getMnozstvo())) {
				upozorniSkladnika();
			}
			
			if (chyba > 3) {
				zviera.getPotrava().setDruhPotravy(typPotravy.BYLINY);
				System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] spravil chybu pri krmeni! [" + zviera.getNazov() + "]");
			}
			else {
				zviera.getPotrava().setDruhPotravy(typPotravy.MASO);
			}
			zviera.getPotrava().setMnozstvo(100-mnozstvo);
			zviera.getPotrava().setKvalita(5-kvalita);
			this.sklad.setPotravaMaso(-(100-mnozstvo));
		}
		
		else if (zviera instanceof Bylinozravec) {
			if (this.sklad.getPotravaMaso() < (100 - zviera.getPotrava().getMnozstvo())) {
				upozorniSkladnika();
			}
			
			if (chyba > 3) {
				zviera.getPotrava().setDruhPotravy(typPotravy.MASO);
				System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] spravil chybu pri krmeni! [" + zviera.getNazov() + "]");
			}
			else {
				zviera.getPotrava().setDruhPotravy(typPotravy.BYLINY);
			}
			zviera.getPotrava().setMnozstvo(100-mnozstvo);
			zviera.getPotrava().setKvalita(5-kvalita);
			this.sklad.setPotravaByliny(-(100-mnozstvo));
		}
		
		else if (zviera instanceof Vsezravec) {
			if (this.sklad.getPotravaMaso() < (100 - zviera.getPotrava().getMnozstvo())) {
				upozorniSkladnika();
			}
			
			if (chyba > 3) {
				zviera.getPotrava().setDruhPotravy(typPotravy.BYLINY);
				System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] spravil chybu pri krmeni! [" + zviera.getNazov() + "]");
			}
			else if (chyba > 4){
				zviera.getPotrava().setDruhPotravy(typPotravy.MASO);
				System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] spravil chybu pri krmeni! [" + zviera.getNazov() + "]");
			}
			else {
				zviera.getPotrava().setDruhPotravy(typPotravy.VSETKO);
			}
			zviera.getPotrava().setMnozstvo(100-mnozstvo);
			zviera.getPotrava().setKvalita(5-kvalita);
			this.sklad.setPotravaMaso(-((100-mnozstvo)/2));
			this.sklad.setPotravaByliny(-((100-mnozstvo)/2));
		}
		
		System.out.println("Zamestnanec [" + getClass().getSimpleName() + "] nakrmil zviera [" + zviera.getNazov() + "]");
		this.energia--;
		skontrolujKlietku(zviera.getKlietka(), this);
	}
	
	public void upozorniSkladnika() {
		this.sklad.getSkladnik().skontrolujZasoby();
	}

}
