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


                String[] arrayString = strCurrentLine.split(",");
                for (int i = 0; i < arrayString.length; i++) {
                    if (Integer.parseInt(arrayString[i])==1){
                        mapBool[i][offset ] = true;
                    }else {
                        mapBool[i][offset] = false;

                    }
                    System.out.print(mapBool[offset][i ]+"-");
                }
//                mapBool[offset][32]=false;
//                mapBool[offset][33]=false;
                System.out.println("");
                offset++;

            }
            } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public double[] movement(double x, double y, double dx, double dy)  {
        int intX = (int)(x+dx)/25;
        int intY = (int)(y+dy)/25;
        Double moveX,moveY;
        moveX=x;
        moveY=y;
       System.out.println(mapBool[(intX)][intY]);



        if ( mapBool[intX+2][intY]){
            moveX=moveX+dx;
            moveY=moveY+dy;
        }
//        else if (dx<0 &&mapBool[(int)intX/25][(int)intY/25]){
//
//            moveX=moveX+dx;
//        }
//
//        if (dxy>0 && mapBool[(int)(intX/25)+1][(int)intY/25]){
//            moveX=moveX+dx;
//        }else if (dx<0 &&mapBool[(int)intX/25][(int)intY/25]){
//            moveX=moveX+dx;
//        }


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
