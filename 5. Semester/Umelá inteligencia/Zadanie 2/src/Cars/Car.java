package Cars;

public class Car {
    public int len;
    public int headPosX;
    public int headPosY;
    public int maxMoveLen;
    public String color;
    public String lastMove;
    public int jump;

    public Car(int headPosX, int headPosY, int len, String color) {
        this.headPosX = headPosX;
        this.headPosY = headPosY;
        this.maxMoveLen = 6-len;
        this.len = len;
        this.color = color;
    }

    public void fillMap(String[][] arr) {}

    public void clearMap(String[][] arr) {}

    public boolean checkInsert(String[][] arr) { return false; }

    public void move(int jump) {}

    public boolean checkMove(String[][] arr, int jump) { return false; }

    public Car copy() { return null; }
}
