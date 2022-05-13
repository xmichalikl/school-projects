package Cars;

public class CarH extends Car {
    public int tailPosX;
    public int tailPosY;

    public CarH(int headPosX, int headPosY, int len, String color) {
        super(headPosX, headPosY, len, color);
        this.tailPosX = headPosX+(len-1);
        this.tailPosY = headPosY;
    }

    public CarH(CarH carH) {
        super(carH.headPosX, carH.headPosY, carH.len, carH.color);
        this.tailPosX = carH.headPosX+(len-1);
        this.tailPosY = carH.headPosY;
    }

    @Override
    public void fillMap(String[][] arr) {
        for (int x = this.headPosX; x <= this.tailPosX; x++) {
            arr[this.headPosY][x] = this.color.substring(0,2);
        }
    }

    @Override
    public void clearMap(String[][] arr) {
        for (int x = this.headPosX; x <= this.tailPosX; x++) {
            arr[this.headPosY][x] = "--";
        }
    }

    @Override
    public boolean checkInsert(String[][] arr) {
        if (arr[this.headPosY][this.headPosX].equals("--")) {
            if (this.tailPosX > 5)
                return false;

            for (int x = this.headPosX; x <= this.tailPosX; x++) {
                if (!arr[this.headPosY][x].equals("--"))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void move(int jump) {
        this.headPosX += jump;
        this.tailPosX += jump;
        this.jump = jump;

        if (jump > 0)
            this.lastMove = "RIGHT";
        else if (jump < 0)
            this.lastMove = "LEFT";
    }

    @Override
    public boolean checkMove(String[][] arr, int jump) {
        if (Math.abs(jump) <= this.maxMoveLen) {
            //System.out.println(Math.abs(jump) + " <= " + this.maxMoveLen);

            if (jump > 0 && tailPosX + jump <= 5) {
                for (int x = tailPosX + 1; x <= tailPosX + jump; x++) {
                    //System.out.println("["+headPosY+"]" + "["+x+"]" + " - " + arr[headPosY][x] + " == " + 1);
                    //if (arr[headPosY][x] == 1)
                    if (!arr[headPosY][x].equals("--"))
                        return false;
                }
                return true;

            } else if (jump < 0 && headPosX + jump >= 0) {
                for (int x = headPosX - 1; x >= headPosX + jump; x--) {
                    //System.out.println("["+headPosY+"]" + "["+x+"]" + " - " + arr[headPosY][x] + " == " + 1);
                    //if (arr[headPosY][x] == 1)
                    if (!arr[headPosY][x].equals("--"))
                        return false;
                }
                return true;
            }
            else
                return false;
        }
        return false;
    }

    @Override
    public CarH copy() {
        return new CarH(this);
    }
}
