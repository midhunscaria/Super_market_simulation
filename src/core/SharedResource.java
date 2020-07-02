package core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;

import static core.Constants.EACH_QUEUE_MAX_SIZE;

public class SharedResource {
    public static ArrayBlockingQueue<Customer> mainCustomerQueue = new ArrayBlockingQueue<Customer>(10000);
    public static List<Checkout> listOfCheckoutqueue = new ArrayList<Checkout>(EACH_QUEUE_MAX_SIZE);

    //checkouts
    public static int CHECKOUT_COUNTER_LIMIT;
    public static int EXPRESS_COUNTER_LIMIT;
    public static int simulationTime;
    public static boolean isStart=false;


    public static double totalCustWaitTime;
    public static int totalNumOfCustomers = 0;
    public static int lostCustomer;
    public static int totalNumberofProducts;
    public static int avgProductsPerTrolly;
    public static double avgCustWaitTime;
    public static int avgCheckoutUtilisation;
}

