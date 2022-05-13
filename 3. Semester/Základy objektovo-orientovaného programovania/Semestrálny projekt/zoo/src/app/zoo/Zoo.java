package app.zoo;
import java.util.ArrayList;
import java.util.Random;

import app.pavilon.Klietka;
import app.pavilon.Pavilon;
import app.stanok.*;
import app.stanok.StanokSObcerstvenim;
import app.zamestnanci.*;
import app.zviera.*;

public class Zoo {
	
	public int pocetNavsevnikov = 0;
	
	public int prijatiZamestnanci = 0;
	public int prijatiVeduci = 0;
	
	public int predaneZvierata = 0;
	public int kupeneZvierata = 0;
	public int umrtieZvierat = 0;
	
	public int vytvorenePavilony = 0;
	public int zrusenePavilony = 0;
	
	public final StavZoo stav;
	public final SkladZoo sklad;
	public final FinancieZoo financie;
		
	public ArrayList<Veduci> veduci; 
	public ArrayList<Zamestnanec> zamestnanci; 	
	public ArrayList<Pavilon> pavilony;
	public ArrayList<Stanok> stanky;
	public ArrayList<Zviera> zvierata;
	public ArrayList<Zviera> mrtveZvierata;
	
	public Zoo(int kapital) {
		
		this.veduci = new ArrayList<Veduci>();
		this.zamestnanci = new ArrayList<Zamestnanec>();
		this.pavilony = new ArrayList<Pavilon>();
		this.stanky = new ArrayList<Stanok>();
		this.zvierata = new ArrayList<Zviera>();
		this.mrtveZvierata = new ArrayList<Zviera>();
		
		this.stav = new StavZoo();
		this.sklad =  new SkladZoo();
		this.financie = new FinancieZoo(kapital);
		
		Skladnik novySkladnik = new Skladnik(this.sklad, this.financie);
		prijmiNovehoZamestnanca(novySkladnik);
		this.sklad.setSkladnik(novySkladnik);
		this.prijatiZamestnanci++;
	}
	
	
	public void vytvorPavilon(String nazovPavilonu, Zviera noveZviera) {
		Pavilon novyPavilon = new Pavilon(nazovPavilonu);
		pavilony.add(novyPavilon);
		novyPavilon.vytvorKlietku(noveZviera);
		prijmiNovehoZamestnanca(new Veduci(novyPavilon, this.zamestnanci, this));
		this.vytvorenePavilony++;
	}
	
	public void odstranPavilon(Pavilon pavilon) {
		if (pavilon.klietky.size() > 0) {
			System.out.println("Nie je mozne odstranit pavilon [" + pavilon.nazovPavilonu + "] (" + pavilon.klietky.size() + "), lebo sa v nom nachadzaju klietky");
			return;
		}
		else {
			this.pavilony.remove(pavilon);
			System.out.println("Pavilon [" + pavilon.nazovPavilonu + "] bol odstraneny");
			pavilon = null;
			this.zrusenePavilony++;
		}
	}
	
	public void vytvorStanok() {
		Random random = new Random();
		int vytvor = random.nextInt(10-1+1)+1;
		Stanok novyStanok = null;
		
		if (vytvor > 5) {
			novyStanok = new StanokSObcerstvenim();
			System.out.println("Zoo prave vytvorila novy stanok [" + novyStanok.getClass().getSimpleName() + "]");
		}
		else {
			novyStanok = new StanokSoSuvenirmi();
			System.out.println("Zoo prave vytvorila novy stanok [" + novyStanok.getClass().getSimpleName() + "]");
		}
		
		this.stanky.add(novyStanok);
		Predavac novyPredavac = new Predavac(novyStanok, this.sklad);
		prijmiNovehoZamestnanca(novyPredavac);
	}
	
	
	public void kupZviera(String druh, String nazovZvierata) {
		if (druh == "M" || druh == "m") {
			
			Random random = new Random();
			int hodnota = random.nextInt(10000-5000+1)+5000;
			
			Zviera noveZviera = new Masozravec(nazovZvierata, hodnota, this);
			zvierata.add(noveZviera);
			this.kupeneZvierata++;
			this.financie.setVydavky(hodnota);
			this.financie.setKapital(-hodnota);
			System.out.println("Zoo prave kupila zviera [" + noveZviera.getNazov() + "] (Masozravec) za " + noveZviera.getHodnota() + "€");
			
			for (int i=0; i<this.pavilony.size(); i++) {
				if (this.pavilony.get(i).nazovPavilonu == nazovZvierata) {
					this.pavilony.get(i).najdiKlietkuPreZviera(noveZviera);
					System.out.println("\n");
					return;
				}
			}
			vytvorPavilon(noveZviera.getNazov(), noveZviera);
			System.out.println("\n");
		}
		
		else if (druh == "B" || druh == "b") {
			
			Random random = new Random();
			int hodnota = random.nextInt(5000-1000+1)+1000;
												
			Zviera noveZviera = new Bylinozravec(nazovZvierata, hodnota, this);
			zvierata.add(noveZviera);
			this.kupeneZvierata++;
			this.financie.setVydavky(hodnota);
			this.financie.setKapital(-hodnota);
			System.out.println("Zoo prave kupila zviera [" + noveZviera.getNazov() + "] (Bylinozravec) za " + noveZviera.getHodnota() + "€");
			
			for (int i=0; i<this.pavilony.size(); i++) {
				if (this.pavilony.get(i).nazovPavilonu == nazovZvierata) {
					this.pavilony.get(i).najdiKlietkuPreZviera(noveZviera);
					System.out.println("\n");
					return;
				}
			}
			vytvorPavilon(noveZviera.getNazov(), noveZviera);
			System.out.println("\n");
		}
		
		else {
			System.out.println("Tento druh zvierata neexistuje\n");
			return;
		}
	}
	
	public void kupZviera(String nazovZvierata) { 
		
		Random random = new Random();
		int hodnota = random.nextInt(7500-2500+1)+2500;
		
		Zviera noveZviera = new Vsezravec(nazovZvierata, hodnota, this);
		zvierata.add(noveZviera);
		this.kupeneZvierata++;
		this.financie.setVydavky(hodnota);
		this.financie.setKapital(-hodnota);
		System.out.println("Zoo prave kupila zviera [" + noveZviera.getNazov() + "] (Vsezravec) za " + noveZviera.getHodnota() + "€");
		
		for (int i=0; i<this.pavilony.size(); i++) {
			if (this.pavilony.get(i).nazovPavilonu == nazovZvierata) {
				this.pavilony.get(i).najdiKlietkuPreZviera(noveZviera);
				System.out.println("\n");
				return;
			}
		}
		vytvorPavilon(noveZviera.getNazov(), noveZviera);
		System.out.println("\n");
	}
	
	
	public void predajZviera() {
		
		if (this.zvierata.size() == 0) {
			System.out.println("Aktualne ZOO nema ziadne zvierata na predaj");
			return;
		}
		
		Random random = new Random();
		int predaj = random.nextInt((this.zvierata.size()-1)-0+1)+0;
			
		Zviera zviera = this.zvierata.get(predaj);
		Klietka klietka = zviera.getKlietka();
		Pavilon pavilon = null;
		
		for (int i=0; i<this.pavilony.size(); i++) {	
			if (this.pavilony.get(i).nazovPavilonu == zviera.getNazov()) {
				pavilon = this.pavilony.get(i);
				break;
			}
		}
				
		klietka.odstranZviera(zviera, pavilon);
		
		if (klietka.zvierataVKlietke.size() == 0) {
			pavilon.odstranKlietku(klietka);
			
			if (pavilon.klietky.size() == 0) {
				odstranPavilon(pavilon);
			}
		}
		
		this.zvierata.remove(zviera);	
		
		int hodnota = (zviera.getHodnota() * (zviera.getZdravotnyStav().getHodnota() / 100));
		this.financie.setZisk(hodnota);
		this.financie.setKapital(hodnota);
		System.out.println("Zoo predala zviera [" + zviera.getNazov() + "] za " + zviera.getHodnota() + "€");
		zviera = null;
		this.predaneZvierata++;
	}
	
	
	public void predajZviera(String nazovZvierata) {
		
		Random random = new Random();
		Pavilon pavilon = null;
				
		for (int i=0; i<this.pavilony.size(); i++) {	
			if (this.pavilony.get(i).nazovPavilonu == nazovZvierata) {
				pavilon = this.pavilony.get(i);
				break;
			}
		}
		
		if (pavilon == null) {
			System.out.println("Zviera [" + nazovZvierata + "] sa v ZOO nenachadza");
			return;
		}
		
		int cisloKlietky = random.nextInt((pavilon.klietky.size()-1)-0+1)+0;
		Klietka klietka = pavilon.klietky.get(cisloKlietky);
		
		int cisloZvierata = random.nextInt((klietka.zvierataVKlietke.size()-1)-0+1)+0;
		Zviera zviera = klietka.zvierataVKlietke.get(cisloZvierata);
		
		klietka.odstranZviera(zviera, pavilon);
		
		if (klietka.zvierataVKlietke.size() == 0) {
			pavilon.odstranKlietku(klietka);
			
			if (pavilon.klietky.size() == 0) {
				odstranPavilon(pavilon);
			}
		}
		
		this.zvierata.remove(zviera);
		
		int hodnota = (zviera.getHodnota() * (zviera.getZdravotnyStav().getHodnota() / 100));
		this.financie.setZisk(hodnota);
		this.financie.setKapital(hodnota);
		
		System.out.println("Zoo predala zviera [" + zviera.getNazov() + "] za " + zviera.getHodnota() + "€");
		zviera = null;
		this.predaneZvierata++;
	}
	
	
	public void predajZviera(Zviera zviera) {
		
		
		Klietka klietka = zviera.getKlietka();
		Pavilon pavilon = null;
		
		for (int i=0; i<this.pavilony.size(); i++) {	
			if (this.pavilony.get(i).nazovPavilonu == zviera.getNazov()) {
				pavilon = this.pavilony.get(i);
				break;
			}
		}
				
		klietka.odstranZviera(zviera, pavilon);
		
		if (klietka.zvierataVKlietke.size() == 0) {
			pavilon.odstranKlietku(klietka);
			
			if (pavilon.klietky.size() == 0) {
				odstranPavilon(pavilon);
			}
		}
		
		this.zvierata.remove(zviera);
		
		int hodnota = (zviera.getHodnota() * (zviera.getZdravotnyStav().getHodnota() / 100));
		this.financie.setZisk(hodnota);
		this.financie.setKapital(hodnota);
		
		System.out.println("Zoo predala zviera [" + zviera.getNazov() + "] za " + zviera.getHodnota() + "€");
		zviera = null;	
		this.predaneZvierata++;
	}
	
	public void odstranZviera(Zviera zviera) {
		
		System.out.println("***** V Zoo prave zomrelo zviera [" + zviera.getNazov() + "] :( *****");
		
		Klietka klietka = zviera.getKlietka();
		Pavilon pavilon = null;
		
		for (int i=0; i<this.pavilony.size(); i++) {	
			if (this.pavilony.get(i).nazovPavilonu == zviera.getNazov()) {
				pavilon = this.pavilony.get(i);
				break;
			}
		}
				
		klietka.odstranZviera(zviera, pavilon);
		
		if (klietka.zvierataVKlietke.size() == 0) {
			pavilon.odstranKlietku(klietka);
			
			if (pavilon.klietky.size() == 0) {
				odstranPavilon(pavilon);
			}
		}
		
		this.zvierata.remove(zviera);
		zviera = null;	
		this.umrtieZvierat++;
	}	
	
	public void prijmiNovehoZamestnanca(Zamestnanec novyZamestnanec) {
		
		Random random = new Random();
		int vek = random.nextInt(55-18+1)+18;
		int skusenosti = random.nextInt(5-0+1)+0;
		
		novyZamestnanec.setVek(vek);
		novyZamestnanec.setSkusenosti(skusenosti);
		
		if (novyZamestnanec instanceof Veduci) {
			Veduci novyVeduci = (Veduci)novyZamestnanec; 
			this.veduci.add(novyVeduci);
			System.out.println("Zoo prave prijala noveho zamestnanca [" + novyZamestnanec.getClass().getSimpleName() + "]");
			this.prijatiVeduci++;
		}
		
		else {
			novyZamestnanec.setVeduci(this.veduci);
			this.zamestnanci.add(novyZamestnanec);
			System.out.println("Zoo prave prijala noveho zamestnanca [" + novyZamestnanec.getClass().getSimpleName() + "]");
			this.prijatiZamestnanci++;
		}
	}
		
}
