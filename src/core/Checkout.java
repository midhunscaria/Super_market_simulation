package core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import static core.SharedResource.*;

public class Checkout extends Thread {

    Customer customer;
    int counterId;
    boolean isExpressCheckout;
    BlockingQueue<Customer> checkoutqueue= new ArrayBlockingQueue<Customer>(6);
    private Thread thread;
    static boolean stopCheckout=true;

    Checkout(int id,boolean isExpressCheckout){
        this.counterId=id;
        this.isExpressCheckout=isExpressCheckout;
        thread=new Thread(this,String.valueOf(id));
    }
  @Override
    public void run(){
        try {
            while(stopCheckout) {
                customer = checkoutqueue.take();
                System.out.println("Counter " + Thread.currentThread().getName() + " processing customer " + customer.custId + "expected wait time " + customer.cWaitTime);

                 //Thread.currentThread().sleep(30);
                customer.endTime= System.currentTimeMillis();
                customer.cTotalWaitTime=customer.cWaitTime+((customer.endTime-customer.startTime)%60);
                //System.out.println("The processing time of customer"+customer.custId+"is "+customer.cTotalWaitTime);
                totalCustWaitTime=totalCustWaitTime+customer.cTotalWaitTime;
                totalNumberofProducts+=customer.totalProducts;
                Thread.currentThread().sleep((long)customer.cWaitTime*1000);

            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    public static  void stopCheckout(){
        stopCheckout=false;
    }

}
