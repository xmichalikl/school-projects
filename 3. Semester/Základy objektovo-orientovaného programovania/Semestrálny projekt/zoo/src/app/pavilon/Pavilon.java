package app.pavilon;
import java.util.ArrayList;
import app.zviera.Zviera;

public class Pavilon {
	
	public String nazovPavilonu;			
	public ArrayList<Klietka> klietky;
	
	public Pavilon() {
		this.klietky = new ArrayList<Klietka>();
		System.out.println("Zoo vytvorila novy pavilon (" + this.klietky.size() + ") [" + this.nazovPavilonu + "]");
	}
	
	public Pavilon(String nazovPavilonu) {
		this.nazovPavilonu = nazovPavilonu;
		this.klietky = new ArrayList<Klietka>();
		System.out.println("Zoo vytvorila novy pavilon [" + this.nazovPavilonu + "] (" + this.klietky.size() + ")");
	}
	
	public void vytvorKlietku() {
		Klietka novaKlietka = new Klietka(this);
		this.klietky.add(novaKlietka);
	}
	
	public void vytvorKlietku(Zviera noveZviera) {
		Klietka novaKlietka = new Klietka(this);
		this.klietky.add(novaKlietka);
		novaKlietka.pridajZviera(noveZviera, this);
	}
	
	
		
	public void odstranKlietku(Klietka klietka) {
		if (klietka.zvierataVKlietke.size() > 0) {
			System.out.println("V pavilone [" + this.nazovPavilonu + "] (" + this.klietky.size() + ") nemozno odstranit klietku (" + klietka.zvierataVKlietke.size() + "/" + klietka.maxPocetZvierat + "), lebo sa v nej nachadzaju zvierata");
			return;
		}
		else {
			this.klietky.remove(klietka);
			System.out.println("V pavilone [" + this.nazovPavilonu + "] (" + (this.klietky.size()+1) + ") -> (" + this.klietky.size() + ") bola odstranena klietka");
			klietka = null;		
		}
	}
	
	public void najdiKlietkuPreZviera(Zviera noveZviera){
		for (int i=0; i<this.klietky.size(); i++) {
			if (this.klietky.get(i).zvierataVKlietke.size() < this.klietky.get(i).maxPocetZvierat) {
				this.klietky.get(i).pridajZviera(noveZviera, this);
				return;
			}
		}
		vytvorKlietku(noveZviera);
	}
}
