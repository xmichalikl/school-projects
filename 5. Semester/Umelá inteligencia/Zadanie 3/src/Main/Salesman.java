package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Salesman {
    private static final int MAX_MAP_SIZE = 200;
    private final ArrayList<City> initialCities;

    public Salesman(String filePath){
        this.initialCities = loadCities(filePath);
    }
    public Salesman(int citiesCount) {
        this.initialCities = generateCities(citiesCount);
    }

    // Load cities from file
    public ArrayList<City> loadCities(String filePath) {
        ArrayList<City> loadedCities = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            // Load all lines from .txt file
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("")) {
                    continue;
                }

                // Split line with space character
                String[] split = line.split("\\s+");
                int posX = Integer.parseInt(split[0]);
                int posY = Integer.parseInt(split[1]);

                if (posX < 0 || MAX_MAP_SIZE < posX || posY < 0 || MAX_MAP_SIZE < posY) {
                    System.out.println("Insertion error: city - "+count+". "+"["+posX+"]"+"["+posY+"]");
                    break;
                }
                else {
                    count++;
                    loadedCities.add(new City(posX, posY, count));
                }
            }
            scanner.close();
            return loadedCities;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generate cities with random positions
    public ArrayList<City> generateCities(int citiesCount) {
        ArrayList<City> generatedCities = new ArrayList<>();

        int count = 0;
        while (count != citiesCount) {
            int randX = (int) ((MAX_MAP_SIZE+1) * Math.random());
            int randY = (int) ((MAX_MAP_SIZE+1) * Math.random());

            boolean duplicity = false;
            for (City city : generatedCities) {
                if (city.getPosX() == randX && city.getPosY() == randY) {
                    duplicity = true;
                    break;
                }
            }
            if (!duplicity) {
                count++;
                generatedCities.add(new City(randX, randY, count));
            }
        }
        return generatedCities;
    }

    // Return initial cities
    public ArrayList<City> getInitialCities(){
        return this.initialCities;
    }
}
