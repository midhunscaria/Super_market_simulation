package core;

public class Product {
    double scanTime=0;
    Product(){
        scanTime=(new GenerateRandomNumbers()).generateRandomNumbersOfRange(0.5,6);
    }
}
