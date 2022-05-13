package Persons;

import Pc.Pc;
import Store.Order;
import Store.Store;

//Rozsirena abstraktna trieda Zamestnanec obsahujuca udaje o zamestnancovi
public abstract class Employee extends Person implements MoveOrder {

	private final Store store;
	
	private final String userName;
	private final String userPassword;
	private boolean isAvailable;

	private Employee nextEmp;
	private Order order;
	private Pc pc;

	/***
	 * Konstruktor na vytvorenie Zamestnanca
	 * @param store Priradi servis
	 * @param name	Priradi krstne meno
	 * @param surname Priradi priezvisko
	 * @param userName Priradi pouzivatelske meno
	 * @param userPassword Priradi pouzivatelske heslo
	 */
	public Employee(Store store, String name, String surname, String userName, String userPassword) {
		super(name, surname);
		this.userName 		= userName;
		this.userPassword 	= userPassword;
		this.store 			= store;
		this.nextEmp		= null;
		this.isAvailable	= true;
	}

	/***
	 * Abstraktna metoda na vykonavanie prislusnej prace
	 * implementovana v kazdom konkretnom zamestnancovi inak
	 */
	public abstract void doTask();


	/***
	 * Getter na pouzivatelske meno
	 * @return Vrati pouzivatelske meno
	 */
	public String getUserName() {
		return this.userName;
	}

	/***
	 * Getter na pouzivatelske heslo
	 * @return Vrati pouzivatelske heslo
	 */
	public String getUserPassword() {
		return this.userPassword;
	}

	/***
	 * Getter na priradeny servis
	 * @return Vrati servis, v ktorom je zamestnanec zaregistrovany
	 */
	public Store getStore() {
		return this.store;
	}

	/***
	 * Getter na priradenu objednavku
	 * @return Vrati proradenu objednavku
	 */
	public Order getOrder() {
		return this.order;
	}

	/***
	 * Setter na priradenie objednavky
	 * @param order Priradi konkretnu objednavku
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/***
	 * Getter na priradeny PC
	 * @return Vrati priradeny PC
	 */
	public Pc getPc() {
		return this.pc;
	}

	/***
	 * Setter na priradenie konkretneho PC
	 * @param pc Priradi konkretny PC zamestnancovi
	 */
	public void setPc(Pc pc) {
		this.pc = pc;
	}

	/***
	 * Getter na nasledujuceho zamestnanca
	 * @return Vrati nasledujuceho zamestnanca, ktory bude pokracovat vo vyrobnom procese
	 */
	public Employee getNextEmp() {
		return this.nextEmp;
	}

	/***
	 * Setter na priradenie nasledujuceho zamestnanca
	 * @param nextEmp Priradi nasledujuceho zamestnanca, ktory bude pokracovat vo vyrobnom procese
	 */
	public void setNextEmp(Employee nextEmp) {
		this.nextEmp = nextEmp;
	}

	/***
	 * Getter na zistenie statusu zamestnanca
	 * @return Vrati status, ci je zamestnanec k dispozicii
	 */
	public boolean getIsAvailable() {
		return this.isAvailable;
	}

	/***
	 * Setter na priradenie statusu zamestnancovi
	 * @param isAvailable Priradi status, ci je zamestnanec k dispozicii
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/***
	 * Abstraktna metoda na zistenie pracovnej pozicie
	 * @return Vrati typ pracovnej pozicie
	 */
	public abstract String getWorkPosition();

	/***
	 * Prekonana toString metoda
	 * @return Vrati zakladne info o zamestnancovi
	 */
	@Override
	public String toString() {
		return (this.getName() + " " + this.getSurname() + " - " + getWorkPosition());
	}
}
