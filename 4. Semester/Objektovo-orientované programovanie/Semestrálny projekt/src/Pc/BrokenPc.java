package Pc;

import Enums.Performance;

//Rozsirena trieda Pokazeny PC
public class BrokenPc extends Pc {

	private String data;
	private boolean virus;
	private boolean osUpdate;

	private final boolean pcCleanup;
	private final boolean pcBackup;

	/***
	 * Konstruktor na vytvorenie Pokazeneho PC
	 * @param performance Vykon pokazeneho PC
	 * @param pcCleanup Doplnkova sluzba na vycistenie PC
	 * @param pcBackup Doplnkova sluzba na zalohu dat z PC
	 */
	public BrokenPc(Performance performance, boolean pcCleanup, boolean pcBackup) {
		this.performance 	= performance;
		this.data			= this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
		this.pcCleanup		= pcCleanup;
		this.pcBackup		= pcBackup;
		this.osUpdate		= false;
	}

	/***
	 * Getter na ziskanie dat z PC
	 * @return Vrati data z PC
	 */
	public String getData() {
		return this.data;
	}

	/***
	 * Setter na nastavenie dat do PC
	 * @param data Data na nastavenie
	 */
	public void setData(String data) {
		this.data = data;
	}

	/***
	 * Getter na zistenie pritomnosti virusu
	 * @return Vrati status pritomnosti virusu
	 */
	public boolean getVirus() {
		return this.virus;
	}

	/***
	 * Setter na priradenie virusu
	 * @param virus Priradeny virus
	 */
	public void setVirus(boolean virus) {
		this.virus = virus;
	}

	/***
	 * Getter na zistenie statusu aktualizacie OS
	 * @return Vrati status aktualizacie OS
	 */
	public boolean getOsUpdate() {
		return this.osUpdate;
	}

	/***
	 * Setter na nastavenie aktualizacie OS
	 * @param newUpdate Aktualizacia OS
	 */
	public void setOsUpdate(boolean newUpdate) {
		this.osUpdate = newUpdate;
	}

	/***
	 * Getter na zistenie doplnkovej sluzby na vycistenie PC
	 * @return Vrati status doplnkovej sluzby na vycistenie PC
	 */
	public boolean getPcCleanup() {
		return this.pcCleanup;
	}

	/***
	 * Getter na zistenie doplnkovej sluzby na zalohu dat
	 * @return Vrati status doplnkovej sluzby na zalohu dat
	 */
	public boolean getPcBackup() {
		return this.pcBackup;
	}

	/***
	 * Prekonana toString metoda
	 * na zistenie typu PC
	 * @return Vrati typ PC
	 */
	@Override
	public String toString() {
		return "Pokazeny PC";
	}

}
