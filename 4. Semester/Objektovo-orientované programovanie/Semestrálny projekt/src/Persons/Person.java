package Persons;

import java.io.Serializable;

//Zakladna abstraktna trieda so zakladnymi udajmi o osobe
public abstract class Person implements Serializable {

	private final String name;
	private final String surname;

	/***
	 * Konstruktor na vytvorenie Osoby
	 * @param name Priradi krstne meno
	 * @param surname Priradi priezvisko
	 */
	public Person(String name, String surname) {
		this.name 		= name;
		this.surname 	= surname;
	}


	/***
	 * Getter na krstne meno
	 * @return Vrati krstne meno
	 */
	public String getName() {
		return this.name;
	}

	/***
	 * Getter na priezvisko
	 * @return Vrati priezvisko
	 */
	public String getSurname() {
		return this.surname;
	}
}
