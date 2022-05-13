package Store;

import Pc.Pc;

import java.io.Serializable;

public class Order implements Serializable {
	
	protected Pc pc;
	protected int orderNumber;
	protected int orderStatus;
	protected boolean orderComplete;

	//objednavke je prideleny PC, cislo obj. a status
	public Order(Pc pc, int orderNumber) {
		this.pc = pc;
		this.orderNumber = orderNumber;
		this.orderStatus = 2;
		this.orderComplete = false;
	}

	//Get a Set metody
	public Pc getPc() {
		return this.pc;
	}
	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	public boolean getOrderComplete() {
		return this.orderComplete;
	}

	public void setOrderComplete(Boolean orderComplete) {
		this.orderComplete = orderComplete;
	}

	public int getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}


	@Override
	public String toString() {
	    return this.pc.toString() + " - Objednávka è. " + this.orderNumber;
	}
}
