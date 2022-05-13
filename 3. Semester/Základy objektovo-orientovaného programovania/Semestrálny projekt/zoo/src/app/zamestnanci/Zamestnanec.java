package app.zamestnanci;

import java.util.ArrayList;

public class Zamestnanec {
	
	protected int vek;
	protected int skusenosti;
	public int energia;
	protected ArrayList<Veduci> veduci; 
	
	public Zamestnanec() {
		this.energia = 5;
	}
	
	public void setSkusenosti(int skusenosti) {
		this.skusenosti = skusenosti;
		this.energia += skusenosti;
	}
	
	public void setVek(int vek) {
		this.vek = vek;
	}
	
	public void setVeduci(ArrayList<Veduci> veduci) {
		this.veduci = veduci;
	}
	
	public void setEnergia() {
		this.energia = this.skusenosti + 5;
	}
}
