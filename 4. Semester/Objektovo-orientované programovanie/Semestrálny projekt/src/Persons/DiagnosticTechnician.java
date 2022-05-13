package Persons;

import java.util.ArrayList;

import Components.*;
import Pc.Pc;
import Store.Store;

//Rozsirena trieda Diagnostik, kontroluje pokazeny PC a oznaci vadne komponenty
public class DiagnosticTechnician extends Employee {
	private final ArrayList<Component> brokenComponents;

	/***
	 * Konstruktor na vytvorenie Diagnostika
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public DiagnosticTechnician(Store store, String name, String surname, String userName, String userPassword) {
		super(store, name, surname, userName, userPassword);
		this.brokenComponents = new ArrayList<Component>(); 
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
			if ( to instanceof ConfigurationTechnician && to.getIsAvailable()) {
				setIsAvailable(true);
				setNextEmp(to);
				((ConfigurationTechnician) to).setPc(pc);
				((ConfigurationTechnician) to).setBrokenComponents(this.brokenComponents);
				this.setPc(null);
				break;
			}
		}
		this.brokenComponents.clear();
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
		if ( to instanceof ConfigurationTechnician ) {
			((ConfigurationTechnician) to).setPc(pc);
			((ConfigurationTechnician) to).setBrokenComponents(this.brokenComponents);
			this.brokenComponents.clear();
			this.setPc(null);
			to.setIsAvailable(false);
			setIsAvailable(true);
		}	
	}



	/***
	 * Prekonana abstraktna metoda z triedy Zamestnanec
	 * Skontroluje vsetky komponenty v PC, vadne komponenty
	 * oznaci a posle dalsiemu zamestnancovi (Konfigurator)
	 */
	@Override
	public void doTask() {
		Cpu checkCpu = getPc().getCpu();
		if ( !checkCpu.getWorking() )
			this.brokenComponents.add(checkCpu);

		Gpu checkGpu = getPc().getGpu();
		if ( !checkGpu.getWorking() )
			this.brokenComponents.add(checkGpu);

		Ram checkRam = getPc().getRam();
		if ( !checkRam.getWorking() )
			this.brokenComponents.add(checkRam);

		Mbo checkMbo = getPc().getMbo();
		if ( !checkMbo.getWorking() )
			this.brokenComponents.add(checkMbo);

		Hdd checkHdd = getPc().getHdd();
		if ( !checkHdd.getWorking() )
			this.brokenComponents.add(checkHdd);

		Psu checkPsu = getPc().getPsu();
		if ( !checkPsu.getWorking() )
			this.brokenComponents.add(checkPsu);

		movePc(this, this.getPc());
	}


	/***
	 * Setter na proriradenie konkretneho chybneho komponentu
	 * @param component Chybny komponent
	 */
	public void setBrokenComponent(Component component) {
		component.setWorking(false);
		this.brokenComponents.add(component);
	}

	/***
	 * Getter na ziskanie chybnych komponentov
	 * @return Vrati chybne komponenty
	 */
	public ArrayList<Component> getBrokenComponents() {
		return this.brokenComponents;
	}



	/***
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Diagnostika PC";
	}
	
}
