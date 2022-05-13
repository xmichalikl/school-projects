package app.zoo;

import java.util.ArrayList;

import app.pavilon.Klietka;
import app.pavilon.Pavilon;


public class StavZoo {
	
	private int cistota = 100;
	private int poskodenie = 0;
	
	
	public void zistiStav(ArrayList<Pavilon> pavilony) {
		
		int pocetKlietok = 0;
		int poskodenie = 0;
		int cistota = 0;
		
		for (Pavilon pavilon : pavilony) {
			pocetKlietok += pavilon.klietky.size();
			
			for (Klietka klietka : pavilon.klietky) {
				cistota += klietka.getCistota();
				poskodenie += klietka.getPoskodenie();
			}
		}
		
		this.poskodenie = (poskodenie / pocetKlietok);
		this.cistota = (cistota / pocetKlietok);
	}
	
	
	public void setCistota() {
		
	} 
	
	public void setPoskodenie() {
		
	} 
	
	public int getCistota() {
		return this.cistota;
	}
	
	public int getPoskodenie() {
		return this.poskodenie;
	}
	
	public float getIndex() {
		float index = (( (this.cistota - this.poskodenie) / 100f) + 1);	
		return index;
	}
		
}
