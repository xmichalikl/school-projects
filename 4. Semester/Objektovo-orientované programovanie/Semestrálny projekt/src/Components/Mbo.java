package Components;

import Enums.Manufacturer;
import Pc.Pc;

public class Mbo extends Component {
	private Manufacturer socket;
		
	public Mbo(int price, boolean working, Manufacturer socket) {
		super(100, working);
		this.socket = socket;
	}

	public Mbo(Mbo mbo) {
		super(mbo.getPrice(), true);
		this.socket = mbo.getSocket();
	}
	
	public Manufacturer getSocket() {
		return this.socket;
	}

	public String getLabel() {
		return "[" + this.socket.toString() + "]";
	}

	@Override
	public boolean stressTest(Pc pc) {
		if (!this.getWorking())
			return false;
		else if (this.socket != pc.getChipsetDriver())
			return false;
		else
			return true;
	}
}
