package core;
import java.util.*;

import static core.Constants.EXPRESS_COUNTER_ITEM_LIMIT;


public class Customer{
    int custId;
    int totalProducts;
    double cWaitTime=0;
    long endTime;
    long startTime;
    double cTotalWaitTime;

    ArrayList<Product> trolley=new ArrayList<Product>();
    Customer(int id){
        this.custId=id;
        totalProducts= (new GenerateRandomNumbers()).generateRandomNumbersOfRange(1,200);
        for(int i=0;i<totalProducts;i++){
            Product p= new Product();
            cWaitTime+=p.scanTime;
            trolley.add(p);
        }
    }

    public boolean isJoinExpressCheckout(){
        if(totalProducts<=EXPRESS_COUNTER_ITEM_LIMIT)
            return true;
        return false;
    }
}
