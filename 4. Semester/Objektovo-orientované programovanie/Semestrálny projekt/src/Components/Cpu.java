package Components;

import Enums.Manufacturer;
import Enums.Performance;
import Pc.Pc;
import sun.security.krb5.internal.APRep;

public class Cpu extends Component {
	private Performance performance;
	private Manufacturer manufacturer;
	
	public Cpu(int price, boolean working, Performance performance, Manufacturer manufacturer) {
		super((int) (200 * performance.getMultiplier()), working);
		this.performance = performance;
		this.manufacturer = manufacturer;
	}

	public Cpu(Cpu cpu) {
		super(cpu.getPrice(), true);
		this.performance = cpu.getPerformance();
		this.manufacturer = cpu.getManufacturer();
	}

	public Performance getPerformance() {
		return this.performance;
	}
	
	public Manufacturer getManufacturer() {
		return this.manufacturer;
	}

	@Override
	public String getLabel() {
		return "[" + this.manufacturer.toString() + "] - " + this.performance.toString();
	}

	@Override
	public boolean stressTest(Pc pc) {
		if (!this.getWorking())
			return false;
		else if (this.manufacturer != pc.getChipsetDriver())
			return false;
		else if (this.performance != pc.getPerformance())
			return false;
		else
			return true;
	}
}
