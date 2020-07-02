package core;


import gui.FinalGUI;
import gui.FirstGui;
//import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;


import static core.Checkout.stopCheckout;
import static core.Constants.EACH_QUEUE_MAX_SIZE;
import static core.SharedResource.*;
import static core.SharedResource.mainCustomerQueue;


public class SuperMarketMain {

    //Gui
    static JFrame frame;
    private JPanel queuePanel[] = new JPanel[CHECKOUT_COUNTER_LIMIT+EXPRESS_COUNTER_LIMIT];
    private CustomerLocation queue[][] = new CustomerLocation[CHECKOUT_COUNTER_LIMIT+EXPRESS_COUNTER_LIMIT][EACH_QUEUE_MAX_SIZE];
    private JPanel checkOutsPanel;
    private JPanel infoPanel;
    private JSlider slider1 = new JSlider();

    //Core

   Customer customer;
    public static Timer genCusTimer;
    public static TimerTask genCusTask;
    public static TimerTask updateTimerTask;

    static boolean shouldStopProducer=true;
    public enum Day{Normal,Busy,Busier}

    //*******Constructor mainly with GUI code
   public SuperMarketMain(){
       checkOutsPanel = new JPanel();
       checkOutsPanel.setLayout(new GridLayout(CHECKOUT_COUNTER_LIMIT+EXPRESS_COUNTER_LIMIT+1,1));
       infoPanel = new JPanel();
       checkOutsPanel.add(infoPanel);


       //Create Panel for each queue

       for (int count = 0; count < CHECKOUT_COUNTER_LIMIT+EXPRESS_COUNTER_LIMIT; count++)
       {
           //express
           String epath="checkout2.jpg";
           ImageIcon ecounterPic = new ImageIcon(getClass().getResource(epath));
           final  JLabel echeckoutMan = new JLabel(" ",ecounterPic,SwingConstants.CENTER);
           //normal
           String path="checkout1.jpg";
           ImageIcon counterPic = new ImageIcon(getClass().getResource(path));
           final  JLabel checkoutMan = new JLabel(" ",counterPic,SwingConstants.CENTER);

           queuePanel[count] = new JPanel();
           queuePanel[count].setLayout(new GridLayout(1,EACH_QUEUE_MAX_SIZE+1));

           int c=count+1;


           if(count<EXPRESS_COUNTER_LIMIT) {
               queuePanel[count].setBackground(Color.white);
               queuePanel[count].add(echeckoutMan);
               queuePanel[count].add(new JLabel(" " + c));
           }
           else {
               queuePanel[count].add(checkoutMan);
               queuePanel[count].add(new JLabel(" " + c));
           }
           for (int i = 0; i < EACH_QUEUE_MAX_SIZE; i++) // now add queue locations for each queue
           {
               queue[count][i] = new CustomerLocation();
               if(count<EXPRESS_COUNTER_LIMIT) {
                   queue[count][i].setBackground(Color.white);
               }
               queuePanel[count].add(queue[count][i]);
           }

           checkOutsPanel.add(queuePanel[count]);
       }

   }

    public static void main(String[] args) {

        FirstGui init=new FirstGui();
        init.setVisible(true);

        try {
            while (!SharedResource.isStart) {
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startSimulation();
    }


    // Will start a timer which is scheduled to run at the end of simulation time
    //Start simulation using startAll()
    static void startSimulation(){

       Timer auto= new Timer();
       //Task to cancel all other timer tasks and call final GUI
       TimerTask endTask= new TimerTask() {
           @Override
           public void run() {
               genCusTask.cancel();
               genCusTimer.cancel();
               stopCheckout();

               shouldStopProducer=false;
               updateTimerTask.cancel();

               stopUI();

               //start FinalGui
               new FinalGUI().setVisible(true);
           }
       };
       auto.schedule(endTask,SharedResource.simulationTime*1000);
       startAll();

    }

    static void startAll(){
        generateCustomers(Day.Normal);
        listOfCheckoutqueue=createCounters();

        SuperMarketMain mainObj= new SuperMarketMain();
        mainObj.createFrame();
        theConsumer();

        System.out.println("Move customers to checkout");
        theProducer();
        mainObj.updateUI();

    }



    //GUI related methods
    void createFrame()
    {
        frame = new JFrame("SuperMarket Simulator");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        Container mainPane = frame.getContentPane();
        mainPane.add(checkOutsPanel);

        //slider1
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Normal"));
        labels.put(1, new JLabel("Busy"));
        labels.put(2, new JLabel("Busier"));
        slider1.setLabelTable(labels);


        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);
        slider1.setSnapToTicks(true);
        slider1.setMaximum(2);
        slider1.setMajorTickSpacing(1);
        slider1.setValue(0);
        slider1.setPreferredSize(new Dimension(800,50));

        frame.getContentPane().add(slider1,BorderLayout.SOUTH);
        frame.setSize(1000, 750);

        slider1.addChangeListener(new ChangeListener() {
                                      public void stateChanged(ChangeEvent e) {
                                          int day=slider1.getValue();
                                          System.out.println("Slider1: " + slider1.getValue());
                                          genCusTask.cancel();
                                          genCusTimer.cancel();
                                          switch(day) {
                                             case 0:generateCustomers(Day.Normal);
                                                    break;
                                              case 1:generateCustomers(Day.Busy);
                                                    break;
                                              case 2:generateCustomers(Day.Busier);
                                          }


                                      }
                                  });
            updateCustomerView();
        frame.setVisible(true);
    }


    void updateUI()
    {

        if (SharedResource.listOfCheckoutqueue.size() == 0) {

            System.out.println("/n/nExiting........");

            System.exit(0);
        }

        Timer updateTimer= new Timer();
        updateTimerTask= new TimerTask() {
            @Override
            public void run() {
                try {
                    updateCustomerView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        updateTimer.scheduleAtFixedRate(updateTimerTask,0,500);
    }

    static void stopUI(){
        frame.setVisible(false);
    }


    public void updateCustomerView() {
        infoPanel.removeAll();
        JLabel totCustomers=new JLabel("Total Customers: "+totalNumOfCustomers);
        //totCustomers.setFont(new Font("Serif", Font.BOLD, 16) );
        infoPanel.add(totCustomers);
        infoPanel.add(new JToolBar.Separator());
        infoPanel.add(new JLabel("Lost Customers: "+lostCustomer));
        int y = 0;
        for (Checkout eachQ :SharedResource.listOfCheckoutqueue) {
            int x = 0;

            for(Customer c: eachQ.checkoutqueue){
                queue[y][x].removeAll();
                queue[y][x].setNumber(c.totalProducts);

                x++;
            }

            y++;
        }

        checkOutsPanel.revalidate();
        checkOutsPanel.repaint();
    }


    //Core methods

    static void generateCustomers(Day day){
        //generate the customers first and add it in a queue
        int max_limit=(day==Day.Normal?30:(day==Day.Busy?100:200));
        System.out.println("generateCustomer limit "+max_limit);

       genCusTimer= new Timer();
       System.out.println("genCusTimer ");
       genCusTask= new TimerTask( ) {
           @Override
           public void run() {
               System.out.println("Current thread " + Thread.currentThread().getName());
               int numOfRandomCustomers=(new GenerateRandomNumbers()).generateRandomNumbersOfRange(1,max_limit);
               System.out.println("Number of customers generated = " + numOfRandomCustomers);
               try {
                   for (int i = 1; i <= numOfRandomCustomers; i++) {
                       Customer c = new Customer(i+totalNumOfCustomers);
                       mainCustomerQueue.put(c);
                   }
                   totalNumOfCustomers=totalNumOfCustomers+numOfRandomCustomers;
                   }
               catch(Exception e){
                   e.printStackTrace();
               }
           }
       };

       genCusTimer.scheduleAtFixedRate(genCusTask,0,5000);
   }

    private static List<Checkout> createCounters() {
        List<Checkout> counters= new ArrayList<>();
        for(int i=0;i<CHECKOUT_COUNTER_LIMIT+EXPRESS_COUNTER_LIMIT;i++){
            if(i<EXPRESS_COUNTER_LIMIT){
                counters.add(new Checkout(i,true));
                System.out.println("Created "+counters.size()+" express counter to serve customers");

            }
            else
            counters.add(new Checkout(i,false));
        }
        System.out.println("Created "+counters.size()+" Counters to serve customers");
        return counters;
    }


   /*
   Producer puts customers into lowest possible checkouts taking from mainqueue
    */
   static void theProducer(){

       Thread pThread= new Thread (new Runnable(){
           @Override
           public void run(){

               Checkout smallestECheckout ;
               Checkout smallestNCheckout;
               boolean noNeedExpressCheckout=false;
               while(shouldStopProducer) {
                   try {
                       Customer customer = mainCustomerQueue.take();
                       //If customer can join express checkout - find smallest queue and add it else send to normal checkout.
                       if(customer.isJoinExpressCheckout()) {
                           smallestECheckout = getSmallestCheckout(true);
                           if(smallestECheckout.checkoutqueue.remainingCapacity()>0) {

                               //found smallest queue if express and if customer is express add,else

                               smallestECheckout.checkoutqueue.put(customer);
                               customer.startTime= System.currentTimeMillis();
                               System.out.println("Added a customer " + customer.custId + "  to  express " + smallestECheckout.getName());
                           }
                           else{
//                               smallestNCheckout=getSmallestCheckout(false);
//                               System.out.println("Added a customer to "+customer.custId+" normal ");
                               noNeedExpressCheckout=true;
                           }

                       }
                       //if customer doesn't need express checkout -check in normal checkout or lost customer
                       if((customer.isJoinExpressCheckout() && noNeedExpressCheckout) || !customer.isJoinExpressCheckout()  ) {
                           smallestNCheckout = getSmallestCheckout(false);
                           if (smallestNCheckout.checkoutqueue.remainingCapacity() > 0) {

                               //found smallest queue if express and if customer is express add,else

                               smallestNCheckout.checkoutqueue.put(customer);
                               customer.startTime= System.currentTimeMillis();
                               System.out.println("Added a customer " + customer.custId + "  to " + smallestNCheckout.getName());
                           } else {
                               lostCustomer = lostCustomer + 1;
                               System.out.println("customer lost" + customer.custId);
                           }
                       }

                   }
                   catch(InterruptedException e){
                       e.printStackTrace();
                   }
               }

           }


       });

       pThread.start();
   }


    /*
   Consumer is each checkout counter open and ready to serve

    */
   static void theConsumer(){

       //start and open all counters

       for(int i=0;i<listOfCheckoutqueue.size();i++){

           Checkout eachCheckOut=listOfCheckoutqueue.get(i);
           eachCheckOut.start();
           System.out.println("Started  "+eachCheckOut.getName());
       }


   }

   static Checkout getSmallestCheckout(boolean inExpress) {
       Checkout small;
       if (SharedResource.listOfCheckoutqueue.size() == 0) {

           System.out.println("/n/nExiting........");

           System.exit(0);
       }
       if (inExpress) {
           //smallest express checkout
           small = listOfCheckoutqueue.get(0);
           for (int i = 0; i < EXPRESS_COUNTER_LIMIT; i++) {
               Checkout each = listOfCheckoutqueue.get(i);
               if (each.checkoutqueue.size() < small.checkoutqueue.size())
                   small = each;
           }
           return small;

       } else {
           //smallest normal checkout
           small = listOfCheckoutqueue.get(EXPRESS_COUNTER_LIMIT);
           for (int i = EXPRESS_COUNTER_LIMIT; i < listOfCheckoutqueue.size(); i++) {
               Checkout each = listOfCheckoutqueue.get(i);
               if (each.checkoutqueue.size() < small.checkoutqueue.size())
                   small = each;
           }

           System.out.println("Found the smallest checkout " + small.getName());
           return small;
       }
   }
}



