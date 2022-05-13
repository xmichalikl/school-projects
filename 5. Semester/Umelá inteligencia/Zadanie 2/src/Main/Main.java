package Main;

import Cars.*;
import Node.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    //Create empty map
    public static String[][] initMap() {
        String[][] arr = new String[6][6];

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                arr[y][x] = "--";
            }
        }
        return arr;
    }
    //Print steps to solution
    public static void printSteps(Node node) {
        ArrayList<String> steps = new ArrayList<>();
        Node actNode = node;

        while (actNode.parentNode != null) {
            Car lastMoveCar = actNode.state.lastMoveCar;
            steps.add(0, lastMoveCar.lastMove + "(" + lastMoveCar.color + ", " + Math.abs(lastMoveCar.jump) + ")");
            actNode = actNode.parentNode;
        }

        int count = 0;
        for (String step : steps) {
            if (count % 10 == 0)
                System.out.println();
            System.out.print(step + " -> ");
            count++;
        }
        System.out.println();
        System.out.println();
    }

    //Print actual state
    public static void printMap(String[][] arr) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++)
                System.out.print(arr[y][x] + "  ");
            System.out.println();
        }
        System.out.println();
    }

    //Check final state
    public static boolean checkFinish(Car car) {
        if (car instanceof CarH)
            if ((car.headPosY == 2) && (car.headPosX == (6-car.len)) && (((CarH) car).tailPosX == 5))
                return true;
        return false;
    }

    //Loads input from file
    public static String[][] loadMap(String filename, ArrayList<Car> carsArr) {
        ArrayList<String> carColors = new ArrayList<>(Arrays.asList("red", "green", "blue", "yellow", "orange", "purple", "pink", "brown", "white", "lred", "lgreen", "lblue", "dred", "dgreen", "dblue"));
        String[][] arr = initMap();
        String orientation, color;
        int posX, posY, len;

        try {
            Scanner scanner = new Scanner(new File(filename));

            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(""))
                    continue;
                String[] split = line.split("\\s+");

                posX = Integer.parseInt(split[0]);
                posY = Integer.parseInt(split[1]);
                len = Integer.parseInt(split[2]);
                orientation = split[3];

                if (posX > 5 || posX < 0 || posY > 5 || posY < 0 || len > 3 || len < 2) {
                    System.out.println("Insertion error");
                    break;
                }

                else {
                    if (orientation.equals("H")) {
                        CarH carH = new CarH(posX, posY, len, carColors.get(count));
                        if (carH.checkInsert(arr)) {
                            carH.fillMap(arr);
                            carsArr.add(carH);
                        } else
                            System.out.println("Insertion error " + posX + " " + posY);
                    }
                    else if (orientation.equals("V")) {
                        CarV carV = new CarV(posX, posY, len, carColors.get(count));
                        if (carV.checkInsert(arr)) {
                            carV.fillMap(arr);
                            carsArr.add(carV);
                        } else
                            System.out.println("Insertion error " + posX + " " + posY);
                    }
                    else
                        System.out.println("Insertion error");
                    count++;
                }
            }
            scanner.close();
            return arr;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void findSolution(String[][] arr, ArrayList<Car> carsArr, String type) {
        ArrayList<String[][]> states = new ArrayList<>();
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> tempQueue = new ArrayList<>();
        ArrayList<Integer> carJumps = new ArrayList<>();

        //Create first node
        Node firstNode = new Node(null, new State(carsArr, arr));
        states.add(firstNode.state.arr);
        queue.add(firstNode);

        //Print start state
        System.out.println("START STATE: ");
        printMap(firstNode.state.arr);
        System.out.println("-------------------------");
        System.out.println();

        //Set firstNode as actNode
        Node actNode = firstNode;
        boolean running = true;
        int depth = firstNode.depth;
        int maxDepth = 0;

        while (running) {
            for (Car car : actNode.state.carsArr) {
                //Skip moves with last car
                if (car == actNode.state.lastMoveCar)
                    continue;

                //Create Jumps
                for (int jump = -car.maxMoveLen; jump <= car.maxMoveLen; jump++) {
                    if (car.checkMove(actNode.state.arr, jump))
                        carJumps.add(jump);
                }

                //Create new Nodes from every car move
                if (!carJumps.isEmpty()) {
                    for (int jump : carJumps) {
                        Node newNode = new Node(actNode, new State());

                        //Create new car with new position
                        Car newCar = car.copy();
                        newCar.move(jump);

                        //Copy all cars and replace old with new one --> add to Node Constructor
                        newNode.state.carsArr.addAll(actNode.state.carsArr);
                        newNode.state.carsArr.set(newNode.state.carsArr.indexOf(car), newCar);
                        newNode.state.lastMoveCar = newCar;

                        //Fill map for each car
                        for (Car newNodeCar : newNode.state.carsArr)
                            newNodeCar.fillMap(newNode.state.arr);

                        //Check duplicity
                        boolean duplicity = false;
                        for (String[][] state : states) {
                            if (Arrays.deepEquals(state, newNode.state.arr)) {
                                duplicity = true;
                                break;
                            }
                        }

                        //Save new Node and new State
                        if (!duplicity) {
                            //Add new Node into act Node child and add new State into states
                            actNode.childNodes.add(newNode);
                            states.add(newNode.state.arr);

                            //Add new Node to queue
                            if (type.equals("BFS"))
                                queue.add(newNode);
                            else if (type.equals("DFS"))
                                tempQueue.add(newNode);

                            //Check final state
                            if (newCar.color.equals("red")) {
                                //Solution was found :)
                                if (checkFinish(newCar)) {
                                    System.out.println("SOLUTION WAS FOUND!");
                                    System.out.println("AT DEPTH (Steps) " + depth);
                                    System.out.println(states.size() + " STATES WERE CREATED!");
                                    printMap(newNode.state.arr);
                                    printSteps(newNode);
                                    return;
                                }
                            }
                        }
                    }
                    carJumps.clear();
                }
            }
            //Remove actNode from queue
            queue.remove(actNode);

            //Add new Nodes to queue
            if (type.equals("DFS")) {
                //Collections.reverse(tempQueue); // -> R DFS
                queue.addAll(0, tempQueue);
                tempQueue.clear();
            }
            //Get next Node if queue is not empty
            if (queue.size() > 0) {
                actNode = queue.get(0);
                depth = actNode.depth;
                if (depth > maxDepth)
                    maxDepth = depth;
            }
            else {
                //No solution :(
                running = false;
                System.out.println("EMPTY QUEUE!");
                System.out.println("NO SOLUTION!");
                System.out.println("MAX DEPTH: " + maxDepth);
                System.out.println(states.size() + " STATES WERE CREATED!");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileDir = "inputs/";
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Enter file name from inputs\\ or 'quit': ");
            String filename = scanner.nextLine();
            if (filename.equals("quit")) {
                running = false;
                continue;
            }

            if (Files.exists(Path.of(fileDir+filename))) {
                ArrayList<Car> carsArr = new ArrayList<>();
                String[][] arr = loadMap(fileDir+filename, carsArr);

                boolean solution = false;
                while (!solution) {
                    System.out.println("(D)FS or (B)FS?:");
                    String type = scanner.nextLine();

                    if (type.equals("D")) {
                        long startTime = System.currentTimeMillis();
                        findSolution(arr, carsArr, "DFS");
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println("TIME: " + duration + " ms");
                        solution = true;
                    } else if (type.equals("B")) {
                        long startTime = System.currentTimeMillis();
                        findSolution(arr, carsArr, "BFS");
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println("TIME: " + duration + " ms");
                        solution = true;
                    } else {
                        System.out.println("Try again!");
                    }
                }
            }
            else {
                System.out.println("The file does not exist!");
            }
        }
    }
}
