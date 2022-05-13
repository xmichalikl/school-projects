package Pc;

import java.io.Serializable;
import java.util.ArrayList;

import Components.*;
import Enums.Manufacturer;
import Enums.Performance;

//Abstraktna tieda PC, obsahuje vsetky komponenty PC a zakladne info o PC
public abstract class Pc implements TurnOnPc, Serializable {
	
	protected Performance performance;
	protected Manufacturer gpuDriver;
	protected Manufacturer chipsetDriver;
	
	protected Cpu cpu;
	protected Gpu gpu;
	protected Ram ram;
	protected Mbo mbo;
	protected Hdd hdd;
	protected Psu psu;

	/***
	 * Getter na zistenie vykonu PC
	 * @return Vrati vykon PC
	 */
	public Performance getPerformance() {
		return this.performance;
	}

	/***
	 * Getter na zistenie typu/ovladaca grafickej karty
	 * @return Vrati typ/ovladac grafickej karty
	 */
	public Manufacturer getGpuDriver() {
		return this.gpuDriver;
	}

	/***
	 * Setter na nastavenie noveho ovladaca pre graficku kartu
	 * @param newGpuDriver Nastavi novy ovladac/typ pre graficku kartu
	 */
	public void setGpuDriver(Manufacturer newGpuDriver) {
		this.gpuDriver = newGpuDriver;
	}

	/***
	 * Getter na zistenie typu/ovladaca procesora
	 * @return Vrati typ/ovladac procesora
	 */
	public Manufacturer getChipsetDriver() {
		return this.chipsetDriver;
	}

	/***
	 * Setter na nastavenie noveho ovladaca pre procesor
	 * @param newChipsetDriver Nastavi novy ovladac/typ pre procesora
	 */
	public void setChipsetDriver(Manufacturer newChipsetDriver) {
		this.chipsetDriver = newChipsetDriver;
	}


	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public Gpu getGpu() {
		return gpu;
	}

	public void setGpu(Gpu gpu) {
		this.gpu = gpu;
	}

	public Ram getRam() {
		return ram;
	}

	public void setRam(Ram ram) {
		this.ram = ram;
	}

	public Mbo getMbo() {
		return mbo;
	}

	public void setMbo(Mbo mbo) {
		this.mbo = mbo;
	}

	public Hdd getHdd() {
		return hdd;
	}

	public void setHdd(Hdd hdd) {
		this.hdd = hdd;
	}

	public Psu getPsu() {
		return psu;
	}

	public void setPsu(Psu psu) {
		this.psu = psu;
	}
	
}
