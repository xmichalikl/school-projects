package Pc;

import Enums.Performance;

//Rozsirena trieda Novy PC
public class NewPc extends Pc {
	
	private int ramSize;
	private int hddSize;
	private int psuPower;

	private boolean osInstalled;

	/***
	 * Konstruktor na vytvorenie Noveho PC
	 * @param performance Vykon noveho PC
	 */
	public NewPc(Performance performance) {
		this.performance = performance;
		this.osInstalled = false;
	}

	/***
	 * Getter na ziskanie pozadovanej velkosti pamate ram
	 * @return Vrati pozadovanu velkost pamate ram
	 */
	public int getRamSize() {
		return ramSize;
	}

	/***
	 * Setter na nastavenie pozadovanej velkosti pamate ram
	 * @param ramSize Pozadovana velkost pamate ram
	 */
	public void setRamSize(int ramSize) {
		this.ramSize = ramSize;
	}

	/***
	 * Getter na ziskanie pozadovanej kapacity pevneho disku
	 * @return Vrati pozadovanu kapacitu pevneho disku
	 */
	public int getHddSize() {
		return hddSize;
	}

	/***
	 * Setter na nastavenie pozadovanej kapacity pevneho disku
	 * @param hddSize Pozadovana kapacita pevneho disku
	 */
	public void setHddSize(int hddSize) {
		this.hddSize = hddSize;
	}

	/***
	 * Getter na ziskanie pozadovaneho vykonu zdroja
	 * @return Vrati pozadovany vykon zdroja
	 */
	public int getPsuPower() {
		return psuPower;
	}

	/***
	 * Setter na nastavenie pozadovaneho vykonu zdroja
	 * @param psuPower Pozadovany vykon zdroja
	 */
	public void setPsuPower(int psuPower) {
		this.psuPower = psuPower;
	}

	/***
	 * Getter na zistenie statusu o nainstalovanom OS
	 * @return Vrati status o nainstalovanom OS
	 */
	public boolean getOsInstalled() {
		return this.osInstalled;
	}

	/***
	 * Setter na instalaciu OS
	 * @param newOs OS na instalaciu
	 */
	public void setOsInstalled(boolean newOs) {
		this.osInstalled = newOs;
	}

	/***
	 * Prekonana toString metoda
	 * na zistenie typu PC
	 * @return Vrati typ PC
	 */
	@Override
	public String toString() {
		return "Novy PC";
	}
}
