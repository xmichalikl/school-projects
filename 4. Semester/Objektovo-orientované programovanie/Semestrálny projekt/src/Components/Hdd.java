package Components;

import Pc.*;

public class Hdd extends Component {
	private int capacity;

	public Hdd(int price, boolean working, int capacity) {
		super((20 * (capacity/500)), working);
		this.capacity = capacity;
	}

	public Hdd(Hdd hdd) {
		super(hdd.getPrice(), true);
		this.capacity = hdd.getCapacity();
	}

	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public String getLabel() {
		return "[" + this.capacity + "GB]";
	}

	@Override
	public boolean stressTest(Pc pc) {
		if (!this.getWorking())
			return false;
		else if (pc instanceof NewPc && this.capacity != ((NewPc)pc).getHddSize())
			return false;
		else
			return true;
	}
}
