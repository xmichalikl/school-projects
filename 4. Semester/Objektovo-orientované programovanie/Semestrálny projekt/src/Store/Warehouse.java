package Store;

import java.io.Serializable;
import java.util.ArrayList;

import Components.*;

public class Warehouse implements Serializable {
	
	protected Store store;

	//Arraylisty rozneho typu komponentov
	//reprezentujuce regale s PC komponentami
	protected ArrayList<Cpu> cpus;
	protected ArrayList<Gpu> gpus;
	protected ArrayList<Ram> rams;
	protected ArrayList<Mbo> mbos;
	protected ArrayList<Hdd> hdds;
	protected ArrayList<Psu> psus;
	
	public Warehouse(Store store) {
		this.store = store;
		this.cpus = new ArrayList<Cpu>();
		this.gpus = new ArrayList<Gpu>();
		this.rams = new ArrayList<Ram>();
		this.mbos = new ArrayList<Mbo>();
		this.hdds = new ArrayList<Hdd>();
		this.psus = new ArrayList<Psu>();
	}


	//Metody na objednanie konkretneho typu PC komponentu
	//vzdy po 5ks, kde 1ks sa prave ide pouzit
	public Cpu orderComponent(Cpu orderCpu) {
		for (int i = 0; i < 4; i++) {
			Cpu newCpu = new Cpu(orderCpu);
			this.cpus.add(newCpu);
		}
		return new Cpu(orderCpu);
	}

	public Gpu orderComponent(Gpu orderGpu) {
		for (int i = 0; i < 4; i++) {
			Gpu newGpu = new Gpu(orderGpu);
			this.gpus.add(newGpu);
		}
		return new Gpu(orderGpu);
	}

	public Ram orderComponent(Ram orderRam) {
		for (int i = 0; i < 4; i++) {
			Ram newRam = new Ram(orderRam);
			this.rams.add(newRam);
		}
		return new Ram(orderRam);
	}

	public Mbo orderComponent(Mbo orderMbo) {
		for (int i = 0; i < 4; i++) {
			Mbo newMbo = new Mbo(orderMbo);
			this.mbos.add(newMbo);
		}
		return new Mbo(orderMbo);
	}

	public Hdd orderComponent(Hdd orderHdd) {
		for (int i = 0; i < 4; i++) {
			Hdd newHdd = new Hdd(orderHdd);
			this.hdds.add(newHdd);
		}
		return new Hdd(orderHdd);
	}

	public Psu orderComponent(Psu orderPsu) {
		for (int i = 0; i < 4; i++) {
			Psu newPsu = new Psu(orderPsu);
			this.psus.add(newPsu);
		}
		return new Psu(orderPsu);
	}

	//Get a Set metody
	public ArrayList<Cpu> getCpus() {
		return this.cpus;
	}

	public ArrayList<Gpu> getGpus() {
		return this.gpus;
	}

	public ArrayList<Ram> getRams() {
		return this.rams;
	}

	public ArrayList<Mbo> getMbos() {
		return this.mbos;
	}

	public ArrayList<Hdd> getHdds() {
		return this.hdds;
	}

	public ArrayList<Psu> getPsus() {
		return this.psus;
	}

}
