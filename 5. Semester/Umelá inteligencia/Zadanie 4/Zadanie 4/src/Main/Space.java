package Main;

import java.util.ArrayList;
import java.util.Random;

public class Space {
    public ArrayList<Point> points;
    public boolean[][] spaceMap;
    public int spaceMapOffset;
    public Random random;

    public Space() {
        this.points = new ArrayList<>();
        this.spaceMap = new boolean[10001][10001];
        this.spaceMapOffset = 5000;
        this.random = new Random();
    }
    public Space(int spaceSize) {
        this.points = new ArrayList<>();
        this.spaceMap = new boolean[spaceSize+1][spaceSize+1];
        this.spaceMapOffset = spaceSize/2;
        this.random = new Random();
    }

    // generate first random 20 unique points
    public void initSpace() {
        int generatedPoints = 0;
        int max = (this.spaceMapOffset + 1);
        int min = (this.spaceMapOffset * (-1));

        while (generatedPoints < 20) {
            int randPosX = random.nextInt(max - min) + min;
            int randPosy = random.nextInt(max - min) + min;

            if (!checkSpacePosition(randPosX, randPosy)) {
                markSpacePosition(randPosX, randPosy);
                this.points.add(new Point(randPosX, randPosy));
                generatedPoints++;
            }
        }

    }

    // generate other random 20 000 unique points
    public void fillSpace(int pointsCount) {
        int generatedPoints = 0;
        int maxOffset = 101;
        int minOffset = -100;
        int collisions = 0;

        while (generatedPoints < pointsCount) {

            // pick one random pont from space
            int randPointIndex = random.nextInt(this.points.size());
            Point randPoint = this.points.get(randPointIndex);

            if (randPoint != null) {
                int posX = randPoint.posX;
                int posY = randPoint.posY;

                int offsetPosX = random.nextInt(maxOffset - minOffset) + minOffset;
                int offsetPosY = random.nextInt(maxOffset - minOffset) + minOffset;

                if (!(offsetPosX == 0 && offsetPosY == 0)) {
                    // take care of edge of space
                    // reduce offsetPosX
                    if (Math.abs(posX) >= (spaceMapOffset - 100)) {
                        if (offsetPosX >= 0) {
                            offsetPosX = ((spaceMapOffset - Math.abs(posX)) / 2);
                        }
                        else {
                            offsetPosX = (((spaceMapOffset - Math.abs(posX)) * (-1)) / 2);
                        }
                    }
                    // reduce offsetPosY
                    if (Math.abs(posY) >= (spaceMapOffset - 100)) {
                        if (offsetPosY >= 0) {
                            offsetPosY = ((spaceMapOffset - Math.abs(posY)) / 2);
                        }
                        else {
                            offsetPosY = (((spaceMapOffset - Math.abs(posY)) * (-1)) / 2);
                        }
                    }

                    int finalPosX = posX + offsetPosX;
                    int finalPosY = posY + offsetPosY;

                    // check space position // add point to space
                    if (!checkSpacePosition(finalPosX, finalPosY)){
                        markSpacePosition(finalPosX, finalPosY);
                        this.points.add(new Point(finalPosX, finalPosY));
                        generatedPoints++;
                    }
                    else {
                        collisions++;
                    }
                }
            }
        }
    }

    public boolean checkSpacePosition(int posX, int posY) {
        int posXCheck = posX+spaceMapOffset;
        int posYCheck = posY+spaceMapOffset;
        return this.spaceMap[posXCheck][posYCheck];
    }

    public void markSpacePosition(int posX, int posY) {
        int posXMark = posX+spaceMapOffset;
        int posYMark = posY+spaceMapOffset;
        this.spaceMap[posXMark][posYMark] = true;
    }
}
