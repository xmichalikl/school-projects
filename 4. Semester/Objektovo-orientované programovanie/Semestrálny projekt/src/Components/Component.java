package Components;

import java.io.Serializable;

//Abstraktna trieda Komponent, obsahuje zakladne info o komponente
public abstract class Component implements StressTest, Serializable {
	private int price;
	private boolean working;
	
	public Component(int price, boolean working) {
		this.price = price;
		this.working = working;
	}

	//Get a Set metody
	public int getPrice() {
		return this.price;
	}
	
	public boolean getWorking() {
		return this.working;
	}
	
	public void setWorking(boolean working) {
		this.working = working;
	}

	//Abstraktna metoda na zistenie popisu komponentu
	public abstract String getLabel();

	@Override
	public String toString() {
		return (this.getClass().getSimpleName().toUpperCase() + " " + getLabel());
	}
}
