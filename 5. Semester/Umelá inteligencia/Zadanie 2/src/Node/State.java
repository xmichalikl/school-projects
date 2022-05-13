package Node;
import Cars.Car;

import java.util.ArrayList;

public class State {
    public ArrayList<Car> carsArr;
    public String[][] arr;
    public Car lastMoveCar;

    public State() {
        this.carsArr = new ArrayList<>();
        this.arr = new String[6][6];
        initMap();
    }

    public State(ArrayList<Car> carsArr, String[][] arr) {
        this.carsArr = carsArr;
        this.arr = arr;
    }

    public void initMap() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                this.arr[y][x] = "--";
            }
        }
    }
}
