package core;
import java.lang.Math;

public class GenerateRandomNumbers {

    int generateRandomNumbersOfRange(double min,double max){

        return (int)((Math.random() *max)+min);
    }

}
