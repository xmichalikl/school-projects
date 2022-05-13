package Persons;

import Pc.*;
import Store.Store;

//Rozsirena trieda Software technik, podla typu a poziadaviek objednavky nainstaluje do PC pozadovany software
public class SoftwareTechnician extends Employee {
	private String dataBackup;

	/***
	 * Konstruktor na vytvorenie Softveroveho technika
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public SoftwareTechnician(Store store, String name, String surname, String userName, String userPassword) {
		super(store, name, surname, userName, userPassword);
		this.dataBackup = null;
	}

	/***
	 * Prekonana metoda z rozhrania MooveOrder,
	 * ktora odosle PC vhodnemu zamestnancovi
	 * @param from Odosielatel
	 * @param pc PC na odoslanie
	 */
	@Override
	public void movePc(Employee from, Pc pc) {
		for ( Employee to : getStore().getEmployees() ) {
			if ( to instanceof TestingTechnician && to.getIsAvailable()) {
				setIsAvailable(true);
				setNextEmp(to);
				((TestingTechnician) to).setPc(pc);
				this.setPc(null);
				break;
			}
		}
		this.dataBackup = null;
	}

	/***
	 * Prekonana metoda z rozhrania MooveOrder, ktora
	 * odosle PC konkretnemu(zvolenemu) zamestnancovi
	 * @param from Odosielatel
	 * @param to Prijimatel
	 * @param pc PC na odoslanie
	 */
	@Override
	public void movePc(Employee from, Employee to, Pc pc) {
		if (to instanceof TestingTechnician) {
			((TestingTechnician) to).setPc(pc);
			this.setPc(null);
			to.setIsAvailable(false);
			setIsAvailable(true);
			this.dataBackup = null;
		}
	}


	/***
	 * Prekonana abstraktna metoda z triedy Zamestnanec
	 * Podla typu PC a poziadaviek objednavky prebehne
	 * proces instalacie softwareu a nasledne a nasledne
	 * sa posle PC dalsiemu zamestnancovi (Testovac)
	 */
	@Override
	public void doTask() {
		Pc pc = this.getPc();

		if (pc instanceof NewPc && !((NewPc)pc).getOsInstalled()) {
			installOs((NewPc) pc);
			installDrivers(pc);
		}
		else if (pc instanceof BrokenPc && !((BrokenPc)pc).getOsUpdate()) {

			if ( ((BrokenPc) pc).getPcBackup() ) {
				backupData((BrokenPc) pc);
			}

			if ( ((BrokenPc) pc).getPcCleanup() ) {
				installOs((BrokenPc) pc);
				returnData((BrokenPc) pc);
			}
			else {
				removeVirus((BrokenPc) pc);
				updateOs((BrokenPc) pc);
			}
			installDrivers(pc);
		}
		movePc(this, this.getPc());
	}


	/***
	 * Zaloha dat z opraveneho PC
	 * @param brokenPc Opraveny PC
	 */
	public void backupData(BrokenPc brokenPc) {
		this.dataBackup = brokenPc.getData();
	}

	/***
	 * Vratenie dat do opraveneho PC
	 * @param brokenPc Opraveny PC
	 */
	public void returnData(BrokenPc brokenPc) {
		brokenPc.setData(this.dataBackup);
		this.dataBackup = null;
	}

	/***
	 * Odstranenie virusu
	 * @param brokenPc Opraveny PC
	 */
	public void removeVirus(BrokenPc brokenPc) {
		brokenPc.setVirus(false);
	}

	/***
	 * Instalacia OS na Novy PC
	 * @param newPc Novy PC
	 */
	public void installOs(NewPc newPc) {
		newPc.setOsInstalled(true);
	}

	/***
	 * Instalacia OS na Opraveny PC
	 * @param brokenPc Opraveny PC
	 */
	public void installOs(BrokenPc brokenPc) {
		brokenPc.setData(null);
		brokenPc.setGpuDriver(null);
		brokenPc.setChipsetDriver(null);
		brokenPc.setVirus(false);
		brokenPc.setOsUpdate(true);
	}

	/***
	 * Update OS na Opravenom PC
	 * @param brokenPc Opraveny PC
	 */
	public void updateOs(BrokenPc brokenPc) {
		brokenPc.setOsUpdate(true);
	}

	/***
	 * Instalacia ovladacov na konkretny PC
	 * @param pc Konkretny PC
	 */
	public void installDrivers(Pc pc) {
		if (pc.getGpuDriver() == null ||
			pc.getGpuDriver() != pc.getGpu().getManufacturer()) {
			pc.setGpuDriver(pc.getGpu().getManufacturer());
		}
		if (pc.getChipsetDriver() == null ||
			pc.getChipsetDriver() != pc.getMbo().getSocket()) {
			pc.setChipsetDriver(pc.getMbo().getSocket());
		}
	}

	/***
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Instalacia SW";
	}
}
