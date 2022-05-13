package Main;

public class City {
    private final int posX;
    private final int posY;
    private final int name;

    public City(int posX, int posY, int name) {
        this.posX = posX;
        this.posY = posY;
        this.name = name;
    }

    // Measure distance between 2 cities
    public double measureDistance(City city) {
        double firstCityPosX = this.posX;
        double firstCityPosY = this.posY;
        double secondCityPosX = city.posX;
        double secondCityPosY = city.posY;
        return Math.sqrt((secondCityPosY - firstCityPosY) * (secondCityPosY - firstCityPosY) + (secondCityPosX - firstCityPosX) * (secondCityPosX - firstCityPosX));
    }

    // Getters
    public int getPosX() {
        return this.posX;
    }
    public int getPosY() {
        return this.posY;
    }
    public int getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return Integer.toString(this.name);
    }
}
