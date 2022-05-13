package Main;

public class AvgTest {
    double avgRoute;
    double maxTemp;
    double cooling;

    public AvgTest(double avgRoute, double maxTemp, double cooling){
        this.avgRoute = avgRoute;
        this.maxTemp = maxTemp;
        this.cooling = cooling;
    }

    public void printAll(){
        System.out.println(this.avgRoute+" "+maxTemp+" "+cooling);
    }
}
