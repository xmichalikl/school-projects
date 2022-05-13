package Cars;

public class CarV extends Car {
    public int tailPosX;
    public int tailPosY;

    public CarV(int headPosX, int headPosY, int len, String color) {
        super(headPosX, headPosY, len, color);
        this.tailPosX = headPosX;
        this.tailPosY = headPosY+(len-1);
    }

    public CarV(CarV carV) {
        super(carV.headPosX, carV.headPosY, carV.len, carV.color);
        this.tailPosX = carV.headPosX;
        this.tailPosY = carV.headPosY+(len-1);
    }

    @Override
    public void fillMap(String[][] arr) {
        for (int y = this.headPosY; y <= this.tailPosY; y++) {
            arr[y][this.headPosX] = this.color.substring(0,2);
        }
    }

    @Override
    public void clearMap(String[][] arr) {
        for (int y = this.headPosY; y <= this.tailPosY; y++) {
            arr[y][this.headPosX] = "--";
        }
    }

    @Override
    public boolean checkInsert(String[][] arr) {
        if (arr[this.headPosY][this.headPosX].equals("--")) {
            if (this.tailPosY > 5)
                return false;

            for (int y = this.headPosY; y <= this.tailPosY; y++) {
                if (!arr[y][this.headPosX].equals("--"))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void move(int jump) {
        this.headPosY += jump;
        this.tailPosY += jump;
        this.jump = jump;

        if (jump > 0)
            this.lastMove = "DOWN";
        else if (jump < 0)
            this.lastMove = "UP";
    }

    @Override
    public boolean checkMove(String[][] arr, int jump) {
        if (Math.abs(jump) <= this.maxMoveLen) {
            //System.out.println(Math.abs(jump) + " <= " + this.maxMoveLen);
            if (jump > 0 && tailPosY + jump <= 5) {
                for (int y = tailPosY + 1; y <= tailPosY + jump; y++) {
                    //System.out.println("["+y+"]" + "["+headPosX+"]" + " - " + arr[y][headPosX] + " == " + 1);
                    //if (arr[y][headPosX] == 1)
                    if (!arr[y][headPosX].equals("--"))
                        return false;
                }
                return true;

            } else if (jump < 0 && headPosY + jump >= 0) {
                for (int y = headPosY - 1; y >= headPosY + jump; y--) {
                    //System.out.println("["+y+"]" + "["+headPosX+"]" + " - " + arr[y][headPosX] + " == " + 1);
                    //if (arr[y][headPosX] == 1)
                    if (!arr[y][headPosX].equals("--"))
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
    public CarV copy() {
        return new CarV(this);
    }
}
