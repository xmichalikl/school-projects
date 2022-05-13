package app.zooSystem;

import java.util.ArrayList;
import java.util.Random;
import app.stanok.*;
import app.zamestnanci.*;
import app.zoo.*;
import app.zviera.*;


public class ZooSystem {
	
	public static int den = 0;
	public static ArrayList<Zoo> zoologickeZahrady;

	
	public static void simulaciaDna(int pocetDni) {
		
		for (int den = 1; den<(pocetDni+1); den++) {
			
			for (int i = 0; i<zoologickeZahrady.size(); i++) {		
				Zoo zoo = zoologickeZahrady.get(i);
				
				//vypocet navstevnosti v zavyslosti od stavu zoo
				zoo.stav.zistiStav(zoo.pavilony);
				zoo.financie.listky( Math.round(300*zoo.stav.getIndex()) , Math.round(100*zoo.stav.getIndex()) );
				zoo.pocetNavsevnikov = ( Math.round(300*zoo.stav.getIndex()) + Math.round(100*zoo.stav.getIndex()) );
				
				//kontorla stankov
				for (Zamestnanec zamestnanec : zoo.zamestnanci) {
					if (zamestnanec instanceof Predavac) {
						Predavac predavac = (Predavac)zamestnanec; 
						predavac.skontrolujTovar();
					}
				}
				
				//predaje stankov
				for (Stanok stanok : zoo.stanky) {
					stanok.predajTovar(zoo.pocetNavsevnikov);
				}
				
				//kontrola zvierat
				for (Veduci veduci : zoo.veduci) {
					veduci.skontrolujPavilon();
				}
				
				//denny cyklus zvierat 
				for (Zviera zviera : zoo.zvierata) {
					Random random = new Random();
					int poskod = random.nextInt(1-0+1)+0;
					
					if (poskod > 0) {
						zviera.poskodKlietku();
					}
					
					zviera.ziedzPotravu();
					zviera.znecistniKlietku();
					
					if (zviera instanceof Vsezravec) {
						zviera.ziedzPotravu();
						zviera.znecistniKlietku();
					}
					
					if (zviera instanceof Masozravec) {
						zviera.ziedzPotravu();
						zviera.ziedzPotravu();
						zviera.znecistniKlietku();
						zviera.znecistniKlietku();
					}
					
				}
				
				//2. kontrola zvierat
				for (Veduci veduci : zoo.veduci) {
					veduci.skontrolujPavilon();
				}
				
				//doplnenie energie zamestnancom
				for (Zamestnanec zamestnanec : zoo.zamestnanci) {
					
					if (zamestnanec instanceof Upratovac) {
						zamestnanec.setEnergia();
					}
					else if (zamestnanec instanceof Udrzbar) {
						zamestnanec.setEnergia();
					}
					else if (zamestnanec instanceof Osetrovatel) {
						zamestnanec.setEnergia();
					}
					else if (zamestnanec instanceof Krmic) {
						zamestnanec.setEnergia();
					}
				}
				
				System.out.println("Index Zoo = " + zoo.stav.getIndex());
				System.out.println("Priemerna cistota = " + zoo.stav.getCistota());
				System.out.println("Priemerne poskodenie = " + zoo.stav.getPoskodenie());
				
				//zarobky stankov
				zoo.financie.stanky(zoo.stanky);
				
				System.out.println("Dnes Zoo navstivilo " + zoo.pocetNavsevnikov + " ludi");
					zoo.pocetNavsevnikov = 0;				
					
				System.out.println("Prijati zamestnanci = " + zoo.prijatiZamestnanci);
					
				System.out.println("\nZamestnancom bola doplnena energia");
				System.out.println("Koniec " + den + ". dna\n");
				
				//predaj zvierat
				if ((den % 10) == 0) {
					System.out.println("Financny kapital po 10 dnoch = " + zoo.financie.getKapital() + "€");
					System.out.println("Zisky po 10 dnoch = " + zoo.financie.getZisk() + "€");
					System.out.println("Vydavky po 10 dnoch = " + zoo.financie.getVydavky() + "€");
					
					if (zoo.financie.getZisk() < zoo.financie.getVydavky() && zoo.financie.getKapital() < 15000) {
						while (zoo.financie.getKapital() < 15000) {
							zoo.predajZviera();
							if (zoo.zvierata.size() == 0) {
								System.out.println("Zoo uz predala vsetky zvierata a skrachovala");
								zoologickeZahrady.remove(zoo);
								return;
							}
						}
					}
				}
				
				//mesacna kontrola a vypis
				if ((den % 30) == 0) {
					
					//pravidelne doplnenie skladu kazdy mesiac
					for (Zamestnanec zamestnanec : zoo.zamestnanci) {
						
						if (zamestnanec instanceof Skladnik) {
							Skladnik skladnik = (Skladnik)zamestnanec;
							skladnik.skontrolujZasoby();
							break;
						}
					} 
					
					//statistiky a nulovanie
					System.out.println("Prijati zamestnanci = " + zoo.prijatiZamestnanci);
						zoo.prijatiZamestnanci = 0;
					System.out.println("Prijati veduci = " + zoo.prijatiVeduci);
						zoo.prijatiVeduci = 0;
					System.out.println("Kupene zvierata = " + zoo.kupeneZvierata);
						zoo.kupeneZvierata = 0;
					System.out.println("Predane zvierata = " + zoo.predaneZvierata);
					 zoo.predaneZvierata = 0;
					System.out.println("Mrtve zvierata = " + zoo.umrtieZvierat);
						zoo.umrtieZvierat = 0;
					System.out.println("Vytvorene pavilony = " + zoo.vytvorenePavilony);
						zoo.vytvorenePavilony = 0;
					System.out.println("Zrusene pavilony = " + zoo.zrusenePavilony);
						zoo.zrusenePavilony = 0;
					
					//vyplaty
					zoo.financie.vyplatyASponzorstvo(zoo.veduci, zoo.zamestnanci, 50000);
					
					//Zisky a vydavky
					System.out.println("Mesacne zisky = " + zoo.financie.getZisk() + "€");
					System.out.println("Mesacne vydavky = " + zoo.financie.getVydavky() + "€");
					
					if (zoo.financie.getZisk() >= zoo.financie.getVydavky()) {
						int zisk = (zoo.financie.getZisk() - zoo.financie.getVydavky());
						
						//nulovanie ziskov a vydavkov
						zoo.financie.setZisk(-zoo.financie.getZisk());
						zoo.financie.setVydavky(-zoo.financie.getVydavky());
						
						System.out.println("Financny kapital Zoo = " + zoo.financie.getKapital());
						System.out.println("Zoo zarobila tento mesiac " + zisk + "€");
					}
					if (zoo.financie.getZisk() < zoo.financie.getVydavky()) {
						int strata = (zoo.financie.getVydavky() - zoo.financie.getZisk());
						
						//nulovanie ziskov a vydavkov
						zoo.financie.setZisk(-zoo.financie.getZisk());
						zoo.financie.setVydavky(-zoo.financie.getVydavky());
						
						System.out.println("Financny kapital Zoo = " + zoo.financie.getKapital());
						System.out.println("Zoo je tento mesiac v strate " + strata + "€");
						if (zoo.financie.getKapital() < 15000) {
							System.out.println("Zoo uz nema financny kapital na jej chod a preto skrachovala!");
							zoologickeZahrady.remove(zoo);
							return;
						}
					}
			
				}
			}	
		}
	}
	
	public static void main(String[] args) {	///// MAIN /////
		
		zoologickeZahrady = new ArrayList<Zoo>();
		Zoo mojaZoo = new Zoo(100000);
		zoologickeZahrady.add(mojaZoo);
			
		mojaZoo.kupZviera("b", "opica");
		mojaZoo.kupZviera("b", "opica");
		mojaZoo.kupZviera("b", "opica");
		
		mojaZoo.kupZviera("b", "slon");
		mojaZoo.kupZviera("b", "slon");
		
		mojaZoo.kupZviera("m", "tiger");
		mojaZoo.kupZviera("m", "tiger");
		
		mojaZoo.kupZviera("m", "gepard");
		
		mojaZoo.kupZviera("m", "medved");
		mojaZoo.kupZviera("m", "medved");
		
		mojaZoo.kupZviera("hroch");
		mojaZoo.kupZviera("hroch");
		
		mojaZoo.kupZviera("nosorozec");
		mojaZoo.kupZviera("nosorozec");
		
		mojaZoo.vytvorStanok();
		mojaZoo.vytvorStanok();
		mojaZoo.vytvorStanok();
		mojaZoo.vytvorStanok();
			
		mojaZoo.prijmiNovehoZamestnanca(new Upratovac());
		mojaZoo.prijmiNovehoZamestnanca(new Udrzbar());
		mojaZoo.prijmiNovehoZamestnanca(new Osetrovatel());
		mojaZoo.prijmiNovehoZamestnanca(new Krmic(mojaZoo.sklad));
				
		
		simulaciaDna(1);
	}
}
