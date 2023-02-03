package niches;

import utilities.RobRandom;

/**
 * Created by Edward on 11/01/2016.
 */
public class Niche {
    public double xCoord, yCoord; //Coordinates
    public Species occupant; //Species
    public int time; //Time
    public int ID;
    private static int globalTime = 0;
    private static double meanx = 100;
    private static double sdx = 10;
    private static double meany = 100;
    private static double sdy = 10;
    private static int globalID = 0;

    public Niche() {
        xCoord = RobRandom.randomNorm(meanx, sdx);
        yCoord = RobRandom.randomNorm(meany, sdy);
        occupant = null;
        time = globalTime;
        ID = globalID;
        globalID++;
        globalTime=globalTime+5;
//        System.out.println("Niche#" + ID + "created");
    }

    public static void resetGlobalTime() {
        globalTime = 0;
    }

    public static double getMeanx() {
        return meanx;
    }

    public static double getSdx() {
        return sdx;
    }

    public static double getMeany() {
        return meany;
    }

    public static double getSdy() {
        return sdy;
    }

    public void setTime (int newValue) {
        time = newValue;
    }

    public void setxCoord (double newValue) {
        xCoord = newValue;
    }

    public void setyCoord (double newValue) {
        yCoord = newValue;
    }

    public void setSpecies (Species newValue) {
        occupant = newValue;
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public Species getOccupant() {
        return occupant;
    }

    public static int getGlobalTime() {
        return globalTime;
    }

    public static void setGlobalTime(int time){
        globalTime = time;
    }

    public static void resetGlobalID(){
        globalID = 0;
    }
}

