package Persons;

import Pc.*;
import Store.Order;
import Store.Store;

//Rozsirena tireda Manager, prideluje objednavky zamestnancom
//alebo spusta simulacny proces opravy/skladania PC
public class Manager extends Employee {

	Employee currentWorkingEmp;
	Employee previousWorkingEmp;

	/***
	 * Konstruktor na vytvorenie Managera
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public Manager(Store store, String name, String surname, String userName, String userPassword) {
		super(store, name, surname, userName, userPassword);
		this.currentWorkingEmp = null;
		this.previousWorkingEmp = null;
	}

	/***
	 * Prekonana metoda z rozhrania MooveOrder, ktora podla
	 * typu objednavky(PC), odosle PC vhodnemu zamestnancovi
	 * @param from Odosielatel
	 * @param pc PC na odoslanie
	 */
	@Override
	public void movePc(Employee from, Pc pc) {
		if (pc instanceof BrokenPc) {
			for (Employee emp : this.getStore().getEmployees()) {
				if (emp instanceof DiagnosticTechnician && emp.getIsAvailable()) {
					this.currentWorkingEmp = emp;
					emp.setIsAvailable(false);
					emp.setPc(pc);
					break;
				}
			}
		}
		if (pc instanceof NewPc) {
			for (Employee emp : this.getStore().getEmployees()) {
				if (emp instanceof ConfigurationTechnician && emp.getIsAvailable()) {
					this.currentWorkingEmp = emp;
					emp.setIsAvailable(false);
					emp.setPc(pc);
					break;
				}
			}
		}
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
		//Pokazeny PC sa posiela na diagnostiku
		if (pc instanceof BrokenPc && to instanceof DiagnosticTechnician) {

			//prenastavenie funkcnosti kazdeho komponentu na funkcny,
			// aby mohol Diagnostik sam oznacit vadne komponenty
			pc.getCpu().setWorking(true);
			pc.getGpu().setWorking(true);
			pc.getRam().setWorking(true);
			pc.getMbo().setWorking(true);
			pc.getHdd().setWorking(true);
			pc.getPsu().setWorking(true);

			to.setPc(pc);
			to.setIsAvailable(false);
		}
		//Novy PC sa posiela na konfiguraciu systemu
		else if (pc instanceof NewPc && to instanceof ConfigurationTechnician) {
			to.setPc(pc);
			to.setIsAvailable(false);
		}
	}



	/***
	 * Zacne proces simulacie opravy/skladania PC
	 * @param order objednavka na spracovanie
	 * @return Vrati status objednavky
	 */
	public int startProduction(Order order) {
		movePc(this, order.getPc());

		//Prechod cez vsetky "etapy" procesu opravy/skladania PC
		if (this.currentWorkingEmp != null) {
			while (this.currentWorkingEmp != null) {
				this.currentWorkingEmp.setIsAvailable(false);
				this.currentWorkingEmp.doTask();
				this.previousWorkingEmp = this.currentWorkingEmp;
				this.currentWorkingEmp = this.currentWorkingEmp.getNextEmp();
				this.previousWorkingEmp.setNextEmp(null);
				//System.out.println(this.previousWorkingEmp.getWorkPosition() + " opravil som " + order);
			}
		}
		//System.out.println("\n");

		//Pokial nie je dostupny ziadny kvalifikovany zamestnanec
		//proces simulacie opravy/skladania PC nemoze zacat
		if (this.previousWorkingEmp == null) {
			System.out.println("Oprava sa nespustila");
			this.currentWorkingEmp = null;
			return 2;
		}

		//Pokial sa proces simulacie opravy/skladania PC dostal do
		//etapy testovania PC, ktore prebeholo uspesne, objednavka
		//bola tym padom vybavena
		else if (this.previousWorkingEmp instanceof TestingTechnician) {
			this.getStore().getOrders().remove(order);
			System.out.println("Oprava je dokoncena");
			this.previousWorkingEmp = null;
			this.currentWorkingEmp = null;
			return 0;
		}

		//Pokial sa proces simulacie opravy/skladania PC niekde
		//zastavil, tak bol proces pozastaveny, resp. jedenemu zo zamestnancov
		//sa nepodarilo posunut PC do dalsej etapy vyrobneho procesu (nemal k dispozicii
		// pozadovaneho zamestnanca) a musi sa rucne prihlasit a tento krok vykonat manualne
		else {
			System.out.println("Oprava je pozastavena");
			this.previousWorkingEmp = null;
			this.currentWorkingEmp = null;
			return 1;
		}
	}



	/***
	 * Zaregistrovanie noveho zamestnanca do servisu
	 * @param newEmployee Novy zamestnanec
	 */
	public void createNewEmployee(Employee newEmployee) {
		this.getStore().getEmployees().add(newEmployee);
	}

	/***
	 * Manager nepracuje :)
	 */
	@Override
	public void doTask() {
		System.out.println("I am a manager, I don't need to work :)");
	}

	/***
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Manager";
	}
}
