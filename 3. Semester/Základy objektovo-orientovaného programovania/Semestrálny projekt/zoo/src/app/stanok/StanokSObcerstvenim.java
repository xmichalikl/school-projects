package app.stanok;

import app.zooSystem.Enums.typTovaru;

public class StanokSObcerstvenim extends Stanok {
	
	protected typTovaru druhTovaru; 
	
	public StanokSObcerstvenim() {
		super();
		this.druhTovaru = typTovaru.OBCERSTVENIE;
		this.cenaTovaru = 3;
	}
	
	public void predajTovar(int navstevnici) {
		
		int predaj = (navstevnici - (navstevnici/4));
		
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
