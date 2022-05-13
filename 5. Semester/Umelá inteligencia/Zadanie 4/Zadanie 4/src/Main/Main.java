package Main;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public static void drawClusters(boolean centroid, ArrayList<Cluster> clusters) throws PythonExecutionException, IOException {
        ArrayList<String> colors = new ArrayList<>(Arrays.asList("#ce6ef1","#ff61b1","#ff0000","#5757ff","#abcdef","#b1bca0","#c9acae","#fafad2","#e0ffff","#4f6f57","#133337","#ffcb12","#8a8daf","#98ff98","#00defe","#311432","#c64444","#4485c6","#cdccff","#00ff00"));
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        Plot plt = Plot.create();

        int clusterNum = 0;
        for (Cluster cluster : clusters) {
            for (Point point : cluster.points) {
                x.add(point.posX);
                y.add(point.posY);
            }
            plt.plot().add(x, y, ".")
                    .color(colors.get(clusterNum))
                    .label("Cluster "+(clusterNum+1));
            x.clear();
            y.clear();
            clusterNum++;
        }

        if (centroid) {
            for (Cluster cluster : clusters) {
                x.add(cluster.centroid.posX);
                y.add(cluster.centroid.posY);
            }
        }
        else {
            for (Cluster cluster : clusters) {
                x.add(cluster.medoid.posX);
                y.add(cluster.medoid.posY);
            }
        }
        plt.plot().add(x, y, "x")
                .color("BLACK")
                .label("Cluster");

        /*plt.legend().loc("upper right");
        plt.title("Title");*/
        plt.show();
    }

    public static boolean successRate(boolean centroid, ArrayList<Cluster> clusters) {
        boolean successStatus = true;
        int distanceAvg, distanceSum;
        int acceptableClusters = 0;
        Point center;

        for (Cluster cluster : clusters) {
            if (centroid) center = cluster.centroid;
            else center = cluster.medoid;

            distanceSum = 0;
            for (Point point : cluster.points) {
                distanceSum += Math.sqrt((Math.pow((center.posY - point.posY), 2)) + (Math.pow((center.posX - point.posX), 2)));
            }
            if (cluster.points.size() > 0)
                distanceAvg = (distanceSum / cluster.points.size());
            else {
                System.out.println("RIP");
                distanceAvg = 10000;
            }
            //System.out.println("Avg. distance from center: "+distanceAvg);
            if (distanceAvg > 500) {
                successStatus = false;
            }
            else acceptableClusters++;
        }
        System.out.println("Percentage success: "+(((double)acceptableClusters)/clusters.size())*100);
        if (successStatus) {
            //System.out.println("Successful\n");
            return true;
        }
        else {
            //System.out.println("Unsuccessful\n");
            return false;
        }
    }

    public static void tester(int choice) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter points count: ");
        int pointsCount = scanner.nextInt();

        System.out.print("Enter clusters count: ");
        int clustersCount = scanner.nextInt();

        System.out.print("Enter tests count: ");
        int testsCount = scanner.nextInt();

        System.out.print("Render clusters (Y/N)?: ");
        boolean renderClustersOpt;
        char renderClustersChoice = scanner.next().charAt(0);
        if (renderClustersChoice == 'Y') renderClustersOpt = true;
        else if (renderClustersChoice == 'N') renderClustersOpt = false;
        else throw new Exception();
        System.out.println();

        //Start time
        long startTimeG = System.currentTimeMillis();
        int successfulTests = 0;
        for (int i=0; i<testsCount; i++) {
            Space space = new Space();
            space.initSpace();
            space.fillSpace(pointsCount);

            switch (choice) {
                case 1 -> {
                    KMeans kMeansC = new KMeans(clustersCount, space.points);
                    kMeansC.createClusters();
                    kMeansC.centroid();
                    if (successRate(true, kMeansC.clusters)) successfulTests++;
                    if (renderClustersOpt) drawClusters(true, kMeansC.clusters);
                }
                case 2 -> {
                    KMeans kMeansM = new KMeans(clustersCount, space.points);
                    kMeansM.createClusters();
                    kMeansM.medoid();
                    if (successRate(false, kMeansM.clusters)) successfulTests++;
                    if (renderClustersOpt) drawClusters(false, kMeansM.clusters);
                }
                case 3 -> {
                    Agglomeration agglomeration = new Agglomeration(clustersCount, space.points);
                    agglomeration.createClusters();
                    agglomeration.initDistanceMap();
                    agglomeration.agglomeration();
                    if (successRate(true, agglomeration.clusters)) successfulTests++;
                    if (renderClustersOpt) drawClusters(true, agglomeration.clusters);
                }
                case 4 -> {
                    Division division = new Division(clustersCount, space.points);
                    division.division();
                    if (successRate(true, division.clusters)) successfulTests++;
                    if (renderClustersOpt) drawClusters(true, division.clusters);
                }
            }
        }
        //End time
        long endTimeG = System.currentTimeMillis();
        long durationG = (endTimeG - startTimeG);
        System.out.println("Avg. duration: "+(durationG / testsCount)+"ms");
        System.out.println("Successful tests: "+successfulTests+"/"+testsCount);
        System.out.println("Percentage success: "+df.format(((double)successfulTests/testsCount)*100)+"%");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("(0) Exit");
            System.out.println("(1) K-means - centroid");
            System.out.println("(2) K-means - medoid");
            System.out.println("(3) Agglomerative - centroid");
            System.out.println("(4) Divisive - centroid");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                if (1 <= choice && choice <= 4) tester(choice);
                else if (choice == 0) running = false;
                else {
                    System.out.println("Bad input!");
                }
            }
            catch (Exception e) {
                System.out.println("Bad input!");
                scanner.nextLine();
            }
        }
    }
}
