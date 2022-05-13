package Components;

import Pc.*;

public class Psu extends Component {
	int power;
	
	public Psu(int price, boolean working, int power) {
		/*float multiplier = (float) power/400;
		int p = (int) (50 * multiplier);
		int pp = (p - (p%10));*/
		super(( (int) (50 * (float) power/400) - ( (int) (50 * (float) power/400)%10 ) ), working);
		this.power = power;
	}

	public Psu(Psu psu) {
		super(psu.getPrice(), true);
		this.power = psu.getPower();
	}

	public int getPower() {
		return this.power;
	}

	public String getLabel() {
		return "[" + this.power + "W]";
	}

	@Override
	public boolean stressTest(Pc pc) {
		if (!this.getWorking())
			return false;
		else if (pc instanceof NewPc && this.getPower() != ((NewPc)pc).getPsuPower())
			return false;
		else
			return true;
	}
}
