package core;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class CustomerLocation extends JPanel {
    final private static Border blackline = BorderFactory.createLineBorder(Color.black);
    static String path="bTrolly.gif";
    final private ImageIcon trollyGIF = new ImageIcon(getClass().getResource(path));
    final private JLabel cust = new JLabel(" ",trollyGIF,SwingConstants.CENTER);
    final private JLabel noCust = new JLabel(" empty");

    private int numberOfGoods = 0;

    public CustomerLocation()
    {

        setLayout(new BorderLayout());
        setBorder(blackline);
        setNumber(0);
    }

    public void setNumber(int inGoods)
    {
        numberOfGoods = inGoods;
        removeAll(); // Clear panel
        if (numberOfGoods > 0)
        {
            add(cust);

            JLabel products = new JLabel("#"+numberOfGoods);
            add(products,BorderLayout.EAST);
        }
        else
        {
            add(noCust);
        }
    }

    public int getNumberofGoods()
    {
        return numberOfGoods;
    }
}
