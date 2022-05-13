package Persons;

import Pc.Pc;

/***
 * Rozhranie na posuvanie objednavky(PC) medzi zamestnancami
 */
public interface MoveOrder {

	/***
	 * Metoda na posuvanie PC medzi zamestnancami (pri simulacii)
	 * @param from Odosielatel
	 * @param pc PC na odoslanie
	 */
	public void movePc(Employee from, Pc pc);



	/***
	 * Metoda na posuvanie pc medzi zamestnancami (pri rucnom spracovani)
	 * @param from Odosielatel
	 * @param to Prijimatel
	 * @param pc PC na odoslanie
	 */
	public void movePc(Employee from, Employee to, Pc pc);

}
