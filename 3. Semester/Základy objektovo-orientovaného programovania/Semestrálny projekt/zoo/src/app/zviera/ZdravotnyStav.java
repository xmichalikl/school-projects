package app.zviera;

public class ZdravotnyStav {
	
	protected int hodnota = 100; 
	
	public void setHodnota(int zmenO) {
		this.hodnota += zmenO;
	}
	
	public int getHodnota() {
		return this.hodnota;
	}
	
}
