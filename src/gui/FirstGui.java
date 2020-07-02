/*
 * Created by JFormDesigner on Wed Nov 21 18:48:44 GMT 2018
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import core.SharedResource;
import net.miginfocom.swing.*;


public class FirstGui extends JFrame {
    public FirstGui() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        SharedResource.CHECKOUT_COUNTER_LIMIT=slider1.getValue();
        SharedResource.EXPRESS_COUNTER_LIMIT=slider2.getValue();
        SharedResource.simulationTime=slider3.getValue();
        SharedResource.isStart=true;
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        slider1 = new JSlider();
        label2 = new JLabel();
        slider2 = new JSlider();
        label3 = new JLabel();
        slider3 = new JSlider();
        button1 = new JButton();

        //======== this ========
        setBackground(Color.white);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[80,fill]" +
            "[333:n,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Normal Checkouts");
        contentPane.add(label1, "cell 0 0");

        //---- slider1 ----
        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);
        slider1.setSnapToTicks(true);
        slider1.setMaximum(8);
        slider1.setMajorTickSpacing(1);
        slider1.setValue(0);
        contentPane.add(slider1, "cell 1 0");

        //---- label2 ----
        label2.setText("Express Checkouts");
        contentPane.add(label2, "cell 0 1");

        //---- slider2 ----
        slider2.setMaximum(2);
        slider2.setMajorTickSpacing(1);
        slider2.setPaintLabels(true);
        slider2.setPaintTicks(true);
        slider2.setValue(0);
        contentPane.add(slider2, "cell 1 1");

        //---- label3 ----
        label3.setText("Simulation time");
        contentPane.add(label3, "cell 0 2");

        //---- slider3 ----
        slider3.setMaximum(60);
        slider3.setMinimum(10);
        slider3.setMajorTickSpacing(10);
        slider3.setPaintLabels(true);
        slider3.setPaintTicks(true);
        slider3.setValue(0);
        contentPane.add(slider3, "cell 1 2");

        //---- button1 ----
        button1.setText("Start simulation");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1, "cell 1 3");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JSlider slider1;
    private JLabel label2;
    private JSlider slider2;
    private JLabel label3;
    private JSlider slider3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
