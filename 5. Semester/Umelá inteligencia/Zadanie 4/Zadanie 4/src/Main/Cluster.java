package Main;
import java.util.ArrayList;
import java.util.List;

public class Cluster {
    public Point centroid;
    public Point medoid;
    public ArrayList<Point> points;
    public int sumX;
    public int sumY;
    public int id;

    // create cluster
    public Cluster(int id, Point point) {
        this.centroid = new Point(point);
        this.medoid = new Point(point);
        this.points = new ArrayList<>(List.of(point));
        this.sumX = point.posX;
        this.sumY = point.posY;
        this.id = id;
    }

    // add new point into cluster
    public void addPointToCluster(Point point) {
        this.points.add(point);
        this.sumX += point.posX;
        this.sumY += point.posY;
    }

    // calculate new centroid
    public void updateCentroid() {
        if (this.points.size() > 0) {
            this.centroid.posX = this.sumX / this.points.size();
            this.centroid.posY = this.sumY / this.points.size();
        }
    }

    // compare distances between medoid-centroid and newPoint-centroid
    public void updateMedoid() {
        Point newMedoid = new Point(this.medoid);
        double shortestDistance = Math.sqrt((Math.pow((this.centroid.posY - this.medoid.posY), 2)) + (Math.pow((this.centroid.posX - this.medoid.posX), 2)));
        double pointDistance;

        for (Point point : this.points) {
            pointDistance = Math.sqrt((Math.pow((this.centroid.posY - point.posY), 2)) + (Math.pow((this.centroid.posX - point.posX), 2)));
            if (pointDistance < shortestDistance) {
                shortestDistance = pointDistance;
                newMedoid.posX = point.posX;
                newMedoid.posY = point.posY;
            }
            this.medoid.posX = newMedoid.posX;
            this.medoid.posY = newMedoid.posY;
        }
    }

    public void resetCluster() {
        this.points.clear();
        this.sumX = 0; //this.centroid.posX;
        this.sumY = 0; //this.centroid.posY;
    }
}
