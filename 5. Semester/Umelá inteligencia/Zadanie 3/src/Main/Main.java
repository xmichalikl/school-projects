package Main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void printCities(ArrayList<City> cities, boolean txtFormat) {
        if (txtFormat) {
            for (City city : cities) {
                System.out.println(+city.getPosX()+" "+city.getPosY());
            }
        }
        else {
            for (City city : cities) {
                System.out.println(city.getName() +".\t["+city.getPosX()+"]["+city.getPosY()+"]");
            }
        }
    }

    public static void printRoute(Route initialRoute, Route finalRoute, boolean longPrint) {
        System.out.println("Initial distance: " + initialRoute.getTotalDistance());
        System.out.println("Final distance: " + finalRoute.getTotalDistance());
        System.out.println();

        if (longPrint) {
            for (int i = 0; i < finalRoute.getCities().size()-1; i++) {
                City from = finalRoute.getCities().get(i);
                City to = finalRoute.getCities().get(i+1);
                double distance = from.measureDistance(to);
                System.out.println(from.getName() + ".\t[" + from.getPosX() + "][" + from.getPosY() + "]\t=> " + to.getName() + ".\t[" + to.getPosX() + "][" + to.getPosY() + "]\t(" + distance + ")");
            }
            City first = finalRoute.getCities().get(0);
            City last = finalRoute.getCities().get(finalRoute.getCities().size()-1);
            double distance = last.measureDistance(first);
            System.out.println(last.getName() + ".\t[" + last.getPosX() + "][" + last.getPosY() + "]\t=> " + first.getName() + ".\t[" + first.getPosX() + "][" + first.getPosY() + "]\t(" + distance + ")");
        }
        else {
            int count = 0;
            for (City city : finalRoute.getCities()) {
                System.out.print("("+city.getPosX()+","+city.getPosY()+"), ");
                count++;
                if (count%15 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static void autoTester(Route initialRoute) {
        //double[] inittialTempArr = new double[]{30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        double[] inittialTempArr = new double[]{200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
        double[] rateOfCoolingArr = new double[]{0.1, 0.05, 0.025, 0.01, 0.005, 0.0025, 0.001, 0.0005, 0.00025, 0.0001};
        int[] maxAdjRoutesArr = new int[]{1000};

        double bestResult = initialRoute.getTotalDistance();
        double bestTemp = 0;
        double bestCooling = 0;
        int bestAdjRoutes = 0;

        double bestResultAvg = initialRoute.getTotalDistance();
        double bestTempAvg = 0;
        double bestCoolingAvg = 0;
        int bestAdjRoutesAvg = 0;


        ArrayList<AvgTest> results = new ArrayList<>();
        int iterations = 20;
        for (int i = 0; i < inittialTempArr.length; i++) {
            //for (int j = 0; j < rateOfCoolingArr.length; j++) {
                double sum = 0;
                for (int n = 0; n < maxAdjRoutesArr.length; n++) {

                    for (int k = 0; k < iterations; k++) {
                        Route finalRoute = new SimulatedAnnealing().findRouteTester(inittialTempArr[i], 0.99, 0.0001, maxAdjRoutesArr[n], initialRoute);
                        sum += finalRoute.getTotalDistance();
                        if (finalRoute.getTotalDistance() < bestResult) {
                            bestResult = finalRoute.getTotalDistance();
                            bestTemp = inittialTempArr[i];
                            //bestCooling = rateOfCoolingArr[j];
                            bestAdjRoutes = maxAdjRoutesArr[n];
                        }
                    }
                    double avg = sum / iterations;
                    results.add(new AvgTest(avg, inittialTempArr[i], 0.0001));

                    if (avg < bestResultAvg) {
                        bestResultAvg = avg;
                        bestTempAvg = inittialTempArr[i];
                        //bestCoolingAvg = rateOfCoolingArr[j];
                        bestAdjRoutesAvg = maxAdjRoutesArr[n];
                    }
                }
            //}
        }
        System.out.println("Best avg option is - "+bestResultAvg+" "+bestTempAvg+" "+bestCoolingAvg+" "+bestAdjRoutes);
        System.out.println("Best option is - "+bestResult+" "+bestTemp+" "+bestCooling+" "+bestAdjRoutesAvg);
        for (AvgTest res : results)
            res.printAll();
    }

    public static void tester(int testsCount, int citiesCount, int iterations, boolean generateRandomCities) {
        String fileDir = "inputs/";
        String filename = "input2.txt";
        Salesman salesman;

        for (int i = 0; i< testsCount; i++) {
            if (generateRandomCities) {
                salesman = new Salesman(citiesCount);
            }
            else {
                salesman = new Salesman(fileDir+filename);
            }
            Route initialRoute = new Route(salesman.getInitialCities(), false);

            double sum = 0;
            double best = initialRoute.getTotalDistance();

            System.out.println("TEST START ("+i+")\n");
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < iterations; j++) {
                Route finalRoute = new SimulatedAnnealing().findRoute(SimulatedAnnealing.INITIAL_TEMPERATURE, initialRoute);
                System.out.println("Iteration: "+j);
                printRoute(initialRoute, finalRoute, false);
                sum += finalRoute.getTotalDistance();
                if (best > finalRoute.getTotalDistance()) {
                    best = finalRoute.getTotalDistance();
                }
            }
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);

            double avg = sum / iterations;
            System.out.println("---- TEST RESULT ----");
            System.out.println("Avg. route: " + df.format(avg) + " km");
            System.out.println("Best route: " + df.format(best) + " km");
            System.out.println("Avg. time: " + duration / iterations + "ms");
            System.out.println("---------------------");
            System.out.println("TEST END ("+i+")\n\n\n");
        }
    }

    public static void main(String[] args) {
        tester(1, 20, 20, true);
    }
}
