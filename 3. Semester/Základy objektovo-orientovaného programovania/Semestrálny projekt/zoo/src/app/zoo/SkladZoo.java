package app.zoo;

import app.zamestnanci.Skladnik;

public class SkladZoo {
	
	private Skladnik skladnik;
	
	private int kapacita = 4000;
	
	private int potravaMaso = 1000;
	private int potravaByliny = 1000;
	
	private int suveniry = 1000;
	private int obcerstvenie = 1000;
	
	
	
	public int getKapacita() {
		return this.kapacita;
	}
	
	public void setKapacita(int zmenO) {
		this.kapacita += zmenO;
	}
		
	public int getPotravaMaso() {
		return this.potravaMaso;
	}
	
	public void setPotravaMaso(int zmenO) {
		this.potravaMaso += zmenO;
	}
	
	public int getPotravaByliny() {
		return this.potravaByliny;
	}
	
	public void setPotravaByliny(int zmenO) {
		this.potravaByliny += zmenO;
	}
	
	public int getSuveniry() {
		return this.suveniry;
	}
	
	public void setSuveniry(int zmenO) {
		this.suveniry += zmenO;
	}
	
	public int getObcerstvenie() {
		return this.obcerstvenie;
	}
	
	public void setObcerstvenie(int zmenO) {
		this.obcerstvenie += zmenO;
	}
	
	public Skladnik getSkladnik() {
		return this.skladnik;
	}
	
	public void setSkladnik(Skladnik skladnik) {
		this.skladnik = skladnik;
	}
	
}
