package Components;

import Enums.Manufacturer;
import Enums.Performance;
import Pc.Pc;

public class Gpu extends Component {
	private Performance performance;
	private Manufacturer manufacturer;

	public Gpu(int price, boolean working, Performance performance, Manufacturer manufacturer) {
		super((int) (300 * performance.getMultiplier()), working);
		this.performance = performance;
		this.manufacturer = manufacturer;
	}

	public Gpu(Gpu gpu) {
		super(gpu.getPrice(), true);
		this.performance = gpu.getPerformance();
		this.manufacturer = gpu.getManufacturer();
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
		else if (this.manufacturer != pc.getGpuDriver())
			return false;
		else if (this.performance != pc.getPerformance())
			return false;
		else
			return true;
	}
}
