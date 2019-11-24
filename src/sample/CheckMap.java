package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class CheckMap {

    boolean[][] mapBool;

    public CheckMap(String filename) throws URISyntaxException {

        mapBool = new boolean[32][34];

        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/sample/"+filename)))) {

            String strCurrentLine;
            Integer offset=0;
            while ((strCurrentLine = br.readLine()) != null) {
                mapBool[0][offset] = false;

                String[] arrayString = strCurrentLine.split(",");
                for (int i = 1; i <= arrayString.length; i++) {
                    mapBool[offset][i ] = Boolean.parseBoolean(arrayString[i-1]);
                }
                mapBool[offset][33] = false;
                offset++;

            }
            } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public double[] movement(double x, double y, double dx, double dy)  {
        Integer intX = (int)(x+dx)/25;
        Integer intY = (int)(y+dy)/25;
        Double moveX,moveY;
        moveX=intX*25.0;
        moveY=intY*25.0;


        if (mapBool[intX][intY]){
         moveX=+dx;
         moveY=+dy;
        }


        double[] returnVal =new double[2];
        returnVal[0]=moveX;
         returnVal[1]=moveY;
         return returnVal;
    }

    public boolean canMoveLeft(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        return mapBool[intX-1][intY];
    }
    public boolean canMoveRight(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;
        return mapBool[intX+1][intY];
    }
    public boolean canMoveUp(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;
        return mapBool[intX][intY-1];
    }
    public boolean canMoveDown(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;
        return mapBool[intX][intY+1];
    }

    public static void main(String[] args) throws URISyntaxException {
    CheckMap myMap = new CheckMap("Map1.csv");
    }


}
