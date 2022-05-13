package app.zviera;
import app.pavilon.Klietka;
import app.zoo.Zoo;

public abstract class Zviera {
	
	protected String nazov;
	protected String typ;
	protected String pohlavie;
	protected int hodnota;
	protected Klietka klietka;
	protected Zoo zoo;
	protected final ZdravotnyStav zdravie;
	protected final Potrava potrava;

	
	public Zviera (String nazov, int hodnota, Zoo zoo) {
		this.nazov = nazov;
		this.hodnota = hodnota;
		this.zoo = zoo;
		this.zdravie = new ZdravotnyStav();
		this.potrava = new Potrava(this);
	}
	
	public Zviera (String nazov, String typ, int hodnota, Zoo zoo) {
		this.nazov = nazov;
		this.typ = typ;
		this.hodnota = hodnota;
		this.zoo = zoo;
		this.zdravie = new ZdravotnyStav();
		this.potrava = new Potrava(this);
	}
	
	public abstract void ziedzPotravu();
	
	public void znecistniKlietku() {
		if (this.klietka.getCistota() >= 10 ) {
			this.klietka.setCistota(-10);
			System.out.println("Zviera [" + this.nazov + "] zaspinilo klietku");
			System.out.println("Cistota klietky (" + (this.klietka.getCistota()+10) + ") -> (" + this.klietka.getCistota() + ")" );
		}
	}
	
	public void poskodKlietku() {
		if (this.klietka.getPoskodenie() <= 90 ) {
			this.klietka.setPoskodenie(10);
			System.out.println("Zviera [" + this.nazov + "] poskodilo klietku");
			System.out.println("Poskodenie klietky (" + (this.klietka.getPoskodenie()-10) + ") -> (" + this.klietka.getPoskodenie() + ")" );
		}
	}
	
	public void umri(Zviera zviera) {
		this.zoo.mrtveZvierata.add(zviera);
	}
	
	
	public String getNazov() {
		return this.nazov;
	}
	
	public String getPohlavie() {
		return this.pohlavie;
	}
	
	public int getHodnota() {
		return this.hodnota;
	}
	
	public void setHodnota(int zmenO) {
		this.hodnota += zmenO;
	}
	
	public Klietka getKlietka() {
		return this.klietka;
	}
	
	public void setKlietka(Klietka novaKlietka) {
		this.klietka = novaKlietka;
	}
	
	public ZdravotnyStav getZdravotnyStav() {
		return this.zdravie;
	}
	
	public Potrava getPotrava() {
		return this.potrava;
	}
}
