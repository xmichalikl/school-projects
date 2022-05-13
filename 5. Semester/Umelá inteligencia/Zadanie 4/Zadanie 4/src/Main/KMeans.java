package Main;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {
    public int k;
    public ArrayList<Point> points;
    public ArrayList<Cluster> clusters;
    public Random random = new Random();
    public final double maxDistance = Integer.MAX_VALUE;

    public KMeans(int k, ArrayList<Point> points) {
        this.k = k;
        this.points = new ArrayList<>(points);
        this.clusters = new ArrayList<>();
    }

    public void createClusters() {
        ArrayList<Point> usedPoints = new ArrayList<>();
        int createdClusters = 0;

        // create k clusters
        while (createdClusters < this.k) {
            int randPointIndex = this.random.nextInt(this.points.size());
            Point randPoint = this.points.get(randPointIndex);

            // pick random point and create cluster
            if (!usedPoints.contains(randPoint)) {
                this.clusters.add(new Cluster(createdClusters, randPoint));
                usedPoints.add(randPoint);

                //this.points.remove(randPoint);
                createdClusters++;
            }
        }
    }

    public void centroid() {
        Cluster bestCluster = null;
        double minDistance, actualDistance;
        ArrayList<Point> oldCentroids = new ArrayList<>();
        ArrayList<Point> actualCentroids = new ArrayList<>();
        int iteration = 0;

        while (checkChange(oldCentroids, actualCentroids)) {
            for (Cluster cluster : this.clusters) {
                cluster.resetCluster();
            }
            for (Point actualPoint : this.points) {
                minDistance = this.maxDistance;
                for (Cluster actualCluster : this.clusters) {
                    Point clusterCentroid = actualCluster.centroid;
                    actualDistance = Math.sqrt((Math.pow((clusterCentroid.posY - actualPoint.posY), 2)) + (Math.pow((clusterCentroid.posX - actualPoint.posX), 2)));
                    if (actualDistance < minDistance) {
                        minDistance = actualDistance;
                        bestCluster = actualCluster;
                    }
                }
                if (bestCluster != null) {
                    bestCluster.addPointToCluster(actualPoint);
                }
            }
            // reset cluster
            oldCentroids.clear();
            actualCentroids.clear();
            for (Cluster cluster : this.clusters) {
                oldCentroids.add(new Point(cluster.centroid));
                cluster.updateCentroid();
                cluster.updateMedoid();
                actualCentroids.add(new Point(cluster.centroid));
            }
            iteration++;
            //System.out.println("Iteration "+iteration);
        }
    }

    public void medoid() {
        Cluster bestCluster = null;
        double minDistance, actualDistance;
        ArrayList<Point> oldMedoids = new ArrayList<>();
        ArrayList<Point> actualMedoids = new ArrayList<>();
        int iteration = 0;

        while (checkChange(oldMedoids, actualMedoids)) {
            for (Cluster cluster : this.clusters) {
                cluster.resetCluster();
            }
            for (Point actualPoint : this.points) {
                minDistance = this.maxDistance;
                for (Cluster actualCluster : this.clusters) {
                    Point clusterMedoid = actualCluster.medoid;
                    actualDistance = Math.sqrt((Math.pow((clusterMedoid.posY - actualPoint.posY), 2)) + (Math.pow((clusterMedoid.posX - actualPoint.posX), 2)));
                    if (actualDistance < minDistance) {
                        minDistance = actualDistance;
                        bestCluster = actualCluster;
                    }
                }
                if (bestCluster != null) {
                    bestCluster.addPointToCluster(actualPoint);
                }
            }
            // reset cluster
            oldMedoids.clear();
            actualMedoids.clear();
            for (Cluster cluster : this.clusters) {
                oldMedoids.add(new Point(cluster.medoid));
                cluster.updateCentroid();
                cluster.updateMedoid();
                actualMedoids.add(new Point(cluster.medoid));
            }
            iteration++;
            //System.out.println("Iteration "+iteration);
        }
    }

    public boolean checkChange(ArrayList<Point> oldCenters, ArrayList<Point> actualCenters) {
        double changeX, changeY;
        for (int i=0; i<oldCenters.size(); i++) {
            changeX = Math.abs(oldCenters.get(i).posX - actualCenters.get(i).posX);
            changeY = Math.abs(oldCenters.get(i).posY - actualCenters.get(i).posY);

            if (changeX > 5 || changeY > 5) {
                return true;
            }
        }
        if (oldCenters.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
