package app.zamestnanci;

import java.util.ArrayList;
import app.pavilon.*;
import app.zoo.Zoo;
import app.zviera.Zviera;

public class Veduci extends Zamestnanec {
	
	private Zoo zoo;
	private Pavilon pavilon;
	private ArrayList<Zamestnanec> zamestnanci; 
	
	public Veduci(Pavilon pavilon, ArrayList<Zamestnanec> zamestnanci, Zoo zoo) {
		super();
		this.pavilon = pavilon;
		this.zamestnanci = zamestnanci;
		this.zoo = zoo;
	}
	
	public void skontrolujPavilon() {
		
		System.out.println("\nZamestnanec [" + this.getClass().getSimpleName() + "] kontroluje pavilon");
	
		int neupratane = 0;
		int neopravene = 0;
		int neosetrene = 0;
		int nenakrmene = 0;
		
		for (Klietka klietka : this.pavilon.klietky)  {	
			
			if (klietka.getCistota() < 50) {							
				for (Zamestnanec zamestnanec : this.zamestnanci) {
					if (zamestnanec instanceof Upratovac && zamestnanec.energia > 0) {
						Upratovac upratovac = (Upratovac)zamestnanec; 
						upratovac.uprac(klietka);
						neupratane--;
						break;
					}
				}
				neupratane++;
			}
			
			if (klietka.getPoskodenie() > 50) {
				for (Zamestnanec zamestnanec : this.zamestnanci) {
					if (zamestnanec instanceof Udrzbar && zamestnanec.energia > 0) {
						Udrzbar udrzbar = (Udrzbar)zamestnanec; 
						udrzbar.oprav(klietka);	
						neopravene--;
						break;
					}
				}
				neopravene++;
			}
		
			for (Zviera zviera : klietka.zvierataVKlietke){
				
				if (zviera.getZdravotnyStav().getHodnota() < 75) {
					for (Zamestnanec zamestnanec : this.zamestnanci) {
						if (zamestnanec instanceof Osetrovatel && zamestnanec.energia > 0) {
							Osetrovatel osetrovatel = (Osetrovatel)zamestnanec; 
							osetrovatel.osetri(zviera);
							neosetrene--;
							break;
						}
					}
					neosetrene++;
				}
				
				if (zviera.getPotrava().getMnozstvo() < 75) {
					for (Zamestnanec zamestnanec : this.zamestnanci) {
						if (zamestnanec instanceof Krmic && zamestnanec.energia > 0) {
							Krmic krmic = (Krmic)zamestnanec; 
							krmic.nakrm(zviera);
							nenakrmene--;
							break;
						}
					}
					nenakrmene++;
				}				
			}			
		}
		
		for (Zviera zviera : this.zoo.mrtveZvierata) {
			this.zoo.odstranZviera(zviera);
		}
		this.zoo.mrtveZvierata.clear();
		
		
		System.out.println("Neupratane klietky = " + neupratane);
		System.out.println("Neopravene klietky = " + neopravene);
		System.out.println("Neosetrene zvierata = " + neosetrene);
		System.out.println("Nenakrmene zvierata = " + nenakrmene);
		
		prijmiZamestnancov(neupratane, neopravene, neosetrene, nenakrmene);
		System.out.println("[" + this.getClass().getSimpleName() + "] skontroloval pavilon\n");
	}
	
	public void prijmiZamestnancov(int neupratane, int neopravene, int neosetrene, int nenakrmene) {
		
		if (neupratane > 0) {
			this.zoo.prijmiNovehoZamestnanca(new Upratovac());
		}
		
		if (neopravene > 0) {
			this.zoo.prijmiNovehoZamestnanca(new Udrzbar());
		}
		
		if (neosetrene > 0) {
			this.zoo.prijmiNovehoZamestnanca(new Osetrovatel());
		}
		
		if (nenakrmene > 0) {
			this.zoo.prijmiNovehoZamestnanca(new Krmic(this.zoo.sklad));
		}
		
	}
	
	public void skontrolujKlietku(String problem, Klietka klietka) {
		
		int neupratane = 0;
		int neopravene = 0;
		
		if (problem == "cistota") {
			for (Zamestnanec zamestnanec : this.zamestnanci) {
				if (zamestnanec instanceof Upratovac && zamestnanec.energia > 0) {
					Upratovac upratovac = (Upratovac)zamestnanec; 
					upratovac.uprac(klietka);
					neupratane--;
					break;
				}
				neupratane++;
			}
		}
		
		else if (problem == "poskodenie") {
			for (Zamestnanec zamestnanec : this.zamestnanci) {
				if (zamestnanec instanceof Udrzbar && zamestnanec.energia > 0) {
					Udrzbar udrzbar = (Udrzbar)zamestnanec; 
					udrzbar.oprav(klietka);	
					neopravene--;
					break;
				}
			}
			neopravene++;
		}
		prijmiZamestnancov(neupratane, neopravene, 0, 0);
	}
	
	public Pavilon getPavilon() {
		return this.pavilon;
	}
}
