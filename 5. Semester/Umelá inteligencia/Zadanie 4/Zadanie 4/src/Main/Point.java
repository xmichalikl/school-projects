package Main;

public class Point {
    public int posX;
    public int posY;

    public Point(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public Point(Point point) {
        this.posX = point.posX;
        this.posY = point.posY;
    }

    @Override
    public String toString() {
        return "["+this.posX+"]["+this.posY+"]";
    }
}
