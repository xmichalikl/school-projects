package Enums;

//Enum na urcenie vyrobcu
public enum Manufacturer {
	INTEL,
	AMD,
	NVIDIA;


	//Metody na vygenerovanie nahodneho vyrobcu pre CPU a GPU
	public static Manufacturer getRandomManufacturerGpu() {
		return values()[(int) (Math.random() * (values().length-1)+1)];
	}

	public static Manufacturer getRandomManufacturerCpu() {
		return values()[(int) (Math.random() * (values().length-1))];
	}
}
