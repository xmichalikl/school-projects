package Persons;

import Pc.Pc;
import Store.Store;
import Store.Order;

//Rozsirena trieda Software technik, otestuje funkcnost PC a bud proces skladani/opravy ukonci uspesne,
//alebo v pripade vyskitu chyby pri testovani PC vrati spat do vyroby vhodnemu zamestnancovi
public class TestingTechnician extends Employee {

	private boolean testingSuccessful;

	/***
	 * Konstruktor na vytvorenie Testera
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public TestingTechnician(Store store, String name, String surname, String userName, String userPassword) {
		super(store, name, surname, userName, userPassword);
		this.testingSuccessful = false;
	}

	/***
	 * Prekonana metoda z rozhrania MooveOrder,
	 * ktora bud ukonci proces vyroby alebo vrati
	 * PC do vyroby vhodnemu zamestnancovi
	 * @param from Odosielatel
	 * @param pc PC na odoslanie
	 */
	@Override
	public void movePc(Employee from, Pc pc) {
		if (this.testingSuccessful) {
			setIsAvailable(true);
			this.setPc(null);
		}
		else {
			for ( Employee to : getStore().getEmployees() ) {
				if ( to instanceof DiagnosticTechnician && to.getIsAvailable()) {
					to.setPc(pc);
					to.setIsAvailable(false);
					setIsAvailable(true);
					this.setPc(null);
				}
			}
		}
		setNextEmp(null);
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
		if ( to instanceof DiagnosticTechnician ) {
			((DiagnosticTechnician) to).setPc(pc);
			to.setIsAvailable(false);
			setIsAvailable(true);
			this.setPc(null);
		}
	}

	/***
	 * Prekonana abstraktna metoda z triedy Zamestnanec
	 * Zapne PC a spusti test pre vsetky komponenty
	 * a nasledne bud ukonci proces vyroby alebo vrati
	 * PC naspat do vyroby
	 */
	@Override
	public void doTask() {
		Pc pc = this.getPc();
		this.testingSuccessful = pc.turnOnPc(pc);
		movePc(this, pc);
	}

	/***
	 * Metoda na ukoncenie procesu skladania/opravy PC
	 * a vymazanie objednavky zo systemu
	 * @param pc Pc ktoreho proces skladania/opravy je dokonceny
	 * @return Vrati objednavku ktorej patri dokonceny PC
	 */
	public Order deleteOrder(Pc pc) {
		for (Order ord : this.getStore().getOrders()) {
			if (ord.getPc() == pc) {
				this.getStore().getOrders().remove(ord);
				ord.setOrderComplete(true);
				ord.setOrderStatus(0);
				setIsAvailable(true);
				this.setPc(null);
				return ord;
			}
		}
		return null;
	}

	/***
	 * Prekonana metoda z tiedy Zamestnanec
	 * na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	@Override
	public String getWorkPosition() {
		return "Testovanie PC";
	}
}
