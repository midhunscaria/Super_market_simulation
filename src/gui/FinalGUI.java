/*
 * Created by JFormDesigner on Thu Nov 22 00:32:19 GMT 2018
 */

package gui;

import static core.SharedResource.CHECKOUT_COUNTER_LIMIT;
import static core.SharedResource.EXPRESS_COUNTER_LIMIT;
import static core.SharedResource.avgCheckoutUtilisation;
import static core.SharedResource.avgCustWaitTime;
import static core.SharedResource.avgProductsPerTrolly;
import static core.SharedResource.lostCustomer;
import static core.SharedResource.totalCustWaitTime;
import static core.SharedResource.totalNumOfCustomers;
import static core.SharedResource.totalNumberofProducts;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import javax.swing.*;

import core.SharedResource;
import net.miginfocom.swing.*;

public class FinalGUI extends JFrame {
    public FinalGUI() {
        initComponents();
        avgProductsPerTrolly=totalNumberofProducts/(totalNumOfCustomers-lostCustomer);
        double custWaitTimeMINs=totalCustWaitTime/60;

        avgCustWaitTime=custWaitTimeMINs/(totalNumOfCustomers-lostCustomer);
        avgCheckoutUtilisation=totalNumberofProducts/(EXPRESS_COUNTER_LIMIT+CHECKOUT_COUNTER_LIMIT);
        simulationTimeLabel.setText(" "+SharedResource.simulationTime);
        totalCustomerslabel.setText(" "+SharedResource.totalNumOfCustomers);
        totalLostCustomerslabel.setText(" "+SharedResource.lostCustomer);
        totalProductslabel.setText(" "+SharedResource.totalNumberofProducts);
        servedCustomersLabel.setText(" "+(SharedResource.totalNumOfCustomers-SharedResource.lostCustomer));
        averageProductsPerTrolleylabel.setText(" "+avgProductsPerTrolly);
        averageCustomerWaitTimelabel.setText(" " +new BigDecimal(avgCustWaitTime).setScale(2,BigDecimal.ROUND_HALF_UP)+ " Minutes");
        averageCheckoutUtilizationlabel.setText(" "+SharedResource.avgCheckoutUtilisation);

    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.dispose();
        System.exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label2 = new JLabel();
        label1 = new JLabel();
        simulationTimeLabel = new JLabel();
        label3 = new JLabel();
        totalCustomerslabel = new JLabel();
        label5 = new JLabel();
        totalLostCustomerslabel = new JLabel();
        label6 = new JLabel();
        totalProductslabel = new JLabel();
        label7 = new JLabel();
        servedCustomersLabel = new JLabel();
        label8 = new JLabel();
        averageProductsPerTrolleylabel = new JLabel();
        label9 = new JLabel();
        averageCustomerWaitTimelabel = new JLabel();
        label4 = new JLabel();
        averageCheckoutUtilizationlabel = new JLabel();
        button1 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[232,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label2 ----
        label2.setText("Summary");
        contentPane.add(label2, "cell 1 0");

        //---- label1 ----
        label1.setText("Simulation Time ");
        contentPane.add(label1, "cell 0 1");

        //---- simulationTimeLabel ----
        simulationTimeLabel.setText("text");
        contentPane.add(simulationTimeLabel, "cell 2 1");

        //---- label3 ----
        label3.setText("Total Customers Generated");
        contentPane.add(label3, "cell 0 2");

        //---- totalCustomerslabel ----
        totalCustomerslabel.setText("text");
        contentPane.add(totalCustomerslabel, "cell 2 2");

        //---- label5 ----
        label5.setText("Total Lost Customers");
        contentPane.add(label5, "cell 0 3");

        //---- totalLostCustomerslabel ----
        totalLostCustomerslabel.setText("text");
        contentPane.add(totalLostCustomerslabel, "cell 2 3");

        //---- label6 ----
        label6.setText("Toal Products processed");
        contentPane.add(label6, "cell 0 4");

        //---- totalProductslabel ----
        totalProductslabel.setText("text");
        contentPane.add(totalProductslabel, "cell 2 4");

        //---- label7 ----
        label7.setText("Total Customers Served ");
        contentPane.add(label7, "cell 0 5");

        //---- servedCustomersLabel ----
        servedCustomersLabel.setText("text");
        contentPane.add(servedCustomersLabel, "cell 2 5");

        //---- label8 ----
        label8.setText("Average products per trolley ");
        contentPane.add(label8, "cell 0 6");

        //---- averageProductsPerTrolleylabel ----
        averageProductsPerTrolleylabel.setText("text");
        contentPane.add(averageProductsPerTrolleylabel, "cell 2 6");

        //---- label9 ----
        label9.setText("Average Customer Wait Time ");
        contentPane.add(label9, "cell 0 7");

        //---- averageCustomerWaitTimelabel ----
        averageCustomerWaitTimelabel.setText("text");
        contentPane.add(averageCustomerWaitTimelabel, "cell 2 7");

        //---- label4 ----
        label4.setText("Average Checkout Utilization");
        contentPane.add(label4, "cell 0 8");

        //---- averageCheckoutUtilizationlabel ----
        averageCheckoutUtilizationlabel.setText("text");
        contentPane.add(averageCheckoutUtilizationlabel, "cell 2 8");

        //---- button1 ----
        button1.setText("Close");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1, "cell 1 10");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label2;
    private JLabel label1;
    private JLabel simulationTimeLabel;
    private JLabel label3;
    private JLabel totalCustomerslabel;
    private JLabel label5;
    private JLabel totalLostCustomerslabel;
    private JLabel label6;
    private JLabel totalProductslabel;
    private JLabel label7;
    private JLabel servedCustomersLabel;
    private JLabel label8;
    private JLabel averageProductsPerTrolleylabel;
    private JLabel label9;
    private JLabel averageCustomerWaitTimelabel;
    private JLabel label4;
    private JLabel averageCheckoutUtilizationlabel;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
