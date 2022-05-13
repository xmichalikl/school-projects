package app.zamestnanci;
import java.util.Random;

import app.pavilon.Klietka;

public interface KontrolaKlietky {
	
	public default void skontrolujKlietku(Klietka klietka, Zamestnanec zamestnanec) {
		
		if (klietka.getCistota() < 50) {
			upozorniVeduceho(klietka, zamestnanec, "cistota");
		}
		
		if (klietka.getPoskodenie() > 50) {
			upozorniVeduceho(klietka, zamestnanec, "poskodenie");
		}
	}
	
	public default void upozorniVeduceho(Klietka klietka, Zamestnanec zamestnanec, String problem) {
		
		Random random = new Random();
		int nahodny = random.nextInt((zamestnanec.veduci.size()-1)-0+1)+0;
		System.out.println("Zamestnanec [" + zamestnanec.getClass().getSimpleName() + "] upozornil veduceho na problem [" + problem + "] v klietke");
		zamestnanec.veduci.get(nahodny).skontrolujKlietku(problem, klietka);
	}
}
