package app.stanok;

import app.zooSystem.Enums.typTovaru;

public class StanokSoSuvenirmi extends Stanok {
	
	protected typTovaru druhTovaru; 
	
	public StanokSoSuvenirmi() {
		super();
		this.druhTovaru = typTovaru.SUVENIRY;
		this.cenaTovaru = 5;
	}
	
	public void predajTovar(int navstevnici) {
		
		int predaj = (navstevnici/4);
		
		if (this.tovar >= predaj) {
			this.tovar -= predaj;
			this.zarobok += this.cenaTovaru*predaj;
		}
		
		else {
			this.zarobok += this.cenaTovaru*tovar;
			this.tovar = 0;
		}
	}
}
