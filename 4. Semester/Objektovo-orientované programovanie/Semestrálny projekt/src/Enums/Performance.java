package Enums;

//Enum na urcenie vykonu PC a komponentov
public enum Performance {
	HIGH (2f) {
		public String toString() {
			return "Vysoky";
		}
	},
	MEDIUM (1f) {
		public String toString() {
			return "Stredny";
		}
	},
	LOW (0.5f) {
		public String toString() {
			return "Nizky";
		}
	};

	private final float multiplier;

	public float getMultiplier() {
		return this.multiplier;
	}
	private Performance(float multiplier) {
		this.multiplier = multiplier;
	}

	//Metoda na vygenerovanie nahodneho typu vykonu
	public static Performance getRandomPerformance() {
		return values()[(int) (Math.random() * values().length)];
	}
}