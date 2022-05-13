package Main;
import java.util.*;

public class Agglomeration {
    public int k;
    public ArrayList<Point> points;
    public ArrayList<Cluster> clusters;
    public double[][] distanceMap;
    public final double maxDistance = Integer.MAX_VALUE;
    public Map<Integer, Cluster> dictionary;
    public ArrayList<Integer> list;

    public Agglomeration(int k, ArrayList<Point> points) {
        this.k = k;
        this.points = new ArrayList<>(points);
        this.clusters = new ArrayList<>();
        this.distanceMap = new double[points.size()][points.size()];
        this.dictionary = new HashMap<>();
        this.list = new ArrayList<>();
    }

    public void createClusters() {
        int createdClusters = 0;
        for (Point point : this.points) {
            Cluster cluster = new Cluster(createdClusters, point);
            this.clusters.add(cluster);
            this.dictionary.put(createdClusters, cluster);
            this.list.add(createdClusters);
            createdClusters++;
        }
    }
    public void initDistanceMap() {
        for (Cluster clusterX : this.clusters) {
            for (Cluster clusterY : this.clusters) {
                this.distanceMap[clusterX.id][clusterY.id] = Math.sqrt((Math.pow((clusterX.centroid.posY - clusterY.centroid.posY), 2)) + (Math.pow((clusterX.centroid.posX - clusterY.centroid.posX), 2)));
            }
        }
    }
    public void agglomeration() {
        double shortestDistance, actualDistance;
        int mergeX = 0, mergeY = 0;
        long iteration, iterationSum = 0;

        while (this.clusters.size() > this.k) {
            iteration = 0;
            shortestDistance = maxDistance;

            // find the shortest distance between 2 clusters
            outerloop:
            for (int x : this.list) {
                for (int y : this.list.subList((this.list.indexOf(x)+1), this.list.size())) {
                    actualDistance = this.distanceMap[x][y];
                    if (actualDistance < shortestDistance) {
                        shortestDistance = actualDistance;
                        mergeX = x;
                        mergeY = y;
                    }
                    if (shortestDistance == 1.0) {
                        break outerloop;
                    }
                    iteration++;
                }
            }
            iterationSum += iteration;

            // merge clusters and delete old cluster
            Cluster clusterX = dictionary.get(mergeX);
            Cluster clusterY = dictionary.get(mergeY);
            for (Point point : clusterY.points) {
                clusterX.addPointToCluster(point);
            }
            this.clusters.remove(clusterY);
            this.dictionary.remove(mergeY);
            this.list.remove(Integer.valueOf(mergeY));

            // update distance map
            clusterX.updateCentroid();
            clusterX = dictionary.get(mergeX);
            for (int xy : this.list) {
                clusterY = dictionary.get(xy);
                distanceMap[mergeX][xy] = Math.sqrt((Math.pow((clusterX.centroid.posY - clusterY.centroid.posY), 2)) + (Math.pow((clusterX.centroid.posX - clusterY.centroid.posX), 2)));
            }
        }
    }
}
