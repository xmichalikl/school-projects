package app.pavilon;
import java.util.ArrayList;
import app.zviera.Zviera;

public class Klietka {
	
	protected int cistota = 100;
	protected int poskodenie = 0;
	protected int maxPocetZvierat = 5;
	public ArrayList<Zviera> zvierataVKlietke;
	
	public Klietka(Pavilon pavilon) {
		this.zvierataVKlietke = new ArrayList<Zviera>();
		if (pavilon.klietky.size() > 0) {
			System.out.println("V pavilone [" + pavilon.nazovPavilonu + "] (" + (pavilon.klietky.size()-1) + ") -> (" + pavilon.klietky.size() + ") bola vytvorena nova klietka (" + this.zvierataVKlietke.size() + "/" + this.maxPocetZvierat + ")");
		}
		else {
			System.out.println("V pavilone [" + pavilon.nazovPavilonu + "] (" + pavilon.klietky.size() + ") -> (" + (pavilon.klietky.size()+1) + ") bola vytvorena nova klietka (" + this.zvierataVKlietke.size() + "/" + this.maxPocetZvierat + ")");
		}
	}
		
	public void pridajZviera(Zviera noveZviera, Pavilon pavilon) {
		this.zvierataVKlietke.add(noveZviera);
		noveZviera.setKlietka(this);
		System.out.println("V pavilone [" + pavilon.nazovPavilonu + "] (" + pavilon.klietky.size() + ") bolo do klietky (" + (this.zvierataVKlietke.size()-1) + "/" + this.maxPocetZvierat + ") -> (" + this.zvierataVKlietke.size() + "/" + this.maxPocetZvierat + ") pridane zviera [" + noveZviera.getNazov() + "]");
	}
			
	public void odstranZviera(Zviera zviera, Pavilon pavilon) {
		this.zvierataVKlietke.remove(zviera);
		System.out.println("V pavilone [" + pavilon.nazovPavilonu + "] (" + pavilon.klietky.size() + ") bolo z klietky (" + (this.zvierataVKlietke.size()+1) + "/" + this.maxPocetZvierat + ") -> (" + this.zvierataVKlietke.size() + "/" + this.maxPocetZvierat + ") odstranene zviera [" + zviera.getNazov() + "]");

	}
	
	
	public int getCistota() {
		return this.cistota;
	}
	
	public void setCistota(int zmenO) {
		this.cistota += zmenO;
	}
	
	public int getPoskodenie() {
		return this.poskodenie;
	}
	
	public void setPoskodenie(int zmenO) {
		this.poskodenie += zmenO;
	}
	
	public int getMaxPocetZvierat() {
		return this.maxPocetZvierat;
	}
	
}
