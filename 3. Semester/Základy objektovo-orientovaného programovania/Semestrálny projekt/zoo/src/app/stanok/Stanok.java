package app.stanok;

public abstract class Stanok {
	
	protected int tovar;
	protected int kapacita = 500;
	protected int cenaTovaru;
	protected int zarobok;
	
	public Stanok() {
		this.tovar = 500;
		this.zarobok = 0;
	}
	
	public abstract void predajTovar(int navstevnici);
	
	public int getTovar() {
		return this.tovar;
	}
	
	public void setTovar(int zmenO) {
		this.tovar += zmenO;
	}
	
	public int getZarobok() {
		return this.zarobok;
	}
	
	public void setZarobok(int zmenO) {
		this.zarobok += zmenO;
	}
	
	public int getKapacita() {
		return this.kapacita;
	}
}
