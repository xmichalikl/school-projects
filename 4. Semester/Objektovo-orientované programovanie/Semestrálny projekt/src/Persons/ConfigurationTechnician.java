package Persons;

import java.util.ArrayList;

import Pc.*;
import Enums.*;
import Components.*;
import Store.Store;
import Store.Warehouse;

//Rozsirena trieda Konfigurator, nakonfiguruje pokazeny/novy PC a prideli mu nove/nahradne komponenty
public class ConfigurationTechnician extends Employee {
	private final ArrayList<Component> brokenComponents;
	private final ArrayList<Component> newComponents;

	/***
	 * Konstruktor na vytvorenie Konfiguratora
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public ConfigurationTechnician(Store store, String name, String surname, String userName, String userPassword) {
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
			if ( to instanceof AssemblyTechnician && to.getIsAvailable()) {
				setIsAvailable(true);
				setNextEmp(to);
				((AssemblyTechnician) to).setPc(pc);
				((AssemblyTechnician) to).setBrokenComponents(this.brokenComponents);
				((AssemblyTechnician) to).setNewComponents(this.newComponents);
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
		if ( to instanceof AssemblyTechnician ) {
			((AssemblyTechnician) to).setPc(pc);
			((AssemblyTechnician) to).setBrokenComponents(this.brokenComponents);
			((AssemblyTechnician) to).setNewComponents(this.newComponents);
			this.brokenComponents.clear();
			this.newComponents.clear();
			this.setPc(null);
			to.setIsAvailable(false);
			setIsAvailable(true);
		}	
	}


	/***
	 * Prekonana abstraktna metoda z triedy Zamestnanec
	 * Podla typu PC zvoli metody na konfigurovanie
	 * noveho/pokazeneho PC a po nakonfigurovani a prideleni
	 * novych komponentov posle oznacene vadne aj nove komponentyv
	 * dalsiemu zamestnancovi (Montaznikovi)
	 */
	@Override
	public void doTask() {

		if (this.getPc() instanceof NewPc)
			configureNewPc();

		else if (this.getPc() instanceof BrokenPc)
			configureBrokenPc();

		movePc(this, this.getPc());
	}



	/***
	 * Metoda na konfiguraciu Pokazeneho PC
	 * Prebehne cez vsetky pokazene komponenty
	 * a hlada nahradny komponent v sklade, ktory
	 * pokial nie je na sklade, tak ho objedna
	 */
	public void configureBrokenPc() {
		Warehouse warehouse = getStore().getWarehouse();

		newComponentsSearch:
		for (Component comp : this.brokenComponents) {
			if ( comp instanceof Cpu ) {
				Cpu brokenCpu = (Cpu) comp;
				for ( Cpu newCpu : warehouse.getCpus() ) {
					if ( brokenCpu.getManufacturer() == newCpu.getManufacturer() &&
						brokenCpu.getPerformance() == newCpu.getPerformance() ) {
						warehouse.getCpus().remove(newCpu);
						this.newComponents.add(newCpu);
						continue newComponentsSearch;
					}
				}
				Cpu newCpu = warehouse.orderComponent(brokenCpu);
				this.newComponents.add(newCpu);
			}
			else if ( comp instanceof Gpu ) {
				Gpu brokenGpu = (Gpu) comp;
				for ( Gpu newGpu : warehouse.getGpus() ) {
					if ( /*brokenGpu.getManufacturer() == newGpu.getManufacturer() &&*/
						brokenGpu.getPerformance() == newGpu.getPerformance() ) {
						warehouse.getGpus().remove(newGpu);
						this.newComponents.add(newGpu);
						continue newComponentsSearch;
					}
				}
				Gpu newGpu = warehouse.orderComponent(brokenGpu);
				this.newComponents.add(newGpu);
			}
			else if ( comp instanceof Ram ) {
				Ram brokenRam = (Ram) comp;
				for ( Ram newRam : warehouse.getRams() ) {
					if ( brokenRam.getCapacity() == newRam.getCapacity() ) {
						warehouse.getRams().remove(newRam);
						this.newComponents.add(newRam);
						continue newComponentsSearch;
					}
				}
				Ram newRam = warehouse.orderComponent(brokenRam);
				this.newComponents.add(newRam);
			}
			else if ( comp instanceof Mbo ) {
				Mbo brokenMbo = (Mbo) comp;
				for ( Mbo newMbo : warehouse.getMbos() ) {
					if ( brokenMbo.getSocket() == newMbo.getSocket() ) {
						warehouse.getMbos().remove(newMbo);
						this.newComponents.add(newMbo);
						continue newComponentsSearch;
					}
				}
				Mbo newMbo = warehouse.orderComponent(brokenMbo);
				this.newComponents.add(newMbo);
			}
			else if ( comp instanceof Hdd ) {
				Hdd brokenHdd = (Hdd) comp;
				for ( Hdd newHdd : warehouse.getHdds() ) {
					if ( brokenHdd.getCapacity() == newHdd.getCapacity() ) {
						warehouse.getHdds().remove(newHdd);
						this.newComponents.add(newHdd);
						continue newComponentsSearch;
					}
				}
				Hdd newHdd = warehouse.orderComponent(brokenHdd);
				this.newComponents.add(newHdd);
			}
			else if ( comp instanceof Psu ) {
				Psu brokenPsu = (Psu) comp;
				for (Psu newPsu : warehouse.getPsus() ) {
					if ( brokenPsu.getPower() == newPsu.getPower() ) {
						warehouse.getPsus().remove(newPsu);
						this.newComponents.add(newPsu);
						continue newComponentsSearch;
					}
				}
				Psu newPsu = warehouse.orderComponent(brokenPsu);
				this.newComponents.add(newPsu);
			}
		}
	}



	/***
	 * Metoda na konfiguraciu Noveho PC
	 * Prebehne cez vsetky poziadavky pre Novy PC
	 * a podla nich hlada novy komponent v sklade, ktory
	 * pokial nie je na sklade, tak ho objedna
	 */
	public void configureNewPc() {
		Warehouse warehouse = getStore().getWarehouse();
		Performance performance = this.getPc().getPerformance();

		Manufacturer cpuManufacturer;

		int ramSize = ((NewPc) this.getPc()).getRamSize();
		int hddSize = ((NewPc) this.getPc()).getHddSize();
		int psuPower = ((NewPc) this.getPc()).getPsuPower();

		cpuCheck: {
			for (Cpu newCpu : warehouse.getCpus()) {
				if (performance == newCpu.getPerformance()) {
					warehouse.getCpus().remove(newCpu);
					this.newComponents.add(newCpu);
					cpuManufacturer = newCpu.getManufacturer();
					break cpuCheck;
				}
			}
			Cpu orderCpu = new Cpu(50, true, performance, Manufacturer.getRandomManufacturerCpu());
			Cpu newCpu = warehouse.orderComponent(orderCpu);
			cpuManufacturer = newCpu.getManufacturer();
			this.newComponents.add(newCpu);
		}

		gpuCheck: {
			for (Gpu newGpu : warehouse.getGpus()) {
				if (performance == newGpu.getPerformance()) {
					warehouse.getGpus().remove(newGpu);
					this.newComponents.add(newGpu);
					break gpuCheck;
				}
			}
			Gpu orderGpu = new Gpu(50, true, performance, Manufacturer.getRandomManufacturerGpu());
			Gpu newGpu = warehouse.orderComponent(orderGpu);
			this.newComponents.add(newGpu);
		}

		ramCheck: {
			for (Ram newRam : warehouse.getRams()) {
				if (ramSize == newRam.getCapacity()) {
					warehouse.getRams().remove(newRam);
					this.newComponents.add(newRam);
					break ramCheck;
				}
			}
			Ram orderRam = new Ram(50, true, ramSize);
			Ram newRam = warehouse.orderComponent(orderRam);
			this.newComponents.add(newRam);
		}

		mboCheck: {
			for (Mbo newMbo : warehouse.getMbos()) {
				if (cpuManufacturer == newMbo.getSocket()) {
					warehouse.getMbos().remove(newMbo);
					this.newComponents.add(newMbo);
					break mboCheck;
				}
			}
			Mbo orderMbo = new Mbo(50, true, cpuManufacturer);
			Mbo newMbo = warehouse.orderComponent(orderMbo);
			this.newComponents.add(newMbo);
		}

		hddCheck: {
			for (Hdd newHdd : warehouse.getHdds()) {
				if (hddSize == newHdd.getCapacity()) {
					warehouse.getHdds().remove(newHdd);
					this.newComponents.add(newHdd);
					break hddCheck;
				}
			}
			Hdd orderHdd = new Hdd(50, true, hddSize);
			Hdd newHdd = warehouse.orderComponent(orderHdd);
			this.newComponents.add(newHdd);
		}

		psuCheck: {
			for (Psu newPsu : warehouse.getPsus()) {
				if (psuPower == newPsu.getPower()) {
					warehouse.getPsus().remove(newPsu);
					this.newComponents.add(newPsu);
					break psuCheck;
				}
			}
			Psu orderPsu = new Psu(50, true, psuPower);
			Psu newPsu = warehouse.orderComponent(orderPsu);
			this.newComponents.add(newPsu);
		}

	}

	/***
	 * Priradi novy/nahradny konkretny komponent
	 * @param component Novy/nahradny komponent
	 */
	public void addNewComponent(Component component) {
		this.newComponents.add(component);
	}

	/***
	 * Getter na ziskanie novych/nahradnych komponentov
	 * @return Vrati nove/nahradne komponenty
	 */
	public ArrayList<Component> getNewComponents() {
		return this.newComponents;
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
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Konfiguracia PC";
	}
}
