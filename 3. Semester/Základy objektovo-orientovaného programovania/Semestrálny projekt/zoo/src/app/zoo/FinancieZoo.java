package app.zoo;

import java.util.ArrayList;

import app.stanok.Stanok;
import app.zamestnanci.Veduci;
import app.zamestnanci.Zamestnanec;

public class FinancieZoo {
	
	private int kapital;
	private int zisk = 0;
	private int vydavky = 0;
	
	
	public FinancieZoo(int peniaze) {
		this.kapital = peniaze;
	}
	
	
	public void vyplatyASponzorstvo(ArrayList<Veduci> veduci, ArrayList<Zamestnanec> zamestnanci, int sponzorstvo) {
		int vyplaty = ((zamestnanci.size() * 900) + (veduci.size() * 1500));
		this.vydavky += vyplaty;
		this.kapital -= vyplaty;
		this.zisk += sponzorstvo;
		this.kapital += sponzorstvo;
	}
	
	public void listky(int pocetDospelych, int pocetDeti) {
		
		int dospelyListok = 10;
		int detskyListok = 5;
		
		this.zisk += (pocetDospelych * dospelyListok);
		this.kapital += (pocetDospelych * dospelyListok);
		this.zisk += (pocetDeti * detskyListok);
		this.kapital += (pocetDeti * detskyListok);
	}
	
	public void stanky(ArrayList<Stanok> stanky) {
		
		int zarobok = 0;
		
		for (Stanok stanok : stanky) {
			zarobok += stanok.getZarobok();
			stanok.setZarobok(-stanok.getZarobok());
		}
		this.zisk += zarobok;
		this.kapital += zarobok;
		
		System.out.println("Stanky v Zoo dnes spolu zarobili " + zarobok + "€");
	}
	
	
	public int getKapital () {
		return this.kapital;
	}
	
	public void setKapital(int zmenO) {
		this.kapital += zmenO;
	}
	
	public int getZisk () {
		return this.zisk;
	}
	
	public void setZisk(int zmenO) {
		this.zisk += zmenO;
	}
	
	public int getVydavky () {
		return this.vydavky;
	}
	
	public void setVydavky(int zmenO) {
		this.vydavky += zmenO;
	}
	
}
