package Persons;

import java.util.ArrayList;

import Components.*;
import Pc.Pc;
import Store.Store;

//Rozsirena trieda Montaznik, demontuje oznacene vadne komponenty a namontuje pridelene nove/nahradne komponenty
public class AssemblyTechnician extends Employee {
	
	private final ArrayList<Component> brokenComponents;
	private final ArrayList<Component> newComponents;

	/***
	 * Konstruktor na vytvorenie Montaznika
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public AssemblyTechnician(Store store, String name, String surname, String userName, String userPassword) {
		super(store, name, surname, userName, userPassword);
		this.brokenComponents = new ArrayList<Component>();
		this.newComponents = new ArrayList<Component>();
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
			if ( to instanceof SoftwareTechnician && to.getIsAvailable()) {
				setIsAvailable(true);
				setNextEmp(to);
				((SoftwareTechnician) to).setPc(pc);
				this.setPc(null);
				break;
			}
		}
		this.brokenComponents.clear();
		this.newComponents.clear();
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
		if (to instanceof SoftwareTechnician) {
			((SoftwareTechnician) to).setPc(pc);
			this.brokenComponents.clear();
			this.newComponents.clear();
			this.setPc(null);
			to.setIsAvailable(false);
			setIsAvailable(true);
		}
	}



	/***
	 * Prekonana abstraktna metoda z triedy Zamestnanec
	 * Prebehne cez vsetky oznacene vadne komponenty a
	 * demontuje ich a namontuje vsetky pridelne
	 * nove/nahradne komponenty a nasledne posle PC
	 * dalsiemu zamestnancovi (Software technik)
	 */
	@Override
	public void doTask() {
		this.brokenComponents.forEach(brokenCom -> {
			removeComponent(brokenCom);
		});

		this.newComponents.forEach(newCom -> {
			addComponent(newCom);
		});

		movePc(this, this.getPc());
	}


	/***
	 * Metoda na odstranenie konkretneho komponentu z PC
	 * @param component Komponent na odstranenie
	 */
	public void removeComponent(Component component) {
		if (component instanceof Cpu) {
			this.getPc().setCpu(null);
		}
		else if (component instanceof Gpu) {
			this.getPc().setGpu(null);
		}
		else if (component instanceof Ram) {
			this.getPc().setRam(null);
		}
		else if (component instanceof Mbo) {
			this.getPc().setMbo(null);
		}
		else if (component instanceof Hdd) {
			this.getPc().setHdd(null);
		}
		else if (component instanceof Psu) {
			this.getPc().setPsu(null);
		}
	}



	/***
	 * Metoda na instalaciu konkretneho komponentu do PC
	 * @param component Komponent na instalaciu
	 */
	public void addComponent(Component component) {
		if (component instanceof Cpu) {
			this.getPc().setCpu((Cpu) component);
		}
		else if (component instanceof Gpu) {
			this.getPc().setGpu((Gpu) component);
		}
		else if (component instanceof Ram) {
			this.getPc().setRam((Ram) component);
		}
		else if (component instanceof Mbo) {
			this.getPc().setMbo((Mbo) component);
		}
		else if (component instanceof Hdd) {
			this.getPc().setHdd((Hdd) component);
		}
		else if (component instanceof Psu) {
			this.getPc().setPsu((Psu) component);
		}
	}

	/***
	 * Setter na proriradenie chybnych komponentov
	 * @param brokenComponents Chybne komponenty
	 */
	public void setBrokenComponents(ArrayList<Component> brokenComponents) {
		this.brokenComponents.addAll(brokenComponents);
	}

	/***
	 * Getter na ziskanie chybnych komponentov
	 * @return Vrati chybne komponenty
	 */
	public ArrayList<Component> getBrokenComponents() {
		return this.brokenComponents;
	}

	/***
	 * Setter na proriradenie novych/nahradnych komponentov
	 * @param newComponents Nove/nahradne komponenty
	 */
	public void setNewComponents(ArrayList<Component> newComponents) {
		this.newComponents.addAll(newComponents);
	}

	/***
	 * Getter na ziskanie novych/nahradnych komponentov
	 * @return Vrati nove/nahradne komponenty
	 */
	public ArrayList<Component> getNewComponents() {
		return this.newComponents;
	}

	/***
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Montaz PC";
	}
	
}
