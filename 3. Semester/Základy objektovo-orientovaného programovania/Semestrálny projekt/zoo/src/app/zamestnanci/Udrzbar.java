package app.zamestnanci;

import app.pavilon.Klietka;

public class Udrzbar extends Zamestnanec {
		
	public Udrzbar() {
		super();
	}
	
	public void oprav(Klietka klietka) {
		int poskodenie = klietka.getPoskodenie();
		
		if (poskodenie > 25) {
			klietka.setPoskodenie(-25);
		}
		else {
			klietka.setPoskodenie(-poskodenie);
		}
		System.out.println("Zamestnanec [" + getClass().getSimpleName() + "] opravil klietku");
		this.energia--;
	}
	
}
