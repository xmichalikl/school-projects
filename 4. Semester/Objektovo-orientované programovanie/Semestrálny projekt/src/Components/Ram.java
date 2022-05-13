package Components;

import Pc.*;

public class Ram extends Component {
	private int capacity;
	
	public Ram(int price, boolean working, int capacity) {
		super((40 * (capacity/4)), working);
		this.capacity = capacity;
	}

	public Ram(Ram ram) {
		super(ram.getPrice(), true);
		this.capacity = ram.getCapacity();
	}
	
	public int getCapacity() {
		return this.capacity;
	}

	public String getLabel() {
		return "[" + this.capacity + "GB]";
	}

	@Override
	public boolean stressTest(Pc pc) {
		if (!this.getWorking())
			return false;
		else if (pc instanceof NewPc && this.capacity != ((NewPc)pc).getRamSize())
			return false;
		else
			return true;
	}
}
