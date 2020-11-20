package Team31;

import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame {
    public static void main(String[] args)
    {
        Template frame = new Template();
    }

}

/*JFrame frame = new JFrame("Predictor of Covid-19");
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel1.add(new JButton("Show Graphs"));
        panel1.add(new JButton("Predict"));
        frame.add(panel1, BorderLayout.SOUTH);
        frame.setVisible(true); // This should always be last because it will make visible every button and every feature that we do
*/
