package app.zamestnanci;

import java.util.Random;

import app.zoo.FinancieZoo;
import app.zoo.SkladZoo;

public class Skladnik extends Zamestnanec {
	
	private SkladZoo sklad;
	private FinancieZoo fiancie;
	
	
	public Skladnik(SkladZoo sklad, FinancieZoo fiancie) {
		super();
		this.sklad = sklad;
		this.fiancie = fiancie;
	}
	
	public void skontrolujZasoby() {
		
		Random random = new Random();
		int kapacita = this.sklad.getKapacita()/4;
	
		if (this.sklad.getPotravaMaso() < kapacita/2) {
			int cena = random.nextInt(5000-2500+1)+2500;
			this.fiancie.setVydavky(cena);
			this.fiancie.setKapital(-cena);
			this.sklad.setPotravaMaso(kapacita/4);
			System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] doplnil zasoby (Maso) do skladu");
		}
		
		if (this.sklad.getPotravaByliny() < kapacita/2) {
			int cena = random.nextInt(3000-1500+1)+1500;
			this.fiancie.setVydavky(cena);
			this.fiancie.setKapital(-cena);
			this.sklad.setPotravaByliny(kapacita/4);
			System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] doplnil zasoby (Byliny) do skladu");
		}
		
		if (this.sklad.getObcerstvenie() < kapacita/2) {
			int cena = random.nextInt(2000-500+1)+500;
			this.fiancie.setVydavky(cena);
			this.fiancie.setKapital(-cena);
			this.sklad.setObcerstvenie(kapacita/4);
			System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] doplnil zasoby (Obcerstvenie) do skladu");
		}
		
		if (this.sklad.getSuveniry() < kapacita/2) {
			int cena = random.nextInt(2500-1500+1)+1500;
			this.fiancie.setVydavky(cena);
			this.fiancie.setKapital(-cena);
			this.sklad.setSuveniry(kapacita/4);
			System.out.println("Zamestnanec [" + this.getClass().getSimpleName() + "] doplnil zasoby (Suveniry) do skladu");
		}
	}
}


	



