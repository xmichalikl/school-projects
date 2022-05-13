package Main;
import java.util.ArrayList;

public class Division {
    public int k;
    public ArrayList<Point> points;
    public ArrayList<Cluster> clusters;

    public Division(int k, ArrayList<Point> points) {
        this.k = k;
        this.points = new ArrayList<>(points);
        this.clusters = new ArrayList<>();
    }

    public void division() {
        KMeans kMeansInit;
        if (this.k == 1) {
            kMeansInit = new KMeans(1, this.points);
        }
        else {
            kMeansInit = new KMeans(2, this.points);
        }
        kMeansInit.createClusters();
        kMeansInit.centroid();
        this.clusters.addAll(kMeansInit.clusters);

        while (this.clusters.size() < this.k) {
            Cluster clusterNext = worstCluster();
            // 2 and more points
            if (clusterNext.points.size() > 1) {
                KMeans kMeansNext = new KMeans(2, clusterNext.points);
                kMeansNext.createClusters();
                kMeansNext.centroid();
                this.clusters.addAll(kMeansNext.clusters);
                this.clusters.remove(clusterNext);
            } // only one point
            else if (clusterNext.points.size() == 1) {
                this.clusters.remove(clusterNext);
                this.clusters.add(clusterNext);
            } // empty points
            else {
                this.clusters.remove(clusterNext);
            }
        }
    }

    // get worst cluster
    public Cluster worstCluster() {
        Cluster worstCluster = null;
        int distanceSum, maxDistance = 0;

        for (Cluster cluster : this.clusters) {
            distanceSum = 0;
            for (Point point : cluster.points) {
                distanceSum += Math.sqrt((Math.pow((cluster.centroid.posY - point.posY), 2)) + (Math.pow((cluster.centroid.posX - point.posX), 2)));
            }
            if (distanceSum > maxDistance) {
                maxDistance = distanceSum;
                worstCluster = cluster;
            }
        }
        return worstCluster;
    }
}
