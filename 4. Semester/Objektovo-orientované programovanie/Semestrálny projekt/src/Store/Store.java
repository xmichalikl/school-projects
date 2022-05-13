package Store;

import java.io.Serializable;
import java.util.ArrayList;
import Persons.*;

public class Store implements CreateOrder, Serializable {

	protected Manager manager;
	protected Warehouse warehouse;
	
	protected ArrayList<Employee> employees;
	protected ArrayList<Order> orders;

	public Store() {
		this.warehouse 	= new Warehouse(this);
		this.employees 	= new ArrayList<Employee>();
		this.orders 	= new ArrayList<Order>();
		this.manager 	= new Manager(this, "CompExpress", "Manager", "admin", "admin");

		//Zakladni zamestnanci servisu
		this.employees.add(new DiagnosticTechnician(this, "Peter", "Presny", "d", "d"));
		this.employees.add(new ConfigurationTechnician(this, "Roman", "Rychly", "c", "c"));
		this.employees.add(new AssemblyTechnician(this, "Zdeno", "Zrucny", "a", "a"));
		this.employees.add(new SoftwareTechnician(this, "Stano", "Slusny", "s", "s"));
		this.employees.add(new TestingTechnician(this, "Tomas", "Tupy", "t", "t"));
	}

	//Get a Set metody
	public Manager getManager() {
		return this.manager;
	}
	
	public ArrayList<Employee> getEmployees() {
		return this.employees;
	}

	public ArrayList<Order> getOrders() {
		return this.orders;
	}

	public Warehouse getWarehouse() {
		return this.warehouse;
	}
}
