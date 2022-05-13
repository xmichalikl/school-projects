package app.zviera;

import app.zooSystem.Enums.typPotravy;

public class Potrava {
	
	protected int mnozstvo;
	protected int kvalita;
	protected typPotravy druhPotravy; 
	
	public Potrava(Zviera zviera) {
		this.mnozstvo = 0;
		this.kvalita = 0;
		
		if (zviera instanceof Bylinozravec) {
			this.druhPotravy = typPotravy.BYLINY;
		}
		
		else if (zviera instanceof Masozravec) {
			this.druhPotravy = typPotravy.MASO;
		}
		
		else {
			this.druhPotravy = typPotravy.VSETKO;
		}
	}
		
	public int getMnozstvo() {
		return this.mnozstvo;
	}
	
	public void setMnozstvo(int zmenO) {
		this.mnozstvo += zmenO;
	}
	
	public int getKvalita() {
		return this.kvalita;
	}
	
	public void setKvalita(int zmenO) {
		this.kvalita += zmenO;
	}
	
	public typPotravy getDruhPotravy() {
		return this.druhPotravy;
	}
	
	public void setDruhPotravy(typPotravy druhPotravy) {
		this.druhPotravy = druhPotravy;
	}
}